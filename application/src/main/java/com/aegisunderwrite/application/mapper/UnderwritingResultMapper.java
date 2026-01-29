package com.aegisunderwrite.application.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.aegisunderwrite.application.dto.UnderwritingResultResponse;
import com.aegisunderwrite.application.entity.Application;
import com.aegisunderwrite.application.entity.Decision;
import com.aegisunderwrite.application.entity.DecisionExplanation;
import com.aegisunderwrite.application.entity.RiskScoreFactor;
import com.aegisunderwrite.application.entity.RuleEvaluation;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UnderwritingResultMapper {
	private final RiskScoreFactorMapper riskScoreFactorMapper;
	private final RuleEvaluationMapper ruleEvaluationMapper;

	public UnderwritingResultResponse toResponse(Application application, Decision decision,
			List<RiskScoreFactor> factors, List<RuleEvaluation> evaluations) {

		UnderwritingResultResponse response = new UnderwritingResultResponse();
		response.setApplicationId(application.getId());
		response.setDecision(decision.getDecision());
		response.setRiskScore(decision.getRiskScore());
		response.setRiskBand(decision.getRiskBand());
		response.setExplanations(decision.getExplanations().stream().map(DecisionExplanation::getExplanation).toList());
		response.setRiskFactors(riskScoreFactorMapper.toResponseList(factors));
		response.setRuleEvaluations(ruleEvaluationMapper.toResponseList(evaluations));
		return response;
	}
}