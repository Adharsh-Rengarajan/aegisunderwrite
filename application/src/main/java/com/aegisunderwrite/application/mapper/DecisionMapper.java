package com.aegisunderwrite.application.mapper;

import org.springframework.stereotype.Component;

import com.aegisunderwrite.application.dto.DecisionResponse;
import com.aegisunderwrite.application.entity.Decision;
import com.aegisunderwrite.application.entity.DecisionExplanation;

@Component
public class DecisionMapper {

	public DecisionResponse toResponse(Decision decision) {
		DecisionResponse response = new DecisionResponse();
		response.setApplicationId(decision.getApplication().getId());
		response.setDecision(decision.getDecision());
		response.setRiskScore(decision.getRiskScore());
		response.setRiskBand(decision.getRiskBand());
		response.setDecidedAt(decision.getDecidedAt());
		response.setExplanations(decision.getExplanations().stream().map(DecisionExplanation::getExplanation).toList());
		return response;
	}
}