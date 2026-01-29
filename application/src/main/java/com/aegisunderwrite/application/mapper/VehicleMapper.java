package com.aegisunderwrite.application.mapper;

import org.springframework.stereotype.Component;

import com.aegisunderwrite.application.dto.VehicleRequest;
import com.aegisunderwrite.application.dto.VehicleResponse;
import com.aegisunderwrite.application.entity.Vehicle;

@Component
public class VehicleMapper {

	public Vehicle toEntity(VehicleRequest request) {
		Vehicle vehicle = new Vehicle();
		vehicle.setMake(request.getMake());
		vehicle.setModel(request.getModel());
		vehicle.setManufactureYear(request.getManufactureYear());
		vehicle.setVehicleValue(request.getVehicleValue());
		vehicle.setUsageType(request.getUsageType());
		return vehicle;
	}

	public VehicleResponse toResponse(Vehicle vehicle) {
		VehicleResponse response = new VehicleResponse();
		response.setId(vehicle.getId());
		response.setMake(vehicle.getMake());
		response.setModel(vehicle.getModel());
		response.setManufactureYear(vehicle.getManufactureYear());
		response.setVehicleValue(vehicle.getVehicleValue());
		response.setUsageType(vehicle.getUsageType());
		return response;
	}
}
