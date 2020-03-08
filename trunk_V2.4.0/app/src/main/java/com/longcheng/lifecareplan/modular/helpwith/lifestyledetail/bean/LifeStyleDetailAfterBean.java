package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class LifeStyleDetailAfterBean {
    @SerializedName("help_wares")
    private LifeStyleDetailAfterBean help_wares;
    private String receive_user_name;
    private String date;
    private String solar_term_cn;
    private String start_time;
    private int progress;
    private String cumulative_number;
    private String des_content;
    private String solar_term_img;

    private int shop_goods_id;
    private String shop_goods_name;
    private String shop_goods_img;
    private String shop_goods_price_name;

    ArrayList<LifeStyleDetailAfterBean> orders;
    private String total_num;
    private String solar_terms_name;
    ArrayList<LifeStyleDetailAfterBean> list;
    private String avatar;
    private String sponsor_user_name;


    @SerializedName("title")
    private String title;
    @SerializedName("desc")
    private String desc;
    @SerializedName("wx_share_url")
    private String wx_share_url;

    public String getSponsor_user_name() {
        return sponsor_user_name;
    }

    public void setSponsor_user_name(String sponsor_user_name) {
        this.sponsor_user_name = sponsor_user_name;
    }

    public String getSolar_term_img() {
        return solar_term_img;
    }

    public void setSolar_term_img(String solar_term_img) {
        this.solar_term_img = solar_term_img;
    }

    public int getShop_goods_id() {
        return shop_goods_id;
    }

    public void setShop_goods_id(int shop_goods_id) {
        this.shop_goods_id = shop_goods_id;
    }

    public String getShop_goods_name() {
        return shop_goods_name;
    }

    public void setShop_goods_name(String shop_goods_name) {
        this.shop_goods_name = shop_goods_name;
    }

    public String getShop_goods_img() {
        return shop_goods_img;
    }

    public void setShop_goods_img(String shop_goods_img) {
        this.shop_goods_img = shop_goods_img;
    }

    public String getShop_goods_price_name() {
        return shop_goods_price_name;
    }

    public void setShop_goods_price_name(String shop_goods_price_name) {
        this.shop_goods_price_name = shop_goods_price_name;
    }

    public LifeStyleDetailAfterBean getHelp_wares() {
        return help_wares;
    }

    public void setHelp_wares(LifeStyleDetailAfterBean help_wares) {
        this.help_wares = help_wares;
    }

    public String getReceive_user_name() {
        return receive_user_name;
    }

    public void setReceive_user_name(String receive_user_name) {
        this.receive_user_name = receive_user_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSolar_term_cn() {
        return solar_term_cn;
    }

    public void setSolar_term_cn(String solar_term_cn) {
        this.solar_term_cn = solar_term_cn;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getCumulative_number() {
        return cumulative_number;
    }

    public void setCumulative_number(String cumulative_number) {
        this.cumulative_number = cumulative_number;
    }

    public String getDes_content() {
        return des_content;
    }

    public void setDes_content(String des_content) {
        this.des_content = des_content;
    }

    public ArrayList<LifeStyleDetailAfterBean> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<LifeStyleDetailAfterBean> orders) {
        this.orders = orders;
    }

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

    public String getSolar_terms_name() {
        return solar_terms_name;
    }

    public void setSolar_terms_name(String solar_terms_name) {
        this.solar_terms_name = solar_terms_name;
    }

    public ArrayList<LifeStyleDetailAfterBean> getList() {
        return list;
    }

    public void setList(ArrayList<LifeStyleDetailAfterBean> list) {
        this.list = list;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWx_share_url() {
        return wx_share_url;
    }

    public void setWx_share_url(String wx_share_url) {
        this.wx_share_url = wx_share_url;
    }
}
