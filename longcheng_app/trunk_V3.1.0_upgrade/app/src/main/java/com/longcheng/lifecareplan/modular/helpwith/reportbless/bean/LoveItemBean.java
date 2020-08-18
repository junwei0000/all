package com.longcheng.lifecareplan.modular.helpwith.reportbless.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class LoveItemBean implements Serializable {
    private String jieqi;
    private String user_name;
    private String avatar;
    String user_id;


    private String abilities;
    private String flora;
    private String tone;
    private ArrayList<String> identity;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getJieqi() {
        return jieqi;
    }

    public void setJieqi(String jieqi) {
        this.jieqi = jieqi;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public String getFlora() {
        return flora;
    }

    public void setFlora(String flora) {
        this.flora = flora;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public ArrayList<String> getIdentity() {
        return identity;
    }

    public void setIdentity(ArrayList<String> identity) {
        this.identity = identity;
    }
}