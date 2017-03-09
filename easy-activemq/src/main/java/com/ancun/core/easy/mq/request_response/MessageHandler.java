package com.ancun.core.easy.mq.request_response;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Map;

/**
 * The interface Message handler.
 *
 * @author Paul
 * @date 2016 /10/10
 */
public interface MessageHandler {

    /**
     * Handle map.
     *
     * @param message the message
     * @return the map
     */
    Map<String, Object> handle(Message message) throws JMSException;

}
