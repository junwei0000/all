package com.longcheng.volunteer.modular.mine.relationship.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Burning on 2018/9/1.
 */

public class RelationshipUser {
    @SerializedName("is_cho")
    int is_cho;
    @SerializedName("register_day")
    int registerDays;
    @SerializedName("user_name")
    String user_name;
    @SerializedName("register_time")
    String register_time;
    @SerializedName("cho_start_date")
    String cho_start_date;


    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getCho_start_date() {
        return cho_start_date;
    }

    public void setCho_start_date(String cho_start_date) {
        this.cho_start_date = cho_start_date;
    }

    public int getIs_cho() {
        return is_cho;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getRegisterDays() {
        return registerDays;
    }

    public void setRegisterDays(int registerDays) {
        this.registerDays = registerDays;
    }

    public boolean isCHO() {
        return is_cho == 1;
    }

    public void setIs_cho(int is_cho) {
        this.is_cho = is_cho;
    }
}
