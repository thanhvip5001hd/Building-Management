package com.laptrinhjavaweb.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.repository.DistrictRepository;
import com.laptrinhjavaweb.repository.entity.DistrictEntity;

@Repository
public class DistrictRepositoryImpl extends SimpleJdbcRepository<DistrictEntity>  implements DistrictRepository {

	@Override
	public DistrictEntity findDistrict(Long id) {
		StringBuilder sql = new StringBuilder("select * from district " + SystemConstant.WHERE_ONE_EQUAL_ONE);
		if(id != null) {
			sql.append(" and id = "+id+"");
		}
		List<DistrictEntity> districtEntities = findByCondition(sql.toString());
		return districtEntities.get(0);
	}

	
	/*
	@Override
	public DistrictEntity findDist	rict(Long id) {
		StringBuilder sql = new StringBuilder("select * from district " + SystemConstant.WHERE_ONE_EQUAL_ONE);
		if(id != null) {
			sql.append(" and id = "+id+"");
		}
		
		return find.get(0);
	}
	*/

}
