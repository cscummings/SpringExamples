package com.cscummings.batch;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.ExitCodeMapper;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.PatternMatchingCompositeLineTokenizer;
import org.springframework.batch.item.file.transform.RangeArrayPropertyEditor;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.cscummings.batch.common.Constants;
import com.cscummings.batch.common.FileProperties;
import com.cscummings.batch.common.JdbcSqlUtilities;
import com.cscummings.batch.common.JobCompletionNotificationListener;
import com.cscummings.batch.common.NomadsProperties;
import com.cscummings.batch.common.SpringBatchDatabaseProperties;
import com.cscummings.batch.common.zOSExitCodeMapper;
import com.cscummings.batch.model.NDNHNomads;
import com.cscummings.batch.model.NDNHNomadsRowMapper;
import com.cscummings.batch.model.NDNH_Audit;
import com.cscummings.batch.model.NDNH_Data;
import com.cscummings.batch.processor.NDNHNomadsItemProcessor;
import com.cscummings.batch.tokenizer.NDNH_AuditFixedLengthTokenizer;
import com.cscummings.batch.tokenizer.NDNH_DataFixedLengthTokenizer;
import com.cscummings.batch.tokenizer.NDNH_TrailerFixedLengthTokenizer;
import com.cscummings.batch.transform.CustomDateEditorRegistrar;
import com.cscummings.batch.transform.CustomLineAggregator;
import com.cscummings.batch.transform.CustomSqlDateEditor;
import com.cscummings.batch.transform.CustomTimestampEditor;
import com.cscummings.batch.transform.NDNHAuditBeanWrapperMapper;
import com.cscummings.batch.transform.NDNHNomadsFieldExtractor;
import com.cscummings.batch.writers.NDNHNomadsFileItemWriter;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = { Constants.base_package, "com.cscummings.batch.common",
		"com.cscummings.batch.model", "com.cscummings.batch.processor", "com.cscummings.batch.tokenizer" })
@EnableAutoConfiguration
//@PropertySource(value = { "classpath:${spring.profiles.active}.properties", "file:${spring.config.location}.properties",
@PropertySource(value = { "file:${spring.config.location}${spring.profiles.active}.properties",
		"classpath:SQLQueries.properties" }, ignoreResourceNotFound = false)
//		"file:${spring.config.location}SQLQueries.properties" }, ignoreResourceNotFound = false)
public class BatchConfiguration {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final SimpleDateFormat dateFormat = Constants.simpleDateFormat;

	@Autowired
	Environment env;

	@Autowired
	private FileProperties fileProps;

	@Autowired
	private NomadsProperties nomadsProps;

	@Autowired
	private SpringBatchDatabaseProperties batchDatabaseProps;
	

	/**
	 * Property placeholder configurer needed to process @Value annotations
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/*
	 * Beans for Batch Processing
	 */
	@Bean
	public static ExitCodeMapper zOSExitCodeMapper() {
		return new zOSExitCodeMapper();
	}

	/*
	 * Custom Property Editors for Date processing of FixedFile Formats to/from Databases
	 */
	@Bean
	public CustomDateEditor dateEditor() {
		return new CustomDateEditor(dateFormat, true);
	}

	@Bean
	public CustomSqlDateEditor sqlDateEditor() {
		return new CustomSqlDateEditor(dateFormat, true);
	}

	@Bean
	public CustomTimestampEditor timeEditor() {
		return new CustomTimestampEditor(dateFormat, true);
	}

	@Bean
	public CustomDateEditorRegistrar customDateEditorRegistrar() {
		return new CustomDateEditorRegistrar();
	}

	@Resource
	@Value("${select_new_SNAP_elig_members}")
	private String select_new_SNAP_elig_members;

	@Resource
	@Value("${select_NDNH_SNAP_Appls}")
	private String select_NDNH_SNAP_Appls;

	@Bean
	public RowMapper<NDNHNomads> NDNHNomadsRowMapper() {
		return new NDNHNomadsRowMapper();
	}

	@Bean
	public ItemReader<NDNHNomads> NDNH_jdbcReader() throws UnexpectedInputException, ParseException, Exception {
		String schema = nomadsProps.getSchema();
		JdbcCursorItemReader<NDNHNomads> itemReader = new JdbcCursorItemReader<NDNHNomads>();
		itemReader.setDataSource(nomadsDataSource());
		if (nomadsProps.getEnddate() == null)
			nomadsProps.setEnddate(JdbcSqlUtilities.retrieveTwnSysdate(schema, nomadsDataSource()));

		select_NDNH_SNAP_Appls = JdbcSqlUtilities.buildEligSqlQuery(select_NDNH_SNAP_Appls, schema, 
				nomadsProps.minRecertApplicationDate(), 
				nomadsProps.maxRecertApplicationDate(), 
				nomadsProps.periodBeginDate(), 
				nomadsProps.nextReDeterminationDate());
		
		itemReader.setSql(select_NDNH_SNAP_Appls);
		itemReader.setRowMapper(NDNHNomadsRowMapper());
		ExecutionContext ecx = new ExecutionContext();
		itemReader.open(ecx);
		Object NDNHNomads = new Object();

		while (NDNHNomads != null) {
			NDNHNomads = itemReader.read();
		}

		itemReader.close();

		return itemReader;
	}

