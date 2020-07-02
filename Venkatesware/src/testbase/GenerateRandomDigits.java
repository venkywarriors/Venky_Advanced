package testbase;

import java.util.Random;

public class GenerateRandomDigits {

	public static void main(String[] args) {
		
		System.out.println("Number with 7 digit - "+generateRandomDigits(7));
		System.out.println("Number with 8 digit - "+generateRandomDigits(8));
		System.out.println("String with 7 AlphaNumeric - "+randomAlphaNumeric(7));
		System.out.println("String with 8 AlphaNumeric - "+randomAlphaNumeric(8));
		System.out.println("String with 7 char - "+randomString(7));
		System.out.println("String with 8 char - "+randomString(8));
	}
	
	public static int generateRandomDigits(int n) {
	    int m = (int) Math.pow(10, n - 1);
	    return m + new Random().nextInt(9 * m);
	}

	
	public static String randomAlphaNumeric(int count) {
	 final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	StringBuilder builder = new StringBuilder();
	while (count-- != 0) {
	int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
	builder.append(ALPHA_NUMERIC_STRING.charAt(character));
	}
	return builder.toString();
	}
	
	public static String randomString(int count) {
		 final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
		int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
		builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
		}
}
