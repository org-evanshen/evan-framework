package org.evanframework.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.Test;

/**
 * Created by evan on 16/9/27.
 */
public class ClearMail  {

    private String pop3Host = "pop.qiye.163.com";
    private String smtpHost = "pop.smtp.163.com";
    private String user = "jcpt@";
    private String password = "Ac201608";

    @Test
    public  void clear() throws  MessagingException,IOException {
        // get the session object
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "smtp");
        //properties.put("mail.pop3s.host", pop3Host);
        //properties.put("mail.pop3s.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");

        Session emailSession = Session.getDefaultInstance(properties);
        // emailSession.setDebug(true);

        // create the POP3 store object and connect with the pop server
        Store store = emailSession.getStore("imap");

        store.connect(pop3Host, user, password);

        Folder defaultFolder = store.getDefaultFolder();

        Folder[] allFolder = defaultFolder.list();

        // create the folder object and open it
        Folder emailFolder = store.getFolder("已发送");
        emailFolder.open(Folder.HOLDS_FOLDERS);

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        // retrieve the messages from the folder in an array and print it
        Message[] messages = emailFolder.getMessages();
        System.out.println("messages.length---" + messages.length);
        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (i + 1));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);

            String subject = message.getSubject();
            System.out.print("Do you want to delete this message [y/n] ? ");
            String ans = reader.readLine();
            if ("Y".equals(ans) || "y".equals(ans)) {
                // set the DELETE flag to true
                message.setFlag(Flags.Flag.DELETED, true);
                System.out.println("Marked DELETE for message: " + subject);
            } else if ("n".equals(ans)) {
                break;
            }
        }

        // expunges the folder to remove messages which are marked deleted
        emailFolder.close(true);
        store.close();
    }

}

