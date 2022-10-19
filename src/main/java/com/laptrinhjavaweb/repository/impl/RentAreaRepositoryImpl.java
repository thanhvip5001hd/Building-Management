package com.laptrinhjavaweb.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.repository.RentAreaRepository;
import com.laptrinhjavaweb.repository.entity.RentAreaEntity;

@Repository
public class RentAreaRepositoryImpl extends SimpleJdbcRepository<RentAreaEntity> implements RentAreaRepository {

	@Override
	public List<RentAreaEntity> findRentAreaByBuildingId(Long id) {
		StringBuilder sql = new StringBuilder("select * from rentarea " + SystemConstant.WHERE_ONE_EQUAL_ONE);
		if(id != null) {
			sql.append(" and buildingid = "+id+"");
		}
		List<RentAreaEntity> rentAreaEntities = findByCondition(sql.toString());
		return rentAreaEntities;
	}

}
