package com.qgx.selectSubjectMS.utils;

import java.util.Calendar;

/**
 * ���ڹ�����
 * @author goxcheer
 *
 */
public class DateUtil {
    /**
     * ��ȡ��ǰ���� ��ʽ���磺2018-1-1
     * @return
     */
	public static String getDate(){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return year + "-" + month + "-" + day;
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getDate());
	}
}
