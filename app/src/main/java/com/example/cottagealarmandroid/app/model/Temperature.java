package com.example.cottagealarmandroid.app.model;

public class Temperature {
    public static final String NAME_PREFS_TEMPERATURE = "temperature";

    private int temperature;

    public Temperature(final int temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(final int temperature) {
        this.temperature = temperature;
    }
}
