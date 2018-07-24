package com.inspireminds.service.jms;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import com.inspireminds.entity.ExchangeValue;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

@Service("currencyConversionJMSPubSub")
public class CurrencyConversionJMSPubSub {

	private MessageProducer producer;
	private Session session;
	private XStream xstream;

	static {
		System.out.println("currencyConversionJMSPublisher started ************************* ");
	}

	public CurrencyConversionJMSPubSub() {
		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue("CCS.TO.FOREX");

			// Create a MessageProducer from the Session to the Topic or Queue
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.setMode(XStream.NO_REFERENCES);
			xstream.alias("ExchangeValue", ExchangeValue.class);

		} catch (Throwable ex) {
			ex.printStackTrace();
		}

	}

	public void publish() {
		try {

			// Create a messages
			String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
			TextMessage message = session.createTextMessage(text);
			//session.createTemporaryQueue();
			//message.setJMSReplyTo(arg0);
			// Tell the producer to send the message
			System.out.println("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
			producer.send(message);
			

			// Clean up
			// session.close();
			// connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	public void publish(ExchangeValue value) {
		try {

			// Create a messages
			//String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
			String jsonMsg = xstream.toXML(value);
			TextMessage message = session.createTextMessage(jsonMsg);

			Destination replyQueue = session.createTemporaryQueue();
			message.setJMSReplyTo(replyQueue);
			
			MessageConsumer responseConsumer = session.createConsumer(replyQueue);
			responseConsumer.setMessageListener(new MessageListener() {

				public void onMessage(Message message) {
					try {
					System.out.println(String.format("CCS JMS consume Received :%s ", message));
					//
					if(message instanceof TextMessage) {
						String txtMsg = ((TextMessage) message).getText();
						System.out.println("Value is :"+txtMsg);
						
					}
					}catch(Throwable t) {
						t.printStackTrace();
					}
				}
				
			});
			
			// Tell the producer to send the message
			System.out.println("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
			producer.send(message);

			// Clean up
			// session.close();
			// connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}
}
