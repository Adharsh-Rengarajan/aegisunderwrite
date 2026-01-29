package com.aegisunderwrite.application.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.aegisunderwrite.application.dto.RuleEvaluationResponse;
import com.aegisunderwrite.application.entity.RuleEvaluation;

@Component
public class RuleEvaluationMapper {

	public RuleEvaluationResponse toResponse(RuleEvaluation evaluation) {
		RuleEvaluationResponse response = new RuleEvaluationResponse();
		response.setRuleName(evaluation.getRule().getRuleName());
		response.setRuleType(evaluation.getRule().getRuleType());
		response.setResult(evaluation.getResult());
		response.setReason(evaluation.getReason());
		return response;
	}

	public List<RuleEvaluationResponse> toResponseList(List<RuleEvaluation> evaluations) {
		return evaluations.stream().map(this::toResponse).toList();
	}
}