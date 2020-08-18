package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionZFBSelectDataBean extends ResponseBean {
    @SerializedName("data")
    protected ArrayList<PionZFBSBean> data;

    public ArrayList<PionZFBSBean> getData() {
        return data;
    }

    public void setData(ArrayList<PionZFBSBean> data) {
        this.data = data;
    }

    public class PionZFBSBean {
        String user_id;
        @SerializedName(value = "phone", alternate = {"user_phone"})
        String phone;
        @SerializedName(value = "user_name", alternate = {"name"})
        String user_name;
        @SerializedName(value = "avatar", alternate = {"user_avatar"})
        String avatar;
        @SerializedName(value = "jieqi_name", alternate = {"jieqi_branch_name"})
        String jieqi_name;
        String entrepreneurs_id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getJieqi_name() {
            return jieqi_name;
        }

        public void setJieqi_name(String jieqi_name) {
            this.jieqi_name = jieqi_name;
        }

        public String getEntrepreneurs_id() {
            return entrepreneurs_id;
        }

        public void setEntrepreneurs_id(String entrepreneurs_id) {
            this.entrepreneurs_id = entrepreneurs_id;
        }
    }
}
