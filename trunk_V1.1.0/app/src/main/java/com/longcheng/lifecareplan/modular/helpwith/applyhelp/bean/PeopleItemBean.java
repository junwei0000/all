package com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：jun on
 * 时间：2018/8/23 17:44
 * 意图：
 */

public class PeopleItemBean {

    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("call_user")
    private String call_user;//关系
    @SerializedName("allow_help")
    private String allow_help;
    @SerializedName("avatar")
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCall_user() {
        return call_user;
    }

    public void setCall_user(String call_user) {
        this.call_user = call_user;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAllow_help() {
        return allow_help;
    }

    public void setAllow_help(String allow_help) {
        this.allow_help = allow_help;
    }

    @Override
    public String toString() {
        return "HomeAfterBean{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", phone='" + phone + '\'' +
                ", allow_help='" + allow_help + '\'' +
                '}';
    }
}