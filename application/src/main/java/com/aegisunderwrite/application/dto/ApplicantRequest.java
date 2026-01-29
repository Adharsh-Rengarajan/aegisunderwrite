package com.aegisunderwrite.application.dto;

import java.time.LocalDate;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantRequest {
	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@SuppressWarnings("deprecation")
	@NotNull
	@Past
	private LocalDate dateOfBirth;

	@SuppressWarnings("deprecation")
	@NotNull
	@Min(300)
	@Max(850)
	private Integer creditScore;

	@SuppressWarnings("deprecation")
	@NotNull
	@Min(0)
	private Integer licenseYears;
}
