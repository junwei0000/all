package com.longcheng.lifecareplan.modular.home.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/23 17:44
 * 意图：
 */

public class HomeAfterBean implements Serializable {

    private HomeItemBean userInfo;
    private String runDay;
    private String hz_people;
    private String hz_ability_sum;
    private HomeItemBean current_jieqi;
    private List<String> top_msgs;
    private List<HomeItemBean> newpu;
    private List<HomeItemBean> rankingData;
    private List<HomeItemBean> actions;
    private List<HomeItemBean> msg;

    private List<HomeItemBean> icons;
    private List<HomeItemBean> banners;

    private String invite_user_url;
    private String sign_url;
    private String kn_url;
    private int  is_show_knp;


    public int getIs_show_knp() {
        return is_show_knp;
    }

    public void setIs_show_knp(int is_show_knp) {
        this.is_show_knp = is_show_knp;
    }

    public String getKn_url() {
        return kn_url;
    }

    public void setKn_url(String kn_url) {
        this.kn_url = kn_url;
    }

    public List<HomeItemBean> getBanners() {
        return banners;
    }

    public void setBanners(List<HomeItemBean> banners) {
        this.banners = banners;
    }

    public List<HomeItemBean> getIcons() {
        return icons;
    }

    public void setIcons(List<HomeItemBean> icons) {
        this.icons = icons;
    }

    public String getSign_url() {
        return sign_url;
    }

    public void setSign_url(String sign_url) {
        this.sign_url = sign_url;
    }

    public String getInvite_user_url() {
        return invite_user_url;
    }

    public void setInvite_user_url(String invite_user_url) {
        this.invite_user_url = invite_user_url;
    }

    public HomeItemBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(HomeItemBean userInfo) {
        this.userInfo = userInfo;
    }

    public String getRunDay() {
        return runDay;
    }

    public void setRunDay(String runDay) {
        this.runDay = runDay;
    }

    public String getHz_people() {
        return hz_people;
    }

    public void setHz_people(String hz_people) {
        this.hz_people = hz_people;
    }

    public String getHz_ability_sum() {
        return hz_ability_sum;
    }

    public void setHz_ability_sum(String hz_ability_sum) {
        this.hz_ability_sum = hz_ability_sum;
    }

    public HomeItemBean getCurrent_jieqi() {
        return current_jieqi;
    }

    public void setCurrent_jieqi(HomeItemBean current_jieqi) {
        this.current_jieqi = current_jieqi;
    }

    public List<String> getTop_msgs() {
        return top_msgs;
    }

    public void setTop_msgs(List<String> top_msgs) {
        this.top_msgs = top_msgs;
    }

    public List<HomeItemBean> getNewpu() {
        return newpu;
    }

    public void setNewpu(List<HomeItemBean> newpu) {
        this.newpu = newpu;
    }

    public List<HomeItemBean> getRankingData() {
        return rankingData;
    }

    public void setRankingData(List<HomeItemBean> rankingData) {
        this.rankingData = rankingData;
    }

    public List<HomeItemBean> getActions() {
        return actions;
    }

    public void setActions(List<HomeItemBean> actions) {
        this.actions = actions;
    }

    public List<HomeItemBean> getMsg() {
        return msg;
    }

    public void setMsg(List<HomeItemBean> msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "HomeAfterBean{" +
                "runDay='" + runDay + '\'' +
                ", hz_people='" + hz_people + '\'' +
                ", hz_ability_sum='" + hz_ability_sum + '\'' +
                ", current_jieqi=" + current_jieqi +
                ", top_msgs=" + top_msgs +
                ", newpu=" + newpu +
                ", rankingData=" + rankingData +
                ", actions=" + actions +
                ", msg=" + msg +
                '}';
    }
}