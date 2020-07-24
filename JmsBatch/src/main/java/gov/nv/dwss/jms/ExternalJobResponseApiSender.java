package gov.nv.dwss.jms;
import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import gov.nv.dwss.config.JmsConfig;

public class ExternalJobResponseApiSender {
    @Autowired
    private ConnectionFactory connectionFactory;
    
    @Autowired
    private JmsConfig jmsConfig;
    
    private JmsTemplate jmsTemplate;

    @PostConstruct
    public void init() {
        this.jmsTemplate = new JmsTemplate(connectionFactory);
    }

    public void sendMessage(String message) {
        System.out.println("sending: " + message);
        jmsTemplate.send(jmsConfig.getQueue(), new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
}
