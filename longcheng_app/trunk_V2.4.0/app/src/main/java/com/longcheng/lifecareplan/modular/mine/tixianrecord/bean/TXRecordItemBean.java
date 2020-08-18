package com.longcheng.lifecareplan.modular.mine.tixianrecord.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class TXRecordItemBean implements Serializable {
    private int position;
    private String user_zhufubao_order_id;
    private int show_type;//1显示倒计时 2显示未接单 3进行中  4已完成
    @SerializedName("customer_time")
    private long match_time;//截止时间
    private String price;
    private String sponsor_user_id;
    private String sponsor_user_name;
    private String sponsor_user_avatar;
    private String sponsor_user_jieqi_name;
    private ArrayList<TXRecordItemBean> sponsor_user_flag;
    private String image;

    private String bless_user_id;
    private String bless_user_name;
    private String bless_user_avatar;
    private String bless_user_jieqi_name;
    private ArrayList<TXRecordItemBean> bless_user_flag;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUser_zhufubao_order_id() {
        return user_zhufubao_order_id;
    }

    public void setUser_zhufubao_order_id(String user_zhufubao_order_id) {
        this.user_zhufubao_order_id = user_zhufubao_order_id;
    }

    public int getShow_type() {
        return show_type;
    }

    public void setShow_type(int show_type) {
        this.show_type = show_type;
    }

    public long getMatch_time() {
        return match_time;
    }

    public void setMatch_time(long match_time) {
        this.match_time = match_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSponsor_user_id() {
        return sponsor_user_id;
    }

    public void setSponsor_user_id(String sponsor_user_id) {
        this.sponsor_user_id = sponsor_user_id;
    }

    public String getSponsor_user_name() {
        return sponsor_user_name;
    }

    public void setSponsor_user_name(String sponsor_user_name) {
        this.sponsor_user_name = sponsor_user_name;
    }

    public String getSponsor_user_avatar() {
        return sponsor_user_avatar;
    }

    public void setSponsor_user_avatar(String sponsor_user_avatar) {
        this.sponsor_user_avatar = sponsor_user_avatar;
    }

    public String getSponsor_user_jieqi_name() {
        return sponsor_user_jieqi_name;
    }

    public void setSponsor_user_jieqi_name(String sponsor_user_jieqi_name) {
        this.sponsor_user_jieqi_name = sponsor_user_jieqi_name;
    }

    public ArrayList<TXRecordItemBean> getSponsor_user_flag() {
        return sponsor_user_flag;
    }

    public void setSponsor_user_flag(ArrayList<TXRecordItemBean> sponsor_user_flag) {
        this.sponsor_user_flag = sponsor_user_flag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBless_user_id() {
        return bless_user_id;
    }

    public void setBless_user_id(String bless_user_id) {
        this.bless_user_id = bless_user_id;
    }

    public String getBless_user_name() {
        return bless_user_name;
    }

    public void setBless_user_name(String bless_user_name) {
        this.bless_user_name = bless_user_name;
    }

    public String getBless_user_avatar() {
        return bless_user_avatar;
    }

    public void setBless_user_avatar(String bless_user_avatar) {
        this.bless_user_avatar = bless_user_avatar;
    }

    public String getBless_user_jieqi_name() {
        return bless_user_jieqi_name;
    }

    public void setBless_user_jieqi_name(String bless_user_jieqi_name) {
        this.bless_user_jieqi_name = bless_user_jieqi_name;
    }

    public ArrayList<TXRecordItemBean> getBless_user_flag() {
        return bless_user_flag;
    }

    public void setBless_user_flag(ArrayList<TXRecordItemBean> bless_user_flag) {
        this.bless_user_flag = bless_user_flag;
    }
}