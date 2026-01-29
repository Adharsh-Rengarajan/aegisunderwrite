package com.aegisunderwrite.application.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.aegisunderwrite.application.enums.VehicleUsage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 50)
	private String make;

	@Column(nullable = false, length = 50)
	private String model;

	@Column(name = "manufacture_year", nullable = false)
	private Integer manufactureYear;

	@Column(name = "vehicle_value", nullable = false, precision = 12, scale = 2)
	private BigDecimal vehicleValue;

	@Enumerated(EnumType.STRING)
	@Column(name = "usage_type", nullable = false)
	private VehicleUsage usageType;

	@OneToMany(mappedBy = "vehicle")
	private List<Application> applications = new ArrayList<>();

}