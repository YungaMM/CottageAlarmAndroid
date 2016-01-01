package com.example.cottagealarmandroid.app.model;

import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;

public class BasicAlarmProperty {
    public static final String NAME_PREFS_ALARM_PHONE="alarmPhone";
    public static final String NAME_PREFS_DATE_IN_DEVICE="dateInDevice";
    public static final String NAME_PREFS_TIME_IN_DEVICE="timeInDevice";
    public static final String NAME_PREFS_ENERGY = "energy";

    private String alarmPhone; //Номер телефона сигнализации

    private UserPhones[] userPhones = new UserPhones[5]; //Номера телефонов забитые в сигнадлизации

    private String dateInDevice; //дата установленная в устройстве

    private String timeInDevice; //время в устройстве

    private boolean energy;      //питание от электросети

    public BasicAlarmProperty(){
        alarmPhone = AdvancePreferences.getProperty(NAME_PREFS_ALARM_PHONE);
        dateInDevice = AdvancePreferences.getProperty(NAME_PREFS_DATE_IN_DEVICE);
        timeInDevice = AdvancePreferences.getProperty(NAME_PREFS_TIME_IN_DEVICE);
        energy = AdvancePreferences.getBooleanProperty(NAME_PREFS_ENERGY);

        for (int i = 0; i < userPhones.length ; i++) {
            userPhones[i] = new UserPhones(i,"");
            userPhones[i].setPhone(AdvancePreferences.getProperty(userPhones[i].NAME_PREFS_USER_PHONE));
        }
    }


//    public BasicAlarmProperty(final String alarmPhone,
//                              final UserPhones[] userPhones,
//                              final String dateInDevice,
//                              final String timeInDevice) {
//        this.alarmPhone = alarmPhone;
//        this.userPhones = userPhones;
//        this.dateInDevice = dateInDevice;
//        this.timeInDevice = timeInDevice;
//    }

    public String getAlarmPhone() {
        return alarmPhone;
    }

    public void setAlarmPhone(final String alarmPhone) {
        this.alarmPhone = alarmPhone;
    }

    public String  getUserPhoneNumber(final int count) {
        return userPhones[count].getPhone();
    }

//    public UserPhones getUserPhoneNumber(final int count) {
//        return userPhones[count];
//    }
    public UserPhones[] getUserPhones(){return userPhones;}

    public String[] getStringArrayPhones(){
        String[] result = new String[userPhones.length];
        for (int i = 0; i < userPhones.length ; i++) {
            result[i] = userPhones[i].getPhone();
        }
        return result;
    }


    public void setUserPhones(final UserPhones[] userPhones) {
        this.userPhones = userPhones;
    }


    public void setUserPhone(final int count, final String userPhones) {
        this.userPhones[count].setPhone(userPhones);
    }

    public String getDateInDevice() {
        return dateInDevice;
    }

    public void setDateInDevice(final String dateInDevice) {
        this.dateInDevice = dateInDevice;
    }

    public String getTimeInDevice() {
        return timeInDevice;
    }

    public void setTimeInDevice(String timeInDevice) {
        this.timeInDevice = timeInDevice;
    }

    public boolean isEnergy() {
        return energy;
    }

    public void setEnergy(boolean energy) {
        this.energy = energy;
    }

}
