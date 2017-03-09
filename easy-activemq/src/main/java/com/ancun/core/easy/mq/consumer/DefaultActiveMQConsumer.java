package com.ancun.core.easy.mq.consumer;

import com.ancun.core.easy.mq.ActiveMQConfig;
import com.ancun.core.easy.mq.request_response.ServerReplyListener;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.MessageListener;

/**
 * Created by Paul on 2016/9/8.
 */
public class DefaultActiveMQConsumer implements ActiveMQConsumer {

    private final ActiveMQConfig activeMQConfig;
    private final ActiveMQConnectionFactory activeMQConnectionFactory;

    public DefaultActiveMQConsumer(ActiveMQConfig activeMQConfig, ActiveMQConnectionFactory activeMQConnectionFactory) {
        this.activeMQConfig = activeMQConfig;
        this.activeMQConnectionFactory = activeMQConnectionFactory;
    }

    @Override
    public void listen2Queue(String queueName, MessageListener listener) {
        listen2Queue(queueName, listener, null);
    }

    @Override
    public void receiveAndReply(ServerReplyListener listener) {
        listener.start();
    }

    @Override
    public void listen2Topic(String queueName, MessageListener listener) {
        listen2Topic(queueName, listener, null);
    }

    /**
     * listen to queue
     *
     * @param queueName
     * @param listener
     * @param messageSelector
     */
    public void listen2Queue(String queueName, MessageListener listener, String messageSelector) {
        start(queueName, listener, messageSelector, false);
    }

    /**
     * listen to topic
     *
     * @param queueName
     * @param listener
     * @param messageSelector
     */
    public void listen2Topic(String queueName, MessageListener listener, String messageSelector) {
        start(queueName, listener, messageSelector, true);
    }

    private void start(String queueName, MessageListener listener,
                       String messageSelector, boolean pubSubDomain) {
        if (null == queueName) {
            return;
        }
        ActiveMQQueue destination = new ActiveMQQueue(queueName);
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        // 监听容器属性的配置
        listenerContainer.setConnectionFactory(activeMQConnectionFactory);
        //设置消息选择器
        if (messageSelector != null && !messageSelector.isEmpty()) {
            listenerContainer.setMessageSelector(messageSelector);
        }
        // 设置目的地
        listenerContainer.setDestination(destination);
        // 设置监听器
        listenerContainer.setMessageListener(listener);
        // 设置消费者个数
        int consumers = activeMQConfig.getConsumers();
        listenerContainer.setConcurrentConsumers(consumers);
        listenerContainer.setPubSubNoLocal(pubSubDomain);
        // listenerContainer.setAcceptMessagesWhileStopping(true);
        // 设置应答模式 默认是4 ====>>>>采用客户端手动应答
        int sessionAcknowledgeMode = activeMQConfig.getSessionAcknowledgeMode();
        listenerContainer.setSessionAcknowledgeMode(sessionAcknowledgeMode);
        // 设置是否启动事物 默认不开启
        listenerContainer.setSessionTransacted(false);
        listenerContainer.initialize();
        // 启动监听
        listenerContainer.start();
    }

}
