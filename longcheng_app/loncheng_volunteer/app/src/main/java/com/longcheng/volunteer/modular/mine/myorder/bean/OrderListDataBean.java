package com.longcheng.volunteer.modular.mine.myorder.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class OrderListDataBean extends ResponseBean {
    @SerializedName("data")
    protected OrderAfterBean data;

    public OrderAfterBean getData() {
        return data;
    }

    public void setData(OrderAfterBean data) {
        this.data = data;
    }
}
