package com.xyy.Gazella.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/10/24.
 */

public class SomeUtills {

    private Calendar CalendarInstance = Calendar.getInstance();
    private  SimpleDateFormat sdf;


    /***
     * 获取 一周天数
     *
     * @param calendar
     * @return
     */

    public HashMap<String, String> getWeekdate(Date calendar) {
        HashMap<String, String> weekMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Calendar c = Calendar.getInstance();
        c.setTime(calendar);
        // 今天是一周中的第几天
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (c.getFirstDayOfWeek() == Calendar.SUNDAY) {
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        // 计算一周开始的日期
        c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        for (int i = 1; i <= 7; i++) {
            c.add(Calendar.DAY_OF_MONTH, 1);
            System.out.println(sdf.format(c.getTime()));
            if (i == 1) weekMap.put("1", sdf.format(c.getTime()));
            if (i == 7) weekMap.put("7", sdf.format(c.getTime()));
        }
        return weekMap;
    }

    /***
     *       获取日期
     * @param type  0 :  年月日   1： 年月
     * @return
     */

    public String getDate(int type) {
        String Date="";
        switch (type) {
            case 0:
                 sdf = new SimpleDateFormat("yyyy.MM.dd");
                Date= sdf.format(CalendarInstance.getTime());
                break;
            case 1:
                 sdf = new SimpleDateFormat("yyyy.MM");
                Date= sdf.format(CalendarInstance.getTime());
                break;
        }
        return Date;
    }

//    public String getDate(int type) {
//        String Date="";
//        switch (type) {
//            case 0:
//                 sdf = new SimpleDateFormat("yyyy.MM.dd");
//                Date= sdf.format(CalendarInstance.getTime());
//                break;
//            case 1:
//                 sdf = new SimpleDateFormat("yyyy.MM");
//                Date= sdf.format(CalendarInstance.getTime());
//                break;
//        }
//        return Date;
//    }

}
