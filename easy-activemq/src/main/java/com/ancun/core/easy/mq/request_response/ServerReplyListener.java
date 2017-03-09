package com.ancun.core.easy.mq.request_response;

import com.alibaba.fastjson.JSON;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.jms.support.JmsUtils;

import javax.jms.*;

import static com.ancun.core.easy.mq.request_response.AmqClient.QUEUE_NAME;

/**
 * The type Server reply listener.
 *
 * @author Paul
 * @date 2016 /10/10
 */
public class ServerReplyListener implements MessageListener {

    private Session session;
    private boolean transacted = false;
    private MessageProducer replyProducer;
    private MessageHandler messageHandler;

    private final ActiveMQConnectionFactory connectionFactory;

    /**
     * Instantiates a new Server reply listener.
     *
     * @param activeMQConnectionFactory the active mq connection factory
     * @param messageHandler            the message handler
     */
    public ServerReplyListener(ActiveMQConnectionFactory activeMQConnectionFactory, MessageHandler messageHandler) {
        this.connectionFactory = activeMQConnectionFactory;
        this.messageHandler = messageHandler;
    }

    /**
     * Start.
     */
    public void start() {
        try {
            //This message broker is embedded
            BrokerService broker = new BrokerService();
            broker.setPersistent(false);
            broker.setUseJmx(false);
            broker.addConnector(connectionFactory.getBrokerURL());
            broker.start();
        } catch (Exception e) {
            //Handle the exception appropriately
        }

        //Delegating the handling of messages to another class, instantiate it before setting up JMS so it
        //is ready to handle messages
        this.setupMessageQueueConsumer();
    }

    private void setupMessageQueueConsumer() {

        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            this.session = connection.createSession(this.transacted, Session.AUTO_ACKNOWLEDGE);
            Destination adminQueue = this.session.createQueue(QUEUE_NAME + ".request");

            //Setup a message producer to respond to messages from clients, we will get the destination
            //to send to from the JMSReplyTo header field from a Message
            this.replyProducer = this.session.createProducer(null);
            this.replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            //Set up a consumer to consume messages off of the admin queue
            MessageConsumer consumer = this.session.createConsumer(adminQueue);
            consumer.setMessageListener(this);
        } catch (JMSException e) {
            //Handle the exception appropriately
        }
    }

    @Override
    public void onMessage(Message message) {
        try {

            String result = JSON.toJSONString(messageHandler.handle(message));

            TextMessage response = this.session.createTextMessage();
            if (message instanceof TextMessage) {
                response.setText(result);
            }

            //Set the correlation ID from the received message to be the correlation id of the response message
            //this lets the client identify which message this is a response to if it has more than
            //one outstanding message to the server
            response.setJMSCorrelationID(message.getJMSCorrelationID());

            //Send the response to the Destination specified by the JMSReplyTo field of the received message,
            //this is presumably a temporary queue created by the client
            this.replyProducer.send(message.getJMSReplyTo(), response);
        } catch (JMSException e) {
            throw JmsUtils.convertJmsAccessException(e);
        }
    }
}
