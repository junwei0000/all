package com.longcheng.lifecareplan.modular.home.bangdan;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/23 17:44
 * 意图：
 */

public class BangDanAfterBean implements Serializable {

    private String date;
    private BangDanAfterBean userSelf;
    private List<BangDanAfterBean> blessExponent;

    private String ranking;
    private String user_name;
    private String avatar;
    private String jieqi_name;
    private String exponent;
    private List<String> identity_img;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getIdentity_img() {
        return identity_img;
    }

    public void setIdentity_img(List<String> identity_img) {
        this.identity_img = identity_img;
    }

    public BangDanAfterBean getUserSelf() {
        return userSelf;
    }

    public void setUserSelf(BangDanAfterBean userSelf) {
        this.userSelf = userSelf;
    }

    public List<BangDanAfterBean> getBlessExponent() {
        return blessExponent;
    }

    public void setBlessExponent(List<BangDanAfterBean> blessExponent) {
        this.blessExponent = blessExponent;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
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

    public String getExponent() {
        return exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }
}