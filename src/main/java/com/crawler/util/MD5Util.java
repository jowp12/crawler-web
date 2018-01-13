package com.crawler.util;

import java.security.MessageDigest;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MD5Util {

	/** */
	/**
	 * Constructs the MD5 object and sets the string whose MD5 is to be computed.
	 *
	 * @param inStr
	 *            the <code>String</code> whose MD5 is to be computed
	 */
	public MD5Util() {
	}

	private static MessageDigest md5 = null;

	private static final Lock lock = new ReentrantLock();

	static {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	/** */
	/**
	 * Computes the MD5 fingerprint of a string.
	 * 
	 * @return the MD5 digest of the input <code>String</code>
	 */
	public static String compute(String inStr) {

		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++){
			byteArray[i] = (byte) charArray[i];
		}

		byte[] md5Bytes = null;
		try {
			lock.lock();
			md5.reset();
			md5Bytes = md5.digest(byteArray);
		} finally {
			lock.unlock();
		}

		StringBuilder hexValue = new StringBuilder();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
