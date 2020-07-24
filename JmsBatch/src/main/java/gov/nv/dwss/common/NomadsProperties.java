package gov.nv.dwss.common;

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

}
