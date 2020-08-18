package com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBAfterBean;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionOpenSetDataBean extends ResponseBean {
    @SerializedName("data")
    protected PionOpenSetItemBean data;

    public PionOpenSetItemBean getData() {
        return data;
    }

    public void setData(PionOpenSetItemBean data) {
        this.data = data;
    }

    public class PionOpenSetItemBean {

        String user_id;
        String avatar;
        String user_name;
        String phone;
        String jieqi_branch_name;
        String jieqi_name;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getJieqi_branch_name() {
            return jieqi_branch_name;
        }

        public void setJieqi_branch_name(String jieqi_branch_name) {
            this.jieqi_branch_name = jieqi_branch_name;
        }

        public String getJieqi_name() {
            return jieqi_name;
        }

        public void setJieqi_name(String jieqi_name) {
            this.jieqi_name = jieqi_name;
        }
    }
}
