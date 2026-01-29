package com.aegisunderwrite.application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aegisunderwrite.application.entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
	List<Vehicle> findByMakeIgnoreCaseAndModelIgnoreCase(String make, String model);
}