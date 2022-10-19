package com.laptrinhjavaweb.repository.impl;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.laptrinhjavaweb.annotation.Entity;
import com.laptrinhjavaweb.annotation.Table;
import com.laptrinhjavaweb.mapper.ResultSetMapper;
import com.laptrinhjavaweb.repository.JdbcRepository;
import com.laptrinhjavaweb.utils.ConnectionUtils;

public class SimpleJdbcRepository<T> implements JdbcRepository<T> {
	
	private Class<T> tClass;
	public SimpleJdbcRepository() {
		tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public List<T> findByCondition(String sql) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionUtils.getConnection();
			stmt = conn.createStatement();
			String tableName = null;
			if(tClass.isAnnotationPresent(Table.class) && tClass.isAnnotationPresent(Entity.class)) {
				Table table = tClass.getAnnotation(Table.class);
				tableName = table.name();
			}
			rs = stmt.executeQuery(sql);
			ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
			return resultSetMapper.mapRow(rs, tClass);
		} catch (SQLException e) {
			// close
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				stmt.close();
				rs.close();
			} catch (SQLException e2) {
				
			}
		}
		return null;
	}

}
