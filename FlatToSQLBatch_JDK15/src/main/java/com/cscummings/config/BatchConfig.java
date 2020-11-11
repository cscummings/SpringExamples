package com.cscummings.config;

import javax.sql.DataSource;
import javax.xml.bind.PropertyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.RecordFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;

import com.cscummings.common.Constants;
import com.cscummings.model.ExternalJobResponseAPI;
import com.cscummings.model.Flat_Data;
import com.cscummings.model.MqFormResp;
import com.cscummings.processor.ExternalJobResponseAPIProcessor;


@Configuration
@Import({ DataBaseConfig.class })
@ComponentScan(basePackages = { Constants.base_package, "com.cscummings.common", "com.cscummings.model", "com.cscummings.config",
		"com.cscummings.jms" })
//@PropertySource(value = { "file:${spring.profiles.active}.properties" }, ignoreResourceNotFound = true)
@PropertySource(value = { "file:${spring.config.location}${spring.profiles.active}.properties"}, ignoreResourceNotFound = false)

@EnableBatchProcessing
public class BatchConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfig.class);	
	@Autowired
	Environment env;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataBaseConfig dataBaseConfig;
	

	@Bean
	BatchConfigurer configurer(@Qualifier("batchDataSource") DataSource dataSource) {
		return new DefaultBatchConfigurer(dataSource);
	}

	//JDK 15 Record
	public record Person(int id, String name) { }
	
	
	/**
	 * Property placeholder configurer needed to process @Value annotations
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	//JDK 15 Spring 4.3 Flatfile Item  reader to handle a record

	/**
	 * bean that reads each line of the Flat_Data input file.
	 *
	 * @return ItemReader
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@Bean
	public FlatFileItemReader<Flat_Data> recordReader() throws Exception {
		return new FlatFileItemReaderBuilder<Flat_Data>()
				.name("personReader")
				.resource(new FileSystemResource("persons.csv"))
				.delimited()
				.names("id", "name")
				.fieldSetMapper(new RecordFieldSetMapper<>(Flat_Data.class))
				.build();
//		FlatFileItemReader<Flat_Data> FlatFileRecordReader = new FlatFileItemReader<Flat_Data>();
//		FlatFileRecordReader.setResource(new FileSystemResource(fileProps.getNDNHRecvFile()));
//		FlatFileRecordReader.setStrict(false);
//		FlatFileRecordReader.setLinesToSkip(1); // Skip the header
//
//		PatternMatchingCompositeLineMapper compositeMapper = new PatternMatchingCompositeLineMapper();
//		Map<String, PropertyEditor> editorMap = new HashMap<String, PropertyEditor>();
//		editorMap.put("org.springframework.batch.item.file.transform.Range[]", new RangeArrayPropertyEditor());
//		editorMap.put("java.util.Date", dateEditor());
//		editorMap.put("java.sql.Date", sqlDateEditor());
//		editorMap.put("java.sql.Timestamp", timeEditor());
//
//		// Create tokenizers
//		NDNH_DataFixedLengthTokenizer ndflt = new NDNH_DataFixedLengthTokenizer(Flat_Data.class);
//		NDNH_AuditFixedLengthTokenizer naflt = new NDNH_AuditFixedLengthTokenizer();
//		NDNH_TrailerFixedLengthTokenizer ntflt = new NDNH_TrailerFixedLengthTokenizer();
//
//		PatternMatchingCompositeLineTokenizer compositeTokenizer = new PatternMatchingCompositeLineTokenizer();
//
//		Map<String, LineTokenizer> tokenMap = new HashMap<String, LineTokenizer>();
//		tokenMap.put("SNPW4M*", ndflt);
//		tokenMap.put("SNPERM*", naflt);
//		tokenMap.put("SNPMTT*", ntflt);
//
//		BeanWrapperFieldSetMapper<Flat_Data> ndnhdataMapper = new BeanWrapperFieldSetMapper<Flat_Data>();
//
//		NDNHAuditBeanWrapperMapper ndnhauditMapper = new NDNHAuditBeanWrapperMapper();
//		NDNHAuditBeanWrapperMapper ndnhtrailerMapper = new NDNHAuditBeanWrapperMapper();  //??? seems this is unnecessary- reuse the one already created
//
//		ndnhdataMapper.setCustomEditors(editorMap);
//		ndnhdataMapper.setTargetType(Flat_Data.class);
//		ndnhauditMapper.setCustomEditors(editorMap);
//		ndnhauditMapper.setTargetType(NDNH_Audit.class);
//
//		Map<String, FieldSetMapper> fieldMappers = new HashMap<String, FieldSetMapper>();
//		fieldMappers.put("SNPW4M*", ndnhdataMapper);
//		fieldMappers.put("SNPERM*", ndnhauditMapper);
//		fieldMappers.put("SNPMTT*", ndnhtrailerMapper);
//
//		compositeMapper.setTokenizers(tokenMap);
//		compositeMapper.setFieldSetMappers(fieldMappers);
//
//		FlatFileRecordReader.setLineMapper(compositeMapper);

		return FlatFileRecordReader;
	}
	@Bean
	public ItemProcessor<ExternalJobResponseAPI, MqFormResp> processor() {
		return new ExternalJobResponseAPIProcessor();
	}

	@Bean
	public JpaItemWriter<MqFormResp> MqFormRespItemWriter() {
		JpaItemWriter<MqFormResp> MqFormRespJpaItemWriter = new JpaItemWriter<>();
		MqFormRespJpaItemWriter.setEntityManagerFactory(dataBaseConfig.nomadsManagerFactory().getObject());
		return MqFormRespJpaItemWriter;
	}

	@Bean
	public Job MQMessageAuditingJob() throws PropertyException {
		return jobBuilderFactory.get("MQMessageAuditingJob").incrementer(new RunIdIncrementer())
				.listener(jobExecutionListener()).flow(processSMARTCOMqueue()).end().build();
	}

	@SuppressWarnings("unchecked")
	private Step processSMARTCOMqueue() throws PropertyException {
		return ((SimpleStepBuilder<ExternalJobResponseAPI, ExternalJobResponseAPI>) stepBuilderFactory.get("processSMARTCOMqueue")
				.<ExternalJobResponseAPI, ExternalJobResponseAPI>chunk(10)
				.transactionManager(dataBaseConfig.nomadstm()))
						.reader(externalResponseJobAPIJmsItemReader(jmsConfig.messageConverter()))
						.processor(processor()).writer(MqFormRespItemWriter()).build();
	}

	
	@Bean
	public JobExecutionListener jobExecutionListener() {
		return new JobExecutionListener() {
			@Override
			public void afterJob(JobExecution jobExecution) {

			}

			@Override
			public void beforeJob(JobExecution jobExecution) {
				// TODO Auto-generated method stub

			}
		};
	}
}
