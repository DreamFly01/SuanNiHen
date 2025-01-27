package com.sg.cj.common.base.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 作者：${chenjie} on 15/11/12 15:32
 * 邮箱：chenjie_goodboy@163.com
 */
public class SgDateUtil {
    public static String[] WEEK = new String[]{"天", "一", "二", "三", "四", "五", "六"};

    private static final long ONE_SECOND = 1000;
    private static final long ONE_MINUTE = ONE_SECOND * 60;
    private static final long ONE_HOUR = ONE_MINUTE * 60;
    private static final long ONE_DAY = ONE_HOUR * 24;

    public static long getStringToLong(String timeString) {
        long time = 0;

        if (timeString.contains(".")) {
            String str[] = timeString.split("\\.");

            time = Long.valueOf(str[0]) * 1000;

        } else {
            time = Long.valueOf(timeString) * 1000;
        }

        return time;
    }


    /**
     * 起始时间
     *
     * @return
     */

    public static long getStartTime(String endTime) {
        long start = getStringToLong(endTime);
//        if (endTime.contains(".")) {
//            start = Long.valueOf((long) (Float.valueOf(endTime) * 1000));
//        } else {
//            start = Long.valueOf(endTime) * 1000;
//        }

        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(new Date(start));
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }


    /**
     * 结束
     *
     * @return
     */

    public static long getEndTime(String endTime) {
        long start = getStringToLong(endTime);
//        if (endTime.contains(".")) {
//            start = Long.valueOf((long) (Float.valueOf(endTime) * 1000));
//        } else {
//            start = Long.valueOf(endTime) * 1000;
//        }

        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(new Date(start));
        todayStart.set(Calendar.HOUR, 23);
        todayStart.set(Calendar.MINUTE, 59);
        todayStart.set(Calendar.SECOND, 59);
        todayStart.set(Calendar.MILLISECOND, 999);
        return todayStart.getTime().getTime();
    }

    /**
     * 当天起始时间
     *
     * @return
     */
    public static long startOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date.getTime();
    }

//    public static long getStartTime() {
//        Calendar todayStart = Calendar.getInstance();
//        todayStart.set(Calendar.HOUR, 0);
//        todayStart.set(Calendar.MINUTE, 0);
//        todayStart.set(Calendar.SECOND, 0);
//        todayStart.set(Calendar.MILLISECOND, 0);
//        return todayStart.getTime().getTime();
//    }


    /**
     * 当天结束时间
     *
     * @return
     */
    public static long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }

    public static boolean isSameDay(Date date, Date sameDate) {

        if (null == date || null == sameDate) {

            return false;

        }

        Calendar nowCalendar = Calendar.getInstance();

        nowCalendar.setTime(sameDate);

        Calendar dateCalendar = Calendar.getInstance();

        dateCalendar.setTime(date);

        if (nowCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)

                && nowCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)

                && nowCalendar.get(Calendar.DATE) == dateCalendar.get(Calendar.DATE)) {

            return true;

        }


        return false;

    }

    public static String getTimestampString(Date var0) {
        String var1 = null;
        long var2 = var0.getTime();
        if (isSameDay(new Date(System.currentTimeMillis()), new Date(var2))) {
            var1 = "今天 HH:mm";

//            Calendar var4 = GregorianCalendar.getInstance();
//            var4.setTime(var0);
//            int var5 = var4.get(Calendar.HOUR);
//            if (var5 > 17) {
//                var1 = "晚上 hh:mm";
//            } else if (var5 >= 0 && var5 <= 6) {
//                var1 = "凌晨 hh:mm";
//            } else if (var5 > 11 && var5 <= 17) {
//                var1 = "下午 hh:mm";
//            } else {
//                var1 = "上午 hh:mm";
//            }
        }
//        else if (isYesterday(var2)) {
//            var1 = "昨天 HH:mm";
//        }
        else {
            var1 = "M月d日 HH:mm";
        }
        return (new SimpleDateFormat(var1, Locale.CHINA)).format(var0);
    }

    public static long getLongFromString(String ttl) {


        float t = Float.valueOf(ttl);

        return ((long) t) * 1000;


    }

    /**
     * Date（long） 转换 String
     *
     * @param time
     * @param format
     * @return
     */
    public static String date2String(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(time);
        return s;
    }

    /**
     * long 去除 时分秒
     * 时分秒全部为0
     *
     * @param date
     * @return
     */
    public static long getYearMonthDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

//    /**
//     * 获取目标时间和当前时间之间的差距
//     *
//     * @param date
//     * @return
//     */
//    public static String getTimestampString(Date date) {
//        Date curDate = new Date();
//        long splitTime = curDate.getTime() - date.getTime();
//        if ( splitTime & lt;
//        (30 * ONE_DAY)){
//            if ( splitTime & lt;
//            ONE_MINUTE){
//                return "刚刚";
//            }
//            if ( splitTime & lt;
//            ONE_HOUR){
//                return String.format("%d分钟前", splitTime / ONE_MINUTE);
//            }
//
//            if ( splitTime & lt;
//            ONE_DAY){
//                return String.format("%d小时前", splitTime / ONE_HOUR);
//            }
//
//            return String.format("%d天前", splitTime / ONE_DAY);
//        }
//        String result;
//        result = "M月d日 HH:mm";
//        return (new SimpleDateFormat(result, Locale.CHINA)).format(date);
//    }
//
//    /**
//     * 24小时制 转换 12小时制
//     *
//     * @param time
//     * @return
//     */
//    public static String time24To12(String time) {
//        String str[] = time.split(":");
//        int h = Integer.valueOf(str[0]);
//        int m = Integer.valueOf(str[1]);
//        String sx;
//        if ( h & lt;
//        1){
//            h = 12;
//            sx = "上午";
//        }else if ( h & lt;
//        12){
//            sx = "上午";
//        }else if ( h & lt;
//        13){
//            sx = "下午";
//        }else{
//            sx = "下午";
//            h -= 12;
//        }
//        return String.format("%d:%02d%s", h, m, sx);
//    }

    /**
     * Date 转换 HH
     *
     * @param date
     * @return
     */
    public static String date2HH(Date date) {
        return new SimpleDateFormat("HH").format(date);
    }

    /**
     * Date 转换 HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String date2HHmm(Date date) {
        return new SimpleDateFormat("HH:mm").format(date);
    }

    /**
     * Date 转换 HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String date2HHmmss(Date date) {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    /**
     * Date 转换 MM.dd
     *
     * @param date
     * @return
     */
    public static String date2MMdd(Date date) {
        return new SimpleDateFormat("MM.dd").format(date);
    }

    /**
     * Date 转换 yyyy.MM.dd
     *
     * @param date
     * @return
     */
    public static String date2yyyyMMdd(Date date) {
        return new SimpleDateFormat("yyyy.MM.dd").format(date);
    }

    /**
     * Date 转换 MM月dd日 星期
     *
     * @param date
     * @return
     */
    public static String date2MMddWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return new SimpleDateFormat("MM月dd日 星期").format(date) + WEEK[dayOfWeek - 1];
    }

    /**
     * Date 转换 yyyy年MM月dd日 星期
     *
     * @param date
     * @return
     */
    public static String date2yyyyMMddWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return new SimpleDateFormat("yyyy年MM月dd日 星期").format(date) + WEEK[dayOfWeek - 1];
    }
}
