package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class AcountItemBean implements Serializable {
    protected String avatar;
    protected String user_name;
    protected String money;

    @SerializedName(value = "user_zhufubao_order_id", alternate = {"entrepreneurs_zhufubao_order_id"})
    private String user_zhufubao_order_id;
    protected String current_user_avatar;
    protected String current_user_name;
    protected String current_user_phone;
    private  ArrayList<String> current_user_identity_images;
    protected String current_user_jieqi_name;
    protected String price;
    protected String create_time;




    protected int start;
    protected int end;
    protected String ratio;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getCurrent_user_phone() {
        return current_user_phone;
    }

    public void setCurrent_user_phone(String current_user_phone) {
        this.current_user_phone = current_user_phone;
    }

    public ArrayList<String> getCurrent_user_identity_images() {
        return current_user_identity_images;
    }

    public void setCurrent_user_identity_images(ArrayList<String> current_user_identity_images) {
        this.current_user_identity_images = current_user_identity_images;
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUser_zhufubao_order_id() {
        return user_zhufubao_order_id;
    }

    public void setUser_zhufubao_order_id(String user_zhufubao_order_id) {
        this.user_zhufubao_order_id = user_zhufubao_order_id;
    }

    public String getCurrent_user_avatar() {
        return current_user_avatar;
    }

    public void setCurrent_user_avatar(String current_user_avatar) {
        this.current_user_avatar = current_user_avatar;
    }

    public String getCurrent_user_name() {
        return current_user_name;
    }

    public void setCurrent_user_name(String current_user_name) {
        this.current_user_name = current_user_name;
    }

    public String getCurrent_user_jieqi_name() {
        return current_user_jieqi_name;
    }

    public void setCurrent_user_jieqi_name(String current_user_jieqi_name) {
        this.current_user_jieqi_name = current_user_jieqi_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

}
