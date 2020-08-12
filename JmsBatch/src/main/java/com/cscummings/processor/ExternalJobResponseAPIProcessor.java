package com.cscummings.processor;

import java.sql.Timestamp;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.cscummings.model.ExternalJobResponseAPI;
import com.cscummings.model.MqFormResp;


public class ExternalJobResponseAPIProcessor implements ItemProcessor<ExternalJobResponseAPI, MqFormResp> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalJobResponseAPIProcessor.class);
	@Override
	public MqFormResp process(ExternalJobResponseAPI item) throws Exception {
		MqFormResp mqFormResp = new MqFormResp();
		// map ExternalJobResponseAPI to mqFormResp
		mqFormResp.setIci(item.getBatchName());
		mqFormResp.setBatchId(item.getBatchID());
		String correlationID = item.getCorrelationID();
		if (correlationID == null) correlationID = UUID.randomUUID().toString();
		mqFormResp.setCorrelationId(correlationID);
		mqFormResp.setResponseReceivedTs(new Timestamp(item.getTimeStamp()));
		mqFormResp.setResponseXml(item.getStatusText());
		mqFormResp.setResponseStatus("0");
		LOGGER.debug(mqFormResp.toString());
		return mqFormResp;
	}


}
