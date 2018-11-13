package com.yhao.SeimiCrawler.util;

import org.apache.commons.collections.map.LinkedMap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 *
 * @author: long.hua
 * @date: 2016-02-16 15:32
 * @since 1.0
 */
public class DateUtil {
	
	private final static SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
	private final static SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
	private final static SimpleDateFormat ym1 = new SimpleDateFormat("yyyyMM");
	private final static SimpleDateFormat ym2 = new SimpleDateFormat("yyyy-MM");
	private final static SimpleDateFormat ymd1 = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat ymd2 = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat ymd3 = new SimpleDateFormat("yyyy年MM月dd日");
	private final static SimpleDateFormat ymd4 = new SimpleDateFormat("yyyy.MM.dd");
	private final static SimpleDateFormat ymdhms1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private final static SimpleDateFormat ymdhms2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * HH:mm
	 */
	public static String hm(Date date) {
		if (date == null) {
			return null;
		}
		return hm.format(date);
	}
	
	/**
	 * HH:mm:ss
	 */
	public static String hms(Date date) {
		if (date == null) {
			return null;
		}
		return hms.format(date);
	}
	
	/**
	 * yyyyMM
	 */
	public static String ym1(Date date) {
		if (date == null) {
			return null;
		}
		return ym1.format(date);
	}
	
	/**
	 * yyyy-MM
	 */
	public static String ym2(Date date) {
		if (date == null) {
			return null;
		}
		return ym2.format(date);
	}
	
	/**
	 * yyyyMMdd
	 */
	public static String ymd1(Date date) {
		if (date == null) {
			return null;
		}
		return ymd1.format(date);
	}
	
	/**
	 * yyyy-MM-dd
	 */
	public static String ymd2(Date date) {
		if (date == null) {
			return null;
		}
		return ymd2.format(date);
	}
	
	/**
	 * yyyy年MM月dd日
	 */
	public static String ymd3(Date date) {
		if (date == null) {
			return null;
		}
		return ymd3.format(date);
	}

	/**
	 *
	 * yyyy.MM.dd
	 */
	public static String ymd4(Date date) {
		if (date == null) {
			return null;
		}
		return ymd4.format(date);
	}
	
	/**
	 * yyyyMMddHHmmssSSS
	 */
	public static String ymdhms1(Date date) {
		if (date == null) {
			return null;
		}
		return ymdhms1.format(date);
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String ymdhms2(Date date) {
		if (date == null) {
			return null;
		}
		return ymdhms2.format(date);
	}
	
	public static Date getYMD(int year, int month, int dayOfMonth) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return cal.getTime();
	}
	
