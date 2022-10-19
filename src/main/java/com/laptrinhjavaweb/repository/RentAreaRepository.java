package com.laptrinhjavaweb.repository;

import java.util.List;

import com.laptrinhjavaweb.repository.entity.RentAreaEntity;

public interface RentAreaRepository extends JdbcRepository<RentAreaEntity>{
	List<RentAreaEntity> findRentAreaByBuildingId(Long id);
}
