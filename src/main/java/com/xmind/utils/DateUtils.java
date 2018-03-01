package com.xmind.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	/**
	 * 格式化日期
	 * @param sDate yyyy-MM-dd
	 * @return
	 */
	public static Date format(String sDate) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdf.parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取当前日期
	 * @return
	 * @throws ParseException
	 */
	public static Date getNowDate() throws ParseException {
		   Date currentTime = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   String dateString = formatter.format(currentTime);
		   Date currentTime_2 = formatter.parse(dateString);
		   return currentTime_2;
		}
	
	public static Date parseDate(String time){
		//格式化时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    try {
			Date createTime = formatter.parse(time);
			System.out.println("时间转换为："+createTime); 
			System.out.println("时间转换为："+createTime.toString()); 
			return createTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	
}
