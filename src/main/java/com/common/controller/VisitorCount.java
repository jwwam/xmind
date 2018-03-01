package com.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

//https://www.cnblogs.com/linnuo/p/6812115.html
public class VisitorCount {
	//使用Properties
    private static Properties p=new Properties();
    
    public static void writeCount(String path,String count, String todayCount){  
        p.setProperty("count", count);
        p.setProperty("todayCount", todayCount);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        p.setProperty("today", sim.format(new Date()));
        try {
            p.store(new FileOutputStream(path), "");  //写入文件,一般是C盘此处就可能会报错,无法访问就去该路径下给权限,客户端没有所需的特权就以管理员身份启动eclipse
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    public static String readCount(String path) {
        String count="1";
        File f = new File(path);
        if (!f.exists()) {
        	writeCount(path, "1", "1");
        }
        try {
            p.load(new FileInputStream(path));
            count = p.getProperty("count");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
   }
    
    public static String readTodayCount(String path) {
    	String todayCount = "1";
        File f = new File(path);
        if (!f.exists()) {
            writeCount(path, "1", "1");
        }
        try {
            p.load(new FileInputStream(path));
            //如果当前日期不是文件中的today,说明日期改变,今日访问量需要置零,从零开始累加. 如果是同一天,则返回今天的计数,判断是否为同一人,是则不加,否则继续累加
            int day =  today(p.getProperty("today"), new Date());
            if(day == 0) {
            	//同一天则判断身份,此处暂时省略,直接返回继续累加
            	todayCount = p.getProperty("todayCount");
            }else {
            	//非同一天,今日访问量置零
            	todayCount = "0";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todayCount;
   }
    
    public static int today(String dates, Date datee){
        System.out.println("dates="+dates+"         datee="+datee);
        int day = 0;
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = sim.parse(dates);
            Date d2 = datee;
            day = differentDays(d1,d2);
        }catch (Exception e){
            System.out.print("日期转化异常");
        }
        return day;
    }
    
    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) //不同年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0) //闰年
                {
                    timeDistance += 366;
                }
                else //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else //同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
    
}
