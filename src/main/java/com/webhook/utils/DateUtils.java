package com.webhook.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期 工具类
 */
public class DateUtils {
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期转字符串
     *
     * @param date 日期
     * @return java.lang.String 字符串日期
     */
    public static String dateToString(Date date) {
        if (null == date) {
            return null;
        }

        String format = YYYY_MM_DD_HH_MM_SS;
        return new SimpleDateFormat(format).format(date);
    }
}
