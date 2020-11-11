package com.cscummings.batch;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.cscummings.batch.common.NomadsProperties;

@Configuration
@ComponentScan(basePackages = {"com.cscummings.batch.common"})
@EnableAutoConfiguration
@EnableConfigurationProperties
@PropertySource(value = { "classpath:/resources/application-dev.properties" }, ignoreResourceNotFound = false)

public class TestConfiguration {
	
	/**
	 * Property placeholder configurer needed to process @Value annotations
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Autowired
	private NomadsProperties nomadsProps;

	@Test
	public void test() {
		fail("Not yet implemented"); 
	}

}
