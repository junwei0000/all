package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class ZYBZuDuiDataBean extends ResponseBean {
    @SerializedName("data")
    protected RiceActiviesAfterBean data;


    public RiceActiviesAfterBean getData() {
        return data;
    }

    public void setData(RiceActiviesAfterBean data) {
        this.data = data;
    }

    public static class RiceActiviesAfterBean {
        ArrayList<RiceActiviesItemBean> userCreateInfos;

        public ArrayList<RiceActiviesItemBean> getUserCreateInfos() {
            return userCreateInfos;
        }

        public void setUserCreateInfos(ArrayList<RiceActiviesItemBean> userCreateInfos) {
            this.userCreateInfos = userCreateInfos;
        }

        public static class RiceActiviesItemBean {
            String user_name;
            int status;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }

}
