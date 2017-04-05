package com.xyy.model;

/**
 * Created by Administrator on 2016/11/10.
 */

public class SleepData {
    public int date;//日期 1-31
    public int time;//时间
    public int hour;//小时
    public int min;//分钟
    public int status;  //睡眠状态 0 静止    1 清醒     2 浅睡   3 深睡
    public int quality;  //睡眠质量
    public int count;
    public int sums;
    public boolean isLast;//是否最后一条数据

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSums() {
        return sums;
    }

    public void setSums(int sums) {
        this.sums = sums;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
