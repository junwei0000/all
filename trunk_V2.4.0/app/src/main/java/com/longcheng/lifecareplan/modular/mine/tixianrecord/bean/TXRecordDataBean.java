package com.longcheng.lifecareplan.modular.mine.tixianrecord.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class TXRecordDataBean extends ResponseBean {
    @SerializedName("data")
    protected TXRecordAfterBean data;

    public TXRecordAfterBean getData() {
        return data;
    }

    public void setData(TXRecordAfterBean data) {
        this.data = data;
    }
}
