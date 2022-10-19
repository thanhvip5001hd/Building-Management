package com.laptrinhjavaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.utils.SqlUtils;
import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.model.response.BuildingSearchResponse;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.repository.entity.BuildingEntity;
import com.laptrinhjavaweb.utils.ValidateUtils;

@Repository
public class BuildingRepositoryImpl extends SimpleJdbcRepository<BuildingEntity> implements BuildingRepository {

	private String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	private String USER = "root";
	private String PASS = "123456";

	@Override
	public List<BuildingEntity> findAll() {
		List<BuildingEntity> results = new ArrayList<>();
		String QUERY = "SELECT * FROM building";
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(QUERY);) {
			while (rs.next()) {
				// Display values
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setName(rs.getString("name"));
				buildingEntity.setStreet(rs.getString("street"));
				buildingEntity.setWard(rs.getString("ward"));
				buildingEntity.setDistrictId(rs.getLong("districtid"));
				results.add(buildingEntity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 *
	 * @author: kythuat-laptrinhjavaweb
	 * @since: 2022/10/05 9:10 PM
	 * @description:
	 */

	@Override
	public List<BuildingEntity> findBuilding(Map<String, Object> params, List<String> buildingTypes) {
		
		StringBuilder sql = new StringBuilder("SELECT b.id,b.name,b.street,b.ward,b.districtid,b.rentprice FROM building as b");
		StringBuilder joinQuery = new StringBuilder();
		StringBuilder whereQuery = new StringBuilder();
		
		buildingQueryWithJoin(params, buildingTypes, joinQuery, whereQuery);
		buildingQueryWithoutJoin(params, whereQuery);
		// nên khai báo constant cho table, mệnh đề where, group by
		sql.append(joinQuery).append(" WHERE 1 = 1").append(whereQuery).append(" GROUP BY b.id");
			
		
		
		return findByCondition(sql.toString());
	}

	/**
	 *
	 * @author: kythuat-laptrinhjavaweb
	 * @since: 2022/10/05 9:30 PM
	 * @description: apply utils khi tạo câu query
	 *
	 */
	private void buildingQueryWithJoin(Map<String, Object> params, List<String> types, StringBuilder joinQuery,
			StringBuilder whereQuery) {

		/*String district = (String) params.get("district");
		if (ValidateUtils.isValid(district)) {
			joinQuery.append(" inner join district as d on b.districtid = d.id");
			whereQuery.append(" and district.code like '%" + district + "%'");
		}

		Integer fromArea = (Integer) params.get("fromarea");
		Integer toArea = (Integer) params.get("toarea");
		if ((ValidateUtils.isValid(fromArea) || ValidateUtils.isValid(toArea))) {
			joinQuery.append(" inner join rentarea as ra on b.id = ra.buildingid");
			if (fromArea != null) {
				whereQuery.append(" and ra.value >= " + fromArea + "");
			}
			if (toArea != null) {
				whereQuery.append(" and ra.value <= " + toArea + "");
			}

		}
		if (types.size() != 0) {
			List<String> buildingTypes = new ArrayList<>();
			joinQuery.append(
					" inner join buildingrenttype as brt on b.id = brt.buildingid inner join renttype as rt on rt.id = brt.renttypeid");

			for (String item : types) {
				buildingTypes.add("'" + item + "'");
			}
			whereQuery.append(" and rt.code in (").append(String.join(",", buildingTypes)).append(")");
		}

		Long staffId = (Long) params.get("staffid");

		if (ValidateUtils.isValid(staffId)) {
			joinQuery.append(
					" inner join assignmentbuilding as ab on b.id = ab.buildingid inner join user as u on ab.staffid = u.id");
			whereQuery.append(" and u.id = " + staffId + "");
		}
		*/
		Integer areaRentFrom = (Integer) params.getOrDefault("arearentfrom", null);
		Integer areaRentTo = (Integer) params.getOrDefault("arearentto", null);

		if (areaRentFrom != null || areaRentTo != null) {
			whereQuery.append("\nAND EXISTS (SELECT * FROM rentarea ra WHERE b.id = ra.buildingid");

			whereQuery.append(SqlUtils.buildQueryUsingBetween("ra.value", areaRentFrom, areaRentTo));
			whereQuery.append(" )");
		}

		Long staffId = (Long) params.getOrDefault("staffid", null);
		if (staffId != null) {
			joinQuery.append("\nJOIN assignmentbuilding ab ON ab.buildingid = b.id");
			whereQuery.append(SqlUtils.buildQueryUsingOperator("ab.staffid", staffId, "="));
		}
		if (ValidateUtils.isValid(types)) {
			joinQuery.append("\nJOIN buildingrenttype as br on br.buildingid = b.id");
			joinQuery.append("\nJOIN renttype as rt on rt.id = br.renttypeid");
			whereQuery.append("\nAND (");
			List<String> buildingTypes = new ArrayList<>();
			for (String item : types) {
				buildingTypes.add("'" + item + "'");
			}
			whereQuery.append(" and rt.code in (").append(String.join(",", buildingTypes)).append(")");
			whereQuery.append(buildingTypes);
			whereQuery.append(")");


		}
	}

	/**
	 *
	 * @author: kythuat-laptrinhjavaweb
	 * @since: 2022/10/05 9:30 PM
	 * @description: apply utils khi tạo câu query
	 *
	 */
	private void buildingQueryWithoutJoin(Map<String, Object> params, StringBuilder whereQuery) {
//		String name = (String) params.get("name");
//		if (ValidateUtils.isValid(name)) {
//			whereQuery.append(" and b.name like '%" + name + "%'");
//		}
//		String street = (String) params.get("street");
//		if (ValidateUtils.isValid(street)) {
//			whereQuery.append(" and b.street like '%" + street + "%'");
//		}
//		String ward = (String) params.get("ward");
//		if (ValidateUtils.isValid(ward)) {
//			whereQuery.append(" and b.ward like '%" + ward + "%'");
//		}
//		Integer numberOfBasement = (Integer) params.get("numberofbasement");
//		if (ValidateUtils.isValid(numberOfBasement)) {
//			whereQuery.append(" and b.numberofbasement = " + numberOfBasement + "");
//		}
//		Integer floorArea = (Integer) params.get("foorarea");
//		if (ValidateUtils.isValid(floorArea)) {
//			whereQuery.append(" and b.floorarea = " + floorArea + "");
//		}
//		String direction = (String) params.get("direction");
//		if (ValidateUtils.isValid(direction)) {
//			whereQuery.append(" and b.direction like '%" + direction + "%'");
//		}
//		String level = (String) params.get("level");
//		if (ValidateUtils.isValid(level)) {
//			whereQuery.append(" and b.level like '%" + level + "%'");
//		}
//
//		Integer fromRentPrice = (Integer) params.get("fromrentprice");
//		Integer toRentPrice = (Integer) params.get("torentprice");
//
//		if ((ValidateUtils.isValid(fromRentPrice) || ValidateUtils.isValid(toRentPrice))) {
//			if (ValidateUtils.isValid(fromRentPrice)) {
//				whereQuery.append(" and b.rentprice >= " + fromRentPrice + "");
//			}
//			if (ValidateUtils.isValid(toRentPrice)) {
//				whereQuery.append(" and b.rentprice <= " + toRentPrice + "");
//			}
//
//		}
//	}
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (!entry.getKey().equals("districtcode") && !entry.getKey().equals("types")
					&& !entry.getKey().startsWith("rentprice") && !entry.getKey().startsWith("arearent")) {
				if (entry.getValue() instanceof String) {
					whereQuery.append("\nAND b." + entry.getKey() + " LIKE '%" + entry.getValue() + "%'");
				}
				if (entry.getValue() instanceof Integer) {
					whereQuery.append("\nAND b." + entry.getKey() + " = '" + entry.getValue() + "'");
				}
			}
			if (entry.getKey().equals("districtcode")) {
				whereQuery.append("\nAND d.code = '" + entry.getValue() + "'");
			}
		}

		Integer rentPriceFrom = (Integer) params.getOrDefault("rentpricefrom", null);
		Integer rentPriceTo = (Integer) params.getOrDefault("rentpriceto", null);

		whereQuery.append(SqlUtils.buildQueryUsingBetween("b.rentprice", rentPriceFrom, rentPriceTo));

	}
}
