package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.lifestylerank.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class LifeRankItemBean {
    @SerializedName("help_goods_id")
    private String help_goods_id;
    @SerializedName("sponsor_user_id")
    private String user_id;
    @SerializedName("sponsor_user_name")
    private String user_name;
    @SerializedName("sponsor_group_name")
    private String group_name;
    @SerializedName("skb_cumulative_price")
    private String price;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("id")
    private String id;


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHelp_goods_id() {
        return help_goods_id;
    }

    public void setHelp_goods_id(String help_goods_id) {
        this.help_goods_id = help_goods_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}
