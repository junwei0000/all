package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class RiceActiviesListDataBean extends ResponseBean {
    @SerializedName("data")
    protected ArrayList<RiceActiviesItemBean> data;

    public ArrayList<RiceActiviesItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<RiceActiviesItemBean> data) {
        this.data = data;
    }

    public static class RiceActiviesItemBean {
        String user_name;
        String desc;
        int create_time;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }

}
