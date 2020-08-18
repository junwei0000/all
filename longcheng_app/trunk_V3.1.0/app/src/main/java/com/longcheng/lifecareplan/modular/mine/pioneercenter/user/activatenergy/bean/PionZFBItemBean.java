package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionZFBItemBean implements Serializable {
    private String avatar;
    private String user_name;
    private String create_time;
    private String price;
    private String jieqi_name;
    private ArrayList<String> identity;
    private String entrepreneurs_zhufubao_order_id;


    public String getEntrepreneurs_zhufubao_order_id() {
        return entrepreneurs_zhufubao_order_id;
    }

    public void setEntrepreneurs_zhufubao_order_id(String entrepreneurs_zhufubao_order_id) {
        this.entrepreneurs_zhufubao_order_id = entrepreneurs_zhufubao_order_id;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getJieqi_name() {
        return jieqi_name;
    }

    public void setJieqi_name(String jieqi_name) {
        this.jieqi_name = jieqi_name;
    }

    public ArrayList<String> getIdentity() {
        return identity;
    }

    public void setIdentity(ArrayList<String> identity) {
        this.identity = identity;
    }

}
