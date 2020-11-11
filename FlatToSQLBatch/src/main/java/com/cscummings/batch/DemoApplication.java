package com.cscummings.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.context.ConfigurableApplicationContext;

import com.cscummings.batch.common.Constants;

@SpringBootApplication
public class DemoApplication  {
	public static ConfigurableApplicationContext ctx = null;

	public static void main(String[] args) throws Exception {
		//SpringApplication.exit(context, exitCodeGenerators)
		//System.exit(SpringApplication.exit(SpringApplication.run(
		//	           BatchConfiguration.class, args)));
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.setWebEnvironment(false);
//		app.setBannerMode(Banner.Mode.OFF);
		ctx = app.run(args);

	    JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
	  
		//SpringApplication.run(DemoApplication.class, args);
	    //for Spring Boot:  System.exit(SpringApplication.exit(SpringApplication.run(
	    //        BatchConfiguration.class, args)));
		
/*	    or java org.springframework.batch.core.launch.support.CommandLineJobRunner com.cscummings.batch.configuration.BatchConfig myJob x=100 y=foo
		  
		$ java -cp "target/dependency-jars/*:target/your-project.jar" org.springframework.batch.core.launch.support.CommandLineJobRunner spring/batch/jobs/job-read-files.xml readJob file.name=testing.cvs  
		
		or JobLauncherCommandLineRunner ???
		   CommandLineJobRunner.main(new String[]{"fileWritingJob.xml", "LayeredMultiThreadJobTest"});
*/		
		
	/*	 
	
		 SpringApplication app = new SpringApplication(DemoApplication.class);
	    app.setWebEnvironment(false);
	    ConfigurableApplicationContext ctx = app.run(args);

	    JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
	    Job job = ctx.getBean("importNDNH_Data", Job.class);
	    JobParameters jobParameters = new JobParametersBuilder().toJobParameters();


	    JobExecution jobExecution = jobLauncher.run(job, jobParameters);
	    BatchStatus batchStatus = jobExecution.getStatus();		
*/		
	}

}
