package com.longcheng.volunteer.modular.home.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回数组
 */

public class HomeDataBean extends ResponseBean {
    @SerializedName("data")
    private HomeAfterBean data;

    public HomeAfterBean getData() {
        return data;
    }

    public void setData(HomeAfterBean data) {
        this.data = data;
    }
}
