package utils;

import my.utils.utils.DateUtil;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilTest {

    @Test
    public void getDate() {
        Date date = DateUtil.getDate(new Date());
        assertEquals(0,date.getHours());
        assertEquals(0,date.getMinutes());
        assertEquals(0,date.getSeconds());
    }

    @Test
    public void parse() {
        assertEquals("2018-12-12 13:20:30",DateUtil.format(DateUtil.parse("2018-12-12 13:20:30",null),"yyyy-MM-dd HH:mm:ss"));
        assertEquals("2018-12-12 18:20:30",DateUtil.format(DateUtil.parse("2018-12-12 18:20:30",null),"yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void format() {
        assertEquals("2018-12-12",DateUtil.format(DateUtil.parse("2018-12-12 13:20:30",null),null));
        assertEquals("2018-12-12",DateUtil.format(DateUtil.parse("2018-12-12",null),null));
    }

    @Test
    public void getWeek() {
        Date date = DateUtil.parse("2018-12-12",null);
        assertEquals(3,DateUtil.getWeek(date));
    }

    @Test
    public void getWeekStr() {
        Date date = DateUtil.parse("2018-12-12",null);
        assertTrue("星期三".equals(DateUtil.getWeekStr(date)));
    }

    @Test
    public void addDay() {
        Date date = DateUtil.parse("2018-12-12",null);
        Date date1 = DateUtil.addDay(date,2);
        assertTrue("2018-12-14".equals(DateUtil.format(date1,null)));
    }

    @Test
    public void addMonth() {
        Date date = DateUtil.parse("2018-12-12",null);
        Date date1 = DateUtil.addMonth(date,2);
        assertEquals("2019-02-12",DateUtil.format(date1,null));
    }

    @Test
    public void addYear() {
        Date date = DateUtil.parse("2018-12-12",null);
        Date date1 = DateUtil.addYear(date,2);
        assertEquals("2020-12-12",DateUtil.format(date1,null));
    }

    @Test
    public void dayBetween() {
        Date date = DateUtil.parse("2018-12-12 23:12:12",null);
        Date date1 = DateUtil.parse("2018-12-14",null);
        assertEquals(1,DateUtil.dayBetween(date,date1));
    }

    @Test
    public void getFirstDayOfMonth() {
        Date date = DateUtil.parse("2018-12-12 23:12:12",null);
        Date date1 = DateUtil.getFirstDayOfMonth(date);

        assertEquals(0,date1.getHours());
        assertEquals(0,date1.getMinutes());
        assertEquals(0,date1.getSeconds());
        assertEquals("2018-12-01",DateUtil.format(date1,null));
    }

    @Test
    public void getFirstDayOfPrdviousMonth() {
        Date date = DateUtil.parse("2018-12-12 23:12:12",null);
        Date date1 = DateUtil.getFirstDayOfPrdviousMonth(date);

        assertEquals(0,date1.getHours());
        assertEquals(0,date1.getMinutes());
        assertEquals(0,date1.getSeconds());
        assertEquals("2018-11-01",DateUtil.format(date1,null));
    }

    @Test
    public void getFirstDayOfWeek() {
        Date date = DateUtil.parse("2018-12-12 23:12:12",null);
        Date date1 = DateUtil.getFirstDayOfWeek(date);

        assertEquals(0,date1.getHours());
        assertEquals(0,date1.getMinutes());
        assertEquals(0,date1.getSeconds());
        assertEquals("2018-12-10",DateUtil.format(date1,null));
    }

    @Test
    public void getLastDayOfMonth() {
        Date date = DateUtil.parse("2018-12-12 23:12:12",null);
        Date date1 = DateUtil.getLastDayOfMonth(date);

        assertEquals(0,date1.getHours());
        assertEquals(0,date1.getMinutes());
        assertEquals(0,date1.getSeconds());
        assertEquals("2018-12-31",DateUtil.format(date1,null));
    }

    @Test
    public void getLastDayOfPrdviousMonth() {
        Date date = DateUtil.parse("2018-12-12 23:12:12",null);
        Date date1 = DateUtil.getLastDayOfPrdviousMonth(date);

        assertEquals(0,date1.getHours());
        assertEquals(0,date1.getMinutes());
        assertEquals(0,date1.getSeconds());
        assertEquals("2018-11-30",DateUtil.format(date1,null));
    }

    @Test
    public void getLastDayOfWeek() {
        Date date = DateUtil.parse("2018-12-12 23:12:12",null);
        Date date1 = DateUtil.getLastDayOfWeek(date);

        assertEquals(0,date1.getHours());
        assertEquals(0,date1.getMinutes());
        assertEquals(0,date1.getSeconds());
        assertEquals("2018-12-16",DateUtil.format(date1,null));
    }
}