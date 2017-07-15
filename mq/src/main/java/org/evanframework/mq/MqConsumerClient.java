package org.evanframework.mq;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Properties;

/**
 * 消费者
 * <p/>
 * create at 2017年03月8日 下午5:15:54
 * @author <a href="mailto:caozhenfei@yourong.com">Dim.Cao</a>
 * @version %I%, %G%
 * @since 1.0
 */
@Deprecated
public class MqConsumerClient extends MqClient {

    private Consumer consumer;

    public MqConsumerClient() {

    }

    public MqConsumerClient(String accessKey, String secretKey, String onsAddr) {
        super(accessKey,secretKey,onsAddr);
    }

    public void createConsumer(String consumerId, String topic, String tag, MessageListener messageListener){
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(PropertyKeyConst.AccessKey, super.getAccessKey());
        consumerProperties.setProperty(PropertyKeyConst.SecretKey, super.getSecretKey());
        consumerProperties.setProperty(PropertyKeyConst.ONSAddr, super.getOnsAddr());
        consumerProperties.setProperty(PropertyKeyConst.ConsumerId, consumerId);
        consumer =  ONSFactory.createConsumer(consumerProperties);
        consumer.subscribe(topic, tag, messageListener);
        this.start();
    }

    public void start(){
        consumer.start();
    }

    public void shutdown(){
        consumer.shutdown();
    }

}
