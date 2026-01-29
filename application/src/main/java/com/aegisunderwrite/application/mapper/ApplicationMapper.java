package com.aegisunderwrite.application.mapper;

import org.springframework.stereotype.Component;

import com.aegisunderwrite.application.dto.ApplicationResponse;
import com.aegisunderwrite.application.entity.Application;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationMapper {
	private final ApplicantMapper applicantMapper;
	private final VehicleMapper vehicleMapper;
	private final DecisionMapper decisionMapper;

	public ApplicationResponse toResponse(Application application) {
		ApplicationResponse response = new ApplicationResponse();
		response.setId(application.getId());
		response.setApplicant(applicantMapper.toResponse(application.getApplicant()));
		response.setVehicle(vehicleMapper.toResponse(application.getVehicle()));
		response.setState(application.getState());
		response.setPolicyType(application.getPolicyType());
		response.setStatus(application.getStatus());
		response.setSubmittedAt(application.getSubmittedAt());
		if (application.getDecision() != null) {
			response.setDecision(decisionMapper.toResponse(application.getDecision()));
		}
		return response;
	}
}
