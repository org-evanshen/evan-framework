package com.ancun.core.mq;

import java.util.Map;

import javax.jms.JMSException;

import org.springframework.jms.listener.AbstractMessageListenerContainer;

/**
 * 消息接口
 * 
 * <p>
 * create at 2016年3月21日 下午6:13:40
 * @author <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a>
 * @version %I%, %G%
 *
 */
public interface ActiveMQHelper {
	/**
	 * 统计队列剩余消息数
	 * @param queueName 队列名
	 *
	 * <p>
	 * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
	 * create at: 2016年3月18日 下午2:49:23
	 */
	Integer getQueueCount(String queueName);
	/**
	 * 获取默认队列名
	 *
	 * <p>
	 * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
	 * create at: 2016年3月18日 下午2:50:12
	 */
	String getDefaulQueueName();
	/**
	 * 发送消息到默认队列 by 消息内容
	 * @throws JMSException
	 * <p>
	 * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
	 * create at: 2016年3月18日 下午2:50:29
	 */
	void sendQueueMessage(Object messageContent);
	/**
	 * 发送消息到默认队列 by (消息内容、队列名)
	 * @param queueName 队列名
	 * @throws JMSException
	 * <p>
	 * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
	 * create at: 2016年3月18日 下午2:51:30
	 */
	void sendQueueMessage(Object messageContent, String queueName);
	/**
	 * 发送消息到默认队列 by (消息内容、队列名、消息属性map)
	 * @param messageContent 消息内容
	 * @param queueName 队列名
	 * @param propertyMap 消息属性map
	 * @throws JMSException
	 * <p>
	 * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
	 * create at: 2016年3月18日 下午2:52:27
	 */
	void sendQueueMessage(Object messageContent, String queueName, Map<String, String> propertyMap);
	/**
	 * 监听接听消息 by (队列名、消息监听处理接口)
	 * @param queueName 队列名
	 * @param receiver 消息监听处理接口
	 * <p>
	 * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
	 * create at: 2016年3月18日 下午2:53:44
	 */
	void receivQueueMessage(String queueName, IActiveMQReceiver receiver);
	/**
	 * 停止监听消息 by (队列名)
	 * @param queueName 队列名
	 * <p>
	 * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
	 * create at: 2016年3月18日 下午2:54:57
	 */
	void stopReceivQueueMessage(String queueName);
	/**
	 * 监听接听消息 by (队列名、消息监听处理接口)
	 * @param queueName 队列名
	 * @param receiver 消息监听处理接口
	 * @param selector 消息选择器,选取自定义属性消息 示例:"JMSPname1='pname1111'"
	 * <p>
	 * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
	 * create at: 2016年3月21日 下午3:49:23
	 */
	void receivQueueMessage(String queueName, IActiveMQReceiver receiver,String selector);

	/**
	 * 根据队列名获取监听容器
	 * @param queueName
	 *
     */
	AbstractMessageListenerContainer getListenerContainer(String queueName);
}
