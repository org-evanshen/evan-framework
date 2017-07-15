package org.evanframework.mq;

/**
 * <p/>
 * create at 2017年03月8日 下午5:15:54
 * @author <a href="mailto:caozhenfei@yourong.com">Dim.Cao</a>
 * @version %I%, %G%
 * @since 1.0
 */
@Deprecated
public class MqClient {

    /**
     * 阿里云accessKey
     */
    private String accessKey;

    /**
     * 阿里云secretKey
     */
    private String secretKey;

    /**
     * RocketMQ服务地址
     */
    private String onsAddr;

    public MqClient() {
    }

    public MqClient(String accessKey, String secretKey, String onsAddr) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.onsAddr = onsAddr;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getOnsAddr() {
        return onsAddr;
    }

    public void setOnsAddr(String onsAddr) {
        this.onsAddr = onsAddr;
    }
}
