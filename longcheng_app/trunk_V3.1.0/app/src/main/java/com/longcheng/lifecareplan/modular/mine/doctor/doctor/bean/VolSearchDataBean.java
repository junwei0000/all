package com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class VolSearchDataBean extends ResponseBean {
    @SerializedName("data")
    private DocItemBean data;


    public DocItemBean getData() {
        return data;
    }

    public void setData(DocItemBean data) {
        this.data = data;
    }

    public class DocItemBean {
        String user_id;
        String user_name;
        String avatar;
        String phone;


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

    }
}
