package com.longcheng.lifecareplan.modular.mine.userinfo.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class GetHomeInfoBean implements Serializable {
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("dongjie")
    private String dongjie;
    @SerializedName("shoukangyuan")
    private String shoukangyuan;
    @SerializedName("shengmingnengliang")
    private String shengmingnengliang;
    @SerializedName("frost_ability")
    private String frost_ability;
    @SerializedName("currentStartLevel")
    private int currentStartLevel;
    @SerializedName("nextStartLevel")
    private String nextStartLevel;
    @SerializedName("disparity")
    private String disparity;
    @SerializedName("asset")
    private String asset;
    @SerializedName("help_number")
    private String help_number;
    @SerializedName("is_show")
    private String is_show;
    @SerializedName("star_level")
    private int star_level;
    @SerializedName("userTotalReward")
    private String userTotalReward;
    @SerializedName("userMonthTotalReward")
    private String userMonthTotalReward;
    @SerializedName("userYesterdayReward")
    private String userYesterdayReward;
    @SerializedName("is_cho")
    private String is_cho;

    @SerializedName("isQimingSponsorUser")
    private int isQimingSponsorUser;
    @SerializedName("qimingSponsorUserUrl")
    private String qimingSponsorUserUrl;
    @SerializedName("qimingaction_goods_id")
    private String qimingaction_goods_id = "56";


    @SerializedName("phone_user_token")
    private String wxToken;
    @SerializedName("star_level_illustrate_url")
    private String star_level_illustrate_url;
    @SerializedName("chatuserStarLevelId")
    private int chatuserStarLevelId;
    @SerializedName("isUnopenedRedPackage")
    private int isUnopenedRedPackage;
    @SerializedName("isDirectorOrTeamLeader")
    private int isDirectorOrTeamLeader;

    @SerializedName("about_me_url")
    private String about_me_url;


    private int isResetCard;//是否显示复活卡  0：不显示  1：显示
    private String phone;
    private String birthday;
    private String lunar_calendar_birthday;
    private String political_status;
    private String is_military_service;
    private String pid;
    private String cid;
    private String aid;
    private String area;


    //坐堂医
    private String promoter_url;//小推手url
    private String become_doctor_url;//成为坐堂医url
    private String become_volunteer_url;//成为志愿者url
    private String already_volunteer_url;//志愿者url
    private String already_doctor_url;//坐堂医url

    private int isDoctorIdentity;//是否是坐堂医 0不是；1 是
    private int isVolunteerIdentity;//是否是志愿者 0不是；1 是


    private String patient_record_url;//就诊记录url
    private int hasDiagnosticRecord;//是否有就诊记录 0没有；1 有


    private String partymember_url;//志愿者列表
    private int isPartymember;//是否有志愿者列表 0没有；1 有


    private String receiptCodeUrl;//收付款

    public String getReceiptCodeUrl() {
        return receiptCodeUrl;
    }

    public void setReceiptCodeUrl(String receiptCodeUrl) {
        this.receiptCodeUrl = receiptCodeUrl;
    }

    public String getPartymember_url() {
        return partymember_url;
    }

    public void setPartymember_url(String partymember_url) {
        this.partymember_url = partymember_url;
    }

    public int getIsPartymember() {
        return isPartymember;
    }

    public void setIsPartymember(int isPartymember) {
        this.isPartymember = isPartymember;
    }

    public String getPatient_record_url() {
        return patient_record_url;
    }

    public void setPatient_record_url(String patient_record_url) {
        this.patient_record_url = patient_record_url;
    }

    public int getHasDiagnosticRecord() {
        return hasDiagnosticRecord;
    }

    public void setHasDiagnosticRecord(int hasDiagnosticRecord) {
        this.hasDiagnosticRecord = hasDiagnosticRecord;
    }

    public String getAlready_volunteer_url() {
        return already_volunteer_url;
    }

    public void setAlready_volunteer_url(String already_volunteer_url) {
        this.already_volunteer_url = already_volunteer_url;
    }

    public String getPromoter_url() {
        return promoter_url;
    }

    public void setPromoter_url(String promoter_url) {
        this.promoter_url = promoter_url;
    }

    public String getBecome_doctor_url() {
        return become_doctor_url;
    }

    public void setBecome_doctor_url(String become_doctor_url) {
        this.become_doctor_url = become_doctor_url;
    }

    public String getBecome_volunteer_url() {
        return become_volunteer_url;
    }

    public void setBecome_volunteer_url(String become_volunteer_url) {
        this.become_volunteer_url = become_volunteer_url;
    }

    public String getAlready_doctor_url() {
        return already_doctor_url;
    }

    public void setAlready_doctor_url(String already_doctor_url) {
        this.already_doctor_url = already_doctor_url;
    }

    public int getIsDoctorIdentity() {
        return isDoctorIdentity;
    }

    public void setIsDoctorIdentity(int isDoctorIdentity) {
        this.isDoctorIdentity = isDoctorIdentity;
    }

    public int getIsVolunteerIdentity() {
        return isVolunteerIdentity;
    }

    public void setIsVolunteerIdentity(int isVolunteerIdentity) {
        this.isVolunteerIdentity = isVolunteerIdentity;
    }

    public String getFrost_ability() {
        return frost_ability;
    }

    public void setFrost_ability(String frost_ability) {
        this.frost_ability = frost_ability;
    }

    public String getQimingaction_goods_id() {
        return qimingaction_goods_id;
    }

    public void setQimingaction_goods_id(String qimingaction_goods_id) {
        this.qimingaction_goods_id = qimingaction_goods_id;
    }

    public int getIsResetCard() {
        return isResetCard;
    }

    public void setIsResetCard(int isResetCard) {
        this.isResetCard = isResetCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLunar_calendar_birthday() {
        return lunar_calendar_birthday;
    }

    public void setLunar_calendar_birthday(String lunar_calendar_birthday) {
        this.lunar_calendar_birthday = lunar_calendar_birthday;
    }

    public String getPolitical_status() {
        return political_status;
    }

    public void setPolitical_status(String political_status) {
        this.political_status = political_status;
    }

    public String getIs_military_service() {
        return is_military_service;
    }

    public void setIs_military_service(String is_military_service) {
        this.is_military_service = is_military_service;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAbout_me_url() {
        return about_me_url;
    }

    public void setAbout_me_url(String about_me_url) {
        this.about_me_url = about_me_url;
    }

    public int getIsDirectorOrTeamLeader() {
        return isDirectorOrTeamLeader;
    }

    public void setIsDirectorOrTeamLeader(int isDirectorOrTeamLeader) {
        this.isDirectorOrTeamLeader = isDirectorOrTeamLeader;
    }

    public int getIsUnopenedRedPackage() {
        return isUnopenedRedPackage;
    }

    public void setIsUnopenedRedPackage(int isUnopenedRedPackage) {
        this.isUnopenedRedPackage = isUnopenedRedPackage;
    }

    public String getStar_level_illustrate_url() {
        return star_level_illustrate_url;
    }

    public void setStar_level_illustrate_url(String star_level_illustrate_url) {
        this.star_level_illustrate_url = star_level_illustrate_url;
    }

    public int getChatuserStarLevelId() {
        return chatuserStarLevelId;
    }

    public void setChatuserStarLevelId(int chatuserStarLevelId) {
        this.chatuserStarLevelId = chatuserStarLevelId;
    }

    public int getStar_level() {
        return star_level;
    }

    public void setStar_level(int star_level) {
        this.star_level = star_level;
    }

    public String getHelp_number() {
        return help_number;
    }

    public void setHelp_number(String help_number) {
        this.help_number = help_number;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public int getIsQimingSponsorUser() {
        return isQimingSponsorUser;
    }

    public String getWxToken() {
        return wxToken;
    }

    public void setWxToken(String wxToken) {
        this.wxToken = wxToken;
    }

    public boolean issQimingSponsorUser() {
        return isQimingSponsorUser == 1;
    }

    public void setIsQimingSponsorUser(int isQimingSponsorUser) {
        this.isQimingSponsorUser = isQimingSponsorUser;
    }

    public String getQimingSponsorUserUrl() {
        return qimingSponsorUserUrl;
    }

    public void setQimingSponsorUserUrl(String qimingSponsorUserUrl) {
        this.qimingSponsorUserUrl = qimingSponsorUserUrl;
    }

    public String getIs_cho() {
        return is_cho;
    }

    public void setIs_cho(String is_cho) {
        this.is_cho = is_cho;
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

    public String getDongjie() {
        return dongjie;
    }

    public void setDongjie(String dongjie) {
        this.dongjie = dongjie;
    }

    public String getShoukangyuan() {
        return shoukangyuan;
    }

    public void setShoukangyuan(String shoukangyuan) {
        this.shoukangyuan = shoukangyuan;
    }

    public String getShengmingnengliang() {
        return shengmingnengliang;
    }

    public void setShengmingnengliang(String shengmingnengliang) {
        this.shengmingnengliang = shengmingnengliang;
    }

    public int getCurrentStartLevel() {
        return currentStartLevel;
    }

    public void setCurrentStartLevel(int currentStartLevel) {
        this.currentStartLevel = currentStartLevel;
    }

    public String getNextStartLevel() {
        return nextStartLevel;
    }

    public void setNextStartLevel(String nextStartLevel) {
        this.nextStartLevel = nextStartLevel;
    }

    public String getDisparity() {
        return disparity;
    }

    public void setDisparity(String disparity) {
        this.disparity = disparity;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getUserTotalReward() {
        return userTotalReward;
    }

    public void setUserTotalReward(String userTotalReward) {
        this.userTotalReward = userTotalReward;
    }

    public String getUserMonthTotalReward() {
        return userMonthTotalReward;
    }

    public void setUserMonthTotalReward(String userMonthTotalReward) {
        this.userMonthTotalReward = userMonthTotalReward;
    }

    public String getUserYesterdayReward() {
        return userYesterdayReward;
    }

    public void setUserYesterdayReward(String userYesterdayReward) {
        this.userYesterdayReward = userYesterdayReward;
    }
}
