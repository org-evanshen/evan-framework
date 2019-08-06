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
package org.evanframework.rocketmq;

import com.aliyun.openservices.ons.api.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by caozhenfie on 2017/3/8.
 * 接收消息示例
 */
public class SimpleMQConsumerTest {

    private static final Logger logger = LoggerFactory.getLogger(SimpleMQConsumerTest.class);

    private static final String ALIYUN_ACCESSKEY="LTAIuLCgThF8Xs2h";

    private static final String ALIYUN_SECRETKEY="lanqXKEW7o70yf8ihgivKsI7iTzYO5";

    private static final String ALIYUN_ONSADDR="http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet";

    private final String CONSUMER_ID = "CID_Raydemo_1";

    private final String TOPIC = "Ray_MQ_demo_1";

    private final String TAG = "mq_test_tag";

    @Test
    public void Receive(){

        MqConsumerClient consumer = new MqConsumerClient(ALIYUN_ACCESSKEY,ALIYUN_SECRETKEY,ALIYUN_ONSADDR);
        consumer.createConsumer(CONSUMER_ID,TOPIC,TAG,new MessageListenerImpl());
        System.out.println("Consumer start success.");

        //等待固定时间防止进程退出
        try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

 }

class MessageListenerImpl implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListenerImpl.class);

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {

        try {
            System.out.println(message.getKey() + " Receive message, Topic is:" +
                    message.getTopic() + ", MsgId is:" + message.getMsgID());

        } catch (Throwable e) {

            logger.error(e.getMessage(), e);

            return Action.ReconsumeLater;//出现异常将重试
        }

        return Action.CommitMessage;//正常消费
    }
}