package com.ancun.core.utils;

import java.util.Random;
import java.util.UUID;

/**
 * ����϶�UUID,����36���Ƶ�128λuuid
 * 
 * @author fish
 * 
 */
public final class ShortUUIDGenerator {

	public static String fromString(String name) {
		return toShortString(UUID.fromString(name));
	}

	public static String nameUUIDFromBytes(byte[] bytes) {
		return toShortString(UUID.nameUUIDFromBytes(bytes));
	}

	public static String randomUUID() {
		return toShortString(UUID.randomUUID());
	}

	public static String generate() {
		return randomUUID();
	}

	private static String toShortString(UUID u) {
		return UUIDtoString(u);
	}

	private static String UUIDtoString(UUID u) {
		long mostSigBits = u.getMostSignificantBits();
		long leastSigBits = u.getLeastSignificantBits();
		return (digits(mostSigBits >> 32) + digits(mostSigBits)
				+ digits(leastSigBits >> 32) + digits(
				leastSigBits));
	}

	private static String digits(long val) {
		return Long.toString((val & 4294967295L), 36);
	}
	
	// 获取随机字符串
	public static String getRandomString(int length) { // length 字符串长度
		StringBuffer buffer = new StringBuffer(ShortUUIDGenerator.randomUUID().toUpperCase());
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}
	
}
