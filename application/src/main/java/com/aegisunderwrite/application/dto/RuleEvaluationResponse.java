package com.aegisunderwrite.application.dto;

import com.aegisunderwrite.application.enums.EvaluationResult;
import com.aegisunderwrite.application.enums.RuleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleEvaluationResponse {
	private String ruleName;
	private RuleType ruleType;
	private EvaluationResult result;
	private String reason;
}
