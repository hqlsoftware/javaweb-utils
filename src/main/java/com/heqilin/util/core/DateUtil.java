package com.heqilin.util.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

/**
 * 时间日期帮助类
 *
 * @author heqilin
 * date:  2018-12-20 ok
 **/
public class DateUtil {

    private DateUtil(){
        throw new AssertionError();
    }

    //region 常量
    /**
     * yyyy-MM-dd HH:mm:ss
     **/
    public final static String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd
     **/
    public final static String DATE_STR = "yyyy-MM-dd";

    /**
     * yyyyMMddHHmmssSSS
     **/
    public static final String DATETIME_MS = "yyyyMMddHHmmssSSS";

    /**
     * yyyy/MM/dd
     **/
    public static final String DATE_SLASH_STR = "yyyy/MM/dd";

    public final static int SECOND = 1000;

    public final static int MINUTE = 60 * SECOND;

    public final static int HOUR = 60 * MINUTE;

    public final static int DAY = 24 * HOUR;
    //endregion

    /**
     * @author heqilin
     * date:  2018-12-20
     * 获取日期部分(不含时间)
     * @param date
     **/
    public  static Date getDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 将字符串解析为时间格式
     * @param date
     * @param pattern 默认为yyyy-MM-dd HH:mm:ss
     **/
    public static Date parse(String date,String pattern){
        if(date==null||date.length()<=0){
            return null;
        }
        date = date.replace("T"," ");
        if(date.length()==10)
            date = date.concat(" 00:00:00");
        String p = pattern==null?DATE_TIME:pattern;
        DateFormat dateFormat = new SimpleDateFormat(p);
        try{
            return dateFormat.parse(date);
        }catch (ParseException ex){
            return  null;
        }
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 将时间转化为字符串
     * @param date
     * @param  pattern  默认为 yyyy-MM-dd
     **/
    public static String format(Date date,String pattern){
        String p = pattern==null?DATE_STR:pattern;
        DateFormat dateFormat=new SimpleDateFormat(p);
        return dateFormat.format(date);
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 获取周几（周一到周日对应1,2,...7）
     * @param date
     * @return int
     **/
    public static int getWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //一周第一天是否为星期天
        boolean isFirstSunday=(calendar.getFirstDayOfWeek()==Calendar.SUNDAY);
        //获取周几
        int weekDay=calendar.get(Calendar.DAY_OF_WEEK);
        //若一周第一天为星期天,则-1 
        if(isFirstSunday){
            weekDay=weekDay-1;
            if(weekDay==0){
                weekDay=7;
            }
        }
        return weekDay;
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 获取星期几
     * @param date
     * @return java.lang.String
     **/
    public static String getWeekStr(Date date) {
        int week = getWeek(date);
        if (week==1) {
            return "星期一";
        } else if (week==2) {
            return "星期二";
        } else if (week==3) {
            return "星期三";
        } else if (week==4) {
            return "星期四";
        } else if (week==5) {
            return "星期五";
        } else if (week==6) {
            return "星期六";
        } else if (week==7) {
            return "星期日";
        }
        return "";
    }

    public static Date addDay(Date date, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,day);
        return calendar.getTime();
    }

    public static Date addMonth(Date date, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,month);
        return calendar.getTime();
    }

    public static Date addYear(Date date, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,year);
        return calendar.getTime();
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 相差几天
     * @param start
     * @param end
     * @return double
     **/
    public static int dayBetween(Date start, Date end){
        if(start==null || end==null)
            return 0;

        return (int)(end.getTime()-start.getTime())/DAY;
    }

    //region 获取第一天/最后一天操作

    /**
     * @author heqilin
     * date:  2018-12-20
     * 获取第一天
     * @param year
     * @param month
     * @return java.util.Date
     **/
    private static  Date getFirstDay(int year,int month){
        Calendar calendar = Calendar.getInstance();
        //设置年份
        calendar.set(Calendar.YEAR, year);
        //设置月份
        calendar.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = calendar.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH,firstDay);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    private static  Date getLastDay(int year,int month){
        Calendar calendar = Calendar.getInstance();
        //设置年份
        calendar.set(Calendar.YEAR, year);
        //设置月份
        calendar.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = calendar.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        calendar.set(Calendar.DAY_OF_MONTH,lastDay);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 取得某月的第一天
     * @param  date
     * @return java.util.Date
     **/
    public static  Date getFirstDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        return getFirstDay(year,month);
    }

    public static  Date getFirstDayOfPrdviousMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        return getFirstDay(year,month);
    }

    public static  Date getFirstDayOfWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = getWeek(date);
        calendar.add(Calendar.DAY_OF_MONTH,1-week);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 获取某月最后一天
     * @param date
     * @return java.util.Date
     **/
    public static  Date getLastDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        return getLastDay(year,month);
    }

    public static  Date getLastDayOfPrdviousMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        return getLastDay(year,month);
    }

    public static  Date getLastDayOfWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = getWeek(date);
        calendar.add(Calendar.DAY_OF_MONTH,7-week);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    //endregion

    // region 格式化前端传进来的不规范的时间格式(转换为yyyy-MM-dd格式)加强版本
    /**
     * 格式化前端传进来的不规范的时间格式(转换为yyyy-MM-dd格式)加强版本
     * @param date
     * @param patten null为yyyy-MM-dd
     * @param handleDate 默认为null
     * @return
     */
    public static String format(String date,String patten, Function<LocalDateTime,LocalDateTime> handleDate){
        return LocalDateUtil.format(date,patten,handleDate);
    }
    // endregion

    // region 不同类型转换
    /**
     * 将 Unix时间戳转为LocalDateTime
     * @param milliSecondTimestamp 毫秒
     * @return java.time.LocalDateTime
     */
    public static Date asDate(long milliSecondTimestamp){
        LocalDateTime date = LocalDateUtil.asLocalDateTime(milliSecondTimestamp);
        return LocalDateUtil.asDate(date);
    }
    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneOffset.ofHours(8)).toLocalDate();
    }
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }
    public static long asMilliSecondTimestamp(Date date){
        LocalDateTime localDateTime = asLocalDateTime(date);
        return LocalDateUtil.asMilliSecondTimestamp(localDateTime);
    }
    // endregion

    public static void main(String[] args) {
        System.out.println(asMilliSecondTimestamp(new Date()));
    }
}
