package com.cscummings.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.cscummings.common.Constants;
import com.cscummings.common.NomadsProperties;
import com.cscummings.common.SpringBatchDatabaseProperties;


@Configuration
public class DataBaseConfig {
	
	@Autowired
	private NomadsProperties nomadsProps;

	@Autowired
	private SpringBatchDatabaseProperties batchDatabaseProps;

	
	/**
	 * batchDataSource is used for storing spring.batch job information
	 * nomadsDataSource is used for querying
	 *
	 * @return DataSource
	 */

	@Bean(name = "batchDataSource")
	@Primary
	public DataSource batchDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
		dataSource.setUrl(batchDatabaseProps.getUrl());
		dataSource.setUsername(batchDatabaseProps.getUsername());
		dataSource.setPassword(batchDatabaseProps.getPassword());
		dataSource.setSchema(batchDatabaseProps.getSchema());

		return dataSource;
	}
	

	@Bean(name = "nomadsDatasource")
	public DataSource nomadsDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(nomadsProps.getUrl());
		dataSource.setUsername(nomadsProps.getUsername());
		dataSource.setPassword(nomadsProps.getPassword());
		dataSource.setSchema(nomadsProps.getSchema());
		dataSource.setDriverClassName("com.ibm.db2.jcc.DB2Driver");

		return dataSource;
	}

	@Bean
	@Primary
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.DB2);
		jpaVendorAdapter.setGenerateDdl(false);
		jpaVendorAdapter.setShowSql(true);
		return jpaVendorAdapter;
	}


	@Bean
	@Qualifier("nomadsTrx")
	public PlatformTransactionManager nomadstm() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(nomadsManagerFactory().getObject());
		transactionManager.setDataSource(nomadsDataSource());
		return transactionManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean nomadsManagerFactory() {

		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setPackagesToScan(Constants.base_package);
		lef.setDataSource(nomadsDataSource());
		lef.setJpaVendorAdapter(jpaVendorAdapter());
		lef.setJpaProperties(new Properties());
		lef.afterPropertiesSet();
		return lef;
	}

	@Bean
	public JpaVendorAdapter nomadsVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.DB2);
		jpaVendorAdapter.setDatabasePlatform(nomadsProps.getPlatform());
		jpaVendorAdapter.setGenerateDdl(false);
		jpaVendorAdapter.setShowSql(true);
		return jpaVendorAdapter;
	}

}
