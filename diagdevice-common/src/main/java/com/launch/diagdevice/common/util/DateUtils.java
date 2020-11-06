package com.launch.diagdevice.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

/**
 * <p>
 * 日期工具类
 * <p>
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月8日
 */
public class DateUtils {

	public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_SECOND = "yyyy-MM-dd HH:mm";
	public static final String DATE_SOLAR_CALENDAR = "yyyy-MM-dd HH";
	public static final String DATE = "yyyy-MM-dd";
	public static final String CHADATE = "yyyy年MM月dd日";// 中文格式
	public static final String ENDATE = "yyyy.MM.dd";// 英文格式
	public static final String ENDATETIME = "MM.dd HH:mm";// 英文格式
	public static final String DATEYEAR = "yyyy";
	public static final String DATEMOTH = "yyyy-MM";
	public static final String DATE_TIME_PATTERN = "yyyyMMddHHmmss";
	public static final String DATE_TIME_PATTERN_MILLISECOND = "yyyyMMddHHmmssSSS";
	/** 时分 */
	public static final String DATE_HOUR_MINUTE = "HH:mm";
	public static final String DATE_HOUR_SECOND = "HH:mm:ss";
	// public static TimeUtil timeUtil = new TimeUtil();
	/** 格式化只得到日 */
	public static final String DF_DAY = "dd";
	// 一年的假期
	private static final String[][] HOLIDAY = { { "1", "2", "3", "9", "10", "16", "17", "23", "24", "30", "31" },
			{ "7", "8", "9", "10", "11", "12", "13", "20", "21", "27", "28" },
			{ "5", "6", "12", "13", "19", "20", "26", "27" },
			{ "2", "3", "4", "9", "10", "16", "17", "23", "24", "30" },
			{ "1", "2", "7", "8", "14", "15", "21", "22", "28", "29" },
			{ "4", "5", "9", "10", "11", "18", "19", "25", "26" },
			{ "2", "3", "9", "10", "16", "17", "23", "24", "30", "31" },
			{ "6", "7", "13", "14", "20", "21", "27", "28" }, { "3", "4", "10", "11", "15", "16", "17", "24", "25" },
			{ "1", "2", "3", "4", "5", "6", "7", "15", "16", "22", "23", "29", "30" },
			{ "5", "6", "12", "13", "19", "20", "26", "27" }, { "3", "4", "10", "11", "17", "18", "24", "25", "31" } };

	/**
	 * 获取当前日期
	 * @return
	 * @since DBS V100
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取当前时间
	 * @return
	 * @since DBS V100
	 */
	public static Timestamp getCurrentTime() {
		return new Timestamp(new Date().getTime());
	}

	public static String convertDateToString(Date aDate) {
		return getDateTime(DATETIME, aDate);
	}

	// 精确到分
	public static String convertDateToStringMM(Date aDate) {
		return getDateTime(DATE_SECOND, aDate);
	}

	public static String convertDateToString(Date aDate, String fmDate) {
		return getDateTime(fmDate, aDate);
	}

	public static String convertDateToString(String aMask, Date aDate) {
		return getDateTime(aMask, aDate);
	}

