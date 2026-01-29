package com.aegisunderwrite.application.dto;

import java.util.List;
import java.util.UUID;

import com.aegisunderwrite.application.enums.DecisionOutcome;
import com.aegisunderwrite.application.enums.RiskBand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingResultResponse {
	private UUID applicationId;
	private DecisionOutcome decision;
	private Integer riskScore;
	private RiskBand riskBand;
	private List<String> explanations;
	private List<RiskScoreFactorResponse> riskFactors;
	private List<RuleEvaluationResponse> ruleEvaluations;
}
