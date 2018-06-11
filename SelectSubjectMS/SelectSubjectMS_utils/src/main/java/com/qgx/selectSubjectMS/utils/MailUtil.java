package com.qgx.selectSubjectMS.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * 邮件工具类
 * @author goxcheer
 *
 */
public  class MailUtil {
	/**
	 * 发送验证邮件的方法
	 * @param toAddress ： 收件人
	 * @param checkCode ：激活码
	 */
	public static void sendMail(String toAddress,String validateCode){
		/**
		 * 1.获得Session对象
		 * 2.创建一个代表邮件的对象Message
		 * 3.发送邮件Transport
		 */
		//1.获取连接对象
		Properties props=new Properties();
		final String smtpPort = "465";
		props.setProperty("mail.transport.protocol", "smtp"); 
		props.setProperty("mail.smtp.host", "smtp.qq.com");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port", smtpPort);
		props.setProperty("mail.debug", "true"); 
		Session session=Session.getInstance(props,new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("481182416@qq.com", "hhhiptayiwkjbhce");
			}	
		});
		//创建邮件对象
		Message message=new MimeMessage(session);
		try {
			//设置发件人
			message.setFrom(new InternetAddress("481182416@qq.com"));
			//设置发送类型TO，收件人地址
			message.addRecipient(RecipientType.TO, new InternetAddress(toAddress));
			//设置标题
			message.setSubject("来自毕设选题系统的找回密码邮件！");
			//设置正文
			message.setContent("<h1>毕设选题系统的找回密码邮件！点击下面链接重新设置密码</h1><h3><a href='http://localhost:8080/SelectSubjectMS_web/user_toResetPage.action?validateCode="+validateCode+"'>http://localhost:8080/SelectSubjectMS_web/user_toResetPage.action?validateCode="+validateCode+"</a></h3>", "text/html;charset=UTF-8");
			//发送邮件
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
