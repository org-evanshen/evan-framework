package org.evanframework.mail;

import org.evanframework.mail.context.MailContext;
import org.evanframework.mail.sender.MailSender;
import org.evanframework.mail.sender.impl.MailSimpleSender;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MailSenderTest {

    private String from = "evanshen8888@163.com";
    private String fromName = "沈炜";
    private String password = "123qweasd";
    private String smtpServer = "smtp.163.com";

    private MailSender mailSender;

    @Before
    public void init() {
        mailSender = new MailSimpleSender(from, fromName, smtpServer, password);
    }

    @Test
    public void testSend1() throws MessagingException {
        MailContext mailContext = new MailContext();

        //mailContext.setTo("277469513@qq.com");
        mailContext.setTo("sun_shen_1977@163.com");
        mailContext.setText("测试邮件内容1");
        mailContext.setSubject("测试邮件2");
        mailContext.setFromName(fromName);

        mailSender.send(mailContext);
    }

    @Test
    public void testSend2() throws MessagingException {
        MailContext mailContext = new MailContext();

        mailContext.setTo("xiangzhitong@", "xiangzhitong@vip.qq.com");
        mailContext.setText("测试邮件内容");
        mailContext.setSubject("测试邮件");
        mailContext.setFrom(from);
        mailContext.setFromName(fromName);
        mailContext.setPassword(password);
        mailContext.setSmtpServer(smtpServer);
        List<File> attachmentlist = new ArrayList<File>();
        attachmentlist.add(new File("E://webapi接口提测.docx"));
        attachmentlist.add(new File("E://webapi接口提测.pdf"));
        attachmentlist.add(new File("E://TG6P6K2TKK221R11KPK1-公证证书.pdf"));
        attachmentlist.add(new File("E://3CWUZPPO503BPN0NZCOZ-保全证书.pdf"));
        mailContext.setAttachmentlist(attachmentlist);
        mailSender.send(mailContext);
    }
}
