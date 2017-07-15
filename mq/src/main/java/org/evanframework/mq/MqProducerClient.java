package org.evanframework.mq;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Properties;

/**
 * 生产者
 * <p/>
 * create at 2017年03月8日 下午5:15:54
 * @author <a href="mailto:caozhenfei@yourong.com">Dim.Cao</a>
 * @version %I%, %G%
 * @since 1.0
 */
@Deprecated
public class MqProducerClient extends MqClient {

    private Producer producer;


    public MqProducerClient() {

    }

    public MqProducerClient(String accessKey, String secretKey, String onsAddr) {
        super(accessKey,secretKey,onsAddr);
    }

    public Producer createProducer(String ProducerId){
        Properties producerProperties = new Properties();
        producerProperties.setProperty(PropertyKeyConst.AccessKey, super.getAccessKey());
        producerProperties.setProperty(PropertyKeyConst.SecretKey, super.getSecretKey());
        producerProperties.setProperty(PropertyKeyConst.ONSAddr, super.getOnsAddr());
        this.producer = ONSFactory.createProducer(producerProperties);
        start();
        return this.producer;
    }

    public void start(){
        producer.start();
    }

    public void shutdown(){
        producer.shutdown();
    }

 }
