package com.longcheng.lifecareplan.push.jpush.message;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PairUBean {
    @SerializedName(value = "zhufubao", alternate = {"fuqibao_limit"})
    double money_limit;

    private ArrayList<PairUBean> queue_items;
    @SerializedName(value = "user_zhufubao_order_id", alternate = {"entrepreneurs_zhufubao_order_id"})
    private String user_zhufubao_order_id;
    private String avatar;
    private String user_name;
    private String jieqi_name;
    private ArrayList<String> img_all;
    private String money;

    @SerializedName(value = "entrepreneurs_team_invitation_id", alternate = {"zhuyoubao_team_invitation_id"})
    private String entrepreneurs_team_invitation_id;
    private String sponsor_user_name;


    //结伴互祝组队创建弹层
    private String knp_group_room_id;
    private String knp_group_item_id;
    private int is_show;
    private int person_number;

    //结伴互祝邀请弹层
    private PairUBean item;
    private String knp_group_invitation_id;
    private int is_alert;

    public String getKnp_group_invitation_id() {
        return knp_group_invitation_id;
    }

    public void setKnp_group_invitation_id(String knp_group_invitation_id) {
        this.knp_group_invitation_id = knp_group_invitation_id;
    }

    public PairUBean getItem() {
        return item;
    }

    public void setItem(PairUBean item) {
        this.item = item;
    }

    public int getIs_alert() {
        return is_alert;
    }

    public void setIs_alert(int is_alert) {
        this.is_alert = is_alert;
    }

    public int getPerson_number() {
        return person_number;
    }

    public void setPerson_number(int person_number) {
        this.person_number = person_number;
    }

    public String getKnp_group_room_id() {
        return knp_group_room_id;
    }

    public void setKnp_group_room_id(String knp_group_room_id) {
        this.knp_group_room_id = knp_group_room_id;
    }

    public String getKnp_group_item_id() {
        return knp_group_item_id;
    }

    public void setKnp_group_item_id(String knp_group_item_id) {
        this.knp_group_item_id = knp_group_item_id;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public String getEntrepreneurs_team_invitation_id() {
        return entrepreneurs_team_invitation_id;
    }

    public void setEntrepreneurs_team_invitation_id(String entrepreneurs_team_invitation_id) {
        this.entrepreneurs_team_invitation_id = entrepreneurs_team_invitation_id;
    }

    public String getSponsor_user_name() {
        return sponsor_user_name;
    }

    public void setSponsor_user_name(String sponsor_user_name) {
        this.sponsor_user_name = sponsor_user_name;
    }

    public double getMoney_limit() {
        return money_limit;
    }

    public void setMoney_limit(double money_limit) {
        this.money_limit = money_limit;
    }

    public String getUser_zhufubao_order_id() {
        return user_zhufubao_order_id;
    }

    public void setUser_zhufubao_order_id(String user_zhufubao_order_id) {
        this.user_zhufubao_order_id = user_zhufubao_order_id;
    }

    public ArrayList<PairUBean> getQueue_items() {
        return queue_items;
    }

    public void setQueue_items(ArrayList<PairUBean> queue_items) {
        this.queue_items = queue_items;
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

    public String getJieqi_name() {
        return jieqi_name;
    }

    public void setJieqi_name(String jieqi_name) {
        this.jieqi_name = jieqi_name;
    }

    public ArrayList<String> getImg_all() {
        return img_all;
    }

    public void setImg_all(ArrayList<String> img_all) {
        this.img_all = img_all;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "PairUBean{" +
                "user_zhufubao_order_id='" + user_zhufubao_order_id + '\'' +
                ", avatar='" + avatar + '\'' +
                ", user_name='" + user_name + '\'' +
                ", jieqi_name='" + jieqi_name + '\'' +
                ", img_all=" + img_all +
                ", money='" + money + '\'' +
                '}';
    }
}
