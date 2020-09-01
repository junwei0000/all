package com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.zyblist.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class ZYBRecordListDataBean extends ResponseBean {
    @SerializedName("data")
    private List<ZYBRecordItemBean> data;

    public List<ZYBRecordItemBean> getData() {
        return data;
    }

    public void setData(List<ZYBRecordItemBean> data) {
        this.data = data;
    }
}
