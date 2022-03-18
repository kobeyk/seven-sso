package com.appleyk.auth.common.util;


import com.appleyk.auth.common.excep.SeCommonException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>日期转换工具类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-10:08
 */
public class SeDateUtils {

    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
    private static DateTimeFormatter dtF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") ;
    private static Calendar calendar =Calendar.getInstance();

    private SeDateUtils(){

    }

    public synchronized static String getCurrentDateForFile() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        return sDateFormat.format(date);
    }

    /**
     * <p>字符日期转Long时间戳</p>
     * @param time 字符串日期
     * @return 日期时间戳
     */
    public static Long getTime(String time) throws SeCommonException {
        if (SeGeneralUtils.isEmpty(time)) {
            throw new SeCommonException("时间["+time+"]格式不合法");
        }
        if (time.length() < 11) {
            dtF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parse = LocalDate.parse(time, dtF);
            return LocalDate.from(parse).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } else {
            LocalDateTime parse = LocalDateTime.parse(time, dtF);
            return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
    }

    /**
     * <p>获取当前时间</p>
     * @return 日期字符串
     */
    public synchronized static String getDate(){
        Date date = new Date();
        sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sDateFormat.format(date);
    }

    /**
     * <p>时间戳转字符串</p>
     * @param time 日期时间戳
     * @return 日期字符串
     */
    public synchronized static String date2Str(Long time){
        Date date = new Date(time);
        sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sDateFormat.format(date);
    }

    /**
     * <p>日期转字符串</p>
     * @param time 日期时间戳
     * @return 日期字符串
     */
    public synchronized static String date2Str(Date time){
        sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sDateFormat.format(time);
    }

    /**
     * <p>字符串转日期</p>
     * @param time 日期字符串
     * @return 日期格式
     * @throws SeCommonException 日期字符串不符合要求
     */
    public synchronized static Date str2Date(String time) throws SeCommonException{
        sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sDateFormat.parse(time);
        } catch (Exception e) {
            throw new SeCommonException("字符串["+time+"]转换日期格式异常");
        }
    }

    /**
     * <p>日期往前/后推mount年 ==》 mount > 0 往后推，mount < 0，往前推</p>
     * @param date 标准日期格式
     * @return 日期
     */
    public static Date addYear(Date date,int mount){
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,mount);
        return calendar.getTime();
    }

    /**
     * <p>日期往前/后推mount年 ==》 mount > 0 往后推，mount < 0，往前推</p>
     * @param time 日期时间戳
     * @return 日期
     */
    public static Date addYear(Long time,int mount){
        Date date = new Date(time);
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,mount);
        return calendar.getTime();
    }

    /**
     * <p>日期往前/后推mount月 ==》 mount > 0 往后推，mount < 0，往前推</p>
     * @param date 标准日期格式
     * @return 日期
     */
    public static Date addMonth(Date date,int mount){
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,mount);
        return calendar.getTime();
    }

    /**
     * <p>日期往前/后推mount月 ==》 mount > 0 往后推，mount < 0，往前推</p>
     * @param time 日期时间戳
     * @return 日期
     */
    public static Date addMonth(Long time,int mount){
        Date date = new Date(time);
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,mount);
        return calendar.getTime();
    }


    /**
     * <p>日期往前/后推mount日 ==》 mount > 0 往后推，mount < 0，往前推</p>
     * @param date 标准日期格式
     * @return 日期
     */
    public static Date addDay(Date date,int mount){
        calendar.setTime(date);
        calendar.add(Calendar.DATE,mount);
        return calendar.getTime();
    }

    /**
     * <p>日期往前/后推mount日 ==》 mount > 0 往后推，mount < 0，往前推</p>
     * @param time 日期时间戳
     * @return 日期
     */
    public static Date addDay(Long time,int mount){
        Date date = new Date(time);
        calendar.setTime(date);
        calendar.add(Calendar.DATE,mount);
        return calendar.getTime();
    }
}
