package com.cscummings.batch.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.validator.routines.DateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Strings;

@Component
@ConfigurationProperties(prefix = "nomads")
@Validated
public class ItemReaderProperties {
	private static final Logger logger = LoggerFactory.getLogger(ItemReaderProperties.class);
	private String platform;
	private String url;
	private String username;
	private String password;
	private String schema;
	private String startdate;
	private String enddate;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		String newDate = new String();
		try {
			newDate = validateDate(startdate, 8);
		} catch (ParseException e) {
			logger.error(
					"Invalid Date format - enter date in format yyyy-MM-dd either in properties file or command line for nomads.startdate "
							+ startdate);
		}
		this.startdate = newDate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		String newDate = new String();
		try {
			newDate = validateDate(enddate, 8);
		} catch (ParseException e) {
			logger.error(
					"Invalid Date format - enter date in format yyyy-MM-dd either in properties file or command line for nomads.enddate "
							+ enddate);
		}
		this.enddate = newDate;
	}

	public static String validateString(String inString, int len) {
		String validString = Strings.nullToEmpty(inString);
		if (inString.length() < len) {
			Strings.padEnd(inString, len, Constants.blankChar);
		} else if (inString.length() > len) {
			validString = validString.substring(0, len);
		}

		return validString;
	}

	public static String validateDate(String inString, int len) throws ParseException{
		 Date checkDate = null;
		 String validString = Strings.nullToEmpty(inString);
		 String[] parsePatterns = {Constants.simpleDateString, Constants.yyyy_MM_dd, Constants.MM_dd_yyyy, Constants.MMddyyyy};
		 
		 if (DateValidator.getInstance().isValid(inString, Constants.simpleDateString)) {
			//if its valid we're done 
			return validString; 
		 } else {
			 
				checkDate = DateUtils.parseDate(validString, parsePatterns);
				validString = new SimpleDateFormat(Constants.simpleDateString).format(checkDate);
		 }
	

		 return validString;
	}
	
	
	public static void buildSQLString(StringBuilder inString, String arg) {
		int start = 0;
		int end = 0;
		while (end < inString.length()) {
			start = inString.indexOf(arg, start);
			end = inString.lastIndexOf(arg, end);
		}

	}


}
