package com.example.android.hawkeye;

import java.util.Date;

public class EntryLog {
    String rnum;
    String uid;
    Date timestamp;
    String dsc;
    String timestamp_dsc;
    String datetime;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    boolean exit_status;


    public boolean isExit_status() {
        return exit_status;
    }

    public void setExit_status(boolean exit_status) {
        this.exit_status = exit_status;
    }

    public String getTimestamp_dsc() {
        return timestamp_dsc;
    }

    public void setTimestamp_dsc(String timestamp_dsc) {
        this.timestamp_dsc = timestamp_dsc;
    }

    public EntryLog(){

    }

    public EntryLog(String rnum, String uid, Date timestamp, String dsc,String timestamp_dsc,boolean exit_status,String datetime) {
        this.rnum = rnum;
        this.uid = uid;
        this.timestamp = timestamp;
        this.dsc = dsc;
        this.timestamp_dsc=timestamp_dsc;
        this.exit_status=exit_status;
        this.datetime=datetime;
    }

    public String getRnum() {
        return rnum;

    }

    public void setRnum(String rnum) {
        this.rnum = rnum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }
}
