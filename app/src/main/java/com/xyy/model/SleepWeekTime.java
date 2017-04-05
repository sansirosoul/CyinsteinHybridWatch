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

    public int getLightsleepHour() {
        return lightsleepHour;
    }

    public void setLightsleepHour(int lightsleepHour) {
        this.lightsleepHour = lightsleepHour;
    }

    public int getLightsleepMin() {
        return lightsleepMin;
    }

    public void setLightsleepMin(int lightsleepMin) {
        this.lightsleepMin = lightsleepMin;
    }

    public int getDeepsleepHour() {
        return deepsleepHour;
    }

    public void setDeepsleepHour(int deepsleepHour) {
        this.deepsleepHour = deepsleepHour;
    }

    public int getDeepsleepMin() {
        return deepsleepMin;
    }

    public void setDeepsleepMin(int deepsleepMin) {
        this.deepsleepMin = deepsleepMin;
    }

    public int getAwakeHour() {
        return awakeHour;
    }

    public void setAwakeHour(int awakeHour) {
        this.awakeHour = awakeHour;
    }

    public int getAwakeMin() {
        return awakeMin;
    }

    public void setAwakeMin(int awakeMin) {
        this.awakeMin = awakeMin;
    }

    int lightsleepHour = 0;
    int lightsleepMin = 0;
    int deepsleepHour = 0;
    int deepsleepMin = 0;
    int awakeHour = 0;
    int awakeMin = 0;
    int sleepHour = 0;
    int sleepMin= 0;

    public int getSleepHour() {
        return sleepHour;
    }

    public void setSleepHour(int sleepHour) {
        this.sleepHour = sleepHour;
    }

    public int getSleepMin() {
        return sleepMin;
    }

    public void setSleepMin(int sleepMin) {
        this.sleepMin = sleepMin;
    }

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
