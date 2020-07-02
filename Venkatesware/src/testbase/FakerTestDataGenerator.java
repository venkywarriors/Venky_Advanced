package testbase;

import java.util.Locale;
import com.github.javafaker.Faker;

public class FakerTestDataGenerator {
	public static void main(String[] args) {

		Locale locale = new Locale("en-IND"); // It will generate India specific data.
		Faker faker = new Faker(locale);
		String fullName = faker.name().fullName();
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String address = faker.address().streetAddress();
		String phoneNumber = faker.phoneNumber().phoneNumber();
		String email = faker.internet().emailAddress();
		System.out.println("The Name is : " + fullName);
		System.out.println("The First Name is : " + firstName);
		System.out.println("The Last Name is : " + lastName);
		System.out.println("The Address is : " + address);
		System.out.println("The Address is : " + phoneNumber);
		System.out.println("The EMail is : " + email);
		
		String streetName = faker.address().streetName();
		String number = faker.address().buildingNumber();
		String city = faker.address().city();
		String country = faker.address().country();
		 
		System.out.println(String.format("%s\n%s\n%s\n%s", number, streetName, city,country));
	}
}
