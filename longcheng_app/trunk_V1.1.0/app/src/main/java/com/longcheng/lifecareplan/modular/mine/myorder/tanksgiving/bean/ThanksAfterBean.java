package com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderItemBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class ThanksAfterBean implements Serializable {

    private String total;
    private String user_name;
    private List<ThanksItemBean> rankings;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<ThanksItemBean> getRankings() {
        return rankings;
    }

    public void setRankings(List<ThanksItemBean> rankings) {
        this.rankings = rankings;
    }
}
