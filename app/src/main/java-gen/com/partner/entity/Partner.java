package com.partner.entity;

import org.greenrobot.greendao.annotation.*;

@Entity
public class Partner {

    @Id
    private Long id;
    private String type;
    private String date;
    private String time;
    private String sleep;
    private String lightsleep;
    private String sleeping;
    private String awake;

    @Generated(hash = 40105147)
    public Partner() {
    }

    public Partner(Long id) {
        this.id = id;
    }

    @Generated(hash = 1995054546)
    public Partner(Long id, String type, String date, String time, String sleep, String lightsleep, String sleeping, String awake) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.time = time;
        this.sleep = sleep;
        this.lightsleep = lightsleep;
        this.sleeping = sleeping;
        this.awake = awake;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public String getLightsleep() {
        return lightsleep;
    }

    public void setLightsleep(String lightsleep) {
        this.lightsleep = lightsleep;
    }

    public String getSleeping() {
        return sleeping;
    }

    public void setSleeping(String sleeping) {
        this.sleeping = sleeping;
    }

    public String getAwake() {
        return awake;
    }

    public void setAwake(String awake) {
        this.awake = awake;
    }

}
