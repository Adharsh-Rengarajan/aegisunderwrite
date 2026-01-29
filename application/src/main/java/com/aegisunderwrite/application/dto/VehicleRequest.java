package com.aegisunderwrite.application.dto;

import java.math.BigDecimal;

import com.aegisunderwrite.application.enums.VehicleUsage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {
	@NotBlank
	private String make;

	@NotBlank
	private String model;

	@NotNull
	@Min(1900)
	private Integer manufactureYear;

	@NotNull
	@Positive
	private BigDecimal vehicleValue;

	@NotNull
	private VehicleUsage usageType;
}