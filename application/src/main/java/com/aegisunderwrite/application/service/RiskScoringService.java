package com.aegisunderwrite.application.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aegisunderwrite.application.entity.Applicant;
import com.aegisunderwrite.application.entity.Application;
import com.aegisunderwrite.application.entity.RiskScoreFactor;
import com.aegisunderwrite.application.entity.Vehicle;
import com.aegisunderwrite.application.enums.RiskBand;
import com.aegisunderwrite.application.repository.RiskScoreFactorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RiskScoringService {
	private final RiskScoreFactorRepository riskScoreFactorRepository;

	private static final Map<String, BigDecimal> FACTOR_WEIGHTS = Map.of("CREDIT_SCORE", new BigDecimal("0.30"),
			"LICENSE_YEARS", new BigDecimal("0.20"), "DRIVER_AGE", new BigDecimal("0.20"), "VEHICLE_VALUE",
			new BigDecimal("0.15"), "VEHICLE_AGE", new BigDecimal("0.15"));

	public int calculateRiskScore(Application application) {
		Applicant applicant = application.getApplicant();
		Vehicle vehicle = application.getVehicle();

		List<RiskScoreFactor> factors = new ArrayList<>();

		// Credit score factor (higher is better)
		factors.add(createFactor(application, "CREDIT_SCORE", new BigDecimal(applicant.getCreditScore()),
				normalizeCreditScore(applicant.getCreditScore())));

		// License years factor (more experience is better)
		factors.add(createFactor(application, "LICENSE_YEARS", new BigDecimal(applicant.getLicenseYears()),
				normalizeLicenseYears(applicant.getLicenseYears())));

		// Driver age factor (middle age is best)
		int age = calculateAge(applicant.getDateOfBirth());
		factors.add(createFactor(application, "DRIVER_AGE", new BigDecimal(age), normalizeAge(age)));

		// Vehicle value factor (lower is better for risk)
		factors.add(createFactor(application, "VEHICLE_VALUE", vehicle.getVehicleValue(),
				normalizeVehicleValue(vehicle.getVehicleValue())));

		// Vehicle age factor (newer cars have better safety)
		int vehicleAge = Year.now().getValue() - vehicle.getManufactureYear();
		factors.add(
				createFactor(application, "VEHICLE_AGE", new BigDecimal(vehicleAge), normalizeVehicleAge(vehicleAge)));

		riskScoreFactorRepository.saveAll(factors);

		// Total score: sum of contributions, scaled to 0-1000
		BigDecimal totalContribution = factors.stream().map(RiskScoreFactor::getContribution).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		return totalContribution.multiply(new BigDecimal("1000")).setScale(0, RoundingMode.HALF_UP).intValue();
	}

	public RiskBand determineRiskBand(int score) {
		if (score >= 700)
			return RiskBand.LOW;
		if (score >= 400)
			return RiskBand.MEDIUM;
		return RiskBand.HIGH;
	}

	private RiskScoreFactor createFactor(Application app, String name, BigDecimal rawValue,
			BigDecimal normalizedValue) {
		RiskScoreFactor factor = new RiskScoreFactor();
		factor.setApplication(app);
		factor.setFactorName(name);
		factor.setFactorValue(rawValue);
		factor.setWeight(FACTOR_WEIGHTS.get(name));
		factor.setContribution(normalizedValue.multiply(FACTOR_WEIGHTS.get(name)));
		return factor;
	}

	private BigDecimal normalizeCreditScore(int score) {
		// 300-850 -> 0-1
		return new BigDecimal(score - 300).divide(new BigDecimal("550"), 4, RoundingMode.HALF_UP);
	}

	private BigDecimal normalizeLicenseYears(int years) {
		// 0-10+ years -> 0-1
		return BigDecimal.valueOf(Math.min(years, 10)).divide(new BigDecimal("10"), 4, RoundingMode.HALF_UP);
	}

	private BigDecimal normalizeAge(int age) {
		// 25-65 optimal range
		if (age < 25)
			return new BigDecimal("0.5");
		if (age > 65)
			return new BigDecimal("0.6");
		return new BigDecimal("1.0");
	}

	private BigDecimal normalizeVehicleValue(BigDecimal value) {
		// Lower value = lower risk, cap at 100k
		BigDecimal capped = value.min(new BigDecimal("100000"));
		return BigDecimal.ONE.subtract(capped.divide(new BigDecimal("100000"), 4, RoundingMode.HALF_UP));
	}

	private BigDecimal normalizeVehicleAge(int age) {
		// 0-5 years = good, 5+ degrades
		if (age <= 5)
			return new BigDecimal("1.0");
		return BigDecimal.valueOf(Math.max(0.5, 1.0 - (age - 5) * 0.1));
	}

	private int calculateAge(LocalDate dob) {
		return Period.between(dob, LocalDate.now()).getYears();
	}
}