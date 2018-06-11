package com.qgx.selectSubjectMS.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
/**
 * �ַ���������
 * @author goxcheer
 *
 */
public class StringUtil {
		/*
		 * �ж��Ƿ�Ϊ�ջ��߲�����
		 */
		public static boolean isNotBlank(String str){
			if(str.equals("")||str==null){
				return false;
			}
			else return true;
		}
		/*
		 * ת��ΪMd5�����ַ���
		 */
		public static String encryptMd5(String plainText) {
			byte[] secretBytes = null;
			try {
				secretBytes = MessageDigest.getInstance("md5").digest(
						plainText.getBytes());
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("û��md5����㷨��");
			}
			String md5code = new BigInteger(1, secretBytes).toString(16);// 16��������
			// �����������δ��32λ����Ҫǰ�油0
			for (int i = 0; i < 32 - md5code.length(); i++) {
				md5code = "0" + md5code;
			}
			return md5code;
		}
		
		public static String getUUID(){
			return UUID.randomUUID().toString().replace("-", "");
		}
}
