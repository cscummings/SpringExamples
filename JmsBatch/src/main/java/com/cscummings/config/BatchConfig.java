package com.cscummings.config;

import javax.sql.DataSource;
import javax.xml.bind.PropertyException;

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
import org.springframework.batch.item.jms.JmsItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

import com.cscummings.common.Constants;
import com.cscummings.model.ExternalJobResponseAPI;
import com.cscummings.model.MqFormResp;
import com.cscummings.processor.ExternalJobResponseAPIProcessor;

@EnableJms
@Configuration
@Import({JmsConfig.class, DataBaseConfig.class})
@ComponentScan(basePackages = { Constants.base_package, "com.cscummings.common",
		"com.cscummings.model", "com.cscummings.config", "com.cscummings.jms" })

@EnableBatchProcessing
public class BatchConfig {

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

    @Bean
    BatchConfigurer configurer(@Qualifier("batchDataSource") DataSource dataSource){
      return new DefaultBatchConfigurer(dataSource);
    }
    
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
	public Job importUserJob() throws PropertyException {
		return jobBuilderFactory.get("MQMessageAuditingJob").incrementer(new RunIdIncrementer())
				.listener(jobExecutionListener()).flow(step1()).end().build();
	}

	@SuppressWarnings("unchecked")
	private Step step1() throws PropertyException {
		return ((SimpleStepBuilder<ExternalJobResponseAPI, ExternalJobResponseAPI>) stepBuilderFactory.get("step1").<ExternalJobResponseAPI, ExternalJobResponseAPI>chunk(10)
				.transactionManager(dataBaseConfig.nomadstm()))
				.reader(externalResponseJobAPIJmsItemReader(jmsConfig.messageConverter())).processor(processor()).writer(MqFormRespItemWriter()).build();
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
