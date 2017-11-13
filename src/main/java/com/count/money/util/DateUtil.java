package com.count.money.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public final static String SDS = "yyyyMMdd";
    public final static long DAY = 24L * 60L * 60L * 1000L;
    /**
     * 获取当前时间，全值yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTimeFull(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    
    /**
     * 获取当前时间，yyyyMMdd
     * @return
     */
    public static String getCurrentDate8(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    public static boolean compareDate(String startDate,String endDate) throws ParseException {
        if (startDate != null) {
            if (startDate.length() != 0) {
                if (endDate != null) {
                    if (endDate.length() != 0) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                        if (format.parse(startDate).getTime() > format.parse(endDate).getTime()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * 格式化日期，yyyy.MM.dd HH:mm:ss
     * @return
     */
    public static String formateDatePoint(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     *
     * <p>函数名称：        </p>
     * <p>功能说明：获取指定间隔天数的日期
     *
     * </p>
     *<p>参数说明：</p>
     * @param dt 日期
     * @param days 天数
     * @return
     */
    public static Date getDateDays(Date dt, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.setTimeInMillis(cal.getTimeInMillis() + days * DAY);
        return cal.getTime();
    }

    /**
     *
     * <p>函数名称：        </p>
     * <p>功能说明：获取指定间隔天数的日期
     *
     * </p>
     *<p>参数说明：</p>
     * @param dt 日期
     * @param format 格式
     * @param days 天数
     * @return date + days的日期
     */
    public static String getDateByDays(String dt, String format, int days) {
        Date dateTmp = getDateDays(stringToDate(dt, format), days);
        return formatDateTime(dateTmp, format);
    }


    /**
     *
     * <p>函数名称：        </p>
     * <p>功能说明：格式化日期
     *
     * </p>
     *<p>参数说明：</p>
     * @param dt 日期
     * @param format 格式串
     * @return
     */
    public static String formatDateTime(Date dt, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dt);
    }

    /**
     *
     * <p>函数名称：        </p>
     * <p>功能说明：字符串日期转化成日期格式
     *
     * </p>
     *<p>参数说明：</p>
     * @param date 日期字符
     * @param format 日期格式
     * @return
     */
    public static Date stringToDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        Date resDate;
        try {
            resDate = sdf.parse(date);
        } catch (Exception e) {
            throw new IllegalArgumentException("非有效日期型数据转换失败!");
        }
        return resDate;
    }

    /**
     *
     * <p>函数名称：        </p>
     * <p>功能说明：获取指定间隔天数的日期
     *
     * </p>
     *<p>参数说明：</p>
     * @param dt 日期(格式:yyyyMMdd)
     * @param days 天数
     * @return date + days的日期
     */
    public static String getDateByDays(String dt, int days) {
        return getDateByDays(dt, SDS, days);
    }
}
