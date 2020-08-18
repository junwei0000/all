package com.longcheng.volunteer.modular.mine.myorder.ordertracking.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class TrankListDataBean extends ResponseBean {
    @SerializedName("data")
    protected TrankAfterBean data;

    public TrankAfterBean getData() {
        return data;
    }

    public void setData(TrankAfterBean data) {
        this.data = data;
    }
}
