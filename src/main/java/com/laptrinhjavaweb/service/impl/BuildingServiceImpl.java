package com.laptrinhjavaweb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.filter.BuildingFilter;
import com.laptrinhjavaweb.model.response.BuildingSearchResponse;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.repository.DistrictRepository;
import com.laptrinhjavaweb.repository.RentAreaRepository;
import com.laptrinhjavaweb.repository.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.entity.RentAreaEntity;
import com.laptrinhjavaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired 
	private RentAreaRepository rentAreaRepository;
	
	@Autowired 
	private DistrictRepository districtRepository;
	
	@Override
	public List<BuildingSearchResponse> findAll() {
		List<BuildingSearchResponse> results = new ArrayList<>();
		List<BuildingEntity> buildingEntities = buildingRepository.findAll();
		for(BuildingEntity item: buildingEntities) {
			BuildingSearchResponse buildingSearchResponse = new BuildingSearchResponse();
			buildingSearchResponse.setName(item.getName());
			buildingSearchResponse.setAddress(item.getStreet() + " - " + item.getWard());
			results.add(buildingSearchResponse);
		}
		return results;
	}

	/**
	 *
	 * @author: kythuat-laptrinhjavaweb
	 * @since 2022/10/05 9:15 PM
	 * @description: check valid params search để apply tự động ở layer repository
	 *
	 */
	@Override
	public List<BuildingSearchResponse> findBuilding(Map<String, Object> params, List<String> buildingTypes) {

		Map<String, Object> validParams = this.validateSearchParams(params);

		List<BuildingSearchResponse> results = new ArrayList<>();
		List<BuildingEntity> buildingEntities = buildingRepository.findBuilding(validParams, buildingTypes);
		for(BuildingEntity item: buildingEntities) {
			results.add(convertToBuildingSearchResponse(item));
		}  
		return results;
	}
	
	private BuildingSearchResponse convertToBuildingSearchResponse(BuildingEntity item) {
		BuildingSearchResponse result = new BuildingSearchResponse();
		result.setId(item.getId());
		result.setName(item.getName());
		result.setAddress(item.getStreet() + " - " + item.getWard() + " - " + districtRepository.findDistrict(item.getDistrictId()).getName());
		result.setRentArea(getRentArea(rentAreaRepository.findRentAreaByBuildingId(item.getId())));
		result.setRentPrice(item.getRentPrice());
		return result;
	}
	
	private String getRentArea(List<RentAreaEntity> rentAreaEntities) {
		List<String> rentAreaValue = new ArrayList<>();
		for(RentAreaEntity item: rentAreaEntities) {
			rentAreaValue.add(item.getValue().toString());
		}
		return String.join(",", rentAreaValue);
	}

	private Map<String, Object> validateSearchParams(Map<String, Object> params) {
		Map<String, Object> validParams = new HashMap<>();

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (ValidateUtils.isValid(entry.getValue())) {
				validParams.put(entry.getKey().toLowerCase(), entry.getValue());
			}
		}
		return validParams;
	}

}
