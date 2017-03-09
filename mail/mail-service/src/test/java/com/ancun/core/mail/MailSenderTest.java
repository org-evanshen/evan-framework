package com.ancun.core.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ancun.core.mail.context.MailContext;
import com.ancun.core.mail.sender.MailSender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:mail-bean.xml", "classpath:test-bean.xml"})

public class MailSenderTest {

    private String NOTARY_MAIL_FROM = "gongzheng@ancun.com";//
    private String NOTARY_MAIL_NAME = "安存公证服务";
    private String NOTARY_MAIL_PASSWORD = "51gz@ancun";
    private String NOTARY_MAIL_SERVER = "smtp.qiye.163.com";

    @Autowired
    @Qualifier("simpleMailSender")
    private MailSender mailSender;

    @Test
    public void testSend1() throws MessagingException {
        MailContext mailContext = new MailContext();

        mailContext.setTo("shenwei@ancun.com", "277469513@qq.com");
        mailContext.setText("测试邮件内容");
        mailContext.setSubject("测试邮件");
        mailContext.setFromName("安存科技");

        mailSender.send(mailContext);
    }

    @Test
    public void testSend2() throws MessagingException {
        MailContext mailContext = new MailContext();

        mailContext.setTo("xiangzhitong@ancun.com", "xiangzhitong@vip.qq.com");
        mailContext.setText("测试邮件内容");
        mailContext.setSubject("测试邮件");
        mailContext.setFrom(NOTARY_MAIL_FROM);
        mailContext.setFromName(NOTARY_MAIL_NAME);
        mailContext.setPassword(NOTARY_MAIL_PASSWORD);
        mailContext.setSmtpServer(NOTARY_MAIL_SERVER);
        List<File> attachmentlist = new ArrayList<File>();
        attachmentlist.add(new File("E://webapi接口提测.docx"));
        attachmentlist.add(new File("E://webapi接口提测.pdf"));
        attachmentlist.add(new File("E://TG6P6K2TKK221R11KPK1-公证证书.pdf"));
        attachmentlist.add(new File("E://3CWUZPPO503BPN0NZCOZ-保全证书.pdf"));
        mailContext.setAttachmentlist(attachmentlist);
        mailSender.send(mailContext);
    }
}
