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
 * �ʼ�������
 * @author goxcheer
 *
 */
public  class MailUtil {
	/**
	 * ������֤�ʼ��ķ���
	 * @param toAddress �� �ռ���
	 * @param checkCode ��������
	 */
	public static void sendMail(String toAddress,String validateCode){
		/**
		 * 1.���Session����
		 * 2.����һ�������ʼ��Ķ���Message
		 * 3.�����ʼ�Transport
		 */
		//1.��ȡ���Ӷ���
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
		//�����ʼ�����
		Message message=new MimeMessage(session);
		try {
			//���÷�����
			message.setFrom(new InternetAddress("481182416@qq.com"));
			//���÷�������TO���ռ��˵�ַ
			message.addRecipient(RecipientType.TO, new InternetAddress(toAddress));
			//���ñ���
			message.setSubject("���Ա���ѡ��ϵͳ���һ������ʼ���");
			//��������
			message.setContent("<h1>����ѡ��ϵͳ���һ������ʼ��������������������������</h1><h3><a href='http://localhost:8080/SelectSubjectMS_web/user_toResetPage.action?validateCode="+validateCode+"'>http://localhost:8080/SelectSubjectMS_web/user_toResetPage.action?validateCode="+validateCode+"</a></h3>", "text/html;charset=UTF-8");
			//�����ʼ�
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