	/**
	 * 判断年月格式是否正确
	 *
	 * @param date
	 * @return
	 */
	public static String formatYM2(String date) {
		if (StringUtil.isBlank(date)) {
			return null;
		}
		
		String[] split = date.split("-");
		if (split.length >= 2) {
			if (split[0].length() == 4) {
				int len = split[1].length();
				if (len <= 2 && len >= 1) {
					if (len == 1) {
						split[1] = "0" + split[1];
					}
					return split[0] + "-" + split[1];
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 判断年月格式是否正确
	 *
	 * @param date
	 * @return
	 */
	public static String formatYMD2(String date) {
		if (StringUtil.isBlank(date)) {
			return null;
		}
		
		String[] split = date.split("-");
		if (split.length >= 3) {
			if (split[0].length() == 4) {
				int len = split[1].length();
				if (len <= 2 && len >= 1) {
					if (len == 1) {
						split[1] = "0" + split[1];
					}
					
					int day = split[2].length();
					if (day <= 2 && day >= 1) {
						if (day == 1) {
							split[2] = "0" + split[1];
						}
						return split[0] + "-" + split[1] + "-" + split[2];
					}
				}
			}
		}
		
		return null;
	}
	
	public static int compareYM2(String firstYm2, String secondYm2) {
		if (firstYm2.matches("\\d{4}-\\d{2}") && secondYm2.matches("\\d{4}-\\d{2}")) {
			firstYm2 = firstYm2.replace("-", "");
			secondYm2 = secondYm2.replace("-", "");
			return firstYm2.compareTo(secondYm2);
		} else {
			throw new RuntimeException("比较参数格式错误：" + firstYm2 + "，" + secondYm2);
		}
	}

	/**
	 * 同一天则返回0
	 * @param firstYmd2
	 * @param secondYmd2
	 * @return
	 */
	public static int compareYMd2(String firstYmd2, String secondYmd2) {
		if (firstYmd2.matches("\\d{4}-\\d{2}-\\d{2}") && secondYmd2.matches("\\d{4}-\\d{2}-\\d{2}")) {
			firstYmd2 = firstYmd2.replace("-", "");
			secondYmd2 = secondYmd2.replace("-", "");
			return firstYmd2.compareTo(secondYmd2);
		} else {
			throw new RuntimeException("比较参数格式错误：" + firstYmd2 + "，" + secondYmd2);
		}
	}

	/**
	 * 同一时间则返回0
	 * @param firstYmdhms2
	 * @param secondYmdhms2
	 * @return
	 */
	public static int compareYMDHMS2(String firstYmdhms2, String secondYmdhms2) {
		if (firstYmdhms2.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")
				&& secondYmdhms2.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
			firstYmdhms2 = firstYmdhms2.replace("-", "");
			firstYmdhms2 = firstYmdhms2.replace(":", "");
			secondYmdhms2 = secondYmdhms2.replace("-", "");
			secondYmdhms2 = secondYmdhms2.replace(":", "");
			return firstYmdhms2.compareTo(secondYmdhms2);
		} else {
			throw new RuntimeException("比较参数格式错误：" + firstYmdhms2 + "，" + secondYmdhms2);
		}
	}

	/**
	 * 获取某天开始的时间
	 * @return
	 */
	public static String getStartTime(Date date) {
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return ymdhms2(date);
	}

	/**
	 * 获取某天结束的时间
	 * @return
	 */
	public static String getEndTime(Date date) {
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		return ymdhms2(date);
	}

	/**
	 * 获取时间段的所有天数
	 * @param startTime
	 * @param endTime
	 * @return yyyy年MM月dd日
	 */
	public static Map<String,Object> getAllDateBetweenTimes(Date startTime,Date endTime){

		Map<String ,Object> timeMap = new LinkedMap();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		while(calendar.getTime().before(endTime)){
			timeMap.put(sdf.format(calendar.getTime()),0);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		timeMap.put(sdf.format(endTime),0);

		return timeMap;
	}

	public static List<String> getAllDateListBetweenTimes(Date startTime,Date endTime){

		List<String> list = new ArrayList<>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Long oneDay = 1000 * 60 * 60 * 24l;
		try {
			Long start = startTime.getTime();
			Long end  = endTime.getTime();
			while (start <= end) {
				Date d = new Date(start);
				list.add(f.format(d));
				start += oneDay;
			}
		}catch (Exception e){
		e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取近X天的日期
	 */
	public static List<String> getDateBetweenDays(Integer date){

		List<String> list = new ArrayList<>();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Long oneDay = 1000 * 60 * 60 * 24l;
		try {
			Long end  =calendar.getTimeInMillis();
			Long start = calendar.getTimeInMillis()- oneDay*(date-1);

			while (start <= end) {
				Date d = new Date(start);
				list.add(f.format(d));
				start += oneDay;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * 获取上个月第一天
	 * @return
	 */
	public static Date getLastMonthFirstDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * 获取一个月的天数
	 * 
	 * */
    public static int getDaysOfMonth(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    } 

	/**
	 * 获取上个月最后一天
	 * @return
	 */
	public static Date getLastMonthLastDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	/**
	 * 获取昨天开始时间
	 * @return
	 */
	public static Date getYesterdayStartTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取昨天结束时间
	 * @return
	 */
	public static Date getYesterdayEndTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 获取昨天11点
	 *
	 */
	public static Date getYesterday11pm(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取今天11点
	 *
	 */
	public static Date getToday11pm(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getTomorrowNow(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * @param one 时间参数 1 格式：1990-01-01 12:00:00
	 * @param two 时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	public static String getDistanceTime(Date one, Date two) {


		long time1 = one.getTime();
		long time2 = two.getTime();
		long diff ;
		if(time1<time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		long day = diff / (24 * 60 * 60 * 1000);
		long hour = (diff / (60 * 60 * 1000) - day * 24);
		long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long sec = (diff/1000-day*24*60*60-hour*60*60-min*60);

		return day + "天" + hour + "小时" + min + "分" + sec + "秒";
	}

	public static Integer getDistanceDays(Date Begin,Date end){
		if (null == Begin || null == end) {

			return -1;

		}

		long intervalMilli = end.getTime() - Begin.getTime();

		return (int) (intervalMilli / (24 * 60 * 60 * 1000))+1;
	}

	/**
	 *
	 * @param dateStr 传入时间 yyyyMMdd
	 * @param days 传入时间，几天后
	 * @return 需要获得传入时间几天后的日期
	 */
	public static String getAfterDate(String dateStr, int days) {
		Long time = days*24*60*60*1000L;
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		long afterDate =0;
		try {
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(df.parse(dateStr));
			long date = calendar.getTimeInMillis();
			afterDate = date+time;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ymd1(new Date(afterDate));
	}

	/**
	 *
	 * @param dateStr 传入时间 yyyyMMdd
	 * @param days 传入时间，几天后
	 * @return 需要获得传入时间几天后的日期
	 */
	public static String getAfterDate(String dateStr, int days, SimpleDateFormat sdf) {
		Long time = days*24*60*60*1000L;
		if(sdf == null){
			return getAfterDate(dateStr,days);
		}
		long afterDate =0;
		try {
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(sdf.parse(dateStr));
			long date = calendar.getTimeInMillis();
			afterDate = date+time;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(new Date(afterDate));
	}

    /**
     * @return 返回当前年月日
     */
	public static String getNowDate() {
		Date date = new Date();
		return new SimpleDateFormat("yyyy年MM月dd日").format(date);
	}

    /**
     * @return 返回当前年份
     */
	public static int getYear() {
		Date date = new Date();
		String year = new SimpleDateFormat("yyyy").format(date);
		return Integer.parseInt(year);
	}

    /**
     * @return 返回当前月份
     */
	public static int getMonth()
	{
		Date date = new Date();
		String month = new SimpleDateFormat("MM").format(date);
		return Integer.parseInt(month);
	}

    /**
     * 判断闰年
     * @param year 年份
     * @return 是否是闰年
     */
	private static boolean isLeap(int year) {
        return (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0));
	}

	/**
	 *
	 * @param year 年份
	 * @param month 月份
	 * @return 返回当月天数
	 */
	public static int getDays(int year, int month)
	{
		int days;
		int febDay = 28;
		if (isLeap(year)){
            febDay = 29;
		}
		switch (month)
		{
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				days = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				days = 30;
				break;
			case 2:
				days = febDay;
				break;
			default:
				days = 0;
				break;
		}
		return days;
	}

	/**
	 *
	 * @param year 年份
	 * @param month 月份
	 * @return 返回当月星期天数
	 */
	public static int getSundays(int year, int month) {
		int sundays = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		Calendar setDate = Calendar.getInstance();
		//从第一天开始
		int day;
		for (day = 1; day <= getDays(year, month); day++)
		{
			setDate.set(Calendar.DATE, day);
			String str = sdf.format(setDate.getTime());
			if (str.equals("星期日"))
			{
				sundays++;
			}
		}
		return sundays;
	}


	/**
	 * 凌晨
	 * @param date
	 * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
	 *       1 返回yyyy-MM-dd 23:59:59日期
	 * @return
	 */
	public static Date weeHours(Date date, int flag) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		//时分秒（毫秒数）
		long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;
		//凌晨00:00:00
		cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);

		if (flag == 0) {
			return cal.getTime();
		} else if (flag == 1) {
			//凌晨23:59:59
			cal.setTimeInMillis(cal.getTimeInMillis()+23*60*60*1000 + 59*60*1000 + 59*1000);
		}
		return cal.getTime();
	}

	/**
	 *
	 * @param date1 <String>
	 * @param date2 <String>
	 * @return int
	 * @throws ParseException
	 */
	public static int getMonthSpace(String date1, String date2)
			throws ParseException {

		int result = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(sdf.parse(date1));
		c2.setTime(sdf.parse(date2));

		result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

		return result == 0 ? 1 : Math.abs(result);

	}


	/**
	 * 获取两个日期之间的日期
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 日期集合
	 */
	public static List<Date> getBetweenDates(Date start, Date end) {
		List<Date> result = new ArrayList<Date>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);
		tempStart.add(Calendar.DAY_OF_YEAR, 1);

		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(end);
		while (tempStart.before(tempEnd)) {
			result.add(tempStart.getTime());
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}
		return result;
	}

	/**
	 * 获取两个日期之间的日期
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 日期集合
	 */
	public static List<String> getBetweenDates(String start, String end) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		List<String> result = new ArrayList<String>();
		Calendar tempStart = Calendar.getInstance();

		try {
			result.add(start);
			tempStart.setTime(sdf.parse(start));

			tempStart.add(Calendar.DAY_OF_YEAR, 1);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(sdf.parse(end));
			while (tempStart.before(tempEnd)) {
				result.add(sdf.format(tempStart.getTime()));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}
			result.add(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<String> getMonthBetween(String minDate, String maxDate) {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
		try {
			Calendar min = Calendar.getInstance();
			Calendar max = Calendar.getInstance();

			min.setTime(sdf.parse(minDate));
			min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

			max.setTime(sdf.parse(maxDate));
			max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

			Calendar curr = min;
			while (curr.before(max)) {
				result.add(sdf.format(curr.getTime()));
				curr.add(Calendar.MONTH, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {

		List<String> list=	getMonthBetween("2018-07-20","2018-08-10");

		for (String str:list			 ) {
			System.out.println(str);
		}

	}

}
