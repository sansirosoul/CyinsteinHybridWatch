package com.xyy.model;

/**
 * Created by Administrator on 2016/10/26.
 */

public class Clock {
    public int id;
    public String time;
    public String rate;
    public int isOpen;//闹钟是否开启 0否 1是
    public String SnoozeTime;
    public String custom;//自定义byte字符串
    public boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSnoozeTime() {
        return SnoozeTime;
    }

    public void setSnoozeTime(String snoozeTime) {
        SnoozeTime = snoozeTime;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public String getRate() {

        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Clock){
            Clock clock = (Clock) o;
            return this.id==clock.id;
        }
        return super.equals(o);
    }

    public static int transformSnoozeTime(String s) {
        int i = 0;
        if(s!=null){}
        if (s.equals("5分钟")) i = 1;
        if (s.equals("10分钟")) i = 2;
        if (s.equals("15分钟")) i = 3;
        if (s.equals("20分钟")) i = 4;
        if (s.equals("25分钟")) i = 5;
        if (s.equals("30分钟")) i = 6;
        return i;
    }

    public static String transformSnoozeTime2(int i) {
        String s = null;
        if (i == 0) s = "无";
        if (i == 1) s = "5分钟";
        if (i == 2) s = "10分钟";
        if (i == 3) s = "15分钟";
        if (i == 4) s = "20分钟";
        if (i == 5) s = "25分钟";
        if (i == 6) s = "30分钟";
        return s;
    }

    public static int transformRate(String s) {
        int i = 5;
        if(s!=null){
            if (s.equals("只响一次")) i = 1;
            if (s.equals("每天")) i = 2;
            if (s.equals("周一到周五")) i = 3;
            if (s.equals("周六、周日")) i = 4;
        }
        return i;
    }

    public static String transformRat2(int i) {
        String s = null;
        if (i == 1) s = "只响一次";
        if (i == 2) s = "每天";
        if (i == 3) s = "周一到周五";
        if (i == 4) s = "周六、周日";
        return s;
    }

    public static String transformCustom(String bytestr){
        StringBuilder stringBuilder = new StringBuilder("周");
        for (int i=bytestr.length()-1;i>=0;i--){
            String s = bytestr.substring(i,i+1);
            if(i==7){
                if(s.equals("1"))stringBuilder.append(" 一");
            }else if(i==6){
                if(s.equals("1"))stringBuilder.append(" 二");
            }else if(i==5){
                if(s.equals("1"))stringBuilder.append(" 三");
            }else if(i==4){
                if(s.equals("1"))stringBuilder.append(" 四");
            }else if(i==3){
                if(s.equals("1"))stringBuilder.append(" 五");
            }else if(i==2){
                if(s.equals("1"))stringBuilder.append(" 六");
            }else if(i==1){
                if(s.equals("1"))stringBuilder.append(" 日");
            }
        }
        return stringBuilder.toString();
    }

}
