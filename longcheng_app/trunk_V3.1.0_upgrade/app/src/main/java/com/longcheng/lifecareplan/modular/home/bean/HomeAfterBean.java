package com.longcheng.lifecareplan.modular.home.bean;

import java.io.Serializable;
import java.util.ArrayList;
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
    private ArrayList<HomeItemBean> rankingData;
    private List<HomeItemBean> actions;
    private List<HomeItemBean> msg;

    private List<HomeItemBean> icons;
    private List<HomeItemBean> banners;

    private String invite_user_url;
    private String sign_url;
    private String kn_url;
    private int is_show_knp;

    private List<HomeItemBean> layer;

    private String display_note;

    private String activity_url;//活动url

    private String my_gratitude_url;
    private String my_dedication_url;


    int entreUserCashIngLayer;//敬售进行中
    int entreUserRechargeIngLayer;//请购进行中

    int isShowChoAbilityLayer;//0 不展示cho 能量9000的弹层， 1 展示。



    HomeItemBean videoArr;
    private String video_1;
    private String video_2;

    private List<HomeItemBean> activity_Layers;

    public List<HomeItemBean> getActivity_Layers() {
        return activity_Layers;
    }

    public void setActivity_Layers(List<HomeItemBean> activity_Layers) {
        this.activity_Layers = activity_Layers;
    }

    public String getVideo_1() {
        return video_1;
    }

    public void setVideo_1(String video_1) {
        this.video_1 = video_1;
    }

    public String getVideo_2() {
        return video_2;
    }

    public void setVideo_2(String video_2) {
        this.video_2 = video_2;
    }
    public HomeItemBean getVideoArr() {
        return videoArr;
    }

    public void setVideoArr(HomeItemBean videoArr) {
        this.videoArr = videoArr;
    }

    public int getIsShowChoAbilityLayer() {
        return isShowChoAbilityLayer;
    }

    public void setIsShowChoAbilityLayer(int isShowChoAbilityLayer) {
        this.isShowChoAbilityLayer = isShowChoAbilityLayer;
    }

    //显示祝福师付款弹层
    private List<HomeItemBean> isOrderZhufubaoLayer;


    public int getEntreUserCashIngLayer() {
        return entreUserCashIngLayer;
    }

    public void setEntreUserCashIngLayer(int entreUserCashIngLayer) {
        this.entreUserCashIngLayer = entreUserCashIngLayer;
    }

    public int getEntreUserRechargeIngLayer() {
        return entreUserRechargeIngLayer;
    }

    public void setEntreUserRechargeIngLayer(int entreUserRechargeIngLayer) {
        this.entreUserRechargeIngLayer = entreUserRechargeIngLayer;
    }

    public List<HomeItemBean> getIsOrderZhufubaoLayer() {
        return isOrderZhufubaoLayer;
    }

    public void setIsOrderZhufubaoLayer(List<HomeItemBean> isOrderZhufubaoLayer) {
        this.isOrderZhufubaoLayer = isOrderZhufubaoLayer;
    }

    public String getMy_gratitude_url() {
        return my_gratitude_url;
    }

    public void setMy_gratitude_url(String my_gratitude_url) {
        this.my_gratitude_url = my_gratitude_url;
    }

    public String getMy_dedication_url() {
        return my_dedication_url;
    }

    public void setMy_dedication_url(String my_dedication_url) {
        this.my_dedication_url = my_dedication_url;
    }

    public String getActivity_url() {
        return activity_url;
    }

    public void setActivity_url(String activity_url) {
        this.activity_url = activity_url;
    }

    public String getDisplay_note() {
        return display_note;
    }

    public void setDisplay_note(String display_note) {
        this.display_note = display_note;
    }

    public List<HomeItemBean> getLayer() {
        return layer;
    }

    public void setLayer(List<HomeItemBean> layer) {
        this.layer = layer;
    }

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

    public ArrayList<HomeItemBean> getRankingData() {
        return rankingData;
    }

    public void setRankingData(ArrayList<HomeItemBean> rankingData) {
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