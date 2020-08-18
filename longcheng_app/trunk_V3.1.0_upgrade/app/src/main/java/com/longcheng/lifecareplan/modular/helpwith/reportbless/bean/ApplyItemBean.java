package com.longcheng.lifecareplan.modular.helpwith.reportbless.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class ApplyItemBean implements Serializable {
    private String jieqi;
    private String user_img;
    private String user_name;
    private String super_ability;
    private ArrayList<String> identity_img;


    private String solar_terms_name;
    private String solar_terms_en;
    private String solar_terms_img;

    public String getJieqi() {
        return jieqi;
    }

    public void setJieqi(String jieqi) {
        this.jieqi = jieqi;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
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

    public ArrayList<String> getIdentity_img() {
        return identity_img;
    }

    public void setIdentity_img(ArrayList<String> identity_img) {
        this.identity_img = identity_img;
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

    public String getSolar_terms_img() {
        return solar_terms_img;
    }

    public void setSolar_terms_img(String solar_terms_img) {
        this.solar_terms_img = solar_terms_img;
    }
}