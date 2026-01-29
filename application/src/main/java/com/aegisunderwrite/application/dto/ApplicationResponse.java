package com.aegisunderwrite.application.dto;

import java.time.Instant;
import java.util.UUID;

import com.aegisunderwrite.application.enums.ApplicationStatus;
import com.aegisunderwrite.application.enums.PolicyType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {
	private UUID id;
	private ApplicantResponse applicant;
	private VehicleResponse vehicle;
	private String state;
	private PolicyType policyType;
	private ApplicationStatus status;
	private Instant submittedAt;
	private DecisionResponse decision;
}
