package com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class RewardDataBean extends ResponseBean {
    @SerializedName("data")
    private RewardItemBean data;


    public RewardItemBean getData() {
        return data;
    }

    public void setData(RewardItemBean data) {
        this.data = data;
    }

    public class RewardItemBean {

        String user_id;
        String balance;
        String cumulative_balance;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getCumulative_balance() {
            return cumulative_balance;
        }

        public void setCumulative_balance(String cumulative_balance) {
            this.cumulative_balance = cumulative_balance;
        }
    }
}
