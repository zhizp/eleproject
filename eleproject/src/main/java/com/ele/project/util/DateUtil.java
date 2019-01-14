package com.ele.project.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



/**
 * @author gaoxj
 * 日期常用工具类
 */
public class DateUtil {
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	public final static String YYYY_MM_DD_HH_MM_SS_S= "yyyyMMddHHmmssS";
	
    /**
     * 根据给定日期格式返回当前日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return 
     * **/
    public static String getCurrentDateTimeByFormat(String format){
    	Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
    } 
	/**
	 * 当前日期时间精确到毫秒
	 * @return (yyyyMMddHHmmssS)精确毫秒
	 * */
	public static String getCurrentDateTimes() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_S);
		return df.format(date);
	}
    /**
     * 根据给定日期和格式返回字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return 
     * **/
    public static String getDateStrByFormat(Date date,String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
    }
    /**
     *将字符串格式yyyyMMdd的字符串转为日期，格式"yyyy-MM-dd"
     *
     * @param date 日期字符串
     * @return 返回格式化的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static String strToDateFormat(String date,String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setLenient(false);
        Date newDate= formatter.parse(date);
        formatter = new SimpleDateFormat(format);
        return formatter.format(newDate);
    }
    /**
     * 根据给定中国标准时间和格式返回字符串日期
     * @param date Wed Jul 19 2017 17:05:08 GMT+0800 (中国标准时间)
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException 
     */
    public static String getStrByFormat(String date,String format){
    	
    	if(!date.equals("") && date!=null){
        	SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z",Locale.ENGLISH);
        	Date d = null;
    		try {
    			d = sf.parse(date);
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    		return getDateStrByFormat(d, format);
    	}else{
    		return date;
    	}
    	
    }
   
    /**
     * 根据给定中国标准时间和格式返回字符串日期
     * @param date Wed Jul 19 2017 17:05:08 GMT+0800 (中国标准时间)
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getStrByFormat1(String date,String format){
    	
    	if(!date.equals("") && date!=null && date.contains("GMT+08")){
    		String str =date.replace("GMT+0800 (中国标准时间)", "GMT+08:00") ;
        	SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z",Locale.ENGLISH);
        	Date d = null;
    		try {
    			d = sf.parse(str);
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    		return getDateStrByFormat(d, format);
    	}else{
    		return date;
    	}
    	
    }

   /**
    *  格式化2017-08-05T02:57:21.392Z
    * @param date  2017-08-05T02:57:21.392Z
    * @param format yyyy-MM-dd
    * @return
    */
    public static String getStrByFormatZ(String date,String format){
    	

    	if(StringUtil.isNotEmpty(date)){
        	try {
            	SimpleDateFormat sdf = new SimpleDateFormat(format);
            	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            	date = date.replace("Z", " UTC");
        		if(date.contains("\""))
        			date=date.replaceAll("\"", "");//去掉""
        		date=sdf.format(formatter.parse(date));
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    	}

		return date;
    	
    }

	/**
	 * 判断时间是否在两个日期之间
	 * @param beginDate -开始日期（字符串格式 yyyy-MM-dd）
	 * @param endDate -结束日期（字符串格式 yyyy-MM-dd）
	 * @param date -要判断的日期
	 * @return 返回true在两个日期之间false不在俩个日期之间
	 */
	public static Boolean dateBetweenStartDateAndEndDate(String date, String beginDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Boolean b = false;
		Date bf = null;
		Date af = null;
		Date nowDate = null;
		try {
			bf = sdf.parse(beginDate);
			af = sdf.parse(endDate);
			nowDate = sdf.parse(date);
		} catch (ParseException e) {
			logger.debug("DateUtils dateBetweenStratDateAndEndDate" + e);
		}
		if ((bf.before(nowDate)) && (af.after(nowDate))) {
			b = true;
		} else {
			b = false;
		}
		return b;
	}

	/**
	 * 两个日期之间相差分钟
	 * @param beginDate  字符串日期
	 * @param endDate 字符串日期
	 * @return 返回相差分钟
	 * */
	public static long daysBetween(String beginDate, String endDate) {
		Date begin = null;
		Date end = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			begin = sdf.parse(beginDate);
			end = sdf.parse(endDate);
		} catch (ParseException e) {
			logger.debug("daysBetween" + e);
		}
		long minute = (begin.getTime() - end.getTime()) / (60 * 1000);
		return minute;
	}

	/**
	 * 两个日期之间相差保留一位小数点的小时数
	 * @param beginDate  字符串日期
	 * @param endDate 字符串日期
	 * @return 返回相差小时数
	 * */
	public static String hoursBetween(String beginDate, String endDate) {
		DecimalFormat df = new DecimalFormat("0.0");
		double hours = 0;
		long minutes = daysBetween(beginDate, endDate);
		long hh = minutes/60;
		double mm = minutes%60;
		double shh = mm/60;
		hours = (double)(hh+shh);
		return df.format(hours);
	}

	/**
	 * 到某一天的第一秒
	 * @param date 给定的时间
	 * @return 定位到当天的第一秒
	 */
	public static Date firstSecondOfDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/** 一天最后一个小时 */
	public static final int LAST_HOUR_OF_DATE = 23;

	/** 一小时最后一分钟 */
	public static final int LAST_MINUTE_OF_HOUR = 59;

	/** 一分钟最后一秒 */
	public static final int LAST_SECOND_OF_MIN = 59;

	/**
	 * 到某一天的最后一秒
	 * @param date 给定的时间
	 * @return 定位到当天的最后一秒
	 */
	public static Date lastSecondOfDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, LAST_HOUR_OF_DATE);
		c.set(Calendar.MINUTE, LAST_MINUTE_OF_HOUR);
		c.set(Calendar.SECOND, LAST_SECOND_OF_MIN);
		return c.getTime();
	}

	public static Date firstDayOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDayOfMonth = calendar.getTime();
		return firstDayOfMonth;
	}

	public static Date lastDayOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDayOfMonth = calendar.getTime();
		return lastDayOfMonth;
	}

    /**
     * 字符串格式转换Date格式 (yyyy-MM-dd hh:mm:ss)
     * @param date 日期（字符串格式）
     * @return Date格式字符串
     */
    public static Date toDateTime(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date dateAfter = null;
        try {
            dateAfter = sdf.parse(date);
        } catch (ParseException e) {
            logger.debug("DateUtils toDateTime" + e);
        }
        return dateAfter;

    }

	/**
	 * 返回自当前月以后的月份（如果返回自今日以前的月份，请用"-months"）
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date, int months) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);

		return calendar.getTime();
	}

	/**
	 * 返回选定日期是星期几
	 * @param strDate
	 * @return
	 */
	public static String getWeekOfDate(String strDate){
		int iWeekNum = 0;
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date curDate = format.parse(strDate);
			calendar.setTime(curDate);
			iWeekNum = calendar.get(Calendar.DAY_OF_WEEK);
			iWeekNum = iWeekNum - 1;
		}catch (ParseException e) {
			logger.debug("DateUtil getWeekOfDate" + e);
		}
		return weekDays[iWeekNum];
	}
}
