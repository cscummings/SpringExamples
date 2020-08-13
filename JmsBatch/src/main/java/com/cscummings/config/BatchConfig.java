package com.cscummings.config;

import java.util.List;

import javax.sql.DataSource;
import javax.xml.bind.PropertyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.jms.JmsItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.cscummings.common.Constants;
import com.cscummings.model.ExternalJobResponseAPI;
import com.cscummings.model.MqFormResp;
import com.cscummings.processor.ExternalJobResponseAPIProcessor;


@EnableJms
@Configuration
@Import({ JmsConfig.class, DataBaseConfig.class })
@ComponentScan(basePackages = { Constants.base_package, "com.cscummings.common", "com.cscummings.model", "com.cscummings.config",
		"com.cscummings.jms" })
//@PropertySource(value = { "file:${spring.profiles.active}.properties" }, ignoreResourceNotFound = true)
@PropertySource(value = { "file:${spring.config.location}${spring.profiles.active}.properties"}, ignoreResourceNotFound = false)

@EnableBatchProcessing
public class BatchConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfig.class);	
	@Value("spring.mail.host")
	String mailHost; 
	
	@Value("spring.mail.port")
	String mailPort;
	
	@Value("spring.mail.username")
	String username;
	
	@Value("spring.mail.to")
	String recipients;

	@Autowired
	Environment env;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public JmsConfig jmsConfig;

	@Autowired
	public DataBaseConfig dataBaseConfig;
	
    @Autowired
    public JavaMailSender emailSender;

	@Bean
	BatchConfigurer configurer(@Qualifier("batchDataSource") DataSource dataSource) {
		return new DefaultBatchConfigurer(dataSource);
	}

	/**
	 * Property placeholder configurer needed to process @Value annotations
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public JmsItemReader externalResponseJobAPIJmsItemReader(MessageConverter messageConverter) {
		JmsItemReader externalResponseJobAPIJmsItemReader = new JmsItemReader<>();
		externalResponseJobAPIJmsItemReader.setJmsTemplate(jmsTemplate);
		externalResponseJobAPIJmsItemReader.setItemType(ExternalJobResponseAPI.class);
		return externalResponseJobAPIJmsItemReader;
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

	
	/**
	 * step2 will send email if batch job fails
	 * 
	 */
	@Bean
	public Step emailOnError() {
		return stepBuilderFactory.get("emailOnError").tasklet((contribution, chunkContext) -> {
			JobExecution jobExecution = chunkContext.getStepContext().getStepExecution().getJobExecution();
			StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next(); // TODO properly get the
																								// stepExecution of the
																								// previous step
			List<Throwable> failureExceptions = stepExecution.getFailureExceptions();
			if (!failureExceptions.isEmpty()) {
				Throwable throwable = failureExceptions.get(0);
		        SimpleMailMessage msg = new SimpleMailMessage();
		        msg.setTo(recipients);
		        msg.setFrom(username);
		        msg.setSubject("MQ Monitor batch error occurred");
		        msg.setText(throwable.getMessage());

		        emailSender.send(msg);

				LOGGER.info("Looks like processSMARTCOMqueue has thrown an exception: " + throwable.getMessage());
			}
			return RepeatStatus.FINISHED;
		}).build();
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
