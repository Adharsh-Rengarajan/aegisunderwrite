package com.aegisunderwrite.application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aegisunderwrite.application.entity.RuleEvaluation;
import com.aegisunderwrite.application.enums.EvaluationResult;

@Repository
public interface RuleEvaluationRepository extends JpaRepository<RuleEvaluation, UUID> {
	List<RuleEvaluation> findByApplicationId(UUID applicationId);

	List<RuleEvaluation> findByApplicationIdAndResult(UUID applicationId, EvaluationResult result);

	boolean existsByApplicationIdAndRuleId(UUID applicationId, UUID ruleId);
}