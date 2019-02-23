package com.example.android.hawkeye;

import java.util.Date;

public class EntryLog {
    String rnum;
    String uid;
    Date timestamp;
    String dsc;
    String timestamp_dsc;

    public String getTimestamp_dsc() {
        return timestamp_dsc;
    }

    public void setTimestamp_dsc(String timestamp_dsc) {
        this.timestamp_dsc = timestamp_dsc;
    }

    public EntryLog(){

    }

    public EntryLog(String rnum, String uid, Date timestamp, String dsc,String timestamp_dsc) {
        this.rnum = rnum;
        this.uid = uid;
        this.timestamp = timestamp;
        this.dsc = dsc;
        this.timestamp_dsc=timestamp_dsc;
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
