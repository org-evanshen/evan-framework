package com.ancun.core.easy.mq.request_response;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.JmsUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

/**
 * The type Amq client.
 *
 * @author Paul
 * @date 2016 /10/10
 */
public class AmqClient {

    /**
     * The constant QUEUE_NAME.
     */
    public static final String QUEUE_NAME = "req_res.queue";

    private static final class CorrelationIdPostProcessor implements MessagePostProcessor {

        private final String correlationId;

        /**
         * Instantiates a new Correlation id post processor.
         *
         * @param correlationId the correlation id
         */
        public CorrelationIdPostProcessor(final String correlationId) {
            this.correlationId = correlationId;
        }

        @Override
        public Message postProcessMessage(final Message msg) throws JMSException {
            msg.setJMSCorrelationID(correlationId);
            return msg;
        }
    }

    private final JmsTemplate jmsTemplate;

    /**
     * Instantiates a new Amq client.
     *
     * @param jmsTemplate the jms template
     */
    public AmqClient(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Request string.
     *
     * @param request the request
     * @return the string
     */
    public String request(final Object request) {
        try {
            final String correlationId = UUID.randomUUID().toString();

            jmsTemplate.send(QUEUE_NAME + ".request", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    Message msg = jmsTemplate.getMessageConverter().toMessage(request, session);
                    msg.setJMSReplyTo(session.createQueue(QUEUE_NAME + ".response"));
                    return new CorrelationIdPostProcessor(correlationId).postProcessMessage(msg);
                }
            });
            return (String) jmsTemplate.receiveSelectedAndConvert(QUEUE_NAME +
                    ".response", "JMSCorrelationID='" + correlationId + "'");
        } catch (Exception e) {
            JMSException jmsException = new JMSException(e.getMessage());
            jmsException.setLinkedException(e);
            throw JmsUtils.convertJmsAccessException(jmsException);
        }
    }

}
