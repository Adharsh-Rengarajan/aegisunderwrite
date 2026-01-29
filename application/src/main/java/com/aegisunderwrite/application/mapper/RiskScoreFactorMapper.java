package com.aegisunderwrite.application.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.aegisunderwrite.application.dto.RiskScoreFactorResponse;
import com.aegisunderwrite.application.entity.RiskScoreFactor;

@Component
public class RiskScoreFactorMapper {

	public RiskScoreFactorResponse toResponse(RiskScoreFactor factor) {
		RiskScoreFactorResponse response = new RiskScoreFactorResponse();
		response.setFactorName(factor.getFactorName());
		response.setFactorValue(factor.getFactorValue());
		response.setWeight(factor.getWeight());
		response.setContribution(factor.getContribution());
		return response;
	}

	public List<RiskScoreFactorResponse> toResponseList(List<RiskScoreFactor> factors) {
		return factors.stream().map(this::toResponse).toList();
	}
}