package com.example.android.hawkeye;

public class Society
{
    private String societyName;
    private long latitude;
    private long longitude;
    private String address;
    private long parkSlots;

    public Society(){}
    //add getter setters.


    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getParkSlots() {
        return parkSlots;
    }

    public void setParkSlots(long parkSlots) {
        this.parkSlots = parkSlots;
    }
}

