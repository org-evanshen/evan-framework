package com.ancun.core.sms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:sms-bean.xml", "classpath:sms-test-bean.xml",})
public class SMSServiceTest {
	@Autowired
	private SMSService smsService;

	@Test
	public void testSend() {
		//smsService.send("13588712642", "【安存科技】您的实名验证信息提交成功，客服将会于3个工作日内进行审核，如果问题请咨询400-7801999");
		//smsService.send("13588712642", "【安存科技】您的实名验证信息提交成功，测试测试测试测试测试");
		smsService.send("15605881523", "您的实名验证信息提交成功，客服将会于3个工作日内进行审核，如果问题请咨询400-7801999","安存科技");
		//smsService.send("18658838526", "【云片网】您的验证码是1234","安存");
	}
}
