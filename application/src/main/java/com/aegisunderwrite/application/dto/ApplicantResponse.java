package com.aegisunderwrite.application.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantResponse {
	private UUID id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private Integer creditScore;
	private Integer licenseYears;
}