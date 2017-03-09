package com.ancun.core.mq.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.core.SessionCallback;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.ancun.core.mq.ActiveMQHelper;
import com.ancun.core.mq.IActiveMQReceiver;
import com.ancun.core.sysconfig.SysConfig;

/**
 * 消息接口实现
 * <p/>
 * <p/>
 * create at 2016年3月21日 下午6:13:18
 *
 * @author <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a>
 * @version %I%, %G%
 */
public class ActiveMQHelperImpl implements ActiveMQHelper {
    private static final Logger logger = LoggerFactory.getLogger(ActiveMQHelperImpl.class);

    private SysConfig sysConfig;
    private JmsTemplate jmsTemplate;
    private MessagePostProcessor messagePostProcessor;
    private ActiveMQConnectionFactory activeMQConnectionFactory;
    private Map<String, DefaultMessageListenerContainer> holder = new HashMap<String, DefaultMessageListenerContainer>();

    public void init(){
        logger.info("消息中间件brokerUrl[{}]",activeMQConnectionFactory.getBrokerURL());
    }

    /**
     * 统计队列剩余消息数
     *
     * @param queueName 队列名
     *                  <p/>
     *                  <p/>
     *                  author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     *                  create at: 2016年3月18日 下午2:49:23
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Integer getQueueCount(final String queueName) {
        Integer count = (Integer) this.jmsTemplate.execute(new SessionCallback() {
            public Object doInJms(Session session) throws JMSException {
                int count = 0;
                Queue queue = new Queue() {
                    @Override
                    public String getQueueName() throws JMSException {
                        if (null == queueName) {
                            return jmsTemplate.getDefaultDestinationName();
                        } else {
                            return queueName;
                        }
                    }
                };
                QueueBrowser browser = session.createBrowser(queue);
                Enumeration messages = browser.getEnumeration();
                while (messages.hasMoreElements()) {
                    count++;
                    messages.nextElement();
                }
                return new Integer(count);
            }
        }, true);
        return count;
    }

    /**
     * 获取默认队列名
     * <p/>
     * <p/>
     * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     * create at: 2016年3月18日 下午2:50:12
     */
    @Override
    public String getDefaulQueueName() {
        return jmsTemplate.getDefaultDestinationName();
    }

    /**
     * 发送消息到默认队列 by 消息内容
     *
     * @throws JMSException <p/>
     *                      author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     *                      create at: 2016年3月18日 下午2:50:29
     */
    public void sendQueueMessage(Object messageContent) {
        this.sendMessage(messageContent, null, null);
    }

    /**
     * 发送消息到默认队列 by (消息内容、队列名)
     *
     * @param queueName 队列名
     * @throws JMSException <p/>
     *                      author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     *                      create at: 2016年3月18日 下午2:51:30
     */
    @Override
    public void sendQueueMessage(Object messageContent, String queueName) {
        this.sendMessage(messageContent, queueName, null);
    }

    /**
     * 发送消息到默认队列 by (消息内容、队列名、消息属性map)
     *
     * @param messageContent 消息内容
     * @param queueName      队列名
     * @param propertyMap    消息属性map（自定义参数必须是JMS为前缀）
     * @throws JMSException <p/>
     *                      author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     *                      create at: 2016年3月18日 下午2:52:27
     */
    @Override
    public void sendQueueMessage(Object messageContent, String queueName, Map<String, String> propertyMap) {
        this.sendMessage(messageContent, queueName, propertyMap);
    }

    /**
     * 监听接听消息 by (队列名、消息监听处理接口)
     *
     * @param queueName 队列名
     * @param receiver  消息监听处理接口
     *                  <p/>
     *                  author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     *                  create at: 2016年3月18日 下午2:53:44
     */
    @Override
    public void receivQueueMessage(String queueName, IActiveMQReceiver receiver) {
        receivQueueMessageHelper(queueName, receiver, null);
    }

