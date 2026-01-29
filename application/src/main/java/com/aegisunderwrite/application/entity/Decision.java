package com.aegisunderwrite.application.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.aegisunderwrite.application.enums.DecisionOutcome;
import com.aegisunderwrite.application.enums.RiskBand;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
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
@Table(name = "decisions")
public class Decision {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_id", nullable = false, unique = true)
	private Application application;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DecisionOutcome decision;

	@Column(name = "risk_score", nullable = false)
	private Integer riskScore;

	@Enumerated(EnumType.STRING)
	@Column(name = "risk_band", nullable = false)
	private RiskBand riskBand;

	@Column(name = "decided_at", nullable = false)
	private Instant decidedAt = Instant.now();

	@OneToMany(mappedBy = "decision", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("displayOrder")
	private List<DecisionExplanation> explanations = new ArrayList<>();

	// Getters and setters
}