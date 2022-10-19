package com.laptrinhjavaweb.repository;

import java.util.List;

public interface JdbcRepository<T> {
	List<T> findByCondition(String sql);
}
