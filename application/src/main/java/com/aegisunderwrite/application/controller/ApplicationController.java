package com.aegisunderwrite.application.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aegisunderwrite.application.dto.ApplicationRequest;
import com.aegisunderwrite.application.dto.ApplicationResponse;
import com.aegisunderwrite.application.dto.DecisionResponse;
import com.aegisunderwrite.application.dto.UnderwritingResultResponse;
import com.aegisunderwrite.application.entity.Application;
import com.aegisunderwrite.application.entity.Decision;
import com.aegisunderwrite.application.entity.RiskScoreFactor;
import com.aegisunderwrite.application.entity.RuleEvaluation;
import com.aegisunderwrite.application.mapper.ApplicationMapper;
import com.aegisunderwrite.application.mapper.DecisionMapper;
import com.aegisunderwrite.application.mapper.UnderwritingResultMapper;
import com.aegisunderwrite.application.repository.RiskScoreFactorRepository;
import com.aegisunderwrite.application.repository.RuleEvaluationRepository;
import com.aegisunderwrite.application.service.UnderwritingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

	private final UnderwritingService underwritingService;
	private final ApplicationMapper applicationMapper;
	private final DecisionMapper decisionMapper;
	private final UnderwritingResultMapper underwritingResultMapper;
	private final RiskScoreFactorRepository riskScoreFactorRepository;
	private final RuleEvaluationRepository ruleEvaluationRepository;

	@PostMapping
	public ResponseEntity<ApplicationResponse> submitApplication(@Valid @RequestBody ApplicationRequest request) {
		Application application = underwritingService.submitApplication(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(applicationMapper.toResponse(application));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApplicationResponse> getApplication(@PathVariable UUID id) {
		Application application = underwritingService.getApplication(id);
		return ResponseEntity.ok(applicationMapper.toResponse(application));
	}

	@PostMapping("/{id}/process")
	public ResponseEntity<DecisionResponse> processApplication(@PathVariable UUID id) {
		Decision decision = underwritingService.processApplication(id);
		return ResponseEntity.ok(decisionMapper.toResponse(decision));
	}

	@GetMapping("/{id}/decision")
	public ResponseEntity<DecisionResponse> getDecision(@PathVariable UUID id) {
		Decision decision = underwritingService.getDecision(id);
		return ResponseEntity.ok(decisionMapper.toResponse(decision));
	}

	@GetMapping("/{id}/details")
	public ResponseEntity<UnderwritingResultResponse> getUnderwritingDetails(@PathVariable UUID id) {
		Application application = underwritingService.getApplication(id);
		Decision decision = application.getDecision();

		if (decision == null) {
			return ResponseEntity.notFound().build();
		}

		List<RiskScoreFactor> factors = riskScoreFactorRepository.findByApplicationId(id);
		List<RuleEvaluation> evaluations = ruleEvaluationRepository.findByApplicationId(id);

		return ResponseEntity.ok(underwritingResultMapper.toResponse(application, decision, factors, evaluations));
	}
}