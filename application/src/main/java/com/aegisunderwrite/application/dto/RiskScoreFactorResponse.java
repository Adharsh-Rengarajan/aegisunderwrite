package com.aegisunderwrite.application.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskScoreFactorResponse {
	private String factorName;
	private BigDecimal factorValue;
	private BigDecimal weight;
	private BigDecimal contribution;
}