package com.longcheng.volunteer.modular.mine.myorder.detail.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class DetailDataBean extends ResponseBean {
    @SerializedName("data")
    protected DetailAfterBean data;

    public DetailAfterBean getData() {
        return data;
    }

    public void setData(DetailAfterBean data) {
        this.data = data;
    }
}
