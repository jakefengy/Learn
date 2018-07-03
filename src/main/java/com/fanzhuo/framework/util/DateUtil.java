package com.fanzhuo.framework.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 建议使用Apche Commons的工具类 DateFormatUtils、DateUtils
 *
 * @author done
 * @version 1.0
 */
public class DateUtil {

    public static final String PATTERN_DATE_TIME_MILLIS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";

    public static final SimpleDateFormat dateTimeMillisFormat = new SimpleDateFormat(PATTERN_DATE_TIME_MILLIS);
    public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(PATTERN_DATE_TIME);
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat(PATTERN_TIME);

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 将日期对象转换为指定格式字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return (dateFormat.format(date));
    }

    public static String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return DateUtil.dateTimeFormat.format(date);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return DateUtil.dateFormat.format(date);
    }

    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }
        return DateUtil.timeFormat.format(date);
    }

    /**
     * 按指定格式转换毫秒为日期字符串
     *
     * @param timeMillis
     * @param pattern
     * @return
     */
    public static String format(long timeMillis, String pattern) {
        Date date = new Date();
        date.setTime(timeMillis);
        return format(date, pattern);
    }

    /**
     * 获取当前日期字符串
     *
     * @return
     */
    public static String getDateTimeMillis() {
        return DateUtil.dateTimeMillisFormat.format(now());
    }

    public static String getDateTime() {
        return DateUtil.dateTimeFormat.format(now());
    }

    public static String getDate() {
        return DateUtil.dateFormat.format(now());
    }

    public static String getTime() {
        return DateUtil.timeFormat.format(now());
    }

    /**
     * 按指定格式转换字符串为日期对象
     *
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parse(String date, String pattern) throws ParseException {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(date);
    }

    /**
     * 将"yyyy-MM-dd HH:mm:ss.SSS"格式的字符串为日期对象
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDateTimeMillis(String date) throws ParseException {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return DateUtil.dateTimeMillisFormat.parse(date);
    }

    /**
     * 将"yyyy-MM-dd HH:mm:ss"格式的字符串为日期对象
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDateTime(String date) throws ParseException {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return DateUtil.dateTimeFormat.parse(date);
    }

    /**
     * 将"yyyy-MM-dd"格式的字符串为日期对象
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date) throws ParseException {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return DateUtil.dateFormat.parse(date);
    }

    /**
     * 将"HH:mm:ss"格式的时间字符串为日期对象，注意：年月日将为1970-01-01
     *
     * @param time HH:mm:ss(24小时制)
     * @return
     */
    public static Date parseTime(String time) throws ParseException {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        return DateUtil.timeFormat.parse(time);
    }

    /**
     * 获取日历对象
     *
     * @param date
     * @return
     */
    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 为时间加上或减去指定毫秒，秒，分，时，天、月或年等
     *
     * @param date
     * @param field  Calendar.DATE,Calendar.MONTH,Calendar.YEAR,
     *               Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE,Calendar.HOUR
     * @param amount 正、负数
     * @return
     */
    public static Date add(Date date, int field, int amount) {
        if (date == null) {
            return date;
        }
        Calendar cal = getCalendar(date);
        cal.add(field, amount);
        return cal.getTime();
    }

    public static Date addMilliSecond(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    public static Date addSecond(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    public static Date addMiunte(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    public static Date addHour(Date date, int amount) {
        return add(date, Calendar.HOUR, amount);
    }

    public static Date addDay(Date date, int amount) {
        return add(date, Calendar.DATE, amount);
    }

    public static Date addMonth(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    public static Date addYear(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    public static int getDayOfMonth(Date date) {
        Calendar cal = getCalendar(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfWeek(Date date) {
        Calendar cal = getCalendar(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int getHourOfDay(Date date) {
        Calendar cal = getCalendar(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinuteOfHour(Date date) {
        Calendar cal = getCalendar(date);
        return cal.get(Calendar.MINUTE);
    }

    public static Date getFirstDateOfMonth(Date date) {
        Calendar cal = getCalendar(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));// Calendar.DATE
        return DateUtils.truncate(cal.getTime(), Calendar.DAY_OF_MONTH);
    }

    public static Date getLastDateOfMonth(Date date) {
        Calendar cal = getCalendar(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));// Calendar.DATE
        return DateUtils.truncate(cal.getTime(), Calendar.DAY_OF_MONTH);
    }

}
