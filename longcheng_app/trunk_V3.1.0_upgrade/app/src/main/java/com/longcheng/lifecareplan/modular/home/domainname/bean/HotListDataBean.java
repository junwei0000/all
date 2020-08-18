package com.longcheng.lifecareplan.modular.home.domainname.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class HotListDataBean extends ResponseBean {
    @SerializedName("data")
    private ArrayList<HotItemInfo> data;

    public ArrayList<HotItemInfo> getData() {
        return data;
    }

    public void setData(ArrayList<HotItemInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }
    public class HotItemInfo{
        String commune_id;
        String commune_name;
        String brigade_name;
        String address;
        int level;

        public String getCommune_id() {
            return commune_id;
        }

        public void setCommune_id(String commune_id) {
            this.commune_id = commune_id;
        }

        public String getCommune_name() {
            return commune_name;
        }

        public void setCommune_name(String commune_name) {
            this.commune_name = commune_name;
        }

        public String getBrigade_name() {
            return brigade_name;
        }

        public void setBrigade_name(String brigade_name) {
            this.brigade_name = brigade_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
