package com.launch.diagdevice.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyUtil {

	/**
	 * 用户名格式验证
	 * 用户名6-20位由英文字母开头,包含英文字母,数字或下划线的字符
	 * LIPING
	 */
	public static Boolean isAcount(String str) {
		Boolean bl = false;
		Pattern pt = Pattern.compile("^[a-zA-Z][0-9a-zA-Z_]{5,19}$");// 6-20
		Matcher mt = pt.matcher(str);
		if (mt.matches()) {
			bl = true;
		}
		return bl;
	}

	/**
	 * 密码格式验证
	 * 请输入密码（由字母、数字、@和_组成，长度6-20位）
	 * LIPING
	 */
	public static boolean isPasswordValid(String str) {
		boolean isValid = str.matches("^[a-zA-Z0-9_@]{6,20}$");
		return isValid;
	}

	/**
	 * 判断是否为bigdecimal类型，限制小数点为0-5位
	 * LIPING
	 */
	public static boolean isBigDecimal(String str) {
		Pattern pattern = Pattern.compile("^[0-9]+.?[0-9]{0,5}$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 验证字符串是否为指定的日期format格式
	 * LIPING
	 */
	public static boolean isValidDate(String strDateTime, String format) {
		boolean convertSuccess = true;
		// 指定日期格式为
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			fmt.setLenient(false);
			fmt.parse(strDateTime);
		} catch (ParseException e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

	/**
	 * 比较两日期之间大小
	 * sDate 在 eDate之前返回true
	 * TODO
	 */
	public static boolean compareDate(Date sDate, Date eDate) {
		if (sDate.before(eDate)) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws ParseException {
		String diagStartTime = "2018-01-01 01:01:01";
		String diagEndTime = "2018-01-01 00:01:01";
		Date sdate = DateUtils.convertStringToDate(DateUtils.DATETIME, diagStartTime);
		Date edate = DateUtils.convertStringToDate(DateUtils.DATETIME,diagEndTime);
		boolean res = compareDate(sdate, edate);
		System.out.println("-=-=-=-=-=>:" + res);
	}

}
