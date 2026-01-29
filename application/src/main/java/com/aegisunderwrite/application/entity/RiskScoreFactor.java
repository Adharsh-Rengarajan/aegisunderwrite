package com.aegisunderwrite.application.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "risk_score_factors")
public class RiskScoreFactor {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_id", nullable = false)
	private Application application;

	@Column(name = "factor_name", nullable = false, length = 100)
	private String factorName;

	@Column(name = "factor_value", nullable = false, precision = 10, scale = 4)
	private BigDecimal factorValue;

	@Column(nullable = false, precision = 5, scale = 4)
	private BigDecimal weight;

	@Column(nullable = false, precision = 10, scale = 4)
	private BigDecimal contribution;

	// Getters and setters
}