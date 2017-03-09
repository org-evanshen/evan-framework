package com.ancun.core.easy.mq.provider;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.Assert;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Map;

/**
 * point to point message
 * create at 8/23/16 9:24 PM
 *
 * @author <a href="mailto:taofei@ancun.com">Paul Yao</a>
 * @version %I%, %G%
 * @see
 */
@Deprecated
public class MQProvider {

    private JmsTemplate jmsQueueTemplate;
    private JmsTemplate jmsTopicTemplate;

    public MQProvider(JmsTemplate jmsQueueTemplate, JmsTemplate jmsTopicTemplate) {
        this.jmsQueueTemplate = jmsQueueTemplate;
        this.jmsTopicTemplate = jmsTopicTemplate;
    }

    /**
     * send queue message
     * @param queueMessage
     * @param queueName
     */
    public void sendQueueMessage(Object queueMessage, String queueName) {
        sendMessage(jmsQueueTemplate, queueMessage, queueName, null);
    }

    /**
     * send queue message
     * @param queueMessage
     * @param queueName
     * @param map
     */
    public void sendQueueMessage(Object queueMessage, String queueName, final Map<String, String> map) {
        sendMessage(jmsQueueTemplate, queueMessage, queueName, map);
    }

    /**
     * send topic message
     * @param queueMessage
     * @param queueName
     */
    public void sendTopicMessage(Object queueMessage, String queueName) {
        sendMessage(jmsTopicTemplate, queueMessage, queueName, null);
    }


    /**
     * send topic message
     * @param queueMessage
     * @param queueName
     * @param map
     */
    public void sendTopicMessage(Object queueMessage, String queueName, final Map<String, String> map) {
        sendMessage(jmsTopicTemplate, queueMessage, queueName, map);
    }

    /**
     * do send message
     * @param jmsTemplate
     * @param queueMessage
     * @param queueName
     * @param map
     */
    private void sendMessage(JmsTemplate jmsTemplate, Object queueMessage,
                             String queueName, final Map<String, String> map) {
//        Assert.notNull(queueMessage, "message can not be null.");
//        Assert.hasText(queueName, "queue name can not be empty.");
//
//        // 附带属性值消息
//        if (map != null && !map.isEmpty() && map.size() > 0) {
//            jmsTemplate.convertAndSend(queueName, queueMessage, new org.springframework.jms.core.MessagePostProcessor() {
//                public Message postProcessMessage(Message message) throws JMSException {
//                    for (String key : map.keySet()) {
//                        message.setStringProperty(key, map.get(key));// 添加消息属性
//                    }
//                    return message;
//                }
//            });
//        } else {
//            jmsTemplate.convertAndSend(queueName, queueMessage);
//        }
    }

}
