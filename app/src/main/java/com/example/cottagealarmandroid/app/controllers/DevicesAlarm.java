package com.example.cottagealarmandroid.app.controllers;

import com.example.cottagealarmandroid.app.model.*;

public class DevicesAlarm extends SmsCommandsAlarm {
    private static DevicesAlarm instance;

    public BasicAlarmProperty basicAlarmProperty; //номер тел. сигнализ., номера тел. юзера, дата устройства

    private Temperature temperature; //Температура

    private Battery battery; //Заряд батареи

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

//        this.basicAlarmProperty = new BasicAlarmProperty(AdvancePreferences.getAlarmPhone(),
//                AdvancePreferences.getUserPhones(),
//                AdvancePreferences.getDateInDevice(),
//                AdvancePreferences.getTimeInDevice());
//        this.temperature = new Temperature(AdvancePreferences.getTemperature());
//        this.battery = new Battery(AdvancePreferences.getBattery());
//        this.relays = AdvancePreferences.getRelays();
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
            return relays[count - 1];
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
