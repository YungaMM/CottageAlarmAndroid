package com.example.cottagealarmandroid.app.model;


public class UserPhones {
    public String NAME_PREFS_USER_PHONE = "";
    public String NAME_PREFS_OPTIONS = "";

    private final int count;
    private String phone = "";
    private String[] option = {"0", "0", "0", "0", "0", "2", "0"};

    public UserPhones() {
        this.count = 0;
        this.phone = "";
    }

    public UserPhones(final int count, final String phone) {
        NAME_PREFS_USER_PHONE = "userPhone" + count;
        NAME_PREFS_OPTIONS = "set" + NAME_PREFS_USER_PHONE;
        this.count = count;
        this.phone = phone;
    }

//    public UserPhones(final int count, final String phone, final String[] option) {
//        NAME_PREFS_USER_PHONE = "userPhone" + count;
//        this.count = count;
//        this.phone = phone;
//        this.option = option;
//    }

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
        for (int i = 0; i < option.length ; i++) {
            result.append(option[i]);
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
}

