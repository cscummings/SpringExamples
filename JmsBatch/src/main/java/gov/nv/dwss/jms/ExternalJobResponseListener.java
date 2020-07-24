package gov.nv.dwss.jms;

import java.util.concurrent.CountDownLatch;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.nv.dwss.config.BatchConfig;
import gov.nv.dwss.model.ExternalJobResponseAPI;

@Component
public class ExternalJobResponseListener implements MessageListener {

	@Autowired
	BatchConfig jmsConfig;

	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalJobResponseListener.class);

	private CountDownLatch latch = new CountDownLatch(1);
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public ExternalJobResponseListener() {
		super();
	}

	// @JmsListener(destination = "${project.mq.queue}")
	public void receive(ExternalJobResponseAPI jobResponse) {
		LOGGER.info("received jobResponse='{}'", jobResponse);
		latch.countDown();
	}

	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				String stringMessage = textMessage.getText();
				LOGGER.info("received message ='{}'", stringMessage);
			}
		} catch (JMSException e) {
			// catch error
		}
	}

}
