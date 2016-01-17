package com.example.cottagealarmandroid.app.controllers;

import com.example.cottagealarmandroid.app.model.*;

public class DevicesAlarm extends SmsCommandsAlarm {
    private static DevicesAlarm instance;

    public BasicAlarmProperty basicAlarmProperty; //номер тел. сигнализ., номера тел. юзера, дата устройства

    private Temperature temperature; //Температура

    private Battery battery; //Заряд батареи
    //сделать не массивом, а ArrayList например, чтобы можно было добавлять реле,
// но не больше 6 шт в общем
    public final static int COUNT_RELAY = 6;
    private Relay[] relays;  //список реле

//    private Energy energy; //электроэнергия вкл/выкл

    public static DevicesAlarm getInstance() {
        if (instance==null) {
            instance = new DevicesAlarm();
        }
        return instance;
    }

    //устанавливаем DevicesAlarm в соответствии с сохраненными установками
    // в Андроид конструктор переделать чтобы считывал установки из SharedPreferences Андроида
    private DevicesAlarm() {
        this.basicAlarmProperty = new BasicAlarmProperty();
        this.relays = new Relay[COUNT_RELAY];

//        this.temperature = new Temperature(AdvancePreferences.getTemperature());
//        this.battery = new Battery(AdvancePreferences.getBattery());
//        this.energy = new Energy(AdvancePreferences.isEnergy());

    }

    public BasicAlarmProperty getBasicAlarmProperty() {
        return basicAlarmProperty;
    }

    public void setBasicAlarmProperty(BasicAlarmProperty basicAlarmProperty) {
        this.basicAlarmProperty = basicAlarmProperty;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public Relay[] getRelays() {
        return relays;
    }

    public Relay getRelay(final int count) {
        return relays[count];
    }

    public void setRelays(Relay[] relays) {
        this.relays = relays;
    }

//    public Energy getEnergy() {
//        return energy;
//    }
//
//    public void setEnergy(Energy energy) {
//        this.energy = energy;
//    }
}
