package com.example.cottagealarmandroid.app.model;

public class Relay {

    private final String NAME_PREFS_RELAY;
    private final String NAME_PREFS_OPTION;
    private final String NAME_PREFS_MODE_CONTROL;

    private static final String COMMAND_OFF = "#R%d=0";
    private static final String COMMAND_ON = "#R%d=1%s";

    private final int count;
    private String name;
    private String modeControl;
    private String option;


    public Relay(final int count) {
        NAME_PREFS_RELAY = "РЕЛЕ"+count;
        NAME_PREFS_OPTION = "ОпцииРЕЛЕ" + count;
        NAME_PREFS_MODE_CONTROL = "РежимУправленияРЕЛЕ" + count;
        this.count = count;
    }


    public String getModeControl() {
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

    public String getOption() {
        if(option.equals("")) option = "0";
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getNAME_PREFS_RELAY() {
        return NAME_PREFS_RELAY;
    }

    public String getNAME_PREFS_OPTION() {
        return NAME_PREFS_OPTION;
    }

    public String getNAME_PREFS_MODE_CONTROL() {
        return NAME_PREFS_MODE_CONTROL;
    }

    //    public void setSwitchOnOff(final boolean switchOnOff) {
//        this.switchOnOff = switchOnOff;
//    }
//
//    public void setSwitchOnOff(final boolean switchOnOff, final String option) {
//        this.switchOnOff = switchOnOff;
//        this.option = option;
//    }
//
//    public boolean isSwitchOnOff() {
//        return switchOnOff;
//    }
}
