package com.cscummings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class JmsBatchApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(JmsBatchApplication.class, args);
	}

}
