package com.ancun.core.easy.mq.consumer;

import com.ancun.core.easy.mq.request_response.ServerReplyListener;

import javax.jms.MessageListener;

/**
 * create at 9/3/16 4:09 PM
 *
 * @author <a href="mailto:taofei@ancun.com">Paul Yao</a>
 * @version %I%, %G%
 * @see
 */
public interface ActiveMQConsumer {

    void receiveAndReply(ServerReplyListener listener);

    void listen2Queue(String queueName, MessageListener listener);

    void listen2Topic(String queueName, MessageListener listener);

    void listen2Queue(String queueName, MessageListener listener, String messageSelector);

    void listen2Topic(String queueName, MessageListener listener, String messageSelector);

}
