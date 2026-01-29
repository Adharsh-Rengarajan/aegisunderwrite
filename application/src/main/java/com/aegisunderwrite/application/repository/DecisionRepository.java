package com.aegisunderwrite.application.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aegisunderwrite.application.entity.Decision;
import com.aegisunderwrite.application.enums.DecisionOutcome;
import com.aegisunderwrite.application.enums.RiskBand;

@Repository
public interface DecisionRepository extends JpaRepository<Decision, UUID> {

	Optional<Decision> findByApplicationId(UUID applicationId);

	@Query("SELECT d FROM Decision d " + "LEFT JOIN FETCH d.explanations " + "WHERE d.application.id = :applicationId")
	Optional<Decision> findByApplicationIdWithExplanations(@Param("applicationId") UUID applicationId);

	List<Decision> findByDecision(DecisionOutcome decision);

	List<Decision> findByRiskBand(RiskBand riskBand);
}