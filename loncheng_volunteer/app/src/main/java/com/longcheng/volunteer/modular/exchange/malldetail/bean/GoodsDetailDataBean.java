package com.longcheng.volunteer.modular.exchange.malldetail.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class GoodsDetailDataBean extends ResponseBean {
    @SerializedName("data")
    private DetailAfterBean data;

    public DetailAfterBean getData() {
        return data;
    }

    public void setData(DetailAfterBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }
}
