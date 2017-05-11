package org.beangle.website.common.service;

import java.util.Properties;

import org.beangle.notification.notifiers.mail.AbstractMailNotifier;
import org.beangle.notification.notifiers.mail.DefaultMailNotifier;
import org.beangle.notification.notifiers.mail.MailMessage;
import org.beangle.notification.service.DefaultMessageQueue;
import org.beangle.notification.service.DefaultNotificationTask;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class SendEmail {

	private String host;
	
	private String userName;
	
	private String password;
	
	private String senderAddr;
	
	private Properties property = new Properties();

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSenderAddr() {
		return senderAddr;
	}

	public void setSenderAddr(String senderAddr) {
		this.senderAddr = senderAddr;
	}

	public Properties getProperty() {
		return property;
	}

	public void setProperty(Properties property) {
		this.property = property;
	}
	
	public SendEmail(){
	}
	
	public SendEmail(String host,String userName,String password,String senderAddr,Properties property){
		this.host = host;
		this.userName = userName;
		this.password = password;
		this.senderAddr = password;
		this.property = property;
	}
	
	/**
	 * 构建邮件发送服务器
	 * @return
	 */
	private JavaMailSenderImpl getGmailMailSender() {
		JavaMailSenderImpl mailSender = new org.springframework.mail.javamail.JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setUsername(userName);
		mailSender.setPassword(password);
		property.put( "mail.smtp.auth",   "true");  
		mailSender.setJavaMailProperties(property);
		return mailSender;
	}
	
	/**
	 * 发送消息
	 * @param sendTo 收件人（支持多人，邮件地址用“;”隔开）
	 * @param subject 邮件主题
	 * @param text 邮件内容
	 */
	public void sendMessage(String sendTo,String subject,String text){
		JavaMailSenderImpl mailSender = getGmailMailSender();
		AbstractMailNotifier notifier = new DefaultMailNotifier();
		notifier.setMailSender(mailSender);
		notifier.setFrom(senderAddr);

		DefaultNotificationTask task = new DefaultNotificationTask();
		task.setNotifier(notifier);
		task.setMessageQueue(new DefaultMessageQueue());
		String[] sendTos = sendTo.split(";");
		for (int i = 0; i < sendTos.length; i++) {
			MailMessage mmc = new MailMessage(subject, text,sendTos[i]);
			mmc.setContentType("text/html;charset=utf-8");
			task.getMessageQueue().addMessage(mmc);
		}
		task.send();
	}
}
