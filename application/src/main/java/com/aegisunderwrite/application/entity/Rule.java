package com.aegisunderwrite.application.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.aegisunderwrite.application.enums.RuleAction;
import com.aegisunderwrite.application.enums.RuleType;

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
@Table(name = "rules")
public class Rule {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "rule_name", nullable = false, unique = true, length = 100)
	private String ruleName;

	@Enumerated(EnumType.STRING)
	@Column(name = "rule_type", nullable = false)
	private RuleType ruleType;

	@Column(nullable = false)
	private Integer priority;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String condition;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RuleAction action;

	@Column(nullable = false)
	private Boolean active = true;

	@OneToMany(mappedBy = "rule")
	private List<RuleEvaluation> ruleEvaluations = new ArrayList<>();

	// Getters and setters
}