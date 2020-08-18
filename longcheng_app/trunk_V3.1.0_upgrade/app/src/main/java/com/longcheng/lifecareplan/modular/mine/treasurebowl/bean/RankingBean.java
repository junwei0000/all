package com.longcheng.lifecareplan.modular.mine.treasurebowl.bean;

import java.util.List;

public class RankingBean {
    /**
     * user_cornucopia_ranking_id : 63
     * user_id : 234032
     * user_name : 18510770350
     * avatar : http://test.t.asdyf.com//static/home/images/icon_pretermission.png?v=1.0.0
     * points : 1000
     * date : 2020-08-10
     * start_date : 2020-08-10 21:00:00
     * end_date : 2020-08-11 21:00:00
     * type : 2
     * create_time : 1597129545
     * update_time : 1597129545
     * jieqi_name : 丁丑·大暑
     * identity_img : ["http://test.t.asdyf.com//static/home/images/useridentity/cho.png?vi=1.0.4"]
     * ranking : 1
     * grades : 1000
     */

    private String user_cornucopia_ranking_id;
    private String user_id;
    private String user_name;
    private String avatar;
    private String points;
    private String date;
    private String start_date;
    private String end_date;
    private int type;
    private int create_time;
    private int update_time;
    private String jieqi_name;
    private String ranking;
    private String grades;
    private List<String> identity_img;

    public String getUser_cornucopia_ranking_id() {
        return user_cornucopia_ranking_id;
    }

    public void setUser_cornucopia_ranking_id(String user_cornucopia_ranking_id) {
        this.user_cornucopia_ranking_id = user_cornucopia_ranking_id;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public String getJieqi_name() {
        return jieqi_name;
    }

    public void setJieqi_name(String jieqi_name) {
        this.jieqi_name = jieqi_name;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public List<String> getIdentity_img() {
        return identity_img;
    }

    public void setIdentity_img(List<String> identity_img) {
        this.identity_img = identity_img;
    }
}