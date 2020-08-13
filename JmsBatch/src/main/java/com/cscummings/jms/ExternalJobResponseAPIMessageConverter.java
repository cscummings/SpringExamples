package com.cscummings.jms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Component;

import com.cscummings.model.ExternalJobResponseAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public class ExternalJobResponseAPIMessageConverter implements MessageConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalJobResponseAPIMessageConverter.class);
	private Marshaller marshaller;

	private Unmarshaller unmarshaller;

	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
		
	}

	private MessageType targetType = MessageType.BYTES;


	ObjectMapper mapper;
	XmlMapper xmlMapper;

	public ExternalJobResponseAPIMessageConverter() {
		mapper = new ObjectMapper();
		xmlMapper = new XmlMapper();
	}

	@Override
	public Message toMessage(Object object, Session session) throws JMSException {
		ExternalJobResponseAPI ejrAPI = (ExternalJobResponseAPI) object;
		String payload = null;
		try {
			payload = mapper.writeValueAsString(ejrAPI);
			LOGGER.info("outbound json='{}'", payload);
		} catch (IOException e) {
			LOGGER.error("error converting form ejrAPI", e);
		}

		TextMessage message = session.createTextMessage();
		message.setText(payload);

		return message;
	}

	/**
	 * This implementation unmarshals the given {@link Message} into an object.
	 * @see #unmarshalFromTextMessage
	 * @see #unmarshalFromBytesMessage
	 */
	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		
		try {
			if (message instanceof TextMessage) {
				System.out.println("this is the value received " + message.getBody(String.class));
				TextMessage textMessage = (TextMessage) message;
				Object obj =  unmarshalFromTextMessage(textMessage, this.unmarshaller);
				ExternalJobResponseAPI ejra = (ExternalJobResponseAPI)obj;
				ejra.setCorrelationID(message.getJMSCorrelationID());
				ejra.setTimeStamp(message.getJMSTimestamp());
				ejra.setStatusText(message.getBody(String.class));
				return ejra;

			}
			else if (message instanceof BytesMessage) {
				BytesMessage bytesMessage = (BytesMessage) message;
				Object obj = unmarshalFromBytesMessage(bytesMessage, this.unmarshaller);
				ExternalJobResponseAPI ejra = (ExternalJobResponseAPI)obj;
				ejra.setCorrelationID(message.getJMSCorrelationID());
				ejra.setTimeStamp(message.getJMSTimestamp());
				ejra.setStatusText(message.getBody(String.class));
				return ejra;
			}
			else {
				return unmarshalFromMessage(message, this.unmarshaller);
			}
		}
		catch (IOException ex) {
			throw new MessageConversionException("Could not access message content: " + message, ex);
		}
		catch (XmlMappingException ex) {
			throw new MessageConversionException("Could not unmarshal message: " + message, ex);
		}
	}

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}
	/**
	 * Marshal the given object to a {@link TextMessage}.
	 * @param object the object to be marshalled
	 * @param session current JMS session
	 * @param marshaller the marshaller to use
	 * @return the resulting message
	 * @throws JMSException if thrown by JMS methods
	 * @throws IOException in case of I/O errors
	 * @throws XmlMappingException in case of OXM mapping errors
	 * @see Session#createTextMessage
	 * @see Marshaller#marshal(Object, Result)
	 */
	protected TextMessage marshalToTextMessage(Object object, Session session, Marshaller marshaller)
			throws JMSException, IOException, XmlMappingException {

		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);
		marshaller.marshal(object, result);
		return session.createTextMessage(writer.toString());
	}

	/**
	 * Marshal the given object to a {@link BytesMessage}.
	 * @param object the object to be marshalled
	 * @param session current JMS session
	 * @param marshaller the marshaller to use
	 * @return the resulting message
	 * @throws JMSException if thrown by JMS methods
	 * @throws IOException in case of I/O errors
	 * @throws XmlMappingException in case of OXM mapping errors
	 * @see Session#createBytesMessage
	 * @see Marshaller#marshal(Object, Result)
	 */
	protected BytesMessage marshalToBytesMessage(Object object, Session session, Marshaller marshaller)
			throws JMSException, IOException, XmlMappingException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
		StreamResult streamResult = new StreamResult(bos);
		marshaller.marshal(object, streamResult);
		BytesMessage message = session.createBytesMessage();
		message.writeBytes(bos.toByteArray());
		return message;
	}

	/**
	 * Template method that allows for custom message marshalling.
	 * Invoked when {@link #setTargetType} is not {@link MessageType#TEXT} or
	 * {@link MessageType#BYTES}.
	 * <p>The default implementation throws an {@link IllegalArgumentException}.
	 * @param object the object to marshal
	 * @param session the JMS session
	 * @param marshaller the marshaller to use
	 * @param targetType the target message type (other than TEXT or BYTES)
	 * @return the resulting message
	 * @throws JMSException if thrown by JMS methods
	 * @throws IOException in case of I/O errors
	 * @throws XmlMappingException in case of OXM mapping errors
	 */
	protected Message marshalToMessage(Object object, Session session, Marshaller marshaller, MessageType targetType)
			throws JMSException, IOException, XmlMappingException {

		throw new IllegalArgumentException("Unsupported message type [" + targetType +
				"]. MarshallingMessageConverter by default only supports TextMessages and BytesMessages.");
	}


	/**
	 * Unmarshal the given {@link TextMessage} into an object.
	 * @param message the message
	 * @param unmarshaller the unmarshaller to use
	 * @return the unmarshalled object
	 * @throws JMSException if thrown by JMS methods
	 * @throws IOException in case of I/O errors
	 * @throws XmlMappingException in case of OXM mapping errors
	 * @see Unmarshaller#unmarshal(Source)
	 */
	protected Object unmarshalFromTextMessage(TextMessage message, Unmarshaller unmarshaller)
			throws JMSException, IOException, XmlMappingException {
		String textString = message.getText();
		Source source = new StreamSource(new StringReader(textString));
		return unmarshaller.unmarshal(source);
	}

	/**
	 * Unmarshal the given {@link BytesMessage} into an object.
	 * @param message the message
	 * @param unmarshaller the unmarshaller to use
	 * @return the unmarshalled object
	 * @throws JMSException if thrown by JMS methods
	 * @throws IOException in case of I/O errors
	 * @throws XmlMappingException in case of OXM mapping errors
	 * @see Unmarshaller#unmarshal(Source)
	 */
	protected Object unmarshalFromBytesMessage(BytesMessage message, Unmarshaller unmarshaller)
			throws JMSException, IOException, XmlMappingException {

		byte[] bytes = new byte[(int) message.getBodyLength()];
		message.readBytes(bytes);
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		StreamSource source = new StreamSource(bis);
		return unmarshaller.unmarshal(source);
	}

	/**
	 * Template method that allows for custom message unmarshalling.
	 * Invoked when {@link #fromMessage(Message)} is invoked with a message
	 * that is not a {@link TextMessage} or {@link BytesMessage}.
	 * <p>The default implementation throws an {@link IllegalArgumentException}.
	 * @param message the message
	 * @param unmarshaller the unmarshaller to use
	 * @return the unmarshalled object
	 * @throws JMSException if thrown by JMS methods
	 * @throws IOException in case of I/O errors
	 * @throws XmlMappingException in case of OXM mapping errors
	 */
	protected Object unmarshalFromMessage(Message message, Unmarshaller unmarshaller)
			throws JMSException, IOException, XmlMappingException {

		throw new IllegalArgumentException("Unsupported message type [" + message.getClass() +
				"]. MarshallingMessageConverter by default only supports TextMessages and BytesMessages.");
	}

	public MessageType getTargetType() {
		return targetType;
	}

	public void setTargetType(MessageType targetType) {
		this.targetType = targetType;
	}
	
}
