package org.evanframework.mail.sender.impl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.evanframework.mail.context.MailContext;
import org.evanframework.mail.sender.AbstractMailSender;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送邮件实现
 * shenwei
 */
public class MailSimpleSender extends AbstractMailSender {
    private final static Logger log = LoggerFactory.getLogger(MailSimpleSender.class);

    private String mailFrom;
    private String mailFromName;
    private String smtpServer;
    private String smtpPort;
    private String password;

    public MailSimpleSender() {
    }

    /**
     * @param mailFrom     发件人邮箱
     * @param mailFromName 发件人名称
     * @param smtpServer   发件服务器
     * @param password     密码
     */
    public MailSimpleSender(String mailFrom, String mailFromName, String smtpServer, String smtpPort,  String password) {
        this.mailFrom = mailFrom;
        this.mailFromName = mailFromName;
        this.smtpServer = smtpServer;
        this.smtpPort = smtpPort;
        this.password = password;

        init();
    }

    public void init() {
        log.info("邮件发送组件加载成功,默认发件邮箱[{}],发件人[{}],smtpServer[{}]", mailFrom, mailFromName, smtpServer);
    }

    @Override
    protected void doSend(MailContext mail) throws MessagingException {
        MimeMessage mimeMessage;

        mimeMessage = createMimeMessage(mail);

        if (log.isDebugEnabled()) {
            log.debug("Send mail:" + mimeMessage);
        }

        Transport.send(mimeMessage);
    }

    private MimeMessage createMimeMessage(MailContext mail) throws MessagingException {
        String prefix = null;
        String suffix = null;

        String from = StringUtils.isNotBlank(mail.getFrom()) ? mail.getFrom() : this.mailFrom;
        String password = StringUtils.isNotBlank(mail.getPassword()) ? mail.getPassword()
                : this.password;
        String smtpServer = StringUtils.isNotBlank(mail.getSmtpServer()) ? mail.getSmtpServer()
                : this.smtpServer;
        String smtpPort = StringUtils.isNotBlank(mail.getSmtpPort()) ? mail.getSmtpPort()
                : this.smtpPort;
        String fromName = StringUtils.isNotBlank(mail.getFromName()) ? mail.getFromName()
                : this.mailFromName;

        String fromNick = null;
        try {
            fromNick = MimeUtility.encodeText(fromName);
        } catch (UnsupportedEncodingException e) {
            if (log.isErrorEnabled()) {
                log.error("Mail from nick name [" + fromName + "] encode fail!");
            }
            fromNick = from;
        }
        // String to_mail_address = mail.getTo()[0];
        Authenticator auth = new PopupAuthenticator(from, password);
        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.host", smtpServer);
        mailProps.put("mail.smtp.port", smtpPort);
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("username", from);
        mailProps.put("password", password);


//        Session mailSession = Session.getDefaultInstance(mailProps, auth);

        // 如果想要同时使用两个帐号发送不能是用单例
        Session mailSession = Session.getInstance(mailProps, auth);

        mailSession.setDebug(true);
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(fromNick + "<" + from + ">"));
        Address[] iaToList = new Address[mail.getTo().length];
        int i = 0;
        for (String mailAddress : mail.getTo()) {
            iaToList[i] = new InternetAddress(mailAddress);
            i++;
        }
        // InternetAddress[] iaToList = new
        // InternetAddress().parse(mail.getTo());
        message.setRecipients(Message.RecipientType.TO, iaToList);
        message.setSubject(mail.getSubject());
        MimeMultipart multi = new MimeMultipart();
        BodyPart textBodyPart = new MimeBodyPart();
        // textBodyPart.setText(mail.getText());
        textBodyPart.setContent(mail.getText(), mail.getContentType() + ";charset=" + mail.getEncoding());
        // textBodyPart.setFileName("37af4739a11fc9d6b311c712.jpg");
        multi.addBodyPart(textBodyPart);
        //添加附件集合
        if (mail.getAttachmentlist() != null && mail.getAttachmentlist().size() > 0) {
            for (int a = 0; a < mail.getAttachmentlist().size(); a++) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                FileDataSource source = new FileDataSource(mail.getAttachmentlist().get(a));
                attachmentPart.setDataHandler(new DataHandler(source));
                try {
                    attachmentPart.setFileName(MimeUtility.encodeWord(mail.getAttachmentlist().get(a).getName(), "gb2312", null));
                } catch (UnsupportedEncodingException e) {
                    if (log.isErrorEnabled()) {
                        log.error("Mail from nick name [" + fromName + "] encode fail!");
                    }
                }
                multi.addBodyPart(attachmentPart);
            }
        }
        message.setContent(multi);
        message.saveChanges();
        return message;
    }

    /**
     * 发件人邮箱
     */
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    /**
     * 发件人名称
     */
    public void setMailFromName(String mailFromName) {
        this.mailFromName = mailFromName;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class PopupAuthenticator extends Authenticator {
    private String username;
    private String password;

    public PopupAuthenticator(String username, String pwd) {
        this.username = username;
        this.password = pwd;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.username, this.password);
    }
}
