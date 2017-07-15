package org.evanframework.mail.sender;

import org.evanframework.mail.context.MailContext;

import javax.mail.MessagingException;


/**
 * 邮件工具类
 * <p/>
 * create at 2015年5月27日 下午3:11:04
 *
 * @author shen.wei
 * @version %I%, %G%
 * @since 1.0
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
