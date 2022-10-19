package com.laptrinhjavaweb.service;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.filter.BuildingFilter;
import com.laptrinhjavaweb.model.response.BuildingSearchResponse;

public interface BuildingService {
	List<BuildingSearchResponse> findAll();
	List<BuildingSearchResponse> findBuilding(Map<String, Object> BuildingSearchInput, List<String> buildingTypes);
}
