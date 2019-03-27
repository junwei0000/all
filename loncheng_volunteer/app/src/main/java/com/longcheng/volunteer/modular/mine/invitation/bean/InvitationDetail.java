package com.longcheng.volunteer.modular.mine.invitation.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Burning on 2018/9/5.
 */

public class InvitationDetail {
    @SerializedName("user_id")
    protected int userId;
    @SerializedName("user_name")
    protected String userName;
    @SerializedName("phone")
    String phone;
    @SerializedName("create_time")
    String date;
    @SerializedName("is_cho")
    int isCHO;
    @SerializedName("avatar")
    String avatar;
    @SerializedName("userUdentity")
    String userUdentity;

    public int getIsCHO() {
        return isCHO;
    }

    public String getUserUdentity() {
        return userUdentity;
    }

    public void setUserUdentity(String userUdentity) {
        this.userUdentity = userUdentity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCHO() {
        return isCHO == 1;
    }

    public void setIsCHO(int isCHO) {
        this.isCHO = isCHO;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
