package com.qgx.selectSubjectMS.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
/**
 * 字符串工具类
 * @author goxcheer
 *
 */
public class StringUtil {
		/*
		 * 判断是否为空或者不存在
		 */
		public static boolean isNotBlank(String str){
			if(str.equals("")||str==null){
				return false;
			}
			else return true;
		}
		/*
		 * 转化为Md5加密字符串
		 */
		public static String encryptMd5(String plainText) {
			byte[] secretBytes = null;
			try {
				secretBytes = MessageDigest.getInstance("md5").digest(
						plainText.getBytes());
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("没有md5这个算法！");
			}
			String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
			// 如果生成数字未满32位，需要前面补0
			for (int i = 0; i < 32 - md5code.length(); i++) {
				md5code = "0" + md5code;
			}
			return md5code;
		}
		
		public static String getUUID(){
			return UUID.randomUUID().toString().replace("-", "");
		}
}
