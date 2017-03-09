package com.ancun.core.sms;

public interface SMSService {
	/**
	 * 
	 * @param mobile
	 * @param content
	 *
	 * <p>
	 * author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
	 * create at: 2014年6月5日下午8:14:32
	 */
	boolean send(String mobile, String content);
	/**
	 * 
	 * @param mobile
	 * @param content
	 * @param sign
	 *
	 */
	boolean send(String mobile, String content,String sign);
}



