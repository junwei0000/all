package com.longcheng.lifecareplan.modular.helpwith.reportbless.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class ListItemBean implements Serializable {
    private String bless_card_id;
    private String bless_id;
    private String solar_terms_name;
    private String solar_terms_en;
    private String solar_terms_en_rgb;
    private String solar_terms_en_pic;
    private String bless_card_number;
    private String desc;
    private int status;
    private int over_time;

    private String user_name;
    private String avatar;
    @SerializedName(value = "count", alternate = {"apply_number"})
    private String apply_number;

    public String getBless_id() {
        return bless_id;
    }

    public void setBless_id(String bless_id) {
        this.bless_id = bless_id;
    }

    public String getBless_card_id() {
        return bless_card_id;
    }

    public void setBless_card_id(String bless_card_id) {
        this.bless_card_id = bless_card_id;
    }

    public String getSolar_terms_name() {
        return solar_terms_name;
    }

    public void setSolar_terms_name(String solar_terms_name) {
        this.solar_terms_name = solar_terms_name;
    }

    public String getSolar_terms_en() {
        return solar_terms_en;
    }

    public void setSolar_terms_en(String solar_terms_en) {
        this.solar_terms_en = solar_terms_en;
    }

    public String getSolar_terms_en_rgb() {
        return solar_terms_en_rgb;
    }

    public void setSolar_terms_en_rgb(String solar_terms_en_rgb) {
        this.solar_terms_en_rgb = solar_terms_en_rgb;
    }

    public String getSolar_terms_en_pic() {
        return solar_terms_en_pic;
    }

    public void setSolar_terms_en_pic(String solar_terms_en_pic) {
        this.solar_terms_en_pic = solar_terms_en_pic;
    }

    public String getBless_card_number() {
        return bless_card_number;
    }

    public void setBless_card_number(String bless_card_number) {
        this.bless_card_number = bless_card_number;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOver_time() {
        return over_time;
    }

    public void setOver_time(int over_time) {
        this.over_time = over_time;
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

    public String getApply_number() {
        return apply_number;
    }

    public void setApply_number(String apply_number) {
        this.apply_number = apply_number;
    }
}