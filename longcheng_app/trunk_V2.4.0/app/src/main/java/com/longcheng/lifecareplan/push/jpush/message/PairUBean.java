package com.longcheng.lifecareplan.push.jpush.message;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PairUBean {
    @SerializedName(value = "zhufubao", alternate = {"fuqibao_limit"})
    double money_limit;

    private ArrayList<PairUBean> queue_items;
    @SerializedName(value = "user_zhufubao_order_id", alternate = {"entrepreneurs_zhufubao_order_id"})
    private String user_zhufubao_order_id;
    private String avatar;
    private String user_name;
    private String jieqi_name;
    private ArrayList<String> img_all;
    private String money;

    public double getMoney_limit() {
        return money_limit;
    }

    public void setMoney_limit(double money_limit) {
        this.money_limit = money_limit;
    }

    public String getUser_zhufubao_order_id() {
        return user_zhufubao_order_id;
    }

    public void setUser_zhufubao_order_id(String user_zhufubao_order_id) {
        this.user_zhufubao_order_id = user_zhufubao_order_id;
    }

    public ArrayList<PairUBean> getQueue_items() {
        return queue_items;
    }

    public void setQueue_items(ArrayList<PairUBean> queue_items) {
        this.queue_items = queue_items;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getJieqi_name() {
        return jieqi_name;
    }

    public void setJieqi_name(String jieqi_name) {
        this.jieqi_name = jieqi_name;
    }

    public ArrayList<String> getImg_all() {
        return img_all;
    }

    public void setImg_all(ArrayList<String> img_all) {
        this.img_all = img_all;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "PairUBean{" +
                "user_zhufubao_order_id='" + user_zhufubao_order_id + '\'' +
                ", avatar='" + avatar + '\'' +
                ", user_name='" + user_name + '\'' +
                ", jieqi_name='" + jieqi_name + '\'' +
                ", img_all=" + img_all +
                ", money='" + money + '\'' +
                '}';
    }
}
