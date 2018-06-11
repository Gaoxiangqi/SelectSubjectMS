package test;

public class StringTest {
	public static void main(String[] args) {
		System.out.println(reverse("abcd"));
	}
	public static String reverse(String str) {
		int length = str.length();
		for (int i=0; i<length-1; i++) {
			str = str.substring(1)+str.charAt(0);
		}
		return str;
	}
}
