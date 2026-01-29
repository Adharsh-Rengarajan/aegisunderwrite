package com.aegisunderwrite.application.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aegisunderwrite.application.entity.Applicant;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {
	Optional<Applicant> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName,
			LocalDate dateOfBirth);

	List<Applicant> findByLastNameIgnoreCase(String lastName);
}