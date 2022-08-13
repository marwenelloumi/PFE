package com.example.kheerMaro.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Plant {
    private String name , userEmail , description;
   @SerializedName("_id")
    private String id ;
    private boolean modeAuto ;
    private Date nextIrrDate;
    private double nextIrrQuan, irrQuan;
    private Integer timeMin,timeSec;

    public Plant() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isModeAuto() {
        return modeAuto;
    }

    public void setModeAuto(boolean modeAuto) {
        this.modeAuto = modeAuto;
    }

    public Date getNextIrrDate() {
        return nextIrrDate;
    }

    public void setNextIrrDate(Date nextIrrDate) {
        this.nextIrrDate = nextIrrDate;
    }

    public double getNextIrrQuan() {
        return nextIrrQuan;
    }

    public void setNextIrrQuan(double nextIrrQuan) {
        this.nextIrrQuan = nextIrrQuan;
    }

    public double getIrrQuan() {
        return irrQuan;
    }

    public void setIrrQuan(double irrQuan) {
        this.irrQuan = irrQuan;
    }

    public Integer getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(Integer timeMin) {
        this.timeMin = timeMin;
    }

    public Integer getTimeSec() {
        return timeSec;
    }

    public void setTimeSec(Integer timeSec) {
        this.timeSec = timeSec;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