	/**
	 * The ItemProcessor is called after a new line is read and it allows the
	 * developer to transform the data read In our example it simply return the
	 * original object
	 *
	 * @return
	 */
	@Bean
	public ItemProcessor<NDNHNomads, NDNHNomads> processor() {
		return new NDNHNomadsItemProcessor();
	}

	@Bean
	public ItemWriter<NDNHNomads> NDNHwriter() throws Exception {
		NDNHNomadsFieldExtractor<NDNHNomads> mfe = new NDNHNomadsFieldExtractor<NDNHNomads>();
		mfe.buildNames(NDNHNomads.class);
		CustomLineAggregator<NDNHNomads> cla = new CustomLineAggregator<NDNHNomads>();
		cla.setFieldExtractor(mfe);
		cla.buildFormat(NDNHNomads.class);
		cla.setMinimumLength(200);
		cla.setMaximumLength(200);

		NDNHNomadsFileItemWriter NDNHwriter = new NDNHNomadsFileItemWriter();

		FlatFileItemWriter<NDNHNomads> NDNHflatwriter = new FlatFileItemWriter<NDNHNomads>();
		NDNHflatwriter.setResource(new FileSystemResource(fileProps.getNDNHSendFile()));
		NDNHflatwriter.setLineAggregator(cla);
		NDNHflatwriter.setHeaderCallback(NDNHwriter);
		NDNHflatwriter.setFooterCallback(NDNHwriter);

		NDNHwriter.setDelegate(NDNHflatwriter);

		return NDNHwriter;
	}

	/**
	 * Send Job
	 *
	 * @param JobBuilderFactory
	 * @param Step
	 * @return Job
	 */

	@Bean
	public Job sendNDNHJob(JobBuilderFactory jobs, @Qualifier(Constants.sendStep) Step sendStep, JobCompletionNotificationListener listener) {
		return jobs.get(Constants.sendJobName)
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(sendStep)
				.end()
				.build();
	}

