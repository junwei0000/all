package com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.caililist.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionCailiListDataBean extends ResponseBean {
    @SerializedName("data")
    private List<PionCailiItemBean> data;

    public List<PionCailiItemBean> getData() {
        return data;
    }

    public void setData(List<PionCailiItemBean> data) {
        this.data = data;
    }
}
