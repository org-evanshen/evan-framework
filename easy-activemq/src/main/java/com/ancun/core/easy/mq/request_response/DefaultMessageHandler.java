package com.ancun.core.easy.mq.request_response;

import com.alibaba.fastjson.JSON;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Paul
 * @date 2016/10/10
 */
public class DefaultMessageHandler implements MessageHandler {

    @Override
    public Map<String, Object> handle(Message message) throws JMSException {
        System.out.println(String.format("receive request message is: [ %s ]", JSON.toJSONString(message)));
        Map<String, Object> response = new HashMap<>();
        response.put("correlationID", message.getJMSCorrelationID());
        return response;
    }

}
