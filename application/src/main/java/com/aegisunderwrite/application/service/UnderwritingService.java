package com.aegisunderwrite.application.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aegisunderwrite.application.dto.ApplicationRequest;
import com.aegisunderwrite.application.entity.Applicant;
import com.aegisunderwrite.application.entity.Application;
import com.aegisunderwrite.application.entity.Decision;
import com.aegisunderwrite.application.entity.DecisionExplanation;
import com.aegisunderwrite.application.entity.RuleEvaluation;
import com.aegisunderwrite.application.entity.Vehicle;
import com.aegisunderwrite.application.enums.ApplicationStatus;
import com.aegisunderwrite.application.enums.DecisionOutcome;
import com.aegisunderwrite.application.enums.EvaluationResult;
import com.aegisunderwrite.application.enums.RiskBand;
import com.aegisunderwrite.application.mapper.ApplicantMapper;
import com.aegisunderwrite.application.mapper.VehicleMapper;
import com.aegisunderwrite.application.repository.ApplicantRepository;
import com.aegisunderwrite.application.repository.ApplicationRepository;
import com.aegisunderwrite.application.repository.DecisionExplanationRepository;
import com.aegisunderwrite.application.repository.DecisionRepository;
import com.aegisunderwrite.application.repository.VehicleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UnderwritingService {

	private final ApplicationRepository applicationRepository;
	private final ApplicantRepository applicantRepository;
	private final VehicleRepository vehicleRepository;
	private final DecisionRepository decisionRepository;
	private final DecisionExplanationRepository decisionExplanationRepository;
	private final RiskScoringService riskScoringService;
	private final RuleEvaluationService ruleEvaluationService;
	private final ApplicantMapper applicantMapper;
	private final VehicleMapper vehicleMapper;

	public Application submitApplication(ApplicationRequest request) {
		Applicant applicant = applicantMapper.toEntity(request.getApplicant());
		applicant = applicantRepository.save(applicant);

		Vehicle vehicle = vehicleMapper.toEntity(request.getVehicle());
		vehicle = vehicleRepository.save(vehicle);

		Application application = new Application();
		application.setApplicant(applicant);
		application.setVehicle(vehicle);
		application.setState(request.getState().toUpperCase());
		application.setPolicyType(request.getPolicyType());
		application.setStatus(ApplicationStatus.PENDING);
		application.setSubmittedAt(Instant.now());

		return applicationRepository.save(application);
	}

	public Decision processApplication(UUID applicationId) {
		Application application = applicationRepository.findByIdWithDetails(applicationId)
				.orElseThrow(() -> new EntityNotFoundException("Application not found: " + applicationId));

		application.setStatus(ApplicationStatus.PROCESSING);
		applicationRepository.save(application);

		// Step 1: Calculate risk score
		int riskScore = riskScoringService.calculateRiskScore(application);
		RiskBand riskBand = riskScoringService.determineRiskBand(riskScore);

		// Step 2: Evaluate rules
		List<RuleEvaluation> evaluations = ruleEvaluationService.evaluateRules(application, riskScore);

		// Step 3: Determine outcome
		DecisionOutcome outcome = ruleEvaluationService.determineOutcome(evaluations);

		// Step 4: Create decision
		Decision decision = new Decision();
		decision.setApplication(application);
		decision.setDecision(outcome);
		decision.setRiskScore(riskScore);
		decision.setRiskBand(riskBand);
		decision.setDecidedAt(Instant.now());
		decision = decisionRepository.save(decision);

		// Step 5: Add explanations
		List<RuleEvaluation> failedEvaluations = evaluations.stream()
				.filter(e -> e.getResult() == EvaluationResult.FAILED).toList();

		int order = 1;
		for (RuleEvaluation eval : failedEvaluations) {
			DecisionExplanation explanation = new DecisionExplanation();
			explanation.setDecision(decision);
			explanation.setDisplayOrder(order++);
			explanation.setExplanation(eval.getReason());
			decisionExplanationRepository.save(explanation);
		}

		// Step 6: Update application status
		application.setStatus(ApplicationStatus.COMPLETED);
		application.setDecision(decision);
		applicationRepository.save(application);

		return decision;
	}

	public Application getApplication(UUID applicationId) {
		return applicationRepository.findByIdWithDecision(applicationId)
				.orElseThrow(() -> new EntityNotFoundException("Application not found: " + applicationId));
	}

	public Decision getDecision(UUID applicationId) {
		return decisionRepository.findByApplicationIdWithExplanations(applicationId)
				.orElseThrow(() -> new EntityNotFoundException("Decision not found for application: " + applicationId));
	}
}