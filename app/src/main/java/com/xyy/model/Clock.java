package com.xyy.model;

import android.content.Context;

import com.ysp.hybridtwatch.R;

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

    public static int transformSnoozeTime(Context context,String s) {
        int i = 0;
        if(s!=null){}
        if (s.equals(context.getResources().getString(R.string.min5))) i = 1;
        if (s.equals(context.getResources().getString(R.string.min10))) i = 2;
        if (s.equals(context.getResources().getString(R.string.min15))) i = 3;
        if (s.equals(context.getResources().getString(R.string.min20))) i = 4;
        if (s.equals(context.getResources().getString(R.string.min25))) i = 5;
        if (s.equals(context.getResources().getString(R.string.min30))) i = 6;
        return i;
    }

    public static String transformSnoozeTime2(Context context,int i) {
        String s = null;
        if (i == 0) s = context.getResources().getString(R.string.none);
        if (i == 1) s = context.getResources().getString(R.string.min5);
        if (i == 2) s = context.getResources().getString(R.string.min10);
        if (i == 3) s = context.getResources().getString(R.string.min15);
        if (i == 4) s = context.getResources().getString(R.string.min20);
        if (i == 5) s = context.getResources().getString(R.string.min25);
        if (i == 6) s = context.getResources().getString(R.string.min30);
        return s;
    }

    public static int transformRate(Context context,String s) {
        int i = 5;
        if(s!=null){
            if (s.equals(context.getResources().getString(R.string.rate1))) i = 1;
            if (s.equals(context.getResources().getString(R.string.rate2))) i = 2;
            if (s.equals(context.getResources().getString(R.string.rate3))) i = 3;
            if (s.equals(context.getResources().getString(R.string.rate4))) i = 4;
        }
        return i;
    }

    public static String transformRat2(Context context,int i) {
        String s = null;
        if (i == 1) s = context.getResources().getString(R.string.rate1);
        if (i == 2) s = context.getResources().getString(R.string.rate2);
        if (i == 3) s = context.getResources().getString(R.string.rate3);
        if (i == 4) s = context.getResources().getString(R.string.rate4);
        return s;
    }

    public static String transformCustom(Context context,String bytestr){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=bytestr.length()-1;i>=0;i--){
            String s = bytestr.substring(i,i+1);
            if(i==7){
                if(s.equals("1"))stringBuilder.append(context.getResources().getString(R.string.Mon));
            }else if(i==6){
                if(s.equals("1"))stringBuilder.append(context.getResources().getString(R.string.Tue));
            }else if(i==5){
                if(s.equals("1"))stringBuilder.append(context.getResources().getString(R.string.Wed));
            }else if(i==4){
                if(s.equals("1"))stringBuilder.append(context.getResources().getString(R.string.Thu));
            }else if(i==3){
                if(s.equals("1"))stringBuilder.append(context.getResources().getString(R.string.Fri));
            }else if(i==2){
                if(s.equals("1"))stringBuilder.append(context.getResources().getString(R.string.Sat));
            }else if(i==1){
                if(s.equals("1"))stringBuilder.append(context.getResources().getString(R.string.Sun));
            }
        }
        return stringBuilder.toString();
    }

}
