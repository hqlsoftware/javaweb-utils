package com.heqilin.util;

import com.heqilin.util.core.LocalDateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalDateUtilTest {

    @Test
    public void getDate() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,12,12,12);
        LocalDate result = LocalDateUtil.getDate(date);
        assertEquals(2018,result.get(ChronoField.YEAR));
        assertEquals(12,result.get(ChronoField.MONTH_OF_YEAR));
        assertEquals(12,result.get(ChronoField.DAY_OF_MONTH));
    }

    @Test
    public void parse() {

        LocalDateTime date = LocalDateUtil.parse("2018-12-12 13:20:30",null);

        assertEquals(13,date.getHour());
        assertEquals(20,date.getMinute());
        assertEquals(30,date.getSecond());

    }

    @Test
    public void format() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,12,12,12);

        assertEquals("2018-12-12 12",LocalDateUtil.format(date,"yyyy-MM-dd HH"));
        assertEquals("2018-12-12",LocalDateUtil.format(date,null));
    }

    @Test
    public void getWeek() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,12,12,12);

        assertEquals(3,LocalDateUtil.getWeek(date));
    }

    @Test
    public void getWeekStr() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,12,12,12);

        assertTrue("星期三".equals(LocalDateUtil.getWeekStr(date)));
    }

    @Test
    public void addDay() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,12,12,12);

        LocalDateTime date1 = LocalDateUtil.addDay(date,2);
        assertTrue("2018-12-14".equals(LocalDateUtil.format(date1,null)));
    }

    @Test
    public void addMonth() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,12,12,12);

        LocalDateTime date1 = LocalDateUtil.addMonth(date,2);
        assertEquals("2019-02-12",LocalDateUtil.format(date1,null));
    }

    @Test
    public void addYear() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,12,12,12);

        LocalDateTime date1 = LocalDateUtil.addYear(date,2);
        assertEquals("2020-12-12",LocalDateUtil.format(date1,null));
    }

    @Test
    public void dayBetween() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,23,12,12);
        LocalDateTime date1 = LocalDateTime.of(2018,12,14,0,12,12);

        assertEquals(2,LocalDateUtil.dayBetween(date,date1));
    }

    @Test
    public void getFirstDayOfMonth() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,23,12,12);

        LocalDateTime date1 = LocalDateUtil.getFirstDayOfMonth(date);

        assertEquals(0,date1.getHour());
        assertEquals(0,date1.getMinute());
        assertEquals(0,date1.getSecond());
        assertEquals("2018-12-01",LocalDateUtil.format(date1,null));
    }

    @Test
    public void getFirstDayOfPrdviousMonth() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,23,12,12);

        LocalDateTime date1 = LocalDateUtil.getFirstDayOfPrdviousMonth(date);

        assertEquals(0,date1.getHour());
        assertEquals(0,date1.getMinute());
        assertEquals(0,date1.getSecond());
        assertEquals("2018-11-01",LocalDateUtil.format(date1,null));
    }

    @Test
    public void getFirstDayOfWeek() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,23,12,12);

        LocalDateTime date1 = LocalDateUtil.getFirstDayOfWeek(date);

        assertEquals(0,date1.getHour());
        assertEquals(0,date1.getMinute());
        assertEquals(0,date1.getSecond());
        assertEquals("2018-12-10",LocalDateUtil.format(date1,null));
    }

    @Test
    public void getLastDayOfMonth() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,23,12,12);

        LocalDateTime date1 = LocalDateUtil.getLastDayOfMonth(date);

        assertEquals(0,date1.getHour());
        assertEquals(0,date1.getMinute());
        assertEquals(0,date1.getSecond());
        assertEquals("2018-12-31",LocalDateUtil.format(date1,null));
    }

    @Test
    public void getLastDayOfPrdviousMonth() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,23,12,12);

        LocalDateTime date1 = LocalDateUtil.getLastDayOfPrdviousMonth(date);

        assertEquals(0,date1.getHour());
        assertEquals(0,date1.getMinute());
        assertEquals(0,date1.getSecond());
        assertEquals("2018-11-30",LocalDateUtil.format(date1,null));
    }

    @Test
    public void getLastDayOfWeek() {
        LocalDateTime date = LocalDateTime.of(2018,12,12,23,12,12);

        LocalDateTime date1 = LocalDateUtil.getLastDayOfWeek(date);

        assertEquals(0,date1.getHour());
        assertEquals(0,date1.getMinute());
        assertEquals(0,date1.getSecond());
        assertEquals("2018-12-16",LocalDateUtil.format(date1,null));
    }
}