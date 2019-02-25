package com.longcheng.lifecareplan.modular.mine.goodluck.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class GoodLuckBean implements Serializable {

    private String mutual_help_user_red_packet_id;//
    private String user_id;
    private String mutual_help_red_packet_id;
    private int type;
    private String money_type;
    private double red_packet_money;

    private int red_packet_status;
    private String pay_status;
    private String msg_id;
    private String one_order_id;

    private String receive_red_packet_time;
    private String create_time;
    private int position;

    //明星
    private String solar_terms_name;
    private String start_name;
    private String photo;
    private String url;
    private String slogan;
    private String advertisement_photo_url;
    private int action_goods_id;
    private String qiming_user_id;

    public String getSolar_terms_name() {
        return solar_terms_name;
    }

    public void setSolar_terms_name(String solar_terms_name) {
        this.solar_terms_name = solar_terms_name;
    }

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAdvertisement_photo_url() {
        return advertisement_photo_url;
    }

    public void setAdvertisement_photo_url(String advertisement_photo_url) {
        this.advertisement_photo_url = advertisement_photo_url;
    }

    public int getAction_goods_id() {
        return action_goods_id;
    }

    public void setAction_goods_id(int action_goods_id) {
        this.action_goods_id = action_goods_id;
    }

    public String getQiming_user_id() {
        return qiming_user_id;
    }

    public void setQiming_user_id(String qiming_user_id) {
        this.qiming_user_id = qiming_user_id;
    }

    public String getMutual_help_user_red_packet_id() {
        return mutual_help_user_red_packet_id;
    }

    public void setMutual_help_user_red_packet_id(String mutual_help_user_red_packet_id) {
        this.mutual_help_user_red_packet_id = mutual_help_user_red_packet_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMutual_help_red_packet_id() {
        return mutual_help_red_packet_id;
    }

    public void setMutual_help_red_packet_id(String mutual_help_red_packet_id) {
        this.mutual_help_red_packet_id = mutual_help_red_packet_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMoney_type() {
        return money_type;
    }

    public void setMoney_type(String money_type) {
        this.money_type = money_type;
    }

    public double getRed_packet_money() {
        return red_packet_money;
    }

    public void setRed_packet_money(double red_packet_money) {
        this.red_packet_money = red_packet_money;
    }

    public int getRed_packet_status() {
        return red_packet_status;
    }

    public void setRed_packet_status(int red_packet_status) {
        this.red_packet_status = red_packet_status;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getOne_order_id() {
        return one_order_id;
    }

    public void setOne_order_id(String one_order_id) {
        this.one_order_id = one_order_id;
    }

    public String getReceive_red_packet_time() {
        return receive_red_packet_time;
    }

    public void setReceive_red_packet_time(String receive_red_packet_time) {
        this.receive_red_packet_time = receive_red_packet_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "RebirthItemBean{" +
                "mutual_help_user_red_packet_id='" + mutual_help_user_red_packet_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", mutual_help_red_packet_id='" + mutual_help_red_packet_id + '\'' +
                ", type=" + type +
                ", money_type='" + money_type + '\'' +
                ", red_packet_money='" + red_packet_money + '\'' +
                ", red_packet_status=" + red_packet_status +
                ", pay_status='" + pay_status + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", one_order_id='" + one_order_id + '\'' +
                ", receive_red_packet_time='" + receive_red_packet_time + '\'' +
                ", create_time='" + create_time + '\'' +
                ", position='" + position + '\'' +
                ", solar_terms_name='" + solar_terms_name + '\'' +
                ", start_name='" + start_name + '\'' +
                ", photo='" + photo + '\'' +
                ", url='" + url + '\'' +
                ", slogan='" + slogan + '\'' +
                ", advertisement_photo_url='" + advertisement_photo_url + '\'' +
                ", action_goods_id='" + action_goods_id + '\'' +
                ", qiming_user_id='" + qiming_user_id + '\'' +
                '}';
    }
}
