package com.cscummings.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;

public class Constants {

	public static final String base_package = "com.cscummings";
	
	//Date Constants
	public static final String dateFormat = "yyyyMMdd";
	public static final String maxDate = "9999-12-31";
	public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.dateFormat);
	public static final SimpleDateFormat hyphenDateFormat = new SimpleDateFormat(Constants.yyyy_MM_dd);
	public static final String simpleDateString = simpleDateFormat.format(new Date());

	//Date parse Patterns for validation
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String MM_dd_yyyy = "MM-dd-yyyy";
	public static final String MMddyyyy = "MMddyyyy";
	public static final String yyyyMMdd = "yyyyMMdd";
	
	/** The Constant SCHEMA_NAME. */
	public static final String SCHEMA_NAME = "##db-schema-name##";
	
	//Formatting Constants - for building format string for Aggregator
	public static final String fmt_hdr = "%-";
	public static final String fmt_string = "s%-";
	
}
