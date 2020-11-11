package com.cscummings.batch.common;

import java.sql.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcSqlUtilities {

	public static String retrieveTwnSysdate(String schemaName, DataSource datasource) {
		JdbcTemplate jdbctemplate = new JdbcTemplate(datasource);
		Date date = jdbctemplate.queryForObject(Constants.sysdateSelect + Constants.TwnSysDateTable,
				java.sql.Date.class);
		return Constants.simpleDateFormat.format(date);

	}

	public static String buildSimpleEligSqlQuery(String insql, String schema, String start, String end) {
		String inSql = insql.replaceAll(Constants.SCHEMA_NAME, schema).replaceAll(Constants.begin_date, start)
				.replaceAll(Constants.end_date, end);

		return inSql;
	}

	public static String buildEligSqlQuery(String insql, String schema, String min, String max, String prd, String redet) {
		String inSql = insql.replaceAll(Constants.SCHEMA_NAME, schema).replaceAll(Constants.min_reCert, min)
				.replaceAll(Constants.max_reCert, max).replaceAll(Constants.prd_beginDate, prd)
				.replaceAll(Constants.reDetDate, redet);

		return inSql;
	}
}
