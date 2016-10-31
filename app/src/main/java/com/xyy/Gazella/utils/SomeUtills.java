package com.xyy.Gazella.utils;

import android.view.View;

import com.xyy.Gazella.activity.SleepActivity;
import com.xyy.Gazella.activity.StepActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/10/24.
 */

public class SomeUtills {

    private Calendar CalendarInstance = Calendar.getInstance();
    private SimpleDateFormat sdf;



    /***
     * 获取 一周天数
     *
     * @param calendar
     * @return
     */

    public HashMap<String, String> getWeekdate(Date calendar) {
        HashMap<String, String> weekMap = new HashMap<>();
         sdf = new SimpleDateFormat("yyyy.MM.dd");
//        Calendar c = Calendar.getInstance();
        CalendarInstance.setTime(calendar);
        // 今天是一周中的第几天
        int dayOfWeek = CalendarInstance.get(Calendar.DAY_OF_WEEK);
        if (CalendarInstance.getFirstDayOfWeek() == Calendar.SUNDAY)
            CalendarInstance.add(Calendar.DAY_OF_MONTH, 1);

        // 计算一周开始的日期
        CalendarInstance.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        for (int i = 1; i <= 7; i++) {
            CalendarInstance.add(Calendar.DAY_OF_MONTH, 1);
            System.out.println(sdf.format(CalendarInstance.getTime()));
            if (i == 1) weekMap.put("1", sdf.format(CalendarInstance.getTime()));
            if (i == 7) weekMap.put("7", sdf.format(CalendarInstance.getTime()));
        }
        return weekMap;
    }

    /***
     * 获取 下周天数
     *
     * @param calendar
     * @param amount      0是上周   1是下周
     * @return
     */

    public HashMap<String, String> getAmountWeekdate(Date calendar, int amount) {
        HashMap<String, String> weekMap = new HashMap<>();
         sdf = new SimpleDateFormat("yyyy.MM.dd");
        CalendarInstance.setTime(calendar);
        // 今天是一周中的第几天
        int dayOfWeek = CalendarInstance.get(Calendar.DAY_OF_WEEK);
        if(amount==0){
        if (CalendarInstance.getFirstDayOfWeek() == Calendar.SUNDAY) {
            CalendarInstance.add(Calendar.DAY_OF_MONTH, -7);
        }} else {
            if (CalendarInstance.getFirstDayOfWeek() == Calendar.SUNDAY) {
                CalendarInstance.add(Calendar.DAY_OF_MONTH, 7);
            }}
        // 计算一周开始的日期
        CalendarInstance.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        for (int i = 1; i <= 7; i++) {
            CalendarInstance.add(Calendar.DAY_OF_MONTH, 1);
            System.out.println(sdf.format(CalendarInstance.getTime()));
            if (i == 1) weekMap.put("1", sdf.format(CalendarInstance.getTime()));
            if (i == 7) weekMap.put("7", sdf.format(CalendarInstance.getTime()));
        }
        return weekMap;
    }

    /***
     * 获取日期
     *
     * @param type 0 :  年月日   1： 年月
     * @return
     */

    public String getDate(Date calendar, int type) {
        String Date = "";
        switch (type) {
            case 0:
                sdf = new SimpleDateFormat("yyyy.MM.dd");
                Date = sdf.format(calendar.getTime());
                break;
            case 1:
                sdf = new SimpleDateFormat("yyyy.MM");
                Date = sdf.format(calendar.getTime());
                break;
        }
        return Date;
    }

    /****
     * 获取  上一天上个月，下一天下个月 日期
     *
     * @param type   0是天数，  1 是月数
     * @param amount 0 是上一天 上个月。1是下一天 下个月
     * @return
     */

    public String getAmountDate(Date calendar, int type, int amount) {
        String Date = "";
        switch (type) {
            case 0:
                sdf = new SimpleDateFormat("yyyy.MM.dd");
                CalendarInstance.setTime(calendar);
                if (amount == 0) {
                    CalendarInstance.add(Calendar.DAY_OF_MONTH, -1);
                    Date = sdf.format(CalendarInstance.getTime());
                } else {
                    CalendarInstance.add(Calendar.DAY_OF_MONTH, +1);
                    Date = sdf.format(CalendarInstance.getTime());
                }
                break;
            case 1:
                sdf = new SimpleDateFormat("yyyy.MM");
                CalendarInstance.setTime(calendar);
                if (amount == 0) {
                    CalendarInstance.add(Calendar.MONTH, -1);
                    Date = sdf.format(CalendarInstance.getTime());
                } else {
                    CalendarInstance.add(Calendar.MONTH, +1);
                    Date = sdf.format(CalendarInstance.getTime());
                }
                break;
        }
        return Date;
    }
    public  void setCalendarViewGone(int type){
        if(type==0) {
            if (SleepActivity.sleepActivity.widget.getVisibility() == View.VISIBLE)
                SleepActivity.sleepActivity.setLlDateVisible(1);
        }else {
            if (StepActivity.stepActivity.widget.getVisibility() == View.VISIBLE)
                StepActivity.stepActivity.setLlDateVisible(1);
        }
    }
}
