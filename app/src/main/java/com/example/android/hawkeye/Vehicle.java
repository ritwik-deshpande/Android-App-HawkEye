package com.example.android.hawkeye;

public class Vehicle {
    String userid;
    String vehicle_company;
    String vehicle_model;
    String colour;
    String reg_number;
    String image_url;
    String s_info;

    public Vehicle() {
    }

    public Vehicle(String userid, String vehicle_company, String vehicle_model, String colour, String reg_number,String society_info) {
        this.userid = userid;
        this.vehicle_company = vehicle_company;
        this.vehicle_model = vehicle_model;
        this.colour = colour;
        this.reg_number = reg_number;
        this.s_info= society_info;
        //this.image_url = image_url;
    }
    public String getS_info() {
        return s_info;
    }

    public void setS_info(String society_info) {
        this.s_info = society_info;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getVehicle_company() {
        return vehicle_company;
    }

    public void setVehicle_company(String vehicle_company) {
        this.vehicle_company = vehicle_company;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }

//    public String getImage_url() {
//        return image_url;
//    }
//
//    public void setImage_url(String image_url) {
//        this.image_url = image_url;
//    }



}
