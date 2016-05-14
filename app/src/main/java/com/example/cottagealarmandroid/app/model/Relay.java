package com.example.cottagealarmandroid.app.model;

import com.example.cottagealarmandroid.app.controllers.AdvancePreferences;

public class Relay {

    private final String NAME_PREFS_RELAY;
    private final String NAME_PREFS_SMS;
    private final String NAME_PREFS_MODE_CONTROL;

    private static final String COMMAND_OFF = "#R%d=0";
    private static final String COMMAND_ON = "#R%d=1%s";

    private final int count;
    private String name;
    private String modeControl;
    private String smsCommand;


    public Relay(final int count) {
        NAME_PREFS_RELAY = "РЕЛЕ"+count;
        NAME_PREFS_SMS = "Sms" + count;
        NAME_PREFS_MODE_CONTROL = "РежимУправленияРЕЛЕ" + count;
        this.count = count;
//        name = AdvancePreferences.getProperty(NAME_PREFS_RELAY);
//        modeControl = AdvancePreferences.getProperty(NAME_PREFS_MODE_CONTROL);
//        smsCommand = AdvancePreferences.getProperty(NAME_PREFS_SMS);
    }


    public String getModeControl() {
        if (modeControl.equals("")) modeControl = "0";
        return modeControl;
    }

    public void setModeControl(String modeControl) {
        this.modeControl = modeControl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public String getSmsCommand() {
        if(smsCommand.equals("")) smsCommand = "0";
        return smsCommand;
    }

    public void setSmsCommand(String smsCommand, int modeControl) {
        AdvancePreferences.addProperty(getNAME_PREFS_SMS(), smsCommand);
        setModeControl(String.valueOf(modeControl));
        AdvancePreferences.addProperty(getNAME_PREFS_MODE_CONTROL(), getModeControl());
        this.smsCommand = smsCommand;
    }
   public void setSmsCommand(String smsCommand) {
        this.smsCommand = smsCommand;
    }

    public String getNAME_PREFS_RELAY() {
        return NAME_PREFS_RELAY;
    }

    public String getNAME_PREFS_SMS() {
        return NAME_PREFS_SMS;
    }

    public String getNAME_PREFS_MODE_CONTROL() {
        return NAME_PREFS_MODE_CONTROL;
    }

}
