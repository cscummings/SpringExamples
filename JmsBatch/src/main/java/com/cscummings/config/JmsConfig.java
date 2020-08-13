package com.cscummings.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.jms.JMSException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import com.cscummings.jms.ExternalJobResponseAPIMessageConverter;
import com.cscummings.model.ExternalJobRequestAPI;
import com.cscummings.model.ExternalJobResponseAPI;

@Configuration
public class JmsConfig {
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

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsConfig.class);
	private CountDownLatch latch = new CountDownLatch(1);

	@Bean
	Jaxb2Marshaller marshaller() throws PropertyException {
	    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	    marshaller.setClassesToBeBound(ExternalJobResponseAPI.class, ExternalJobRequestAPI.class);
        Map<String, Object> marshallerProperties = new HashMap<>();
        Map<String, Object> unMarshallerProperties = new HashMap<>();
        marshallerProperties.put(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        unMarshallerProperties.put(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.setMarshallerProperties(marshallerProperties);
	    return marshaller;
	}

	@Bean
	public JmsListenerContainerFactory<?> queueListenerFactory() throws PropertyException {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setMessageConverter(messageConverter());
		return factory;
	}
	
	@Bean
	public MQQueueConnectionFactory mqQueueConnectionFactory() {
		MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
		mqQueueConnectionFactory.setHostName(host);
		try {
			mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
			mqQueueConnectionFactory.setCCSID(819);
			mqQueueConnectionFactory.setChannel(channel);
			mqQueueConnectionFactory.setPort(port);
			mqQueueConnectionFactory.setQueueManager(queueManager);
			mqQueueConnectionFactory.setFailIfQuiesce(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mqQueueConnectionFactory;
	}
	
	@Bean
	UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(
			MQQueueConnectionFactory mqQueueConnectionFactory) {
		UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
		userCredentialsConnectionFactoryAdapter.setUsername(username);
		userCredentialsConnectionFactoryAdapter.setPassword(password);
		userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory);
		return userCredentialsConnectionFactoryAdapter;
	}

	@Bean
	@Primary
	public CachingConnectionFactory cachingConnectionFactory(
			UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter) throws JMSException {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter);
		cachingConnectionFactory.setSessionCacheSize(500);
		cachingConnectionFactory.setReconnectOnException(true);
		return cachingConnectionFactory;
	}

	@Bean
	public PlatformTransactionManager jmsTransactionManager(CachingConnectionFactory  cachingConnectionFactory) {
		JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
		jmsTransactionManager.setConnectionFactory(cachingConnectionFactory);
		return jmsTransactionManager;
	}

	@Bean
	MessageConverter messageConverter() throws PropertyException {
	    ExternalJobResponseAPIMessageConverter converter = new ExternalJobResponseAPIMessageConverter();
	    converter.setMarshaller(marshaller());
	    converter.setUnmarshaller(marshaller());
	    converter.setTargetType(MessageType.OBJECT);
		return converter;
	}
	
	@Bean
	public JmsTemplate jmsOperations(CachingConnectionFactory cachingConnectionFactory) throws PropertyException {
		JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
		jmsTemplate.setReceiveTimeout(receiveTimeout);
		jmsTemplate.setMessageConverter(messageConverter());
		jmsTemplate.setDefaultDestinationName(queue);
		return jmsTemplate;
	}	

}
