package com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回数组
 */

public class PeopleSearchDataBean extends ResponseBean {
    @SerializedName("data")
    private List<PeopleItemBean> data;

    public List<PeopleItemBean> getData() {
        return data;
    }

    public void setData(List<PeopleItemBean> data) {
        this.data = data;
    }
}
