package com.ancun.core.sms.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.core.sms.SMSService;

public class SMSServiceImpl implements SMSService {
    private final static Logger log = LoggerFactory.getLogger(SMSServiceImpl.class);

    private static final String SMS_ENCODING = "GBK";
    private static String ENCODING = "UTF-8";

    private String smsServerUrl;
    private String smsServerUser;
    private String smsServerPwd;

    public SMSServiceImpl() {
    }

    public SMSServiceImpl(String smsServerUrl, String smsServerUser, String smsServerPwd) {
        this.smsServerUrl = smsServerUrl;
        this.smsServerUser = smsServerUser;
        this.smsServerPwd = smsServerPwd;
    }

    public void init() {
        hasText(smsServerUrl, "短信服务商地址未提供");
        hasText(smsServerUser, "短信服务商账号未提供");
        hasText(smsServerPwd, "短信服务商密码未提供");

        log.info("短信发送组件加载成功,服务商地址[{}],账号[{}]", smsServerUrl, smsServerUser);
    }

    @Override
    public boolean send(String mobile, String content) {
        return send(mobile, content, null);
    }

    @Override
    public boolean send(String mobile, String content, String sign) {
        hasText(mobile, "手机号码不能为空");
        hasText(content, "内容不能为空");

        boolean result = false;
        int inputLine = -1;

        // 发送内容
        String contentSend;
        if (StringUtils.isBlank(sign)) {
            contentSend = content;
        } else {
            contentSend = "【" + sign + "】" + content;
        }
        try {
            if (log.isDebugEnabled()) {
                log.debug("发送短信的手机号码：【{}】,短信内容：【{}】", mobile, contentSend);
            }
            contentSend = URLEncoder.encode(contentSend.replaceAll("<br/>", " ") + " ", SMS_ENCODING);
            contentSend = URLEncoder.encode(contentSend, "GBK");
        } catch (UnsupportedEncodingException e1) {
            log.error("短信发送失败，短信内容编码失败！", e1);
            return result;
        }

        String urlString = smsServerUrl + "/dxin10/SendMessage?UserName=" + smsServerUser + "&UserPass=" + smsServerPwd
                + "&Mobile=" + mobile + "&Content=" + contentSend;

        if (log.isDebugEnabled()) {
            log.debug("短信发送url 为 : " + urlString);
        }

        /**短信发送失败,重试3次*/
        int i = 1;
        while (i < 3) {
            result = sendSms(mobile, contentSend, result, inputLine, urlString);
            if (result) {
                break;
            } else {
                i++;
            }
        }

        return result;
    }

    /**
     * 发送短信
     *
     * @param mobile    手机号码
     * @param content   短信内容
     * @param result    返回值
     * @param inputLine 错误代码
     * @param urlString 发送短信地址
     * @return <p/>
     * author: <a href="mailto:caozhenfei@ancun.com">CaoZhenfei</a><br>
     * create at: 2016年9月10日 下午2:34:06
     */
    private boolean sendSms(String mobile, String content, boolean result, int inputLine, String urlString) {
        String returnResult = httpGetSend(urlString);
        log.info(String.format("短信返回结果为%s", returnResult));
        if (StringUtils.isNotBlank(returnResult)) {
            if (returnResult.indexOf(",") > 0) {
                String code = returnResult.substring(0, returnResult.indexOf(","));
                inputLine = new Integer(code).intValue();
            } else {
                inputLine = new Integer(returnResult).intValue();
            }
        } else {
            log.error("短信发送失败：响应内容为空, 手机号码：【{}】,短信内容：【{}】", mobile, content);
            return false;
        }

        if (log.isDebugEnabled()) {
            log.debug(String.format("短信返回code结果为%s", inputLine));
        }

        if (inputLine == 0 || inputLine == 3) {
            if (log.isDebugEnabled()) {
                log.debug("短信发送成功");
            }
            return true;
        }

        String logMsg = dealwithResult(inputLine);

        log.error("短信发送失败：{}, 手机号码：【{}】,短信内容：【{}】", logMsg, mobile, content);

        return false;
    }

    /**
     * 错误代码
     *
     * @param inputLine
     * @return <p/>
     * author: <a href="mailto:caozhenfei@ancun.com">CaoZhenfei</a><br>
     * create at: 2016年9月10日 下午2:35:22
     */
    private String dealwithResult(int inputLine) {
        String logMsg = null;
        switch (inputLine) {
            case 2:
                logMsg = "IP限制";
                break;
            case 4:
                logMsg = "用户名错误 ";
                break;
            case 5:
                logMsg = "密码错误";
                break;
            case 6:
                logMsg = "编码错误";
                break;
            case 7:
                logMsg = "发送时间有误";
                break;
            case 8:
                logMsg = "参数错误";
                break;
            case 9:
                logMsg = "手机号码有误";
                break;
            case 10:
                logMsg = "扩展号码有误";
                break;
            case 11:
                logMsg = "余额不足";
                break;
            case -1:
                logMsg = "服务器内部异常";
                break;
            default:
                logMsg = "未知异常";
                break;
        }
        return logMsg;
    }


    /**
     * httpGet发送短信
     *
     * @param urlPath
     * @return <p/>
     * author: <a href="mailto:caozhenfei@ancun.com">CaoZhenfei</a><br>
     * create at: 2016年9月10日 下午2:36:23
     */
    private String httpGetSend(String urlPath) {
        StringBuffer sbf = new StringBuffer("");
        BufferedReader reader = null;
        HttpURLConnection uc = null;
        try {
            URL url = new URL(urlPath);
            uc = (HttpURLConnection) url.openConnection();
            uc.setConnectTimeout(30000);
            uc.setReadTimeout(30000);
            uc.setRequestMethod("GET");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.connect();
            reader = new BufferedReader(new InputStreamReader(uc.getInputStream())); // 读取服务器响应信息
            String line;
            while ((line = reader.readLine()) != null) {
                sbf.append(line);
            }
            return sbf.toString();
        } catch (Exception e) {
            log.error("httpGetSend error " + e, e);
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
                if (null != uc) {
                    uc.disconnect();
                }
            } catch (Exception e2) {
                log.error("httpGetSend error " + e2, e2);
            }
        }
        return null;
    }

    private void hasText(String text, String message) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public void setSmsServerUrl(String smsServerUrl) {
        this.smsServerUrl = smsServerUrl;
    }

    public void setSmsServerUser(String smsServerUser) {
        this.smsServerUser = smsServerUser;
    }

    public void setSmsServerPwd(String smsServerPwd) {
        this.smsServerPwd = smsServerPwd;
    }
}
