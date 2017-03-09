package com.ancun.core.easy.mq.provider;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.Assert;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Map;

/**
 * create at 9/2/16 4:26 PM
 *
 * @author <a href="mailto:taofei@ancun.com">Paul Yao</a>
 * @version %I%, %G%
 * @see
 */
public abstract class AbstractMQProvider {

    protected final JmsTemplate jmsTemplate;

    public AbstractMQProvider(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * do send message
     * @param queueMessage
     * @param queueName
     * @param map
     */
    protected void sendMessage(Object queueMessage, String queueName, final Map<String, String> map) {
        Assert.notNull(queueMessage, "message can not be null.");
        Assert.hasText(queueName, "queue name can not be empty.");

        // 附带属性值消息
        if (map != null && !map.isEmpty() && map.size() > 0) {
            jmsTemplate.convertAndSend(queueName, queueMessage, new org.springframework.jms.core.MessagePostProcessor() {
                public Message postProcessMessage(Message message) throws JMSException {
                    for (String key : map.keySet()) {
                        message.setStringProperty(key, map.get(key));// 添加消息属性
                    }
                    return message;
                }
            });
        } else {
            jmsTemplate.convertAndSend(queueName, queueMessage);
        }
    }

}
