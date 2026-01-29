package com.aegisunderwrite.application.dto;

import com.aegisunderwrite.application.enums.PolicyType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {
	@NotNull
	@Valid
	private ApplicantRequest applicant;

	@NotNull
	@Valid
	private VehicleRequest vehicle;

	@NotBlank
	@Size(min = 2, max = 2)
	private String state;

	@NotNull
	private PolicyType policyType;
}