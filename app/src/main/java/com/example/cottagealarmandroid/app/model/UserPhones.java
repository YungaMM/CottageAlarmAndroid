package com.example.cottagealarmandroid.app.model;


public class UserPhones {
    private final String NAME_PREFS_USER_PHONE;
    private final String NAME_PREFS_OPTIONS;

    private final int count;
    private String phone;
    private String[] option = {"0", "0", "0", "0", "0", "2", "0"};

    public UserPhones(final int count) {
        NAME_PREFS_USER_PHONE = "userPhone" + count;
        NAME_PREFS_OPTIONS = "set" + NAME_PREFS_USER_PHONE;
        this.count = count;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String[] getOption() {
        return option;
    }

    public String getOptionString (){
        StringBuilder result = new StringBuilder();
        for (String anOption : option) {
            result.append(anOption);
        }
        return result.toString();
    }

    public String getOptionOnCount(final int count){
        return option[count];
    }

    public void setOption(String[] option) {
        this.option = option;
    }

    public void setOptionFromString(final String option){
        for (int i = 0; i < option.length(); i++) {
            this.option[i] = String.valueOf(option.charAt(i));
        }
    }

    public int getCount() {
        return count;
    }

    public void setOption(final int count, final String option){
        this.option[count] = option;
    }

    public String getNAME_PREFS_USER_PHONE() {
        return NAME_PREFS_USER_PHONE;
    }

    public String getNAME_PREFS_OPTIONS() {
        return NAME_PREFS_OPTIONS;
    }
}

