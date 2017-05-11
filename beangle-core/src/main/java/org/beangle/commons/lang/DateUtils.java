/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import java.util.Calendar;
import java.util.Date;

/**
 * @author CHII
 */
public final class DateUtils {

	public static Date rollMinutes(Date date, int mount) {
		return new Date(date.getTime() + mount * 60 * 1000);
	}

	public static Date getStartOfMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
		return c.getTime();
	}

	public static Date getEndOfMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 0, 23, 59, 59);
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
}
