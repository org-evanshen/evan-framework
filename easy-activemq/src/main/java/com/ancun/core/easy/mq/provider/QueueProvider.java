package com.ancun.core.easy.mq.provider;

import org.springframework.jms.core.JmsTemplate;

import java.util.Map;

/**
 * create at 9/2/16 2:38 PM
 *
 * @author <a href="mailto:taofei@ancun.com">Paul Yao</a>
 * @version %I%, %G%
 * @see
 */
public class QueueProvider extends AbstractMQProvider implements ActiveMQProvider {

    public QueueProvider(JmsTemplate jmsTemplate) {
        super(jmsTemplate);
    }

    @Override
    public void send(Object queueMessage, String queueName) {
        sendMessage(queueMessage, queueName, null);
    }

    @Override
    public void send(Object queueMessage, String queueName, Map<String, String> map) {
        sendMessage(queueMessage, queueName, map);
    }

}
