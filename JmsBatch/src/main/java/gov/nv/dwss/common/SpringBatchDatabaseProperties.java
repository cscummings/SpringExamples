package gov.nv.dwss.common;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author ccummings
 *
 */

@Component
@ConfigurationProperties(prefix = "database")
@Validated
public class SpringBatchDatabaseProperties {
	private String driver;
	private String url;
	private String username;
	private String password;
	
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
	

}
