package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class SelectItemBean {
    int position;
    int showSelect;//0 不显示；1 显示
    int surplus_number;

    String phone;
    String user_id;
    String user_name;
    String avatar;
    String jieqi_name;
    String entrepreneurs_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getShowSelect() {
        return showSelect;
    }

    public void setShowSelect(int showSelect) {
        this.showSelect = showSelect;
    }

    public int getSurplus_number() {
        return surplus_number;
    }

    public void setSurplus_number(int surplus_number) {
        this.surplus_number = surplus_number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getJieqi_name() {
        return jieqi_name;
    }

    public void setJieqi_name(String jieqi_name) {
        this.jieqi_name = jieqi_name;
    }

    public String getEntrepreneurs_id() {
        return entrepreneurs_id;
    }

    public void setEntrepreneurs_id(String entrepreneurs_id) {
        this.entrepreneurs_id = entrepreneurs_id;
    }
}
