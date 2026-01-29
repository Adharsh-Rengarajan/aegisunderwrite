package com.aegisunderwrite.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.aegisunderwrite.application.enums.VehicleUsage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {
	private UUID id;
	private String make;
	private String model;
	private Integer manufactureYear;
	private BigDecimal vehicleValue;
	private VehicleUsage usageType;
}
