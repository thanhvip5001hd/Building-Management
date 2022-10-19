package com.laptrinhjavaweb.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.laptrinhjavaweb.annotation.Column;


public class ResultSetMapper<T> {

	public List<T> mapRow(ResultSet rs, Class<T> tClass) {
		List<T> result = new ArrayList<>();
		try {
			Field[] fields = tClass.getDeclaredFields();
			ResultSetMetaData resultSetMetaData = rs.getMetaData();
			while(rs.next()) {
				T object = tClass.newInstance();
				for(int i = 0;i < resultSetMetaData.getColumnCount();i++) {
					String columnName = resultSetMetaData.getColumnName(i + 1);
					Object columnValue = rs.getObject(i + 1);
					for(Field field: fields) {
						Column column = field.getAnnotation(Column.class);
						if(column.name().equals(columnName) && columnValue != null) {
							org.apache.commons.beanutils.BeanUtils.setProperty(object, field.getName(), columnValue);
							break;
						}
					}
					Class<?> parentClass = tClass.getSuperclass();
					while(parentClass != null) {
						for(Field field: parentClass.getDeclaredFields()) {
							if(field.isAnnotationPresent(Column.class)) {
								Column column = field.getAnnotation(Column.class);
								if(column.name().equals(columnName) && columnValue != null) {
									org.apache.commons.beanutils.BeanUtils.setProperty(object, field.getName(), columnValue);
									break;
								}
							}
						}
						parentClass = parentClass.getSuperclass();
					}
				}
				result.add(object);
			}
			return result;
		} catch(SQLException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
