package org.beangle.website.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

	public static Date getYearStart() {
		return getYearStart(null);
	}

	public static Date getYearEnd() {
		return getYearEnd(null);
	}

	/**
	 * 一年的开始时间
	 * @param year
	 * @return
	 */
	public static Date getYearStart(Integer year) {
		Calendar c = Calendar.getInstance();
		year = getYear(c, year);
		c.set(year, 0, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 获取年份
	 * @param year
	 * @return
	 */
	private static Integer getYear(Calendar c, Integer year) {
		if (year == null) {
			year = c.get(Calendar.YEAR);
		}
		return year;
	}
	
	/**
	 * 获取年份
	 * @param year
	 * @return
	 */
	public static Integer getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return getYear(c, null);
	}
	
	public static Integer getYear(){
		return getYear(new Date());
	}


	/**
	 * 一年的结束时间
	 * @param year
	 * @return
	 */
	public static Date getYearEnd(Integer year) {
		Calendar c = Calendar.getInstance();
		year = getYear(c, year);
		c.set(year, 11, 31, 23, 59, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 一天的开始时间
	 * @param year
	 * @return
	 */
	public static Date getDayStart(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 一天的结束时间
	 * @param year
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 根据身份证获取出生年月
	 * @param year
	 * @return
	 */
	public static Date getBirthDay(String sfzh) {
		if(sfzh == null || !(sfzh.length() == 18 || sfzh.length() == 15)){
			return null;
		}
		int index = 6;
		String pattern = "yyyyMMdd";
		if(sfzh.length() == 15){
			pattern = "yyMMdd";
		}
		String datestr = sfzh.substring(index, index + pattern.length());
		try {
			Date date = new SimpleDateFormat(pattern).parse(datestr);
			return date;
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 比较2个日期相差天数
	 * @param selDate
	 * @param today
	 * @return
	 */
	public static long dateDistance(Date date1,Date date2){
		  long daysBetween=(date2.getTime()-date1.getTime())/(3600*24*1000);
		  return daysBetween;

	}
	
	/**
	 * 取得n天后的日期
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getDate(Date date,long n){
		
		long x=n*(3600*24*1000)+date.getTime();
		return new Date(x);
	}

	/**
	 * 一天的开始时间
	 * @param year
	 * @return
	 */
	public static Date getStartTime(Date start){
		if(start == null){
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 一天的结束时间
	 * @param year
	 * @return
	 */
	public static Date getEndTime(Date end){
		if(end == null){
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(end);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 一月的开始时间
	 * @param year
	 * @return
	 */
	public static Date getMonthStart(Calendar c){
		c.set(Calendar.DAY_OF_MONTH,1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	

	/**
	 * 一月的结束时间
	 * @param year
	 * @return
	 */
	public static Date getMonthEnd(Calendar c){
		c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	public static Date clearSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(getYearStart(2011));
		System.out.println(getYearEnd(2011));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		System.out.println(sdf.format(getBirthDay("330326198412226712")));
		System.out.println(sdf.format(getBirthDay("330326841222671")));
		System.out.println(sdf.format(getDate(new Date(), -7)));
	}

	public static int getMonth() {
		Integer month = Calendar.getInstance().get(Calendar.MONTH);
		return month;
	}public static Date getWeekStart(Date startTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(startTime);
		c.set(Calendar.DAY_OF_WEEK,2);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getWeekEnd(Date startTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(startTime);
		c.set(Calendar.DAY_OF_WEEK,1);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 7);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}
}
