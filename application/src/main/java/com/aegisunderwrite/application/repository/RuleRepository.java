package com.aegisunderwrite.application.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aegisunderwrite.application.entity.Rule;
import com.aegisunderwrite.application.enums.RuleType;

@Repository
public interface RuleRepository extends JpaRepository<Rule, UUID> {
	List<Rule> findByActiveTrueOrderByPriorityAsc();

	Optional<Rule> findByRuleName(String ruleName);

	List<Rule> findByRuleTypeAndActiveTrue(RuleType ruleType);
}