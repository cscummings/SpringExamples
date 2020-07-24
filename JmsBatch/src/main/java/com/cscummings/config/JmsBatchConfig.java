package com.cscummings.config;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.jms.JmsItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

import com.cscummings.model.ExternalJobResponseAPI;
import com.cscummings.model.MqFormResp;

@EnableJms
@Configuration
@EnableBatchProcessing
public class JmsBatchConfig {

	public static final Logger logger = LoggerFactory.getLogger(JmsBatchConfig.class.getName());
	@Value("${project.mq.host}")
	private String host;
	@Value("${project.mq.port}")
	private Integer port;
	@Value("${project.mq.queue-manager}")
	private String queueManager;
	@Value("${project.mq.channel}")
	private String channel;
	@Value("${project.mq.username}")
	private String username;
	@Value("${project.mq.password}")
	private String password;
	@Value("${project.mq.queue}")
	private String queue;
	@Value("${project.mq.responseQueue}")
	private String responseQueue;

	public final String getQueue() {
		return queue;
	}

	@Value("${project.mq.receive-timeout}")
	private long receiveTimeout;

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsBatchConfig.class);
	private CountDownLatch latch = new CountDownLatch(1);

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	Jaxb2Marshaller marshaller() {
	    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	    marshaller.setClassesToBeBound(ExternalJobResponseAPI.class);
	    return marshaller;
	}

	@Bean
	public JmsListenerContainerFactory<?> queueListenerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setMessageConverter(messageConverter());
		return factory;
	}

	@Bean
	MessageConverter messageConverter() {
	    MarshallingMessageConverter converter = new MarshallingMessageConverter();
	    converter.setMarshaller(marshaller());
	    converter.setUnmarshaller(marshaller());
		return converter;
	}
	@Bean
	@Primary
	public PlatformTransactionManager transactionManager() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		transactionManager.setDataSource(nomadsDataSource());
		return transactionManager;
	}
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		//lef.setPackagesToScan(Constants.base_package);
		lef.setDataSource(nomadsDataSource());
		lef.setJpaVendorAdapter(jpaVendorAdapter());
		lef.setJpaProperties(new Properties());
		lef.afterPropertiesSet();

		return lef;
	}

	@Bean(name = "nomadsDatasource")
	public DataSource nomadsDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(nomadsProps.getDriver());
//		dataSource.setUrl(nomadsProps.getUrl());
//		dataSource.setUsername(nomadsProps.getUsername());
//		dataSource.setPassword(nomadsProps.getPassword());
//		dataSource.setSchema(nomadsProps.getSchema());

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
	public JmsItemReader externalResponseJobAPIJmsItemReader(MessageConverter messageConverter) {
		JmsItemReader externalResponseJobAPIJmsItemReader = new JmsItemReader<>();
		externalResponseJobAPIJmsItemReader.setJmsTemplate(jmsTemplate);
		externalResponseJobAPIJmsItemReader.setItemType(ExternalJobResponseAPI.class);
		return externalResponseJobAPIJmsItemReader;
	}

	@Bean
	public JpaItemWriter<MqFormResp> MqFormRespItemWriter() {
	    JpaItemWriter<MqFormResp> MqFormRespJpaItemWriter = new JpaItemWriter<>();
	    MqFormRespJpaItemWriter.setEntityManagerFactory(entityManagerFactory().getObject());
	    return MqFormRespJpaItemWriter;
	}

	@Bean
	public Job importUserJob() {
		return jobBuilderFactory.get("MQMessageAuditingJob").incrementer(new RunIdIncrementer())
				.listener(jobExecutionListener()).flow(step1()).end().build();
	}

	private Step step1() {
		return stepBuilderFactory.get("step1").<ExternalJobResponseAPI, ExternalJobResponseAPI>chunk(10).reader(externalResponseJobAPIJmsItemReader(messageConverter()))
				.writer(MqFormRespItemWriter()).build();
	}

	@Bean
	public JobExecutionListener jobExecutionListener() {
		return new JobExecutionListener() {
//			@Override
//			public void beforeJob(JobExecution jobExecution) {
//				ExternalJobResponseAPI[] jobResponses = { new Person("Jack", "Ryan"), new Person("Raymond", "Red"),
//						new Person("Olivia", "Dunham"), new Person("Walter", "Bishop"), new Person("Harry", "Bosch") };
//				for (ExternalJobResponseAPI jobResponse : jobResponses) {
//					logger.info(jobResponse.toString());
//					//jmsTemplate.convertAndSend(jobResponse);
//				}
//			}

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
