package com.gzucm.wsyy.utils;

import android.text.format.Time;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/19 0019.
 */

public class Dateutil {


    public static ArrayList<String> getSevenDate(int intervals) {
        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i <intervals; i++) {
            fetureDaysList.add(getFetureDate(i));
        }
        return fetureDaysList;
    }
    public static ArrayList<String> getTwoWeekDate() {
        ArrayList<String> daysList = new ArrayList<>();
        for (int i = 0; i <7; i++) {
            daysList.add(getPastDate(6-i) + getWeekOfDate(StrToDate(getPastDate(6-i))));
        }
        for (int i = 7; i <13; i++) {
            daysList.add(getFetureDate(i-6) + getWeekOfDate(StrToDate(getFetureDate(i-6))));
        }
        return daysList;
    }
    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        Log.e(null, result);
        return result;
    }


    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    public static ArrayList<String> getWeek(List<String> datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        List<String> week = new ArrayList<>();
        for(int i = 0;i < datetime.size();i++){
            try {
                datet = f.parse(datetime.get(i));
                cal.setTime(datet);
                int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
                if (w < 0){
                    w = 0;
                }
                String day = weekDays[w];
                week.add(day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return (ArrayList<String>) week;
    }

    public static ArrayList<String> getSeven(List<String> dates) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        ArrayList<String> serven = new ArrayList<>();
        for(int i = 0;i < dates.size();i++){
            Date date = StrToDate(dates.get(i));
            String now = simpleDateFormat.format(date);
            serven.add(now);
        }
        return serven;
    }

    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 判断当前系统时间是否在指定时间的范围内
     *
     * @param beginHour
     * 开始小时，例如22
     * @param beginMin
     * 开始小时的分钟数，例如30
     * @param endHour
     * 结束小时，例如 8
     * @param endMin
     * 结束小时的分钟数，例如0
     * @return true表示在范围内，否则false
     */
    public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int endHour, int endMin) {
        boolean result = false;
        final long aDayInMillis = 1000 * 60 * 60 * 24;
        final long currentTimeMillis = System.currentTimeMillis();

        Time now = new Time();
        now.set(currentTimeMillis);

        Time startTime = new Time();
        startTime.set(currentTimeMillis);
        startTime.hour = beginHour;
        startTime.minute = beginMin;

        Time endTime = new Time();
        endTime.set(currentTimeMillis);
        endTime.hour = endHour;
        endTime.minute = endMin;

        if (!startTime.before(endTime)) {
        // 跨天的特殊情况（比如22:00-8:00）
            startTime.set(startTime.toMillis(true) - aDayInMillis);
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
            Time startTimeInThisDay = new Time();
            startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis);
            if (!now.before(startTimeInThisDay)) {
                result = true;
            }
        } else {
        // 普通情况(比如 8:00 - 14:00)
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
        }
        return result;
    }

}
