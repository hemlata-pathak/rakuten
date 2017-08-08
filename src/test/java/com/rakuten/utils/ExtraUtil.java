package com.rakuten.utils;

import java.util.Random;

public class ExtraUtil {

	public static String convertPriceToDecimalFormat(String productPrice) {
		return productPrice.replaceAll(",", ".").replaceAll("€", "").replaceAll("%", "").trim();
	}

	public static String generateRandomEmail() {
		return "testRakuten" + String.valueOf(generateNdigitRandomNumber(4)) + "@gmail.com";
	}
	
	public static String generateRandomPassword() {
		return "Rakuten@" + String.valueOf(generateNdigitRandomNumber(3));
	}

	// Generate N Digit Random Number
	public static int generateNdigitRandomNumber(int n) {
		Random randGen = new Random();
		Double startNum = Math.pow(10, n - 1);
		int num1 = startNum.intValue();
		Double endNum = Math.pow(10, n);
		int num2 = endNum.intValue();
		int range = num2 - num1 + 1;
		int randomNum = randGen.nextInt(range) + num1;
		return randomNum;
	}
	
}
