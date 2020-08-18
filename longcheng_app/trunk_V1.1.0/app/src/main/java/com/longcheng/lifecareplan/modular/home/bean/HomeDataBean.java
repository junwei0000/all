package com.longcheng.lifecareplan.modular.home.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

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
