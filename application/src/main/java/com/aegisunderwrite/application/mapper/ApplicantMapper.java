package com.aegisunderwrite.application.mapper;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.aegisunderwrite.application.dto.ApplicantRequest;
import com.aegisunderwrite.application.dto.ApplicantResponse;
import com.aegisunderwrite.application.entity.Applicant;

@Component
public class ApplicantMapper {

	public Applicant toEntity(ApplicantRequest request) {
		Applicant applicant = new Applicant();
		applicant.setFirstName(request.getFirstName());
		applicant.setLastName(request.getLastName());
		applicant.setDateOfBirth(request.getDateOfBirth());
		applicant.setCreditScore(request.getCreditScore());
		applicant.setLicenseYears(request.getLicenseYears());
		applicant.setCreatedAt(Instant.now());
		return applicant;
	}

	public ApplicantResponse toResponse(Applicant applicant) {
		ApplicantResponse response = new ApplicantResponse();
		response.setId(applicant.getId());
		response.setFirstName(applicant.getFirstName());
		response.setLastName(applicant.getLastName());
		response.setDateOfBirth(applicant.getDateOfBirth());
		response.setCreditScore(applicant.getCreditScore());
		response.setLicenseYears(applicant.getLicenseYears());
		return response;
	}
}