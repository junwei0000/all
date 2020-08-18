package com.longcheng.lifecareplan.modular.helpwith.reportbless.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class ReportItemBean implements Serializable {
    private String show_name;
    private String show_number;
    private String show_en;


    private String avatar;
    private String user_name;
    private String super_ability;
    private String branch_info;
    private ArrayList<ReportItemBean> identity_flag;
    private String image;


    private String bless_id;
    private String solar_term_cn;
    private String bless_card_number;
    private String time;
    private String user_money;
    @SerializedName("show_status")
    private int status;//1 在途  ； 2 未领   ；  3 已领


    public String getShow_en() {
        return show_en;
    }

    public void setShow_en(String show_en) {
        this.show_en = show_en;
    }

    public String getBless_id() {
        return bless_id;
    }

    public void setBless_id(String bless_id) {
        this.bless_id = bless_id;
    }


    public String getSolar_term_cn() {
        return solar_term_cn;
    }

    public void setSolar_term_cn(String solar_term_cn) {
        this.solar_term_cn = solar_term_cn;
    }

    public String getBless_card_number() {
        return bless_card_number;
    }

    public void setBless_card_number(String bless_card_number) {
        this.bless_card_number = bless_card_number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBranch_info() {
        return branch_info;
    }

    public void setBranch_info(String branch_info) {
        this.branch_info = branch_info;
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

    public String getSuper_ability() {
        return super_ability;
    }

    public void setSuper_ability(String super_ability) {
        this.super_ability = super_ability;
    }

    public ArrayList<ReportItemBean> getIdentity_flag() {
        return identity_flag;
    }

    public void setIdentity_flag(ArrayList<ReportItemBean> identity_flag) {
        this.identity_flag = identity_flag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShow_name() {
        return show_name;
    }

    public void setShow_name(String show_name) {
        this.show_name = show_name;
    }

    public String getShow_number() {
        return show_number;
    }

    public void setShow_number(String show_number) {
        this.show_number = show_number;
    }
}