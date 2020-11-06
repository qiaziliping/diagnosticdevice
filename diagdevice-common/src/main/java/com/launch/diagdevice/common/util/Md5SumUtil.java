package com.launch.diagdevice.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5SumUtil {
	private static final Logger log = LoggerFactory.getLogger(Md5SumUtil.class);

	public static String md5(String source) throws Exception {

		StringBuffer sb = new StringBuffer(32);

		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] array = md.digest(source.getBytes("utf-8"));

		for (int i = 0; i < array.length; i++) {
			sb.append(Integer.toHexString((array[i] & 0xff) | 0x100).toLowerCase().substring(1, 3));
		}

		return sb.toString();
	}

	public static String fileMd5(String sourcePath) {

		StringBuffer sb = new StringBuffer(32);
		// BufferedInputStream in=new BufferedInputStream(new FileInputStream(new
		// File(sourcePath)));
		InputStream fileInputStream = null;
		DigestInputStream digestInputStream = null;
		MessageDigest messageDigest = null;
		// 拿到结果，也是字节数组，包含16个元素

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			fileInputStream = new BufferedInputStream(new FileInputStream(new File(sourcePath)));
			digestInputStream = new DigestInputStream(fileInputStream, MessageDigest.getInstance("MD5"));
			// 缓冲区大小（这个可以抽出一个参数）
			int bufferSize = 256 * 1024;
			// read的过程中进行MD5处理，直到读完文件
			byte[] buffer = new byte[bufferSize];
			while (digestInputStream.read(buffer) > 0)
				;
			// 获取最终的MessageDigest
			messageDigest = digestInputStream.getMessageDigest();
			byte[] array = messageDigest.digest();

			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xff) | 0x100).toLowerCase().substring(1, 3));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				digestInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	 public static void main(String[] args) throws Exception {
		 String sourcePath="E:/logs/mycar.log.1";
		 System.out.println(fileMd5(sourcePath));
	 System.out.println(md5("654321"));
	 }
}