    @Override
    public void receivQueueMessage(String queueName, IActiveMQReceiver receiver, String messageSelector) {
        receivQueueMessageHelper(queueName, receiver, messageSelector);
    }

    /**
     * 根据队列名获取监听容器
     *
     * @param queueName
     */
    @Override
    public AbstractMessageListenerContainer getListenerContainer(String queueName) {
        return holder.get(queueName);
    }

    /**
     * 停止监听消息 by (队列名)
     *
     * @param queueName 队列名
     *                  <p/>
     *                  author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     *                  create at: 2016年3月18日 下午2:54:57
     */
    @Override
    public void stopReceivQueueMessage(String queueName) {
        this.stopReceiverByName(queueName);
    }

    /**
     * 发送消息
     *
     * @param queueMessage 消息
     * @param queueName    队列名
     * @param map          属性map
     * @throws JMSException 消息异常
     *                      <p/>
     *                      author:
     *                      <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     *                      create at: 2016年3月17日 上午11:19:15
     */
    public void sendMessage(Object queueMessage, String queueName, final Map<String, String> map) {
        // 空消息
        if (null == queueMessage) {
            return;
        }
        // 指定对列名消息
        if (null == queueName) {
            queueName = jmsTemplate.getDefaultDestinationName();
        }
        // 附带属性值消息
        if (map != null && !map.isEmpty() && map.size() > 0) {
            jmsTemplate.convertAndSend(queueName, queueMessage, new MessagePostProcessor() {
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

    public void stopReceiverByName(String queueName) {
        String key = new StringBuffer(queueName).toString();
        DefaultMessageListenerContainer listenerContainer = holder.get(key);
        if (listenerContainer != null) {
            // 销毁容器
            listenerContainer.destroy();
            holder.remove(key);
        }
    }

    public MessagePostProcessor getMessagePostProcessor() {
        return messagePostProcessor;
    }

    public void setMessagePostProcessor(MessagePostProcessor messagePostProcessor) {
        this.messagePostProcessor = messagePostProcessor;
    }

    private void receivQueueMessageHelper(String queueName, IActiveMQReceiver receiver, String messageSelector) {
        boolean useMessageSelector = true;
        if (null == messageSelector) {
            useMessageSelector = false;
        }
        if (null == queueName) {
            return;
        }
        ActiveMQQueue destination = new ActiveMQQueue(queueName);
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        // 监听容器属性的配置
        listenerContainer.setConnectionFactory(activeMQConnectionFactory);
        //设置消息选择器
        if (useMessageSelector) {
            listenerContainer.setMessageSelector(messageSelector);
        }
        // 设置目的地
        listenerContainer.setDestination(destination);
        // 设置监听器
        listenerContainer.setMessageListener(receiver);
        // 设置消费者集群数
        int consumers = Integer.valueOf(sysConfig.get("activemq.consumers"));
        listenerContainer.setConcurrentConsumers(consumers);
        // 设置监听队列还是主题 默认是队列
        Boolean pubSubDomain = Boolean.parseBoolean(sysConfig.get("activemq.pubSubDomain"));
        listenerContainer.setPubSubNoLocal(pubSubDomain);
        // listenerContainer.setAcceptMessagesWhileStopping(true);
        // 设置应答模式 默认是4 ====>>>>采用客户端手动应答
        int sessionAcknowledgeMode = Integer.parseInt(sysConfig.get("activemq.sessionAcknowledgeMode"));
        listenerContainer.setSessionAcknowledgeMode(sessionAcknowledgeMode);
        // 设置是否启动事物 默认不开启
        listenerContainer.setSessionTransacted(false);
        // 将监听容器保存在holder中
        holder.put(queueName, listenerContainer);
        listenerContainer.initialize();
        // 启动监听
        listenerContainer.start();
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setActiveMQConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        this.activeMQConnectionFactory = activeMQConnectionFactory;
    }

    public void setSysConfig(SysConfig sysConfig) {
        this.sysConfig = sysConfig;
    }
}
