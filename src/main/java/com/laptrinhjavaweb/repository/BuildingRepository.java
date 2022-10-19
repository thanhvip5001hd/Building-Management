package com.laptrinhjavaweb.repository;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.repository.entity.BuildingEntity;

public interface BuildingRepository {
	List<BuildingEntity> findAll();
	List<BuildingEntity> findBuilding(Map<String, Object> results, List<String> buildingTypes);
}
