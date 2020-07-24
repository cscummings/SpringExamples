package gov.nv.dwss.processor;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;

import gov.nv.dwss.model.ExternalJobResponseAPI;
import gov.nv.dwss.model.MqFormResp;

public class ExternalJobResponseAPIProcessor implements ItemProcessor<ExternalJobResponseAPI, MqFormResp> {

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
		System.out.println(mqFormResp);
		return mqFormResp;
	}


}
