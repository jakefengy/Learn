package com.fanzhuo.framework.util;

import org.apache.commons.lang.StringUtils;

public class SqlUtil {

    public static final String white_space = " ";
    public static final String single_quotes = "'";
    public static final String symbol_like = "%";
    public static final String time_begin = "00:00:00";
    public static final String time_end = "23:59:59";

    /**
     * 参数不为空时返回："%" + value + "%"
     *
     * @param value
     * @return
     */
    public static String likeFull(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        return symbol_like + value + symbol_like;
    }

    /**
     * 参数不为空时返回：value + "%"
     *
     * @param value
     * @return
     */
    public static String likeLeft(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        return value + symbol_like;
    }

    /**
     * 参数不为空时返回："%" + value
     *
     * @param value
     * @return
     */
    public static String likeRight(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        return symbol_like + value;
    }

    /**
     * 参数不为空时返回："'" + value + "'"
     *
     * @param value
     * @return
     */
    public static String quotesFull(String value) {
        if (value == null) {
            return value;
        }
        return single_quotes + value + single_quotes;
    }

    /**
     * 在日期后附加起始时间" 00:00:00"
     *
     * @param value
     * @return
     */
    public static String appendTimeBegin(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        return value + white_space + time_begin;
    }

    /**
     * 在日期后附加结束时间" 23:59:59"
     *
     * @param value
     * @return
     */
    public static String appendTimeEnd(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        return value + white_space + time_end;
    }

}
