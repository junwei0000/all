package com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.pionopen.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionOpenListDataBean extends ResponseBean {
    @SerializedName("data")
    private List<PionOpenListItemBean> data;

    public List<PionOpenListItemBean> getData() {
        return data;
    }

    public void setData(List<PionOpenListItemBean> data) {
        this.data = data;
    }
}
