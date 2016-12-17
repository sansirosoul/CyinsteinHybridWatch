package com.xyy.model;

/**
 * Created by Administrator on 2016/11/10.
 */

public class SleepData {
    public int date;//日期
    public int time;//时间
    public int status;//睡眠状态 0 清醒     1 潜睡     2深睡
    public int quality;//睡眠质量

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
