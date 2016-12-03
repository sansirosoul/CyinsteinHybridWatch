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

    public int getId() {
        return id;
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
}
