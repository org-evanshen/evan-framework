package com.ancun.core.easy.mq;

/**
 * create at 9/3/16 5:21 PM
 *
 * @author <a href="mailto:taofei@ancun.com">Paul Yao</a>
 * @version %I%, %G%
 * @see
 */
public class ActiveMQConfig {

    public int consumers;
    public int sessionAcknowledgeMode;

    public int getConsumers() {
        return consumers;
    }

    public void setConsumers(int consumers) {
        this.consumers = consumers;
    }

    public int getSessionAcknowledgeMode() {
        return sessionAcknowledgeMode;
    }

    public void setSessionAcknowledgeMode(int sessionAcknowledgeMode) {
        this.sessionAcknowledgeMode = sessionAcknowledgeMode;
    }
}
