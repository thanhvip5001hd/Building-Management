package com.laptrinhjavaweb.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.bean.BuildingBean;
import com.laptrinhjavaweb.customexception.FieldRequiredException;
import com.laptrinhjavaweb.filter.BuildingFilter;
import com.laptrinhjavaweb.model.request.BuildingAssignmentRequest;
import com.laptrinhjavaweb.model.response.BuildingSearchResponse;
import com.laptrinhjavaweb.service.BuildingService;

@RestController
@RequestMapping("/api/building")
public class BuildingAPI {
	
	 @Autowired
	 private BuildingService buildingService;
	
	@RequestMapping(value = "/api/building", method = RequestMethod.GET)
	public @ResponseBody List<BuildingBean> getBuilding(@RequestParam("name") String name, @RequestParam("street") String street,
																		@RequestParam("numberofbasement") Integer numberOfBasement) {
		List<BuildingBean> results = new ArrayList<>();

		return results;
	}
	/*
	@PostMapping("/api/building")
	public BuildingBean createBuilding(@RequestBody BuildingBean newBuilding) {
		System.out.println(10/0);
		return newBuilding;
	}
	*/

	@GetMapping
	public List<BuildingSearchResponse> findAll(@RequestParam Map<String, Object> requestParam,
										@RequestParam(value = "types", required = false) List<String> types) {
		List<BuildingSearchResponse> results = buildingService.findBuilding(requestParam, types);
		return results;
	}
	
	@PostMapping
	public BuildingBean createBuilding(@RequestBody BuildingBean newBuilding) {
		/*try {
			System.out.println(10/0);
			return newBuilding;
		} catch(Exception e) {
			ErrorResponseBean errorResponseBean = new ErrorResponseBean();
			errorResponseBean.setError(e.getMessage());
			List<String> details = new ArrayList<>();
			details.add("1 không chia được cho 0");
			return errorResponseBean;
		}*/
		validataData(newBuilding);
		return newBuilding;
		
		
	}
	
	
	private void validataData(BuildingBean newBuilding) {
		if(newBuilding.getName() == null || newBuilding.getName().equals("") || newBuilding.getNumberOfBasement() == null) {
			throw new FieldRequiredException("name or numberofbasement is null");
		}
	}

	@PutMapping
	public Object updateBuilding(@RequestBody BuildingBean updateBuilding) {
		return updateBuilding;
	}
	
	@PostMapping("/assignment")
	public void assignBuilding(@RequestBody BuildingAssignmentRequest assignBuildingBean) {
		System.out.println("hello");
	}
	
	@RequestMapping(value = "/api/building", method = RequestMethod.DELETE)
	public BuildingBean deleteBuilding(@RequestBody Long[] ids) {
		return null;
	}

}
