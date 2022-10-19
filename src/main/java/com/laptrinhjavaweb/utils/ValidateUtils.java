package com.laptrinhjavaweb.utils;

import java.util.Collection;

import com.laptrinhjavaweb.constant.SystemConstant;

public class ValidateUtils {
	public static boolean isValid(Object object) {
		boolean isTrue = null != object && !SystemConstant.EMPTY_STRING.equals(object.toString());
		if(isTrue) {
			if(object instanceof String) {
				return true;
			} else if(object instanceof Integer) {
				return Integer.parseInt(object.toString()) >= 0;
			} else if(object instanceof Long) {
				return Long.parseLong(object.toString()) >= 0;
			} else if(object instanceof Collection) {
				return !((Collection<?>) object).isEmpty();
			}
		}
		return false;
	}
}
