package com.longcheng.lifecareplan.modular.mine.myorder.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class OrderAfterBean implements Serializable {
    @SerializedName("count")
    private int count;
    @SerializedName("orders")
    private List<OrderItemBean> orders;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OrderItemBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItemBean> orders) {
        this.orders = orders;
    }

}
