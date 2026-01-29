package com.aegisunderwrite.application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aegisunderwrite.application.entity.RiskScoreFactor;

@Repository
public interface RiskScoreFactorRepository extends JpaRepository<RiskScoreFactor, UUID> {
	List<RiskScoreFactor> findByApplicationId(UUID applicationId);

	void deleteByApplicationId(UUID applicationId);
}