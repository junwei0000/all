package com.longcheng.lifecareplan.modular.mine.recovercash.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderAfterBean;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class AcountInfoDataBean extends ResponseBean {
    @SerializedName("data")
    protected AcountAfterBean data;

    public AcountAfterBean getData() {
        return data;
    }

    public void setData(AcountAfterBean data) {
        this.data = data;
    }
}
