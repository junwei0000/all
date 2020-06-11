package com.longcheng.lifecareplan.modular.mine.pioneercenter.openorder.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionOpenGMListDataBean extends ResponseBean {
    @SerializedName("data")
    private List<PionOpenGMItemBean> data;

    public List<PionOpenGMItemBean> getData() {
        return data;
    }

    public void setData(List<PionOpenGMItemBean> data) {
        this.data = data;
    }
}
