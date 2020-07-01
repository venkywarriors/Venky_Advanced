package testbase;

import java.util.Random;

public class RandomTestDataGenerator {
	public static void main(String[] args) {
		
		Random r = new Random();
		String fullName = "fullName" + r.nextInt();
		String firstName = "firstName" + r.nextInt();
		String lastName = "lastName" + r.nextInt();
		String streetAddress = "address" + r.nextInt();
		long phoneNumber = (long) (Math.random() * 100000 + 3333000000L);
		String email = "email" + r.nextInt() + "@gmail.com";
		
		
		System.out.println("The Full Name is : " + fullName);
		System.out.println("The First Name is : " + firstName);
		System.out.println("The Last Name is : " + lastName);
		System.out.println("The Address is : " + streetAddress);
		System.out.println("The Phone Number is : " + phoneNumber);
		System.out.println("The EMail is : " + email);
	}
}
