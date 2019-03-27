package com.longcheng.volunteer.modular.mine.rewardcenters.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Burning on 2018/9/4.
 */

public class RewardDetail {
    @SerializedName("phone")
    String phone;
    @SerializedName("user_name")
    String userName;
    @SerializedName("user_id")
    int userId;
    @SerializedName("avatar")
    String avatar;
    @SerializedName("num")
    int num;
    @SerializedName("reward_skb")
    int rewardSkb;
    @SerializedName("add_time")
    String addTime;
    @SerializedName("is_cho")
    int is_cho;

    public boolean isCHO() {
        return is_cho == 1;
    }

    public void setIs_cho(int is_cho) {
        this.is_cho = is_cho;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getRewardSkb() {
        return rewardSkb;
    }

    public void setRewardSkb(int rewardSkb) {
        this.rewardSkb = rewardSkb;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
