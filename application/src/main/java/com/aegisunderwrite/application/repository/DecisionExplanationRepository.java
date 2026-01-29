package com.aegisunderwrite.application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aegisunderwrite.application.entity.DecisionExplanation;

@Repository
public interface DecisionExplanationRepository extends JpaRepository<DecisionExplanation, UUID> {
	List<DecisionExplanation> findByDecisionIdOrderByDisplayOrderAsc(UUID decisionId);
}