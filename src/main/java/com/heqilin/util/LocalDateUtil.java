package com.heqilin.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.function.Function;

/**
 * 时间日期帮助类
 *
 * @author heqilin
 * date:  2018-12-20 ok
 **/
public class LocalDateUtil {

    private LocalDateUtil(){
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
    public  static LocalDate getDate(LocalDateTime date){
        return date.toLocalDate();
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 将字符串解析为时间格式
     * @param date
     * @param pattern 默认为yyyy-MM-dd HH:mm:ss
     **/
    public static LocalDateTime parse(String date,String pattern){
        String p = pattern==null?DATE_TIME:pattern;
        return LocalDateTime.parse(date,DateTimeFormatter.ofPattern(p));
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 将时间转化为字符串
     * @param date
     * @param  pattern  默认为 yyyy-MM-dd
     **/
    public static String format(LocalDateTime date,String pattern){
        String p = pattern==null?DATE_STR:pattern;
        return date.format(DateTimeFormatter.ofPattern(p));
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 获取周几（周一到周日对应1,2,...7）
     * @param date
     * @return int
     **/
    public static int getWeek(LocalDateTime date){
        return  date.get(ChronoField.DAY_OF_WEEK);
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 获取星期几
     * @param date
     * @return java.lang.String
     **/
    public static String getWeekStr(LocalDateTime date) {
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

    public static LocalDateTime addDay(LocalDateTime date, int day){
        return date.plusDays(day);
    }

    public static LocalDateTime addMonth(LocalDateTime date, int month){
        return date.plusMonths(month);
    }

    public static LocalDateTime addYear(LocalDateTime date, int year){
        return date.plusYears(year);
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 相差几天
     * @param start
     * @param end
     * @return int
     **/
    public static int dayBetween(LocalDateTime start, LocalDateTime end){
        return Period.between(start.toLocalDate(),end.toLocalDate()).getDays();
    }

    //region 获取第一天/最后一天操作

    /**
     * @author heqilin
     * date:  2018-12-20
     * 取得某月的第一天
     * @param  date
     * @return java.util.Date
     **/
    public static  LocalDateTime getFirstDayOfMonth(LocalDateTime date){
        return date.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
    }

    public static  LocalDateTime getFirstDayOfPrdviousMonth(LocalDateTime date){
        return date.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
    }

    public static  LocalDateTime getFirstDayOfWeek(LocalDateTime date){
        return date.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
    }

    /**
     * @author heqilin
     * date:  2018-12-20
     * 获取某月最后一天
     * @param date
     * @return java.util.Date
     **/
    public static  LocalDateTime getLastDayOfMonth(LocalDateTime date){
        return date.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().atStartOfDay();
    }

    public static  LocalDateTime getLastDayOfPrdviousMonth(LocalDateTime date){
        return date.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().atStartOfDay();
    }

    public static  LocalDateTime getLastDayOfWeek(LocalDateTime date){
        return date.with(DayOfWeek.SUNDAY).toLocalDate().atStartOfDay();
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
        if(date==null||date.length()<=0){
            return date;
        }
        date = date.replace("T"," ");
        if(patten==null||patten.length()<=0){
            patten = DATE_STR;
        }
        int hour=0;
        int minute=0;
        int second=0;
        try{
            if(date.split(" ").length>1){
                String[] temp = date.split(" ")[1].split(":");
                if(temp.length>0 && temp.length<=1){
                    hour= Integer.parseInt(temp[0]);
                }else if(temp.length>1 && temp.length<=2){
                    minute= Integer.parseInt(temp[1]);
                }else if(temp.length>2 && temp.length<=3){
                    second= Integer.parseInt(temp[2]);
                }
            }
        }catch (Exception ex){
            LogUtil.error("DateUtil.format(String date,String patten, Function<LocalDateTime,LocalDateTime> handleDate) 出现异常");
        }
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd[[ HH][:mm][:ss]]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, hour)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, minute)
                .parseDefaulting(ChronoField.MILLI_OF_SECOND, second)
                .toFormatter();
        LocalDateTime result = LocalDateTime.parse(date,formatter);
        if(handleDate!=null){
            result = handleDate.apply(result);
        }
        return result.format(DateTimeFormatter.ofPattern(patten));
    }
    // endregion

    // region 不同类型转换
    /**
     * 将 Unix时间戳转为LocalDateTime
     * @param timestamp 毫秒
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime asLocalDateTime(long timestamp){
        return LocalDateTime.ofEpochSecond(timestamp/1000,0, ZoneOffset.ofHours(8));
    }
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneOffset.ofHours(8)).toInstant());
    }
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
    }
    public static long asMilliSecondTimestamp(LocalDateTime localDateTime){
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }
    public static long asMilliSecondTimestamp(LocalDate localDate){
        return localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }
    // endregion

}
