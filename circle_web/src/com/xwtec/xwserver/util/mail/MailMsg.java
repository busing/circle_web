package com.xwtec.xwserver.util.mail;


/**
 * 发送邮件的信息. <br>
 * <p>
 * Copyright: Copyright (c) 2013-11-27 上午12:31:10
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public final class MailMsg {
    
    /**
	 * 邮件接收者的地址
	 */
    private String[] toAddress = new String[]{};
    
    /**
     * 邮件抄送地址
     */
    private String[] ccAddress = new String[]{};
    
    /**
	 * 邮件主题
	 */
    private String subject;

    /**
	 * 邮件的文本内容
	 */
    private String content;
    
    /**
     * 来源名称(调用消息平台时需要注明)
     */
    private String sourceName;
	
	public String[] getToAddress() {
		return toAddress;
	}
	
	public void setToAddress(String[] toAddress) {
		this.toAddress = toAddress;
	}
	
	public void setToAddress(String toAddress) {
		this.toAddress = new String[1];
		this.toAddress[0] = toAddress;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String[] getCcAddress() {
		return ccAddress;
	}

	public void setCcAddress(String[] ccAddress) {
		this.ccAddress = ccAddress;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
}