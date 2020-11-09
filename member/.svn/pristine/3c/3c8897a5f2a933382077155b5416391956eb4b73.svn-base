package com.taole.member.sql;

public class CommonSql {
	public static String addPaging(String mainSql, int start, int end) {
		String sql = " select * from ( select A.*, rownum rn from (" + mainSql + ") A where rownum<=" + end + ") where rn>=" + start;
		return sql;
	}

	public static String count(String mainSql) {
		String sql = " select count(*) from ( " + mainSql + ") c";
		return sql;
	}
}

