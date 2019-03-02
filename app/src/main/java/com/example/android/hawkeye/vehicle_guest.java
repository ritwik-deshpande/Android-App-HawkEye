package com.example.android.hawkeye;

import java.util.Date;

public class vehicle_guest extends Vehicle {
    Date atime;
    int duration;

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    String sdate;

    public vehicle_guest(String userid, String vehicle_company, String vehicle_model, String colour, String reg_number,String society_info, Date atime, int duration,String sdate) {
        super(userid, vehicle_company, vehicle_model, colour, reg_number,society_info);
        this.atime = atime;
        this.duration = duration;
        this.sdate=sdate;
    }

    public Date getAtime() {

        return atime;
    }

    public void setAtime(Date atime) {
        this.atime = atime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public vehicle_guest(){

    }

}
