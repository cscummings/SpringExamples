package com.cscummings.batch.common;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.launch.support.ExitCodeMapper;

/**
 * An implementation of {@link ExitCodeMapper} that can be configured through a
 * map from batch exit codes (String) to integer results. 
 * Modified default entries to comply with standard IBM JCL return codes.
 * (copied from Spring Project) and modified for DWSS
 * JCL Return Code=0(Prog. Executed Successfully) 
 * JCL Return Code=4(Prog. Executed Successfully but with warnings) 
 * JCL Return Code=8(Error) 
 * JCL Return Code=12(Serious Error) 
 * JCL Return Code=16(Fatal Error)
 * 
 * @author Stijn Maller
 * @author Lucas Ward
 * @author Dave Syer
 * @author Connie Cummings
 */

public class zOSExitCodeMapper implements ExitCodeMapper {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());            
	static int ZOS_EXITCODE_COMPLETED = 0;

	static int ZOS_EXITCODE_WARNINGS = 4;

	static int ZOS_EXITCODE_ERROR = 8;
	
	static int ZOS_EXITCODE_SEVERE_ERROR = 12;
	
	static int ZOS_EXITCODE_FATAL_ERROR = 16;

	private Map<String, Integer> mapping;

	public zOSExitCodeMapper() {
		mapping = new HashMap<String, Integer>();
		mapping.put(ExitStatus.COMPLETED.getExitCode(), ZOS_EXITCODE_COMPLETED);
		mapping.put(ExitStatus.FAILED.getExitCode(), ZOS_EXITCODE_SEVERE_ERROR);
		mapping.put(ExitStatus.NOOP.getExitCode(), ZOS_EXITCODE_WARNINGS);		
		mapping.put(ExitCodeMapper.JOB_NOT_PROVIDED, ZOS_EXITCODE_FATAL_ERROR);
		mapping.put(ExitCodeMapper.NO_SUCH_JOB, ZOS_EXITCODE_FATAL_ERROR);
	}

	public Map<String, Integer> getMapping() {
		return mapping;
	}

	/**
	 * Supply the ExitCodeMappings
	 * @param exitCodeMap A set of mappings between environment specific exit
	 * codes and batch framework internal exit codes
	 */
	public void setMapping(Map<String, Integer> exitCodeMap) {
		mapping.putAll(exitCodeMap);
	}

	/**
	 * Get the operating system exit status that matches a certain Batch
	 * Framework exit code
	 * @param exitCode The exit code of the Batch Job as known by the Batch
	 * Framework
	 * @return The exitCode of the Batch Job as known by the JVM
	 */
	@Override
	public int intValue(String exitCode) {

		Integer statusCode = null;

		try {
			statusCode = mapping.get(exitCode);
		}
		catch (RuntimeException ex) {
			// We still need to return an exit code, even if there is an issue
			// with
			// the mapper.
			logger.error("Error mapping exit code, generic exit status returned.", ex);
		}

		return (statusCode != null) ? statusCode.intValue() : ZOS_EXITCODE_SEVERE_ERROR;
	}

}