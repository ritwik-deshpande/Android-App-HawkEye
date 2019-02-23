package com.example.android.hawkeye;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

class UtilFunctions {
    public String getUser_key(String email)
    {
        email = email.substring(0,email.indexOf('@'));
        email.replaceAll("[.]","");
        return email;
    }
    public String generate_newpassword(){
        Random rand = new Random();
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        String t = today.toString();
        t = t.split("\\s+")[2];
        long milisecond = today.getTime()%1000;
        long r = (rand.nextInt(999)+100)%1000;
        long id = ((long)Math.pow(r,2) +(long) Math.pow(milisecond,2)) %1000;
        String axisid = "HAWKEYE" + t + ((id < 100)?'0': ((id<10)?"00":"") )+ id;
        return axisid;
    }
    public String generate_id(String desc){
        Random rand = new Random();
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        String t = today.toString();
        t = t.split("\\s+")[2];
        long milisecond = today.getTime()%1000;
        long r = (rand.nextInt(999)+100)%1000;
        long id = ((long)Math.pow(r,2) +(long) Math.pow(milisecond,2)) %1000;
        String axisid = desc + t + ((id < 100)?'0': ((id<10)?"00":"") )+ id;
        return axisid;
    }
}
