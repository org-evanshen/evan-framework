package org.evanframework.mail.sender;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;

import org.evanframework.mail.context.MailContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 发送邮件
 * @since 1.0
 */
public abstract class AbstractMailSender implements MailSender {
    private static final Executor DEFAULT_EXECUTOR = Executors.newFixedThreadPool(10);

    protected final Logger logger = LoggerFactory.getLogger(AbstractMailSender.class);

    private boolean isAsynchronous = false;

    private Executor executor = DEFAULT_EXECUTOR;

    protected boolean isAsynchronous() {
        return isAsynchronous;
    }

    public void setAsynchronous(boolean isAsynchronous) {
        this.isAsynchronous = isAsynchronous;
    }

    protected Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public void send(final MailContext mail) throws MessagingException {
        if (mail == null) {
            throw new IllegalArgumentException("parameter mail can't be null");
        }
        checkMail(mail);
        if (isAsynchronous() || mail.isAsynchronous()) {
            executor.execute(new Runnable() {
                public void run() {
                    try {
                        doSend(mail);
                    } catch (MessagingException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
        } else {
            doSend(mail);
        }
    }

    public void send(MailContext[] mails) throws MessagingException {

        if (mails == null || mails.length == 0) {
            throw new IllegalArgumentException("parameter mail array can't be null or empty");
        }
        for (MailContext mail : mails) {
            send(mail);
        }
    }

    protected void checkMail(MailContext mail) {
        if (mail != null && (mail.getTo() == null || mail.getTo().length == 0)) {
            throw new IllegalArgumentException("parameter mail to can't be null");
        }
    }

    protected abstract void doSend(MailContext mail) throws MessagingException;
}
