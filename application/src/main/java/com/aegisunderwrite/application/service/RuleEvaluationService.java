package com.aegisunderwrite.application.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aegisunderwrite.application.entity.Applicant;
import com.aegisunderwrite.application.entity.Application;
import com.aegisunderwrite.application.entity.Rule;
import com.aegisunderwrite.application.entity.RuleEvaluation;
import com.aegisunderwrite.application.entity.Vehicle;
import com.aegisunderwrite.application.enums.DecisionOutcome;
import com.aegisunderwrite.application.enums.EvaluationResult;
import com.aegisunderwrite.application.enums.RuleType;
import com.aegisunderwrite.application.repository.RuleEvaluationRepository;
import com.aegisunderwrite.application.repository.RuleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RuleEvaluationService {
	private final RuleRepository ruleRepository;
	private final RuleEvaluationRepository ruleEvaluationRepository;

	public List<RuleEvaluation> evaluateRules(Application application, int riskScore) {
		List<Rule> activeRules = ruleRepository.findByActiveTrueOrderByPriorityAsc();
		List<RuleEvaluation> evaluations = new ArrayList<>();

		Applicant applicant = application.getApplicant();
		Vehicle vehicle = application.getVehicle();
		int driverAge = Period.between(applicant.getDateOfBirth(), LocalDate.now()).getYears();

		for (Rule rule : activeRules) {
			RuleEvaluation evaluation = new RuleEvaluation();
			evaluation.setApplication(application);
			evaluation.setRule(rule);
			evaluation.setEvaluatedAt(Instant.now());

			EvaluationResult result = evaluateRule(rule, applicant, vehicle, driverAge, riskScore);
			evaluation.setResult(result);

			if (result == EvaluationResult.FAILED) {
				evaluation.setReason(generateReason(rule, applicant, vehicle, driverAge));
			}

			evaluations.add(evaluation);
		}

		return ruleEvaluationRepository.saveAll(evaluations);
	}

	private EvaluationResult evaluateRule(Rule rule, Applicant applicant, Vehicle vehicle, int driverAge,
			int riskScore) {
		return switch (rule.getRuleName()) {
		case "MIN_DRIVER_AGE" -> driverAge >= 18 ? EvaluationResult.PASSED : EvaluationResult.FAILED;
		case "MIN_LICENSE_YEARS" ->
			applicant.getLicenseYears() >= 1 ? EvaluationResult.PASSED : EvaluationResult.FAILED;
		case "MIN_CREDIT_SCORE" ->
			applicant.getCreditScore() >= 500 ? EvaluationResult.PASSED : EvaluationResult.FAILED;
		case "MAX_VEHICLE_VALUE" ->
			vehicle.getVehicleValue().compareTo(new BigDecimal("150000")) <= 0 ? EvaluationResult.PASSED
					: EvaluationResult.FAILED;
		case "MAX_VEHICLE_AGE" -> (Year.now().getValue() - vehicle.getManufactureYear()) <= 20 ? EvaluationResult.PASSED
				: EvaluationResult.FAILED;
		case "HIGH_RISK_SCORE" -> riskScore >= 300 ? EvaluationResult.PASSED : EvaluationResult.FAILED;
		default -> EvaluationResult.SKIPPED;
		};
	}

	private String generateReason(Rule rule, Applicant applicant, Vehicle vehicle, int driverAge) {
		return switch (rule.getRuleName()) {
		case "MIN_DRIVER_AGE" -> "Driver age (" + driverAge + ") below minimum requirement of 18";
		case "MIN_LICENSE_YEARS" ->
			"License history (" + applicant.getLicenseYears() + " years) below minimum requirement";
		case "MIN_CREDIT_SCORE" -> "Credit score (" + applicant.getCreditScore() + ") below minimum threshold";
		case "MAX_VEHICLE_VALUE" -> "Vehicle value ($" + vehicle.getVehicleValue() + ") exceeds maximum coverage limit";
		case "MAX_VEHICLE_AGE" -> "Vehicle age exceeds maximum insurable age";
		case "HIGH_RISK_SCORE" -> "Application flagged as high risk";
		default -> "Rule evaluation failed";
		};
	}

	public DecisionOutcome determineOutcome(List<RuleEvaluation> evaluations) {
		boolean hasHardFail = evaluations.stream()
				.anyMatch(e -> e.getResult() == EvaluationResult.FAILED && e.getRule().getRuleType() == RuleType.HARD);

		boolean hasSoftFail = evaluations.stream()
				.anyMatch(e -> e.getResult() == EvaluationResult.FAILED && e.getRule().getRuleType() == RuleType.SOFT);

		if (hasHardFail)
			return DecisionOutcome.DECLINE;
		if (hasSoftFail)
			return DecisionOutcome.REFER;
		return DecisionOutcome.APPROVE;
	}
}
