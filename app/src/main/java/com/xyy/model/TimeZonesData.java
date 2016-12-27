package com.xyy.model;

/**
 * Created by Administrator on 2016/12/26.
 */

public class TimeZonesData {

    String Gtm;
    String name;
    boolean isClick;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {

        isClick = click;
    }

    public  TimeZonesData(){

    }

    public TimeZonesData(String gtm, String name,boolean isClick) {
        Gtm = gtm;
        this.name = name;
        this.isClick=isClick;
    }

    public String getGtm() {
        return Gtm;
    }

    public String getName() {
        return name;
    }

    public void setGtm(String gtm) {
        Gtm = gtm;
    }

    public void setName(String name) {
        this.name = name;
    }
}
