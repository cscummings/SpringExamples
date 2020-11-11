package com.cscummings.batch.common;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author ccummings
 *
 */

@Component
@ConfigurationProperties(prefix = "file")
@Validated
public class FileProperties {
	private File NDNHSendFile;
	private File NDNHRecvFile;
	
	private File xmlLayout;
	
	public File getNDNHSendFile() {
		return NDNHSendFile;
	}
	public void setNDNHSendFile(File nDNHSendFile) {
		NDNHSendFile = nDNHSendFile;
	}

	public File getNDNHRecvFile() {
		return NDNHRecvFile;
	}
	public void setNDNHRecvFile(File nDNHRecvFile) {
		NDNHRecvFile = nDNHRecvFile;
	}
	
	public File getXmlLayout() {
		return xmlLayout;
	}
	public void setXmlLayout(File xmlLayout) {
		this.xmlLayout = xmlLayout;
	}

}
