package com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class NFCDetailItemBean implements Serializable {
    private String user_id;
    private String avatar;
    private String user_name;
    private String jieqi_name;
    private String phone;
    private ArrayList<String> img_all;
    @SerializedName("id")
    private String id;
    private int m_time;
    private int over_time;
    private String f_user;
    private String goods_id;
    private String ability_price_action;
    private String cumulative_number;
    @SerializedName("asset")
    private String asset;
    private String super_ability;

    private String mutual_help_money_id;
    private int is_default;
    private int money;
    private String ability;
    private String image;

    @SerializedName("goods_name")
    private String action_name;
    private String reduce_number;
    private int create_time;


    @SerializedName("user_identity_jieqi")
    private String user_identity_jieqi;
    private String price;
    private ArrayList<String> user_identity_img;
    private String lao_address;

    private float fromX;
    private float fromY;

    public NFCDetailItemBean(float fromX, float fromY) {
        this.fromX = fromX;
        this.fromY = fromY;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getF_user() {
        return f_user;
    }

    public void setF_user(String f_user) {
        this.f_user = f_user;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public float getFromX() {
        return fromX;
    }

    public void setFromX(float fromX) {
        this.fromX = fromX;
    }

    public float getFromY() {
        return fromY;
    }

    public void setFromY(float fromY) {
        this.fromY = fromY;
    }

    public String getUser_identity_jieqi() {
        return user_identity_jieqi;
    }

    public void setUser_identity_jieqi(String user_identity_jieqi) {
        this.user_identity_jieqi = user_identity_jieqi;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getUser_identity_img() {
        return user_identity_img;
    }

    public void setUser_identity_img(ArrayList<String> user_identity_img) {
        this.user_identity_img = user_identity_img;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public String getReduce_number() {
        return reduce_number;
    }

    public void setReduce_number(String reduce_number) {
        this.reduce_number = reduce_number;
    }

    public String getLao_address() {
        return lao_address;
    }

    public void setLao_address(String lao_address) {
        this.lao_address = lao_address;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getMutual_help_money_id() {
        return mutual_help_money_id;
    }

    public void setMutual_help_money_id(String mutual_help_money_id) {
        this.mutual_help_money_id = mutual_help_money_id;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getSuper_ability() {
        return super_ability;
    }

    public void setSuper_ability(String super_ability) {
        this.super_ability = super_ability;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<String> getImg_all() {
        return img_all;
    }

    public void setImg_all(ArrayList<String> img_all) {
        this.img_all = img_all;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getM_time() {
        return m_time;
    }

    public void setM_time(int m_time) {
        this.m_time = m_time;
    }

    public int getOver_time() {
        return over_time;
    }

    public void setOver_time(int over_time) {
        this.over_time = over_time;
    }

    public String getAbility_price_action() {
        return ability_price_action;
    }

    public void setAbility_price_action(String ability_price_action) {
        this.ability_price_action = ability_price_action;
    }

    public String getCumulative_number() {
        return cumulative_number;
    }

    public void setCumulative_number(String cumulative_number) {
        this.cumulative_number = cumulative_number;
    }
}
