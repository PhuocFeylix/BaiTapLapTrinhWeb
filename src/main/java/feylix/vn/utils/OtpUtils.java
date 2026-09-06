package feylix.vn.utils;

import java.security.SecureRandom;

public class OtpUtils {

	private static final SecureRandom RANDOM = new SecureRandom();

	// Thoi gian song cua OTP: 5 phut
	public static final long OTP_VALID_MILLIS = 5 * 60 * 1000L;

	/**
	 * Sinh 1 ma OTP gom 6 chu so, vi du: "045213"
	 */
	public static String generateOtp() {
		int number = 100000 + RANDOM.nextInt(900000);
		return String.valueOf(number);
	}
}