	public static Date convertStringToDate(final String strDate) {
		try {
			return convertStringToDate(DATETIME, strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * 获得日期与本周一相差的天数
	 * @param date
	 * @return
	 */
	public static int getMondayPlus(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	/**
	 * @function 获取当前日期的上一天
	 * @return 日期
	 */
	public static Date getYesterday() {
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DATE, -1);
		return cd.getTime();
	}

	/**
	 * @function 获取给定日期的前一天
	 * @return 日期
	 */
	public static Date getYesterday(Date date) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.DATE, -1);
		return cd.getTime();
	}

	/**
	 * @function 获取给定日期的后一天
	 * @return 日期
	 */
	public static Date getTomorrow(Date date) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.DATE, 1);
		return cd.getTime();
	}

	/**
	 * @function 如果date比当前时间早，返回true；反之返回false
	 * @param date
	 *            日期
	 * @return boolean
	 */
	public static boolean isBypastTime(Date date) {
		Calendar cd1 = Calendar.getInstance();
		Calendar cd2 = Calendar.getInstance();
		cd2.setTime(date);
		return cd1.after(cd2);
	}

	/**
	 * 获取两个周一之间所有的周一列表（格式yyyy-MM-dd)
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getBetweenMondays(String date1, String date2) throws ParseException {
		if (StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2))
			return null;
		List<String> list = new ArrayList<String>();
		if (!date1.equals(date2)) {
			String tmp;
			if (date1.compareTo(date2) > 0) { // 确保 date1的日期不晚于date2
				tmp = date1;
				date1 = date2;
				date2 = tmp;
			}
			tmp = convertDateToString(DATE, convertStringToDate(DATE, date1));// 格式化
			while (tmp.compareTo(date2) <= 0) {
				list.add(tmp);
				tmp = addDate(DATE, tmp, "D", 7);
			}
		} else {
			list.add(date1);
		}
		return list;
	}

	/**
	 * 获取两个日期之间所有的日期列表（格式yyyy-MM-dd)
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getBetweenDates(String date1, String date2) throws ParseException {
		if (StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2))
			return null;
		List<String> list = new ArrayList<String>();
		if (!date1.equals(date2)) {
			String tmp;
			if (date1.compareTo(date2) > 0) { // 确保 date1的日期不晚于date2
				tmp = date1;
				date1 = date2;
				date2 = tmp;
			}
			tmp = convertDateToString(DATE, convertStringToDate(DATE, date1));// 格式化
			while (tmp.compareTo(date2) <= 0) {
				list.add(tmp);
				tmp = addDate(DATE, tmp, "D", 1);
			}
		} else {
			list.add(date1);
		}
		return list;
	}

	/**
	 * 获取两个月份之间所有的月份列表（格式yyyy-MM)
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getBetweenMonths(String date1, String date2) throws ParseException {
		if (StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2))
			return null;
		List<String> list = new ArrayList<String>();
		if (!date1.equals(date2)) {
			String tmp;
			if (date1.compareTo(date2) > 0) { // 确保 date1的日期不晚于date2
				tmp = date1;
				date1 = date2;
				date2 = tmp;
			}
			tmp = convertDateToString(DATEMOTH, convertStringToDate(DATEMOTH, date1));// 格式化
			while (tmp.compareTo(date2) <= 0) {
				list.add(tmp);
				tmp = addDate(DATEMOTH, tmp, "M", 1);
			}
		} else {
			list.add(date1);
		}
		return list;
	}

	/**
	 * 获得两个日期之间月的个数
	 * 
	 * @param s
	 * @param e
	 * @return
	 */
	public static int getMonthSize(Date s, Date e) {
		if (s.after(e)) {
			Date t = s;
			s = e;
			e = t;
		}
		Calendar start = Calendar.getInstance();
		start.setTime(s);
		Calendar end = Calendar.getInstance();
		end.setTime(e);
		Calendar temp = Calendar.getInstance();
		temp.setTime(e);
		temp.add(Calendar.DATE, 1); 
		int y = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
		int m = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);

		if ((start.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
			return y * 12 + m + 1;
		} else if ((start.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
			return y * 12 + m;
		} else if ((start.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {
			return y * 12 + m;
		} else {
			return (y * 12 + m - 1) < 0 ? 0 : (y * 12 + m - 1);
		}
	}

	/**
	 * 比较两个日期的大小 author:sdarmy
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(Date date1, Date date2) {
		if (date1.getTime() > date2.getTime()) {
			return 1;
		} else if (date1.getTime() < date2.getTime()) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 两个日期相差的天数 author:sdarmy
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getGapOfTwoDate(Date date1, Date date2) {
		long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0
				? (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000)
				: (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
		return Integer.parseInt(String.valueOf(day));

	}

	/**
	 * 日期增加或者减少秒，分钟，天，月，年
	 * @param srcDate
	 * @param type 类型 Y M D HH MM SS 年月日时分秒
	 * @param offset （整数）
	 * @return 增加或者减少之后的日期
	 */
	public static java.util.Date addDate(Date srcDate, String type, int offset) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(srcDate);
		if (type.equals("SS")) {
			gc.add(Calendar.SECOND, offset);
		} else if (type.equals("MM")) {
			gc.add(Calendar.MINUTE, offset);
		} else if (type.equals("HH")) {
			gc.add(Calendar.HOUR, offset);
		} else if (type.equals("D")) {
			gc.add(Calendar.DATE, offset);
		} else if (type.equals("M")) {
			gc.add(Calendar.MONTH, offset);
		} else if (type.equals("Y")) {
			gc.add(Calendar.YEAR, offset);
		}
		return gc.getTime();
	}

	public static String addDate(String srcDate, String type, int offset) throws ParseException {
		return convertDateToString(addDate(convertStringToDate(srcDate), type, offset));
	}

	public static String addDate(String aMask, String srcDate, String type, int offset) throws ParseException {
		return convertDateToString(aMask, addDate(convertStringToDate(aMask, srcDate), type, offset));
	}

	public static int getAgeByBirthday(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

	/**
	 * 判断时间戳是否符合格式
	 * @param date时间戳
	 * @param pattern 格式
	 * @return
	 */
	public static boolean isDatePattern(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		boolean result = false;
		try {
			if ((!StringUtils.isEmpty(date)) && date.trim().length() == pattern.trim().length()) {
				sdf.setLenient(false);
				System.out.println(sdf.parse(date));
				result = true;
			}
		} catch (ParseException e) {
			result = false;
		}
		return result;
	}

	public static long datetoUnixTimes(Date date) {
		return date.getTime() / 1000;
	}

	public static Date unixTimeToDate(long time) {
		return new Date(time * 1000);
	}

	/**
	 * 两日期相差的天数。
	* @Title: dateMinus  
	* @Description: TODO
	* @param @param endTime  yyyy-MM-dd
	* @param @param startTime  yyyy-MM-dd
	* @param @return 参数
	* @return int    返回类型  
	* @author:duanzongming 
	* @throws
	 */
	public static int dateMinus(String endTime, String startTime) {

		Date d2 = strToDate(endTime, DATE);
		Date d1 = strToDate(startTime, DATE);
		// 两个日期的时间错，相差数除以60*60*24
		Long value = (d2.getTime() / 1000 - d1.getTime() / 1000) / 86400;
		return Integer.valueOf(value + "");
	}

	/**
	 * 两日期相差的天数。
	* @Title: dateMinus  
	* @Description: TODO
	* @param @param endTime  yyyy-MM-dd
	* @param @param startTime  yyyy-MM-dd
	* @param @return 参数
	* @return int    返回类型  
	* @author:duanzongming 
	* @throws
	 */
	public static int dateMinusByDate(Date endTime, Date startTime) {

		// 两个日期的时间错，相差数除以60*60*24
		Long value = (endTime.getTime() / 1000 - startTime.getTime() / 1000) / 86400;
		return Integer.valueOf(value + "");
	}

	/**
	 * 
	* @Title: DateAdd  
	* @Description: TODO
	* @param @param dateStr 日期字符串
	* @param @param dayNum 以整天为单位
	* @param @return 参数
	* @return String    返回类型  
	* @author:duanzongming 
	* @throws
	 */
	public static Date DateAdd(Date date, int dayNum) {
		// convertStringToDate(dateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, dayNum);
		return calendar.getTime();
	}
    /**
     * 
    * @Title: AddMonth  
    * @Description: 添加月份数  
    * @param @param date
    * @param @param monthNum
    * @param @return    参数  
    * @return Date    返回类型  
    * @throws
     */
	public static Date AddMonth(Date date, int monthNum) {
		// convertStringToDate(dateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, monthNum);
		return calendar.getTime();
	}
	
	public static Date getYYYMmdd(Date d) {
		String strd = convertDateToString(d);
		return strToDate(strd, DATE);
	}

	public static Date getYYYMm(Date d) {
		String strd = convertDateToString(d);
		return strToDate(strd, DATEMOTH);
	}

	/**
	 * 
	* @Title: strToDate  
	* @Description: TODO
	* @param @return 参数
	* @return Date    返回类型  
	* @author:duanzongming 
	* @throws
	 */
	public static Date strToDate(String dateStr, String simpleDateStr) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(simpleDateStr);
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 是否是节假日。
	* @Title: isHoliday  
	* @Description: TODO
	* @param @param date
	* @param @return 参数
	* @return boolean    返回类型  
	* @author:duanzongming 
	* @throws
	 */
	public static boolean isHoliday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);// 获取月份 月份从0开始。数组也是从0开始
		int day = calendar.get(Calendar.DATE);// 获取日
		String[] days = HOLIDAY[month];
		for (String str : days) {
			if (str.equals(day + "")) {
				return true;
			}
		}
		return false;
	}

	public static String getWeekDay(Date date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		String d = convertDateToString("yyyy.MM.dd", date);
		return d + " " + weekDays[w];
	}

	/**
	 * 某一个月第一天和最后一天
	 * @param date
	 * @return
	 */

	/**
	 * 当月第一天
	 * @return
	 */
	public static String getFirstDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();

		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
		return str.toString();

	}

	/**
	 * 当月最后一天
	 * @return
	 */
	public static String getLastDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		// 上个月最后一天
		calendar.add(Calendar.MONTH, 1); // 加一个月
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		String day_last = df.format(calendar.getTime());
		StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
		day_last = endStr.toString();
		return day_last;

	}

	/**
	 * 获取两个月内的提醒日期
	* @Title: getRemind  
	* @Description: TODO
	* @param @param date 日期
	* @param @param type 类型。0代表单次、1每天、2每周、3每月、4工作日
	* @param @return 参数
	* @return Date    返回类型  
	* @author:duanzongming 
	* @throws
	 */
	public static List<Date> getRemind(Date date, int type) {
		List<Date> list = new ArrayList<Date>();
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);// 拿到具体的时间
		list.add(date);
		if (type == 2) {
			for (int i = 0; i < 15; i++) {
				Calendar calendar1 = calendar;
				calendar1.add(Calendar.DATE, 7);
				calendar = calendar1;
				// 超过两个
				if (calendar2.get(Calendar.MONTH) + 1 < calendar1.get(Calendar.MONTH)) {
					break;
				}
				list.add(calendar1.getTime());
			}
		} else if (type == 4) {
			for (int i = 0; i < 100; i++) {
				Calendar calendar1 = calendar;
				calendar1.add(Calendar.DATE, 1);// 加一天
				// 如果是节假日，则跳出
				if (isHoliday(calendar1.getTime())) {
					continue;
				}
				calendar = calendar1;
				// 超过两个
				if (calendar2.get(Calendar.MONTH) + 1 < calendar1.get(Calendar.MONTH)) {
					break;
				}
				list.add(calendar1.getTime());
			}
		} else if (type == 1) {
			for (int i = 0; i < 100; i++) {
				Calendar calendar1 = calendar;
				calendar1.add(Calendar.DATE, 1);// 加一天
				calendar = calendar1;
				// 超过两个
				if (calendar2.get(Calendar.MONTH) + 1 < calendar1.get(Calendar.MONTH)) {
					break;
				}
				list.add(calendar1.getTime());
			}
		} else if (type == 3) {// 每月
			for (int i = 0; i < 1; i++) {
				Calendar calendar1 = calendar;
				calendar1.add(Calendar.MONTH, 1);// 加一个月
				calendar = calendar1;
				list.add(calendar1.getTime());
			}
		}
		return list;
	}

	/**
	 * 获取两个月内的提醒日期
	* @Title: getRemind  
	* @Description: TODO
	* @param @param date 日期
	* @param @param type 类型。0代表单次、1每天、2每周、3每月、4工作日
	* @param @return 参数
	* @return Date    返回类型  
	* @author:duanzongming 
	* @throws
	 */
	public static List<Date> getTwoMonthRemind(Date date, int type) {
		List<Date> list = new ArrayList<Date>();
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);// 拿到具体的时间
		// list.add(date);
		if (type == 2) {
			for (int i = 0; i < 15; i++) {
				Calendar calendar1 = calendar;
				calendar1.add(Calendar.DATE, 7);
				calendar = calendar1;
				// 超过两个
				if (calendar2.get(Calendar.MONTH) + 1 < calendar1.get(Calendar.MONTH)) {
					break;
				}
				list.add(calendar1.getTime());
			}
		} else if (type == 4) {
			for (int i = 0; i < 100; i++) {
				Calendar calendar1 = calendar;
				calendar1.add(Calendar.DATE, 1);// 加一天
				// 如果是节假日，则跳出
				if (isHoliday(calendar1.getTime())) {
					continue;
				}
				calendar = calendar1;
				// 超过两个
				if (calendar2.get(Calendar.MONTH) + 1 < calendar1.get(Calendar.MONTH)) {
					break;
				}
				list.add(calendar1.getTime());
			}
		} else if (type == 1) {
			for (int i = 0; i < 100; i++) {
				Calendar calendar1 = calendar;
				calendar1.add(Calendar.DATE, 1);// 加一天
				calendar = calendar1;
				// 超过两个
				if (calendar2.get(Calendar.MONTH) + 1 < calendar1.get(Calendar.MONTH)) {
					break;
				}
				list.add(calendar1.getTime());
			}
		} else if (type == 3) {// 每月
			for (int i = 0; i < 1; i++) {
				Calendar calendar1 = calendar;
				calendar1.add(Calendar.MONTH, 2);// 加一个月
				calendar = calendar1;
				list.add(calendar1.getTime());
			}
		}
		return list;
	}

	/**
	 * 根据分钟或秒，或者毫秒转化为 想要的日期格式
	 * @param 类型 type 1:分钟,2:秒,3:毫秒
	 * @param 时间
	 * @param 格式化
	 * @author:LIPING
	 */
	public static String getStrDateByLongTime(int type, long time, String format) {
		String result = null; // 最终返回结果
		long longtime = 0;
		if (StringUtils.isEmpty(format)) {
			format = DATE_HOUR_MINUTE;
		}
		DateFormat df = new SimpleDateFormat(format);
		switch (type) {
		case 1:
			longtime = time * 60 * 1000;// 分钟转为毫秒
			break;
		case 2:
			longtime = time * 1000;// 秒转为毫秒
			break;
		case 3:
			longtime = time;
			break;
		default:
			break;
		}
		long raw = TimeZone.getDefault().getRawOffset(); // 获取时区差毫秒数
		longtime = longtime - raw;
		Date date = new Date(longtime);
		result = df.format(date);
		return result;
	}

	/**
	 * 日期国际化显示
	* @Title: convertStringToString  
	* @Description: TODO
	* @param @param aMask
	* @param @param param
	* @param @return 参数
	* @return String    返回类型  
	* @author:duanzongming 
	* @throws
	 */
	public static String convertStringToString(String format, String strdate) {

		try {
			SimpleDateFormat simpDate = new SimpleDateFormat(format);
			Date d = convertStringToDate(DateUtils.DATE, strdate);
			return simpDate.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 得到这个月所有的日期列表
	 * format 
	 * date格式：yyyy-MM-dd
	 * @throws ParseException 
	 */
	public static List<String> getStrAllTheDateOftheMonth(String format, String strdate) throws ParseException {
		List<String> list = new ArrayList<String>();

		Date date = DateUtils.convertStringToDate(DateUtils.DATE, strdate);

		DateFormat df = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		cal.add(Calendar.MONTH, 1); // 0表示上月，1表示本月，2表示下月
		cal.add(Calendar.DATE, -1);
		String lastDay = df.format(cal.getTime());
		String aDay = "";
		int i = 1;
		while (!lastDay.equals(aDay)) {
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), i);
			aDay = df.format(cal.getTime());
			// System.out.println(sf.format(cal.getTime()));
			list.add(df.format(cal.getTime()).trim());
			i++;
		}
		return list;
	}

	/**
	 * 根据date获取这一周所有的日期列表
	 * @throws ParseException 
	 */
	public static List<String> getStrAllTheDateOftheWeek(String format, String strdate) throws ParseException {
		final List<String> resultlist = new ArrayList<String>();

		Date date = DateUtils.convertStringToDate(DateUtils.DATE, strdate);

		SimpleDateFormat sf = new SimpleDateFormat(format);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date);
		cal2.add(Calendar.WEEK_OF_MONTH, 0); // 0表示本周，-1表示上周，1表示下周,-2表示上上周

		for (int i = 0; i < 7; i++) {
			cal2.add(Calendar.DATE, -1 * cal2.get(Calendar.DAY_OF_WEEK) + 1 + i);
			resultlist.add(sf.format(cal2.getTime()));
		}

		return resultlist;
	}

	/**
	 * 得到月所有的日期列表
	 * @param type 0表示上月，1表示本月，2表示下月
	 */
	public static List<String> getStrAllTheDateOfMonthList(String format, String strdate, int type)
			throws ParseException {
		List<String> list = new ArrayList<String>();

		Date date = DateUtils.convertStringToDate(DateUtils.DATE, strdate);

		DateFormat df = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		cal.add(Calendar.MONTH, type); // 0表示上月，1表示本月，2表示下月
		cal.add(Calendar.DATE, -1);
		String lastDay = df.format(cal.getTime());
		String aDay = "";
		int i = 1;
		while (!lastDay.equals(aDay)) {
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), i);
			aDay = df.format(cal.getTime());
			list.add(df.format(cal.getTime()).trim());
			i++;
		}
		return list;
	}

	/**
	 * 根据date获取一周所有的日期列表
	 * @param type 0表示本周，-1表示上周，1表示下周,-2表示上上周
	 * @throws ParseException 
	 * 
	 */
	public static List<String> getStrAllTheDateOfWeekList(String format, String strdate, int type)
			throws ParseException {
		final List<String> resultlist = new ArrayList<String>();

		Date date = DateUtils.convertStringToDate(DateUtils.DATE, strdate);

		SimpleDateFormat sf = new SimpleDateFormat(format);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date);
		cal2.add(Calendar.WEEK_OF_MONTH, type); // 0表示本周，-1表示上周，1表示下周,-2表示上上周

		for (int i = 0; i < 7; i++) {
			cal2.add(Calendar.DATE, -1 * cal2.get(Calendar.DAY_OF_WEEK) + 1 + i);
			resultlist.add(sf.format(cal2.getTime()));
		}

		return resultlist;
	}

	/**
	 * 传入日期，获取年龄
	 * @param birthday
	 * @return
	 */
	public static int getAge(String birthday) {
		try {
			Date bd = convertStringToDate(DATE, birthday);
			Calendar c = Calendar.getInstance();
			c.setTime(bd);
			int birthdayYear = c.get(Calendar.YEAR);// 年
			c.setTime(new Date());
			int curYear = c.get(Calendar.YEAR);
			return curYear - birthdayYear;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		// return 0;
	}

	// 通过年龄拿到生日
	public static String getBirthday(int age) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int curYear = c.get(Calendar.YEAR);// 拿到当年的年
		int birthYear = curYear - age;// 拿到出生的年
		String birthday = birthYear + "-01-01";
		return birthday;
	}

	/**
	 * 获取上月的当天
	 * @Description: TODO
	 * @param  da
	 * @param  format
	 * @return String    返回类型
	 * @author:LIPING
	 */
	public static String getBeforeMonthCurrDay(Date da, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(da);
		cal.add(Calendar.MONTH, -1); // -1表示上月，0表示本月，1表示下月
		Date date = cal.getTime();
		String str = DateUtils.convertDateToString(date, format);
		return str;
	}

	/**
	 * 获取当天日期月份的最后一天
	 * @Title: getMonthLastDay
	 * @param  date
	 * @param  format
	 * @return Date    返回类型
	 * @author:LIPING
	 * @throws
	 */
	public static Date getMonthLastDay(Date date) {
		// 获取Calendar
		Calendar calendar = Calendar.getInstance();
		// 设置时间,当前时间不用设置
		calendar.setTime(date);
		// 设置日期为本月最大日期
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

		return calendar.getTime();
	}

	/**
	 * 指定的时间添加上秒数后，获得新的时间 
	 * @param beforeDate
	 * @param seconds
	 * @return
	 */
	public static Date getAfterDate(Date beforeDate, int seconds) {
		Calendar c = new GregorianCalendar();
		c.setTime(beforeDate);
		c.add(Calendar.SECOND, seconds);
		return c.getTime();
	}

	/**
	 * 通过睡眠的开始时间及结束时间获取睡眠的所属日期
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String getSleepBelongDate(Date startTime, Date endTime) {
		Calendar c = Calendar.getInstance();
		// c.setTime(startTime);
		c.setTime(startTime);
		int startHour = c.get(Calendar.HOUR_OF_DAY);
		// String startDate=convertDateToString(startTime);
		String startDate = convertDateToString(startTime);
		// 没跨天
		if (startHour < 18) {
			String et = startDate.substring(0, 10);
			return et;
		} else {
			c.add(Calendar.DATE, +1);
			Date stime = c.getTime();
			String st = convertDateToString(stime).substring(0, 10);
			return st;
		}
	}

	/**
	 * 计算日期是否跨度18:00
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean isSpan(Date startTime, Date endTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(endTime);
		int ehour = c.get(Calendar.HOUR_OF_DAY);
		c.setTime(startTime);
		int sHour = c.get(Calendar.HOUR_OF_DAY);
		if (ehour >= 18 && sHour < 18) {
			return true;
		} else {
			return false;
		}

	}

	public static Date getEndTime(Date startTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(startTime);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String endTime = year + "-" + month + "-" + day + " 18:00:00";
		return strToDate(endTime, DATETIME);
	}

	/**
	 * 根据日期得到第几周
	 * 当date为null时，得到当前日期的第几周
	 * 周一为一周的第一天
	 * @param date
	 */
	public static int getNOWeek(Date date) {

		Calendar c = Calendar.getInstance();
		// 把周一设为一周的第一天
		c.setFirstDayOfWeek(Calendar.MONDAY);
		if (null != date) {
			c.setTime(date);
		}
		int num = c.get(Calendar.WEEK_OF_YEAR);
		return num;
	}

	/**
	 * 
	 * redies 设置运动是否推送消息，每次推完消息后。到第二天0点失效
	 * duanzongming
	 */
	public static Long getSportRemindInvalid() {
		try {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, 1);
			Date d = c.getTime();
			String s = convertDateToString(DATE, d);
			Date d1 = convertStringToDate(DATE, s);
			Date dd = new Date();
			return d1.getTime() / 1000 - dd.getTime() / 1000;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0L;
		}

	}
	
	public static String getBelongDate(Date date){
		return convertDateToString(date, DATE);
	}
	/**
	 * 当前的时间距离0点相差的秒数
	* @Title: getDifferenceSecond  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param @param date1
	* @param @param date2
	* @param @return    参数  
	* @return long    返回类型  
	* @throws
	 */
	public static long getDifferenceSecond(Date time) {
		Date date1=strToDate(convertDateToString(time, "yyyy-MM-dd 23:59:59") , DATETIME);
		Long dateL1 = date1.getTime()/1000;
		
		Long dateL2 = time.getTime()/1000;
		return dateL1 - dateL2;
	}

	public static long getDifferenceOfTwoTime(Date date1, Date date2) {
		Long dateL1 = date1.getTime()/1000;
		Long dateL2 = date2.getTime()/1000;
		return Math.abs(dateL1 - dateL2)/60;
	}
	public static void main(String[] args) throws ParseException {
	
		
		System.out.println(DateUtils.AddMonth(new Date(), -1));

	}

}
