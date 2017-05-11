package org.beangle.website.common.util;

import java.util.Calendar;
import java.util.Date;

public class CommonUtils {
	/**
	 * 将String数组转换为Long类型数组 
	 * @param strs
	 * @return
	 */
	public static Long[] convertionToLong(String[] strs){
		Long[] longs = new Long[strs.length]; //声明long类型的数组  
		for(int i = 0;i<strs.length;i++) {   
			String str = strs[i]; //将strs字符串数组中的第i个值赋值给str  
		long thelong = Long.valueOf(str);//将str转换为long类型，并赋值给thelong   
		longs[i] = thelong;//将thelong赋值给 longs数组中对应的地方 
		}      
		return longs;  //返回long数组 }
		
	}
	
	/**
	 * 比较2个日期相差天数
	 * @param selDate
	 * @param today
	 * @return
	 */
	@Deprecated
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
	@Deprecated
	public static Date getDate(Date date,long n){
		
		long x=n*(3600*24*1000)+date.getTime();
		return new Date(x);
	}
	
	@Deprecated
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
	
	@Deprecated
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
	
	@Deprecated
	public static Date getMonthStart(Calendar c){
		c.set(Calendar.DAY_OF_MONTH,1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	@Deprecated
	public static Date getMonthEnd(Calendar c){
		c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}
	
	/**
	 * 根据数量获取字母标识（0：A,1：B...）
	 * @return（A,B,C,D...）
	 */
	public static String getIdentString(int count){
		int initCount = 65;
		int defaultCount = initCount + count;
		char numChar = (char) defaultCount;
		return String.valueOf(numChar);
	}

}
