package com.longcheng.volunteer.modular.helpwith.energydetail.rank.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class RankItemBean {
    @SerializedName("msg_id")
    private String msg_id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("group_name")
    private String group_name;
    @SerializedName("price")
    private String price;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("sort_rank")
    private String id;
    @SerializedName("action_id")
    private int action_id;

    public int getAction_id() {
        return action_id;
    }

    public void setAction_id(int action_id) {
        this.action_id = action_id;
    }

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

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
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
