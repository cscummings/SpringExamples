package com.cscummings.common;


public class EncryptionUtils {

	/*
	 * Usage: String encPW = encrypt("PutPasswordToEncryptHere");
	 * System.out.println("Encrypted password: " + encPW);
	 */
	public static String encrypt(String inString) {
		String resultString = "";

		for (char c : inString.toCharArray()) {
			int i = (int) c;
			resultString += String.format("%03d", i);
		}
		return resultString;
	}

	public static String decrypt(String inString) {
		int strLength = inString.length() / 3;
		String resultString = "";
		int j = 0;
		int k = 3;
		for (int x = 0; x < strLength; x++) {
			int i = Integer.parseInt(inString.substring(j, k));
			j = k;
			k += 3;
			resultString += (char) i;
		}
		return resultString;
	}

}
