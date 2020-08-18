package com.longcheng.volunteer.modular.mine.userinfo.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class UserSetBean implements Serializable {
    @SerializedName("political_status_conf")
    private ArrayList<UserSetInfoBean> political_status_conf;
    @SerializedName("military_service_conf")
    private ArrayList<UserSetInfoBean> military_service_conf;

    public ArrayList<UserSetInfoBean> getPolitical_status_conf() {
        return political_status_conf;
    }

    public void setPolitical_status_conf(ArrayList<UserSetInfoBean> political_status_conf) {
        this.political_status_conf = political_status_conf;
    }

    public ArrayList<UserSetInfoBean> getMilitary_service_conf() {
        return military_service_conf;
    }

    public void setMilitary_service_conf(ArrayList<UserSetInfoBean> military_service_conf) {
        this.military_service_conf = military_service_conf;
    }

    @Override
    public String toString() {
        return "UserSetBean{" +
                "political_status_conf=" + political_status_conf +
                ", military_service_conf=" + military_service_conf +
                '}';
    }

    public class UserSetInfoBean {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "UserSetInfoBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


}
