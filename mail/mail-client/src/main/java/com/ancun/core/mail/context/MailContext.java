package com.ancun.core.mail.context;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 邮件对象
 *
 * @since 1.0.0
 */
public class MailContext implements Serializable {

    private static final long serialVersionUID = 8213712507606119127L;

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final String DEFAULT_CONTENT_TYPE = "text/html";

    private String id;
    private String from;
    private String replyTo;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private Date sentDate;
    private String subject;
    private String text;
    private String user;
    private String password;
    private String smtpServer;
    private String fromName;
    private String encoding = DEFAULT_ENCODING;
    private String contentType = DEFAULT_CONTENT_TYPE;
    private boolean isAsynchronous = false;
    private List<File> attachmentlist; //附件集

    //private String template;
    //private Map<String, Object> model;

    public String getId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return this.from;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setTo(String... to) {
        this.to = to;
    }

    public String[] getTo() {
        return this.to;
    }

    public void setCc(String cc) {
        this.cc = new String[]{cc};
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getCc() {
        return cc;
    }

    public void setBcc(String bcc) {
        this.bcc = new String[]{bcc};
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

//	public String getTemplate() {
//		return template;
//	}
//
//	public void setTemplate(String template) {
//		this.template = template;
//	}
//
//	public Map<String, Object> getModel() {
//		if (model == null) {
//			model = new HashMap<String, Object>();
//		}
//		return model;
//	}

//	public void addAttribute(String attributeName, Object attributeValue) {
//		getModel().put(attributeName, attributeValue);
//	}
//
//	public void addAllAttributes(Map<String, Object> attributes) {
//		getModel().putAll(attributes);
//	}
//
//	public void setModel(Map<String, Object> model) {
//		this.model = model;
//	}

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SimpleMailMessage: ");
        sb.append("from=").append(this.from).append("; ");
        sb.append("replyTo=").append(this.replyTo).append("; ");
        sb.append("to=").append(this.to).append("; ");
        sb.append("cc=").append(this.cc).append("; ");
        sb.append("bcc=").append(this.bcc).append("; ");
        sb.append("sentDate=").append(this.sentDate).append("; ");
        sb.append("subject=").append(this.subject).append("; ");
        sb.append("text=").append(this.text).append("; ");
        if (this.attachmentlist != null && this.attachmentlist.size() > 0) {
            for (int i = 0; i < this.attachmentlist.size(); i++) {
                sb.append("file[" + String.valueOf(i + 1) + "]=").append(this.attachmentlist.get(i)).append("; ");
            }
        }
        //sb.append("model=").append(this.model);
        return sb.toString();
    }

    public MailContext copy() {
        MailContext mail = new MailContext();
        return mail;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public boolean isAsynchronous() {
        return isAsynchronous;
    }

    public void setAsynchronous(boolean isAsynchronous) {
        this.isAsynchronous = isAsynchronous;
    }

    public List<File> getAttachmentlist() {
        return attachmentlist;
    }

    public void setAttachmentlist(List<File> attachmentlist) {
        this.attachmentlist = attachmentlist;
    }

    //	private static String[] copy(String[] state) {
    //		String[] copy = new String[state.length];
    //		System.arraycopy(state, 0, copy, 0, state.length);
    //		return copy;
    //	}
}
