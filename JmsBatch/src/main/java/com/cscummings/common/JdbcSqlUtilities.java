package com.cscummings.common;

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

}
