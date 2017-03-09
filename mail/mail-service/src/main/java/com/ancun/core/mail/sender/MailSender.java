package com.ancun.core.mail.sender;

import javax.mail.MessagingException;

import com.ancun.core.mail.context.MailContext;

/**
 * 邮件工具类
 * <p/>
 * <p/>
 * create at 2015年5月27日 下午3:11:04
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 */
public interface MailSender {
    /**
     * 发送邮件
     *
     * @param mail
     * @throws MessagingException <p/>
     *                            author: ShenWei<br>
     *                            create at 2015年5月27日 下午3:11:48
     */
    void send(MailContext mail) throws MessagingException;
}
