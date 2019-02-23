package com.example.android.hawkeye;

import java.util.Date;

public class Client {
    protected String fname;
    protected String lname;
    protected Date dob;
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected String email;
    protected String gender;
    protected String society_info;
    protected String raddress;
    protected String aadharno;
    protected String password;
    protected String pno;
    protected String desc;
    protected String user_key;
    protected boolean cl_status;

    public boolean getCl_status() {
        return cl_status;
    }

    public void setCl_status(boolean cl_status) {
        this.cl_status = cl_status;
    }


    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
//        try {
//            this.dob = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
//        }catch (Exception e){
//            System.out.println(e.toString());
//            this.dob = new GregorianCalendar(2019,02,30,10,30).getTime();
//        }
        this.dob=dob;
    }

    public String getSociety_info() {
        return society_info;
    }

    public void setSociety_info(String society_info) {
        this.society_info = society_info;
    }

    public Client()
    {

    }
    public Client(String fname, String lname, String email, String gender, String raddress, String aadharno,Date d,String society_info,String password,String desc,String pno,String id,String user_key,boolean cl_status) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.gender = gender;
        this.raddress = raddress;
        this.aadharno = aadharno;
        this.society_info=society_info;
//        try {
//            this.dob = new SimpleDateFormat("dd/MM/yyyy").parse(d);
//        }catch (Exception e){
//            System.out.println(e.toString());
//            this.dob = new GregorianCalendar(2019,02,30,10,30).getTime();
//        }
        this.dob=d;
        this.password=password;
        this.desc=desc;
        this.pno=pno;
        this.id=id;
        this.user_key=user_key;
        this.cl_status=cl_status;
    }


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRaddress() {
        return raddress;
    }

    public void setRaddress(String raddress) {
        this.raddress = raddress;
    }

    public String getAadharno() {
        return aadharno;
    }

    public void setAadharno(String aadharno) {
        this.aadharno = aadharno;
    }


}
