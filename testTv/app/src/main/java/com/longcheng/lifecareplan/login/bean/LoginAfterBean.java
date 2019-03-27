package com.longcheng.lifecareplan.login.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class LoginAfterBean implements Serializable {
    private String user_id;
    private String user_name;
    private String phone;
    private String avatar;
    private String star_level;//星级
    private String is_cho;//是否是cho,1:是  ；0：不是
    private String birthday;
    private String lunar_calendar_birthday;
    private String political_status;
    private String is_military_service;
    private String pid;
    private String cid;
    private String aid;
    private String area;
    @SerializedName("phone_user_token")
    private String wxToken;
    private int group_id;
    private int team_id;

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getWxToken() {
        return wxToken;
    }

    public void setWxToken(String wxToken) {
        this.wxToken = wxToken;
    }

    public LoginAfterBean() {

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStar_level() {
        return star_level;
    }

    public void setStar_level(String star_level) {
        this.star_level = star_level;
    }

    public String getIs_cho() {
        return is_cho;
    }

    public void setIs_cho(String is_cho) {
        this.is_cho = is_cho;
    }
}
