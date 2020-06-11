package com.longcheng.lifecareplan.modular.mine.cashflow.bean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class CashFlowItemBean {
    int position;
    private String money;
    private String user_name;
    private String avatar;
    private String jieqi_name;
    private String userMoney;
    ArrayList<String> identity_img;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public String getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(String userMoney) {
        this.userMoney = userMoney;
    }

    public ArrayList<String> getIdentity_img() {
        return identity_img;
    }

    public void setIdentity_img(ArrayList<String> identity_img) {
        this.identity_img = identity_img;
    }
}
