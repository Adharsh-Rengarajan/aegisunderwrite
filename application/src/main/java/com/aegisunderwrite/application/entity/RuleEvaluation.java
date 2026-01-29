package com.aegisunderwrite.application.entity;

import java.time.Instant;
import java.util.UUID;

import com.aegisunderwrite.application.enums.EvaluationResult;

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
@Table(name = "rule_evaluations")
public class RuleEvaluation {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_id", nullable = false)
	private Application application;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rule_id", nullable = false)
	private Rule rule;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EvaluationResult result;

	@Column(columnDefinition = "TEXT")
	private String reason;

	@Column(name = "evaluated_at", nullable = false)
	private Instant evaluatedAt = Instant.now();

	// Getters and setters
}