package com.cscummings.batch.common;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author ccummings
 *
 */
// TODO add logic for RCRT_APL_DT >= date calculation
// TODO add logic for PRD_BEG_DT begining and end
// TODO add logic for NEXT_REDET_DT

@Component
@ConfigurationProperties(prefix = "nomads")
@Validated
public class NomadsProperties {
	private static final Logger logger = LoggerFactory.getLogger(NomadsProperties.class);
	private static final Calendar c = Calendar.getInstance();
	private boolean validatedDate = false;
	
	private Date currentDate = new Date();
	
	private String platform;
	private String driver;
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

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {

		return EncryptionUtils.decrypt(username);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return EncryptionUtils.decrypt(password);
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
		if (startdate == null) {
			try {
				validateCurrentDate();		
				c.add(Calendar.DATE, -30); // Subtracting 30 days
				startdate = Constants.simpleDateFormat.format(c.getTime());

			} catch (ParseException e) {
				logger.error(
						"Invalid Date format - returned from database. Override either in properties file or command line for --nomads.startdate. Format is yyyy-MM-dd"
								+ startdate);
			}
		}
		return startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setStartdate(String startdate) {
		String newDate = new String();
		try {
			newDate = ValidationUtils.validateDate(startdate, 8);
		} catch (ParseException e) {
			logger.error(
					"Invalid Date format - enter date in format yyyy-MM-dd either in properties file or command line for nomads.startdate "
							+ startdate);
		}
		this.startdate = newDate;
	}

	public void setEnddate(String enddate) {
		String newDate = new String();
		try {
			newDate = ValidationUtils.validateDate(enddate, 8);
		} catch (ParseException e) {
			logger.error(
					"Invalid Date format - enter date in format yyyy-MM-dd either in properties file or command line for nomads.enddate "
							+ enddate);
		}
		this.enddate = newDate;
	}

	/*
	 * Date Calculations Methods
	 * 
	 */

	public String minRecertApplicationDate() throws ParseException {
		validateCurrentDate();		
		c.add(Calendar.DATE, -30); // Subtracting 30 days
		String minValue = hyphenateDate();

		return minValue;

	}

	public String maxRecertApplicationDate() throws ParseException {
		validateCurrentDate();		
		String maxValue = hyphenateDate();

		return maxValue;
	}

	public String periodBeginDate() throws ParseException {
		validateCurrentDate();		
		c.set(Calendar.DAY_OF_MONTH, 1); //Go to first of month

		return hyphenateDate();
	}

	public String nextReDeterminationDate() throws ParseException {
		validateCurrentDate();		
		c.add(Calendar.MONTH, 1); // add 1 month
		int theMax = c.getMaximum(Calendar.DAY_OF_MONTH); // get last day of month
		c.set(Calendar.DAY_OF_MONTH, theMax);
		return hyphenateDate();
	}
	
	public void validateCurrentDate() throws ParseException {
		String stringDate = this.getEnddate();
		if (stringDate != null && !validatedDate){
			currentDate = ValidationUtils.stringToDate(stringDate);
			validatedDate = true;
		}
		c.setTime(currentDate); 

	}
	
	public String hyphenateDate() {
		return Constants.hyphenDateFormat.format(c.getTime());
	}
}
