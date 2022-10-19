package com.laptrinhjavaweb.utils;

/**
 * @author: kythuat-laptrinhjavaweb
 * @since: 2022/10/05 9:30 PM
 * @description: Tạo Utils để build câu query theo từng điều kiện
 */
public class SqlUtils {

    public static String buildQueryUsingLike(String column, String value) {
        return (!ValidateUtils.isValid(value)) ? ""
                : String.format(" AND %s LIKE %s", column, "'%" + value + "%'");
    }

    public static String buildQueryUsingOperator(String column, Object value, String operator) {
        if (!ValidateUtils.isValid(value)) return "";

        return (value instanceof String) ? String.format(" AND %s %s '%s'", column, operator, value)
                : String.format(" AND %s %s %s", column, operator, value);
    }

    public static String buildQueryUsingBetween(String column, Object from, Object to) {
        if (null == from && null == to) return "";
        else {
            if (null == from) from = 0;
            if (null == to) {
                if (from instanceof Integer) {
                    to = Integer.MAX_VALUE;
                } else if (from instanceof Double) {
                    to = Double.MAX_VALUE;
                }
            }

            return String.format(" AND %s BETWEEN %s AND %s", column, from, to);
        }
    }
}
