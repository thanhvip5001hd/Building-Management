package com.laptrinhjavaweb.repository;

import com.laptrinhjavaweb.repository.entity.DistrictEntity;

public interface DistrictRepository extends JdbcRepository<DistrictEntity> {
	DistrictEntity findDistrict(Long id);
}
