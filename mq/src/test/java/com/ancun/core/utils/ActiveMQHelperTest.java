package com.ancun.core.utils;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ancun.core.mq.ActiveMQHelper;
import com.ancun.core.mq.IActiveMQReceiver;

/**
 * 消息接口   测试
 * <p/>
 * <p/>
 * create at 2016年3月21日 下午3:22:40
 *
 * @author <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a>
 * @version %I%, %G%
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:mq-bean.xml", "classpath:sysconfig-bean.xml","classpath:test-bean.xml"})
public class ActiveMQHelperTest {

    @Autowired
    private ActiveMQHelper activeMQHelper;

    /**
     * 获取队列剩余消息数量
     * <p/>
     * <p/>
     * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     * create at: 2016年3月21日 下午3:22:02
     */
    @Test
    public void getQueueCount() {
        Integer count = activeMQHelper.getQueueCount(activeMQHelper.getDefaulQueueName());
        System.out.println(count);
    }

    /**
     * 获取默认队列名
     * <p/>
     * <p/>
     * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     * create at: 2016年3月21日 下午3:23:47
     */
    @Test
    public void getDefaulQueueName() {
        String defaulQueueName = activeMQHelper.getDefaulQueueName();
        System.out.println(defaulQueueName);
    }

    /**
     * 发送消息到默认队列
     * 消息内容：object（TextMessage/BytesMessage/MapMessage/ObjectMessage/）
     * <p/>
     * <p/>
     * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     * create at: 2016年3月21日 下午3:24:07
     */
    @Test
    public void sendQueueMessage() {
        String queueName = "ancun-bps-queue1";
        String jsonMessage = "这是一条测试消息。。。。。";
        //自定义消息属性
        Map<String, String> propertyMap = new HashMap<String, String>();
        propertyMap.put("JMSPname1", "pname1111");
        propertyMap.put("JMSPname2", "pname2222");
        int i = 0;
        //测试发送10W条TextMessage
        while (i < 20) {
            i++;
            //发送默认队列名消息
//			activeMQHelper.sendQueueMessage(jsonMessage);
            //发送默认指定队列名消息
//			activeMQHelper.sendQueueMessage(jsonMessage,queueName);
            //发送默认指定队列名、自定义属性消息
            activeMQHelper.sendQueueMessage(jsonMessage, queueName, propertyMap);
            System.out.println("已发送消息:" + i + "条");
        }
    }

    @Test
    public void receiverMessage() throws InterruptedException{
        //设置线程等待时间
        long second=10;//单位:秒
        String queueName="ancun-bps-queue1";
		/*
		 * JMSPname1='pname1111'
		 * 说明:比较运算符有  = , > , >= ,  < , <= , <>
		 * 根据类型相应的利用运算符比较,字符串和布尔比较 只支持 = 或者 <>,更加复杂条件筛选请上网查阅
		 */
        String messageSelector="JMSPname1='pname1111'";
//		activeMQHelper.receivQueueMessage(queueName, new ReceiverImpl());
        //通过队列名和属性值获取消息
        activeMQHelper.receivQueueMessage(queueName, new Receiver(),messageSelector);
//		Thread.sleep(Long.MAX_VALUE);//正式环境监听
        Thread.sleep(10000*second);//测试监听10秒
        // 关闭连接  销毁容器
        activeMQHelper.stopReceivQueueMessage(queueName);
    }
}

class Receiver implements IActiveMQReceiver {
    private static int count = 0;

    @Override
    public void onMessage(Message message) {
        count++;
        System.out.println("已接受到" + count + "条消息");
        try {
            String pname1 = message.getStringProperty("JMSPname1");//获取消息属性
            System.out.println(pname1);
            String pname2 = message.getStringProperty("JMSPname2");//获取消息属性
            System.out.println(pname2);
            Object obj = new SimpleMessageConverter().fromMessage(message);
            System.out.println(obj);
            if (count == 1) {
                throw new RuntimeException(new JMSException("11111"));
            }
        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
    }

}