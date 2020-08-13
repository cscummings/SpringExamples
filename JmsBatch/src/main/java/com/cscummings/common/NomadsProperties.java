package com.cscummings.common;

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
 *
 */

@Component
@ConfigurationProperties(prefix = "nomads.datasource")
@Validated
public class NomadsProperties {
	private static final Logger logger = LoggerFactory.getLogger(NomadsProperties.class);
	
	private Date currentDate = new Date();
	
	private String platform;
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private String schema;

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
		//return username;  //for testing don't use decrypt/encrypt
		return EncryptionUtils.decrypt(username);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		//return password;
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

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}


}
