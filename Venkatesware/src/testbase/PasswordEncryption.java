package testbase;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class PasswordEncryption {

	public static void main(String[] args) {
		final String PASS = "okokok";
		Encoder encode = Base64.getEncoder();
		byte[] encodedPwdBytes = encode.encode(PASS.getBytes());
		String encodedPwd = new String(encodedPwdBytes);
		System.out.println("Encoded -->" + encodedPwd.toString());
		String output = passwordDecoder(encodedPwd.toString());
		System.out.println("output --->" + output);
	}

	public static String passwordDecoder(String encodedPassword) {
		String decodedPassword = null;
		try {
			String password = encodedPassword;
			Decoder decode = Base64.getDecoder();
			byte[] decodedPwdBytes = decode.decode(password);
			decodedPassword = new String(decodedPwdBytes);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return decodedPassword;
	}
}
