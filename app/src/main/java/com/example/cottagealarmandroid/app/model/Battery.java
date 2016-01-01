package com.example.cottagealarmandroid.app.model;

public class Battery {

    public static final String NAME_PREFS_BATTERY = "battery";

    private  double battery; //пишем заряд батареи

    public Battery(final double battery) {
        this.battery = battery;
    }

    public  double getBattery() {
        return battery;
    }

    public  void setBattery(final double battery) {
        this.battery = battery;
    }
}
