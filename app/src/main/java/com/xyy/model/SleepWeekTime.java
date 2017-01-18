package com.xyy.model;

/**
 * Created by Administrator on 2017/1/12.
 */

public class SleepWeekTime {

    int awakeTime;
    int lightsleepTime;
    int sleepingTime;
    int sleeptime;
    int awakeCount;

    public int getAwakeCount() {
        return awakeCount;
    }

    public void setAwakeCount(int awakeCount) {
        this.awakeCount = awakeCount;
    }

    public SleepWeekTime() {
    }

    public int getSleeptime() {
        return sleeptime;
    }

    public void setSleeptime(int sleeptime) {
        this.sleeptime = sleeptime;
    }

    public SleepWeekTime(int awakeTime, int lightsleepTime, int sleepingTime, int sleeptime ) {
        this.awakeTime = awakeTime;
        this.lightsleepTime = lightsleepTime;
        this.sleepingTime = sleepingTime;
        this.sleeptime = sleeptime;

    }

    public int getAwakeTime() {
        return awakeTime;
    }

    public void setAwakeTime(int awakeTime) {
        this.awakeTime = awakeTime;
    }

    public int getLightsleepTime() {
        return lightsleepTime;
    }

    public void setLightsleepTime(int lightsleepTime) {
        this.lightsleepTime = lightsleepTime;
    }

    public int getSleepingTime() {
        return sleepingTime;
    }

    public void setSleepingTime(int sleepingTime) {
        this.sleepingTime = sleepingTime;
    }
}
