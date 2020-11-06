package com.launch.diagdevice.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderUtils {

	private static long codeLength = 4;
	private static String randString = "0123456789";// 随机产生的字符串
	// private static String randString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//
	// 随机产生的字符串
	private OrderUtils() {
	}

	public static void main(String[] args) {
		String ss = OrderUtils.getOrderCode("");
		System.out.println(ss);
	}
	
	/**
	 * 获取订单号
	 * @return
	 */
	public static String getOrderCode(String pre) {
		StringBuffer sb = new StringBuffer();
		// 获取当前年月日时分秒
		String timeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// 获取5位随机码
		String randomCode = getRandomCode(4);

		sb.append(pre);
		sb.append(timeStr);
		sb.append(randomCode);

		return sb.toString();
	}
	/**
	 * 获取消费编号 
	 * 默认前缀 11
	 * LIPING
	 */
	public static String getConsumerNo(String pre) {
		StringBuffer sb = new StringBuffer();
		// 获取当前年月日时分秒
		String timeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// 获取5位随机码
		String randomCode = getRandomCode(4);

		sb.append(pre);
		sb.append(timeStr);
		sb.append(randomCode);

		return sb.toString();
	}

	public static String getRandomCode(long cl) {
		long l = codeLength;
		if (cl > 0) {
			l = cl;
		}
		// 生成6位加密串
		Random random = new Random();
		String encodestr = "";
		for (int i = 0; i < l; i++) {
			encodestr = encodestr + String.valueOf(randString.charAt(random.nextInt(randString.length())));
		}
		return encodestr;
	}
}