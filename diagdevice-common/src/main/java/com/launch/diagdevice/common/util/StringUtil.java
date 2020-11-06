package com.launch.diagdevice.common.util;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.springframework.util.StringUtils;

/**
 * @version 0.0.1
 * @since 2018年8月30日
 */
public class StringUtil {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };
	private static char[] letters = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

	/**
	 * 获取字符串的长度，如果有中文，在oracle中一个中文字符占3位
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static int getStringLength(String value) {
		int valueLength = 0;
		if (!StringUtils.isEmpty(value)) {
			String chinese = "[\u0391-\uFFE5]";
			/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为3，否则为1 */
			for (int i = 0; i < value.length(); i++) {
				/* 获取一个字符 */
				String temp = value.substring(i, i + 1);
				/* 判断是否为中文字符 */
				if (temp.matches(chinese)) {
					/* 中文字符长度为3 */
					valueLength += 3;
				} else {
					/* 其他字符长度为1 */
					valueLength += 1;
				}
			}
		}
		return valueLength;
	}

	/**
	 * 
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * 
	 * @return 16进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {

		StringBuffer resultSb = new StringBuffer();

		for (int i = 0; i < b.length; i++) {

			resultSb.append(byteToHexString(b[i]));

		}

		return resultSb.toString();

	}

	private static String byteToHexString(byte b) {

		int n = b;

		if (n < 0)

			n = 256 + n;

		int d1 = n / 16;

		int d2 = n % 16;

		return hexDigits[d1] + hexDigits[d2];

	}

	public static String MD5Encode(String origin) {

		String resultString = null;

		try {

			resultString = new String(origin);

			MessageDigest md = MessageDigest.getInstance("MD5");

			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));

		}

		catch (Exception ex) {

		}

		return resultString;

	}

	/**
	 * 该方法用于替换用户审批时的状态 2012-03-31
	 * 
	 * @author ZhangKui
	 * 
	 */
	public static String changeCharByIndex(String fromString, int index, char value) {
		char[] chars = fromString.toCharArray();
		chars[index] = value;
		return new String(chars);
	}

	public static String replace(String strSource, String strFrom, String strTo) {
		if (strFrom == null || strFrom.equals("")) {
			return strSource;
		}
		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;
		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
		}
		strDest = strDest + strSource;
		return strDest;
	}

	/**
	 * 将dao中使用like的时候将内容转义，可以转义'\','%','_'
	 * 
	 * @param content
	 *            转义前的字符串
	 * @return String 转义后的字符串
	 */
	public static String escapeForDaoLike(String content) {
		// 转义前的字符串不为空，执行替换操作
		if (content != null && !content.equals("")) {
			content = content.replace("\\", "\\\\");
			content = content.replace("%", "\\%");
			content = content.replace("_", "\\_");
			return content;
		} else// 为空，返回空串
		{
			return "";
		}
	}

	public static String generateUUID() {
		// 生成全局唯一UUID
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成全局唯一UUID,去掉"-"
	 * liping
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String encodeId(Long id) {
		if (id == null)
			return null;
		String source = Long.toHexString(id);
		String code = MD5Encode(source).substring(0, 3);
		long sub = Long.parseLong(code, 16) % 10;
		return Long.toHexString(id * 10 + sub);
	}

	public static Long decodeId(String xid) {
		if (xid == null || xid.trim().length() == 0)
			return null;
		long xid10 = Long.parseLong(xid, 16);
		long id = xid10 / 10;
		long sub = xid10 % 10;
		String source = Long.toHexString(id);
		String code = MD5Encode(source).substring(0, 3);
		if (Long.parseLong(code, 16) % 10 == sub) {
			return id;
		} else {
			// throw new RuntimeException();
			return -514L;// error
		}
	}

	public static boolean isNoValue(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean hasValue(String str) {
		return !isNoValue(str);
	}

	/**
	 * 连结字符串.
	 */
	public static String contactString(String... s) {
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < s.length; i++) {
			if (!StringUtils.isEmpty(s[i])) {
				temp.append(s[i]);
			}
		}
		return temp.toString();
	}

	/**
	 * 生成指定长度的随机字母符串.
	 */
	public static final String randomString(int length) {
		Random randGen = new Random();
		if (length < 1) {
			return null;
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = letters[randGen.nextInt(36)];
		}
		return new String(randBuffer);
	}

	/**
	 * 数字自动补零 限6位数
	 * 
	 * @param number
	 * @return
	 */
	public static String autoPaddingZero(Integer number) {
		String pattern = "000000";
		java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);
		return df.format(number);
	}

	/**
	 * 数字自动补零
	 * 
	 * @param number
	 * @param pattern
	 *            格式为 00000 （0的个数是多少就补齐多少位）
	 * @return
	 */
	public static String autoPaddingZero(Integer number, String pattern) {
		java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);
		return df.format(number);
	}

	/**
	 * 校验手机号码的合法性
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		if (mobile == null)
			return false;

		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1-3,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);

		Pattern p1 = Pattern.compile("^(\\(\\d{3}\\)\\d{3}-?\\d{4})|(\\d{3}-\\d{3}-\\d{4})|(\\d{10,11})$");
		Matcher m1 = p1.matcher(mobile);

		if (m.matches() || m1.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 驼峰转下划线
	 * 
	 * @param param
	 * @return
	 */
	public static String camel4underline(String param) {
		Pattern p = Pattern.compile("[A-Z]");
		if (param == null || param.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder(param);
		Matcher mc = p.matcher(param);
		int i = 0;
		while (mc.find()) {
			builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
			i++;
		}

		if ('_' == builder.charAt(0)) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}

	/**
	 * 大陆号码或香港号码均可
	 */
	public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
		return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
	}

	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+除1和4的任意数
	 * 17+除9的任意数 147
	 */
	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 香港手机号码8位数，5|6|8|9开头+7位任意数
	 */
	public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^(5|6|8|9)\\d{7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * Unicode 转中文
	 * 
	 * @param dataStr
	 * @return
	 */
	public static String decodeUnicode(final String dataStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = dataStr.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = dataStr.substring(start + 2, dataStr.length());
			} else {
				charStr = dataStr.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}

	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}

	/**
	 * Unicode转 汉字字符串
	 * 
	 * @param str
	 *            \u6728
	 * @return '木' 26408
	 */
	public static String unicodeToString(String str) {

		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			// group 6728
			String group = matcher.group(2);
			// ch:'木' 26408
			ch = (char) Integer.parseInt(group, 16);
			// group1 \u6728
			String group1 = matcher.group(1);
			str = str.replace(group1, ch + "");
		}
		return str;
	}

	/**
	 * 获取mac地址
	 * 
	 * @param mac
	 * @return
	 */
	public static String getMacAddress(String mac) {
		if (!StringUtils.isEmpty(mac) && mac.length() == 12) {
			String mac1 = mac.substring(0, 2);
			String mac2 = mac.substring(2, 4);
			String mac3 = mac.substring(4, 6);
			String mac4 = mac.substring(6, 8);
			String mac5 = mac.substring(8, 10);
			String mac6 = mac.substring(10, 12);
			String macAddress = mac1 + ":" + mac2 + ":" + mac3 + ":" + mac4 + ":" + mac5 + ":" + mac6;
			return macAddress;
		} else {
			return null;
		}

	}

	/**
	 * duanzongming json 数组返回arrayList
	 * @param json
	 * @param clazz
	 * @return
	 */
	/*
	 * public static <T> ArrayList<T> jsonToArrayList(String json, Class<T>
	 * clazz) { Type type = new TypeToken<ArrayList<JsonObject>>() {}.getType();
	 * ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
	 * 
	 * ArrayList<T> arrayList = new ArrayList<>(); for (JsonObject jsonObject :
	 * jsonObjects) { arrayList.add(new Gson().fromJson(jsonObject, clazz)); }
	 * return arrayList; }
	 */

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	/**
	 * 
	* @Title: generateShortUuid  
	* @Description:随机生成8位的随机数，重复概率低 
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

	public static void main(String[] args) {
		// System.out.println(unicodeToString("\u5317\u4eac"));
		System.out.println(generateShortUuid());
	}
}
