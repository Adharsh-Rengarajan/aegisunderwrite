package com.aegisunderwrite.application.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.aegisunderwrite.application.enums.ApplicationStatus;
import com.aegisunderwrite.application.enums.PolicyType;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "applications")
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_id", nullable = false)
	private Applicant applicant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_id", nullable = false)
	private Vehicle vehicle;

	@Column(nullable = false, length = 2)
	private String state;

	@Enumerated(EnumType.STRING)
	@Column(name = "policy_type", nullable = false)
	private PolicyType policyType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ApplicationStatus status = ApplicationStatus.PENDING;

	@Column(name = "submitted_at", nullable = false)
	private Instant submittedAt = Instant.now();

	@OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
	private Decision decision;

	@OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
	private List<RuleEvaluation> ruleEvaluations = new ArrayList<>();

	@OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
	private List<RiskScoreFactor> riskScoreFactors = new ArrayList<>();

	// Getters and setters
}
