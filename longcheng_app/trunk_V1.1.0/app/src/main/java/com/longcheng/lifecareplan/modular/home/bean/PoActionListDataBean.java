package com.longcheng.lifecareplan.modular.home.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回数组
 */

public class PoActionListDataBean extends ResponseBean {
    @SerializedName("data")
    private List<HomeItemBean> data;

    public List<HomeItemBean> getData() {
        return data;
    }

    public void setData(List<HomeItemBean> data) {
        this.data = data;
    }
}
