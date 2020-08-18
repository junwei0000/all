package com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionRewardListDataBean extends ResponseBean {
    @SerializedName("data")
    private List<PionRewardItemBean> data;

    public List<PionRewardItemBean> getData() {
        return data;
    }

    public void setData(List<PionRewardItemBean> data) {
        this.data = data;
    }
}
