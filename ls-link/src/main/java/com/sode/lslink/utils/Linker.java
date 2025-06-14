package com.sode.lslink.utils;

import java.util.Random;

public class Linker {

	private static final int KEY_LEN = 6;
	private static final String PRINTABLES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; 

	public static String generateAccessKey() {

		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < KEY_LEN; i++) {
			int randomIndex = random.nextInt(PRINTABLES.length());
			sb.append(PRINTABLES.charAt(randomIndex));
		}

		return sb.toString();

	}

}
