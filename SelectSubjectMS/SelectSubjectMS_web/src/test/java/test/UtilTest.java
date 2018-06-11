package test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class UtilTest {
  
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.get(1) + "-" + calendar.get(2) + "-" + calendar.get(5));
	}
}
