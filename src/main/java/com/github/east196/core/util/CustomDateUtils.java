package com.github.east196.core.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;

/**
 * @Author east196
 * @Date 2019/9/9 10:34
 */
public class CustomDateUtils extends DateUtils{

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
            "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 日期枚举常量
     */
    public enum Pattern {
        year, month, day, minutes, second, millisecond, hour
    }

    ;

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    public static String getYearMonth(Date date) {
        return formatDate(date, "yyyy-MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
     * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    // 获得今天0点时间
    public static Date getTodayStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();


    }

    // 获得本周一0点时间
    public static Date getWeekStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    // 获得本月第一天0点时间
    public static Date getMonthStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }


    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = System.currentTimeMillis() - date.getTime();

        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static long getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取上个月第一天
     */
    public static String getLastMonthFristDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return CustomDateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");
    }


    /**
     * 加月 （n 为负数是减）
     */
    public static Date addMonth(Date date, Integer n) {
        Calendar calendar = DateToCalendar(date);
        calendar.add(Calendar.MONTH, n);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static String getMonthFristDay(String yyyyMM) {

        Calendar calendar = DateToCalendar(parseDate(yyyyMM + "-01"));
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return CustomDateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");
    }

    public static Date getLastMonthFristDay(Date date) {
        Calendar calendar = DateToCalendar(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取上个月最后一天
     */
    public static String getLastMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return CustomDateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");
    }

    public static String nextMonthFirstDate(String yyyyMM) {
        Calendar calendar = DateToCalendar(parseDate(yyyyMM + "-01"));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return CustomDateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");
    }

    public static String nextMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return CustomDateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");
    }

    public static Date strToDate(String dstr) {
        FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
        try {
            return sdf.parse(dstr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Long getStrToDateTime(String dstr) {
        FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
        try {
            return sdf.parse(dstr).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Long getStrToDateTimeAddOne(String dstr) {
        FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(dstr));
            cal.add(Calendar.DATE, 1);
            return cal.getTimeInMillis() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算两个日期间的差值
     *
     * @param date1
     *            日期1
     * @param date2
     *            日期2
     * @param p
     *            日期常量
     * @return date1-date2 的整型值
     */
    /**
     * Java日期转Unix 时间
     */
    public static long toUnixTime(Date date) {

        return date.getTime() / 1000;
    }

    /**
     * Unix 时间转 Java日期
     */
    public static Date toDate(long unixTime) {
        Long timestamp = unixTime * 1000;
        return new Date(timestamp);
    }

    /**
     * 判断一个时间是否是两个时间的区间
     *
     * @return
     */
    public static boolean isTwoDateSection(Date beforeDate, Date afterDate, Date currentDate) {
        if (currentDate.after(beforeDate) && currentDate.before(afterDate)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 日期加dayNum天
     */
    public static Date addDay(Date date, int dayNum) {
        if (null == date) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date); // 设置当前日期
        c.add(Calendar.DATE, dayNum); // 日期加dayNum天
        date = c.getTime();
        return date;
    }

    public static int dateDiff(Date date1, Date date2, Pattern p) {
        int diff = 0;
        Calendar cal1 = Calendar.getInstance();
        cal1.clear();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.clear();
        cal2.setTime(date2);
        switch (p) {
            case year: {
                int year1 = cal1.get(Calendar.YEAR);
                int year2 = cal2.get(Calendar.YEAR);
                diff = year1 - year2;
                break;
            }
            case month: {
                int year1 = cal1.get(Calendar.YEAR);
                int year2 = cal2.get(Calendar.YEAR);
                int month1 = cal1.get(Calendar.MONTH);
                int month2 = cal2.get(Calendar.MONTH);

                System.out.println(month1 + " " + month2);
                diff = month1 + (year1 - year2) * 12 - month2;
                break;
            }
            case minutes: {
                int minute1 = cal1.get(Calendar.MINUTE);
                int minute2 = cal2.get(Calendar.MINUTE);
                diff = minute1 - minute2;
                break;
            }
            case second: {
                int second1 = cal1.get(Calendar.SECOND);
                int second2 = cal2.get(Calendar.SECOND);
                diff = second1 - second2;
                break;
            }
            case millisecond: {
                int mil1 = cal1.get(Calendar.MILLISECOND);
                int mil2 = cal2.get(Calendar.MILLISECOND);
                diff = mil1 - mil2;
                break;
            }
            default:
                break;
        }
        return diff;
    }

    /**
     * 根据当前所过时间的毫秒数返回日期实体
     *
     * @param milliseconds 毫秒数
     * @return
     */
    public static Date getDateByMillSec(long milliseconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds * 1000);
        return c.getTime();
    }

    /**
     * 根据当前所过时间的秒数返回日期实体
     *
     * @param
     * @return
     */
    public static Date getDateBysecondSec(long second) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(second);
        return c.getTime();
    }

    /**
     * 把String类型的日期转换成Date类型
     */
    public static Date parseDate(String date) {
        FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 当前时间减去12个月得到的时间
     *
     * @return
     */
    public static String getDateDiff() {
        Calendar c = Calendar.getInstance();
        String now = formatDate(c.getTime(), "yyyy-MM-dd");
        c.add(Calendar.MONTH, -11);
        String lnow = formatDate(c.getTime(), "yyyy-MM-dd");
        return now + "@" + lnow;
    }

    /**
     * 得到系统当前时间
     */
    public static String getNowDate() {
        Calendar c = Calendar.getInstance();
        String now = formatDate(c.getTime(), "yyyy-MM-dd");
        return now;
    }

    public static String getNowDate(String formatDate) {
        Calendar c = Calendar.getInstance();
        String now = formatDate(c.getTime(), formatDate);
        return now;
    }

    /**
     * 得到当前时间减去12个月的时间
     *
     * @return
     */
    public static String getNowMonthsubtraction12() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -11);
        String snow = formatDate(c.getTime(), "yyyy-MM-dd");
        return snow;
    }

    public static String getNextDateStr() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, +1);
        String snow = formatDate(c.getTime(), "yyyy-MM-dd");
        return snow;
    }

    /**
     * 得到开始日期和结束日期之间的日期 *
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param noDay     是否不显示日
     * @return
     */
    public static List<String> getDateMonth(String startDate, String endDate, boolean noDay) {
        Date date1 = parseDate(startDate);
        Date date2 = parseDate(endDate);
        List<String> list = new ArrayList<String>();

        int numMonth = dateDiff(date1, date2, Pattern.month);
        System.out.println(numMonth);
        for (int i = 0; i <= numMonth; i++) {
            Calendar c1 = new GregorianCalendar();
            c1.setTime(date1);
            c1.add(Calendar.MONTH, -i);
            if (noDay == true) {
                list.add(formatDate(c1.getTime(), "yyyy-MM"));
            } else {
                list.add(formatDate(c1.getTime(), "yyyy-MM-dd"));
            }
        }
        Collections.reverse(list);
        return list;
    }

    /**
     * 得到一个月中的最后一天
     */
    public static Integer getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.get(Calendar.DATE);
    }

    /**
     * 日期转日历
     *
     * @param date
     * @return Calendar
     */
    public static Calendar DateToCalendar(Date date) {

        Calendar startdate = Calendar.getInstance();
        startdate.setTime(date);

        return startdate;
    }

    public static String getMDate(Date date) {
        String date1 = CustomDateUtils.formatDate(date, "yyyy年MM月");
        return date1;
    }

    public static String getYMDDate(Date date) {
        String date1 = CustomDateUtils.formatDate(date, "yyyy年MM月dd日");
        return date1;
    }

    // 得到当前时间的下个月。
    public static Date getNextDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, +1);
        return calendar.getTime();
    }

    /**
     * 得到当前时间的上个月
     *
     * @param date
     * @return
     * @author zxr
     */
    public static Date getPreDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 改变当前时间的格式
     *
     * @param date
     * @return
     * @author
     */
    public static Date dateToFormatDate(Date date,String fmt) {
        String sft = formatDate(date, fmt);
        Date formatDate = parseDate(sft, fmt);
        return formatDate;
    }

    /**
     * 改变当前时间的月份
     *
     * @param date
     * @return
     * @author
     */
    public static Date getDateChangeMonth(Date date,Integer n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, n);
        return calendar.getTime();
    }

    /**
     * 改变当前时间的年份
     *
     * @param date
     * @return
     * @author
     */
    public static Date getDateChangeYear(Date date,Integer n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, n);
        return calendar.getTime();
    }

    /**
     * 得到时间的前一天。
     */
    public static Date getUpDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    public static Date getUpDate(Date date, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 得到时间的前7天。
     */
    public static Date getUpDate7(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        return calendar.getTime();
    }

    /**
     * 得到时间的下一天。
     */
    public static Date getDownDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        return calendar.getTime();
    }

    /**
     * 得到时间的下七天。
     */
    public static Date getDownDate7(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +7);
        return calendar.getTime();
    }

    /**
     * 获取当期时间 author gzb
     *
     * @return
     */
    public static String getNowTime() {
        Date dates = new Date();
        FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String now = sdf.format(dates);
        return now;
    }

    /**
     * 得打本季度
     *
     * @return
     */
    public static Integer getQuarter() {
        Calendar calendar = Calendar.getInstance();
        int quarter = calendar.get(Calendar.MONTH);
        if (quarter <= 12 && quarter >= 10) {
            return 10;
        }
        if (quarter <= 9 && quarter >= 7) {
            return 7;
        }
        if (quarter <= 6 && quarter >= 4) {
            return 4;
        }
        if (quarter <= 3 && quarter >= 1) {
            return 1;
        }
        return 0;
    }

    /**
     * 得到当前时间减去12个月的时间
     *
     * @return
     */
    public static Date minusHour(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, hour);
        return c.getTime();
    }

    public static String getYesterday() {
        Calendar d = Calendar.getInstance();
        d.add(Calendar.DATE, -1);
        return CustomDateUtils.formatDate(d.getTime(), "yyyy-MM-dd");
    }

    /**
     * 得到指定日期的开始 如2010-01-10 00:00
     *
     * @param d
     * @author zdw
     */
    public static Date getNowdayBegin(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date getNowdayBeginMILLISECOND(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getNowdayEndMILLISECOND(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 59);
        return c.getTime();
    }

    /**
     * 得到指定日期的结束 如2010-01-10 23:59
     *
     * @param d
     * @author zdw
     */
    public static Date getNowdayEnd(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 得到当前时间减去3个月的时间
     *
     * @return
     */
    public static String getNowMonthsubtraction3() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -2);
        return formatDate(c.getTime(), "yyyy-MM-dd");
    }

    /**
     * 得到指定时间的开始时间 如:2010-10-10 00:00
     *
     * @return
     */
    public static Date getBegin(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return c.getTime();
    }

    /**
     * 得到指定时间的结束时间 如:2010-10-10 23:59
     *
     * @return
     */
    public static Date getEnd(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return c.getTime();
    }

    /**
     * 将指定格式日期形式的字符串转换成日期类型
     * <p>
     * <pre>
     *   常用日期格式有:精确到秒的形式 &quot;yyyy-MM-dd HH:mm:ss&quot;,精确到日的形式 &quot;yyyy-MM-dd&quot;
     *
     *   例如，将字符串&quot;2009-12-24 12:09:35&quot;转换成日期类型，则需要将参数strFormat置为
     * &quot;yyyy-MM-dd HH:mm:ss&quot;形式，这样就能将其转换为日期类型的了。
     * </pre>
     *
     * @param strDate   - 需要转换的日期(字符串)
     * @param strFormat - 需要格式化日期(字符串)的格式
     * @return - 日期
     */
    public static Date string2Date(String strDate, String strFormat) {
        try {
            FastDateFormat sdf = FastDateFormat.getInstance(strFormat);
            //SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
            if (strDate.length() == 16) {
                strDate += ":00";
            } else if (strDate.length() == 10) {
                strDate += " 00:00:00";
            }
            Date date = sdf.parse(strDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将"yyyy-MM-dd"格式的日期字符串转换为日期类型
     *
     * @param strDate - 需要转换的日期字符串
     * @return - 日期类型
     */
    public static Date string2Date(String strDate) {
        return string2Date(strDate, "yyyy-MM-dd");
    }

    /**
     * 将日期转换成指定格式的字符串类型. 常用日期格式有:精确到秒的形式 "yyyy-MM-dd HH:mm:ss",精确到日的形式
     * "yyyy-MM-dd"
     *
     * @param date   - 需要转换的日期
     * @param format - 日期格式
     * @return - 转换成的字符串
     */
    public static final String date2String(Date date, String format) {
        if (date == null || format == null) {
            return "";
        }
        //SimpleDateFormat sdf = new SimpleDateFormat(format);
        FastDateFormat sdf = FastDateFormat.getInstance(format);
        return sdf.format(date);

    }

    /**
     * 将日期转换成指定格式( "yyyy-MM-dd")的字符串类型.
     *
     * @param date - 需要转换的日期
     * @return - 转换成的字符串
     */
    public static final String date2String(Date date) {
        return date2String(date, "yyyy-MM-dd");
    }

    /**
     * 计算两个日期之间的秒差
     *
     * @param a
     * @param b
     * @return
     */
    public static int getBetweenMin(Date a, Date b) {
        long dayNumber = 0;
        // 1小时=60分钟=3600秒=3600000
        long mins = 1000L;
        // long day= 24L * 60L * 60L * 1000L;计算天数之差
        try {
            dayNumber = (a.getTime() - b.getTime()) / mins;
        } catch (Exception e) {

        }
        if (dayNumber < 0) {
            return 300;
        }
        return (int) dayNumber;
    }

    public static Long getBetweenSub(Date a, Date b) {
        long dayNumber = 0;
        // 1小时=60分钟=3600秒=3600000
        long mins = 1000L;
        // long day= 24L * 60L * 60L * 1000L;计算天数之差

        dayNumber = (a.getTime() - b.getTime()) / mins;

        return dayNumber;
    }

    public static boolean getBetweenBig(Date a, Date b) {
        long dayNumber = 0;
        // 1小时=60分钟=3600秒=3600000
        long mins = 1000L;
        // long day= 24L * 60L * 60L * 1000L;计算天数之差
        try {
            dayNumber = (a.getTime() - b.getTime()) / mins;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dayNumber < 0) {
            return false;
        }
        return true;
    }

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_NAME = "yyyy-MM-ddHHmmss";
    private static final String DATE_EL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))"
            + "[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))"
            + "[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|"
            + "([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?"
            + "((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])"
            + "|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])"
            + "|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-9]))\\:([0-5]?[0-9])" + "((\\s)|(\\:([0-5]?[0-9])))))?$";

    /**
     * 日期转换常用格式
     *
     * @author sunhaiyang
     */
    public enum Format {

        YYYYMMDD("yyyyMMdd"),

        YYYY_MM_DD("yyyy-MM-dd"),

        YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),

        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),

        YYYYMMDDHHMMSS("yyyyMMddHHmmss"),

        DDMMYYYY("ddMMMyy");

        private final String value;

        Format(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    /**
     * 把String类型的日期转换成Date类型
     */
    public static Date parseDate(String date, String pattern) {
        final FastDateFormat sdf = FastDateFormat.getInstance(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期转换的方法
     *
     * @param date
     * @param
     * @return String
     */
    public static String formatDate(Date date, String fmt) {
        if (null != date) {
            final FastDateFormat sdf = FastDateFormat.getInstance(fmt);
            //final SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            //SimpleDateFormat 线程不安全
            return sdf.format(date);
        }
        return "";
    }

    /**
     * <B>功能简述</B><br>
     * EBE AV 日期查询处理
     *
     * @param dates
     * @return String
     * @author xiaojingze
     */
    public static String dealDate(String dates) {
        String month = getMMM(parseDate(dates, Format.YYYY_MM_DD.value()));
        String day = dates.split("-")[2];
        return day + month;
    }

    /**
     * 得到当前月的第一天
     */
    public static Date getMonthDay(String f) {
        final Calendar calendar = Calendar.getInstance();
        if ("first".equals(f)) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            return calendar.getTime();
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return calendar.getTime();
        }

    }

    /**
     * 得到去年的日期
     *
     * @return
     */
    public static Date getLastYear() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 得到制定时间的年，月，日 已int 类型返回
     */
    public static int getDatePattern(Date date, Pattern p) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (p) {
            case year:
                return calendar.get(Calendar.YEAR);
            case month:
                return calendar.get(Calendar.MONTH) + 1;
            case day:
                return calendar.get(Calendar.DATE);
            case hour:
                return calendar.get(Calendar.HOUR);
            default:
                break;
        }
        return 0;
    }

    /**
     * 匹配英文表示的月份
     *
     * @param date 日期
     */
    public static String getMMM(Date date) {
        final String mmm = "";
        final int month = getDatePattern(date, Pattern.month);
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                break;
        }
        return mmm;
    }

    /**
     * <B>功能简述</B><br>
     * 根据英文简拼得到 月份
     *
     * @param mmm 简拼
     * @return 月份
     */
    public static String getMonth(String mmm) {
        String m = null;
        if ("JAN".equalsIgnoreCase(mmm)) {
            m = "01";
        } else if ("FEB".equalsIgnoreCase(mmm)) {
            m = "02";
        } else if ("MAR".equalsIgnoreCase(mmm)) {
            m = "03";
        } else if ("APR".equalsIgnoreCase(mmm)) {
            m = "04";
        } else if ("MAY".equalsIgnoreCase(mmm)) {
            m = "05";
        } else if ("JUN".equalsIgnoreCase(mmm)) {
            m = "06";
        } else if ("JUL".equalsIgnoreCase(mmm)) {
            m = "07";
        } else if ("AUG".equalsIgnoreCase(mmm)) {
            m = "08";
        } else if ("SEP".equalsIgnoreCase(mmm)) {
            m = "09";
        } else if ("OCT".equalsIgnoreCase(mmm)) {
            m = "10";
        } else if ("NOV".equalsIgnoreCase(mmm)) {
            m = "11";
        } else if ("DEC".equalsIgnoreCase(mmm)) {
            m = "12";
        }
        return m;
    }

    /**
     * 判断是否为时间类型
     *
     * @param date
     * @return
     * @date Dec 14, 2011 4:00:46 PM
     * @comment
     */
    public static boolean isDateType(String date) {
        final java.util.regex.Pattern p = java.util.regex.Pattern.compile(DATE_EL);
        final Matcher m = p.matcher(date);
        return m.matches();
    }

    /**
     * 获取给定日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getTheFirstDayOfWeek(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getADayOfWeek(cal, Calendar.MONDAY).getTime();
    }

    /**
     * 获取给定日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getTheLastDayOfWeek(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getADayOfWeek(cal, Calendar.SUNDAY).getTime();
    }

    /**
     * 获取上一周的第一天
     *
     * @param
     * @return
     */
    public static Date getLastWeekStartTime() {
        Date todayTime = CustomDateUtils.dateToFormatDate(new Date(), "yyyy-MM-dd");
        final Calendar cal = Calendar.getInstance();
        cal.setTime(todayTime);
        cal.add(Calendar.DATE, -Calendar.DAY_OF_WEEK);
        return getADayOfWeek(cal, Calendar.MONDAY).getTime();
    }

    /**
     * 获取给定日期上一周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getTheLastDayOfPreWeek(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -Calendar.DAY_OF_WEEK);
        return getADayOfWeek(cal, Calendar.SUNDAY).getTime();
    }

    /**
     * 获取给定日期所在周的某一天
     *
     * @param day
     * @param dayOfWeek
     * @return
     */
    private static Calendar getADayOfWeek(Calendar day, int dayOfWeek) {
        final int currDayOfWeek = day.get(Calendar.DAY_OF_WEEK);
        final int week = 7;
        if (currDayOfWeek == dayOfWeek) {
            return day;
        }
        int diffDay = dayOfWeek - currDayOfWeek;
        if (currDayOfWeek == Calendar.SUNDAY) {
            diffDay -= week;
        } else if (dayOfWeek == Calendar.SUNDAY) {
            diffDay += week;
        }
        day.add(Calendar.DATE, diffDay);
        return day;
    }

    /**
     * 获取当前时间的小时
     *
     * @return
     */
    public static Integer getCurrentHour() {
        final Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getWeekOfDate(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * <B>功能简述</B><br>
     * 根据给定日期和需要减少天数，获取减少后的日期
     *
     * @param date 修改时间
     * @param day  减少天数（正整数）
     * @return [返回类型说明]
     * @author
     */
    public static Date getBeforeDate(Date date, long day) {
        if (date != null) {
            final Calendar calendar = Calendar.getInstance();
            long time = date.getTime() - (day * 24 * 60 * 60 * 1000);
            calendar.setTimeInMillis(time);
            return calendar.getTime();
        }
        return null;
    }

//    public static Date getBeforeDateM(Date date, int month) {
//        if (date != null) {
//            final Calendar calendar = Calendar.getInstance();
//            calendar.add(calendar.MONTH, month);
//            return calendar.getTime();
//        }
//        return null;
//    }

    /**
     * <B>功能简述</B><br>
     * 根据给定日期和需要增加天数，获取增加后的日期
     *
     * @param date 修改时间
     * @param day  增加天数（正整数）
     * @return [返回类型说明]
     * @author
     */
    public static Date getAfterDate(Date date, long day) {
        if (date != null) {
            final Calendar calendar = Calendar.getInstance();
            long time = date.getTime() + (day * 24 * 60 * 60 * 1000);
            calendar.setTimeInMillis(time);
            return calendar.getTime();
        }
        return null;
    }

    public static String getWeeByDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    public static List<String> getSevenDate() {
        List<String> dateList = new ArrayList<String>();

        Date date = CustomDateUtils.getAfterDate(new Date(), -6);
        for (int i = 0; i < 7; i++) {
            dateList.add(CustomDateUtils.date2String(CustomDateUtils.getAfterDate(date, i)));
        }
        return dateList;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        try {
            FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd");
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            return -1;
        }

    }

    public static String getRepTime() {
        FastDateFormat sdf = FastDateFormat.getInstance("yyyyMMddHHmmss");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    public static final Date dateFormat(Date date) {
        return string2Date(date2String(date, "yyyy-MM-dd"));
    }

    public static void main(String[] args) {
        System.out.println(formatDateTHH(new Date()));
    }

    public static String formatDateTHH(Date date) {
        String formatDate = getRepTime();
        return formatDate.substring(0, 8) + "T" + formatDate.substring(7, formatDate.length() - 1);

    }

    public static String formatDateyMdHm(Date date, String form) {
        FastDateFormat formatter = FastDateFormat.getInstance(form);
        //SimpleDateFormat formatter = new SimpleDateFormat(form);
        return formatter.format(date);
    }

}