	/**
	 * Step send We declare that every 1000 lines processed the data has to be
	 * committed
	 *
	 * @param stepBuilderFactory
	 * @param reader
	 * @param writer
	 * @param processor
	 * @return Step
	 */
	@Bean
	public Step sendStep(StepBuilderFactory stepBuilderFactory, 
			@Qualifier("NDNH_jdbcReader") ItemReader<NDNHNomads> reader,
			@Qualifier("NDNHwriter") ItemWriter<NDNHNomads> writer,
			@Qualifier("processor") ItemProcessor<NDNHNomads, 
			NDNHNomads> processor) {

		return stepBuilderFactory.get(Constants.sendStep)
				.allowStartIfComplete(true)
				.<NDNHNomads, NDNHNomads>chunk(1000)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

	/**
	 * bean that reads each line of the NDNH_Data input file.
	 *
	 * @return ItemReader
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@Bean
	public ItemReader<NDNH_Data> NDNHReader() throws Exception {
		FlatFileItemReader<NDNH_Data> NDNHreader = new FlatFileItemReader<NDNH_Data>();
		NDNHreader.setResource(new FileSystemResource(fileProps.getNDNHRecvFile()));
		NDNHreader.setStrict(false);
		NDNHreader.setLinesToSkip(1); // Skip the header

		Map<String, PropertyEditor> editorMap = new HashMap<String, PropertyEditor>();
		editorMap.put("org.springframework.batch.item.file.transform.Range[]", new RangeArrayPropertyEditor());
		editorMap.put("java.util.Date", dateEditor());
		editorMap.put("java.sql.Date", sqlDateEditor());
		editorMap.put("java.sql.Timestamp", timeEditor());

		// Create tokenizers
		NDNH_DataFixedLengthTokenizer ndflt = new NDNH_DataFixedLengthTokenizer(NDNH_Data.class);
		NDNH_AuditFixedLengthTokenizer naflt = new NDNH_AuditFixedLengthTokenizer();
		NDNH_TrailerFixedLengthTokenizer ntflt = new NDNH_TrailerFixedLengthTokenizer();

		PatternMatchingCompositeLineTokenizer compositeTokenizer = new PatternMatchingCompositeLineTokenizer();

		Map<String, LineTokenizer> tokenMap = new HashMap<String, LineTokenizer>();
		tokenMap.put("SNPW4M*", ndflt);
		tokenMap.put("SNPERM*", naflt);
		tokenMap.put("SNPMTT*", ntflt);

		BeanWrapperFieldSetMapper<NDNH_Data> ndnhdataMapper = new BeanWrapperFieldSetMapper<NDNH_Data>();

		NDNHAuditBeanWrapperMapper ndnhauditMapper = new NDNHAuditBeanWrapperMapper();
		NDNHAuditBeanWrapperMapper ndnhtrailerMapper = new NDNHAuditBeanWrapperMapper();  //??? seems this is unnecessary- reuse the one already created

		ndnhdataMapper.setCustomEditors(editorMap);
		ndnhdataMapper.setTargetType(NDNH_Data.class);
		ndnhauditMapper.setCustomEditors(editorMap);
		ndnhauditMapper.setTargetType(NDNH_Audit.class);

		Map<String, FieldSetMapper> fieldMappers = new HashMap<String, FieldSetMapper>();
		fieldMappers.put("SNPW4M*", ndnhdataMapper);
		fieldMappers.put("SNPERM*", ndnhauditMapper);
		fieldMappers.put("SNPMTT*", ndnhtrailerMapper);

		PatternMatchingCompositeLineMapper compositeMapper = new PatternMatchingCompositeLineMapper();
		compositeMapper.setTokenizers(tokenMap);
		compositeMapper.setFieldSetMappers(fieldMappers);

		NDNHreader.setLineMapper(compositeMapper);

		return NDNHreader;
	}

	/**
	 * The ItemProcessor is called after a new line is read and it allows the
	 * developer to transform the data read In our example it simply return the
	 * original object. Right now nothing is happening. If something needs to
	 * happen A ClassifierCompositeItemProcessor will be needed with appropriate
	 * Classifiers (see Spring-Retry project for more details
	 *
	 * @return
	 */
	@Bean
	public ItemProcessor NDNHprocessor() {
		return new PassThroughItemProcessor();
	}

	/*
	 * This bean writes flat file to NDNH_Data to the database via JPA since it
	 * is a straightforward mapping
	 */
	@Bean
	@Transactional
	public ItemWriter jpaWriter() {
		CompositeItemWriter compWriter = new CompositeItemWriter();

		JpaItemWriter<NDNH_Data> dataWriter = new JpaItemWriter<NDNH_Data>();
		dataWriter.setEntityManagerFactory(nomadsManagerFactory().getObject());
		JpaItemWriter<NDNH_Audit> auditWriter = new JpaItemWriter<NDNH_Audit>();
		auditWriter.setEntityManagerFactory(nomadsManagerFactory().getObject());

		List<ItemWriter> iList = new ArrayList<ItemWriter>();
		iList.add(dataWriter);
		iList.add(auditWriter);
		compWriter.setDelegates(iList);

		return compWriter;
	}

	/**
	 * Receive Job and associated step(s)
	 *
	 * @param JobBuilderFactory
	 * @param Step
	 * @return Job
	 */

	@Bean
	public Job receiveNDNHJob(JobBuilderFactory jobs, @Qualifier(Constants.receiveStep) Step receiveStep, JobCompletionNotificationListener listener) {
		return jobs.get(Constants.receiveJobName)
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(receiveStep)
				.end()
				.build();
	}

	/**
	 * Step receive We declare that every 1000 lines processed the data has to
	 * be committed
	 *
	 * @param stepBuilderFactory
	 * @param reader
	 * @param writer
	 * @param processor
	 * @return Taskletstep
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public Step receiveStep(StepBuilderFactory stepBuilderFactory, 
			@Qualifier("NDNHReader") ItemReader<NDNH_Data> reader,
			@Qualifier("jpaWriter") ItemWriter writer,
			@Qualifier("NDNHprocessor") ItemProcessor processor) {
		return stepBuilderFactory.get(Constants.receiveStep)
				.transactionManager(nomadstm())
				.allowStartIfComplete(true)
				.<NDNH_Data, NDNH_Data>chunk(1000)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

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
		dataSource.setDriverClassName(batchDatabaseProps.getDriver());
		dataSource.setUrl(batchDatabaseProps.getUrl());
		dataSource.setUsername(batchDatabaseProps.getUsername());
		dataSource.setPassword(batchDatabaseProps.getPassword());
		return dataSource;
	}

	@Bean(name = "nomadsDatasource")
	public DataSource nomadsDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(nomadsProps.getDriver());
		dataSource.setUrl(nomadsProps.getUrl());
		dataSource.setUsername(nomadsProps.getUsername());
		dataSource.setPassword(nomadsProps.getPassword());
		dataSource.setSchema(nomadsProps.getSchema());

		return dataSource;
	}

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		transactionManager.setDataSource(batchDataSource());
		return transactionManager;
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
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setPackagesToScan(Constants.base_package);
		lef.setDataSource(batchDataSource());
		lef.setJpaVendorAdapter(jpaVendorAdapter());
		lef.setJpaProperties(new Properties());
		lef.afterPropertiesSet();

		return lef;
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