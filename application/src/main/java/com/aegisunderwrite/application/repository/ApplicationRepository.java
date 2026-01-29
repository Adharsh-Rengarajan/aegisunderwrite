package com.aegisunderwrite.application.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aegisunderwrite.application.entity.Application;
import com.aegisunderwrite.application.enums.ApplicationStatus;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
	List<Application> findByApplicantId(UUID applicantId);

	List<Application> findByStatus(ApplicationStatus status);

	@Query("SELECT a FROM Application a " + "JOIN FETCH a.applicant " + "JOIN FETCH a.vehicle " + "WHERE a.id = :id")
	Optional<Application> findByIdWithDetails(@Param("id") UUID id);

	@Query("SELECT a FROM Application a " + "JOIN FETCH a.applicant " + "JOIN FETCH a.vehicle "
			+ "LEFT JOIN FETCH a.decision d " + "LEFT JOIN FETCH d.explanations " + "WHERE a.id = :id")
	Optional<Application> findByIdWithDecision(@Param("id") UUID id);
}