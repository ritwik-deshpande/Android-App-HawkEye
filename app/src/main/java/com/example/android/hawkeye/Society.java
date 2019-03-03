package com.example.android.hawkeye;

public class Society
{
    private String societyName;
    private double latitude;
    private double longitude;
    private String address;
    private int parkSlots;
    private int Max;

    public void setParkSlots(int parkSlots) {
        this.parkSlots = parkSlots;
    }

    public int getMax() {
        return Max;
    }

    public void setMax(int max) {
        Max = max;
    }

    public Society(){}
    //add getter setters.


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getParkSlots() {
        return parkSlots;
    }


}

