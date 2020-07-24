package gov.nv.dwss.common;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	zOSExitCodeMapper zOSExitCodeMapper = new zOSExitCodeMapper();
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		
		Map<String, Integer> theMap = zOSExitCodeMapper.getMapping();
	
		ExitStatus es = jobExecution.getExitStatus();
		String ecode = es.getExitCode();
		
		Integer zosCode = theMap.get(jobExecution.getExitStatus().getExitCode());
		setZOSJobStatus(zosCode, jobExecution);
		
		log.info("Status Code: " + jobExecution.getExitStatus());
	}
	
	public void setZOSJobStatus(Integer zosCode, JobExecution jobExecution) {
		ExitStatus es = new ExitStatus(Integer.toString(zosCode), jobExecution.getJobConfigurationName());
		es.replaceExitCode(Integer.toString(zosCode));
		jobExecution.setExitStatus(es);
		
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		
	}
}
