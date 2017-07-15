/**
 * Copyright (C) 2010-2016 Alibaba Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.evanframework.mq;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import org.junit.Test;

import java.util.Date;

/**
 * Created by caozhenfie on 2017/3/8.
 * 发送普通消息示例
 */
public class SimpleMQProducerTest {

    private static final String ALIYUN_ACCESSKEY="LTAIuLCgThF8Xs2h";

    private static final String ALIYUN_SECRETKEY="lanqXKEW7o70yf8ihgivKsI7iTzYO5";

    private static final String ALIYUN_ONSADDR="http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet";

    private static final String PRODUCER_ID="PID_Raydemo_1";

    private static final String TOPIC="Ray_MQ_demo_1";

    private static final String TAG="mq_test_tag";

     @Test
    public void testSend(){
         MqProducerClient mqClient = new MqProducerClient(ALIYUN_ACCESSKEY,ALIYUN_SECRETKEY,ALIYUN_ONSADDR);
        Producer producer = mqClient.createProducer(PRODUCER_ID);
        System.out.println("Producer Started");

        for (int i = 0; i < 2; i++) {
            Message message = new Message(TOPIC, TAG, "mq send transaction message support".getBytes());
            message.setKey(i+"");//根据业务唯一标识的 key 做幂等处理
            try {
                SendResult sendResult = producer.send(message);
                if (sendResult != null) {
                    System.out.println(new Date() + " Send mq message success! Topic is:" + TOPIC + " msgId is: " + sendResult.getMessageId());
                }
            }catch (ONSClientException e){
                System.out.println("发送失败");
            }
        }
    }

}
