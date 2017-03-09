package com.ancun.core.easy.mq.provider;

import java.util.Map;

/**
 * create at 9/2/16 2:50 PM
 *
 * @author <a href="mailto:taofei@ancun.com">Paul Yao</a>
 * @version %I%, %G%
 * @see
 */
public interface ActiveMQProvider {

    void send(Object queueMessage, String queueName);

    void send(Object queueMessage, String queueName, final Map<String, String> map);

}
