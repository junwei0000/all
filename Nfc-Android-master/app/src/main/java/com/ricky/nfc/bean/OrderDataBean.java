package com.ricky.nfc.bean;

import com.google.gson.annotations.SerializedName;
import com.ricky.nfc.api.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class OrderDataBean extends ResponseBean {
    @SerializedName("data")
    private OrderAfterBean data;

    public OrderAfterBean getData() {
        return data;
    }

    public void setData(OrderAfterBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }
}
