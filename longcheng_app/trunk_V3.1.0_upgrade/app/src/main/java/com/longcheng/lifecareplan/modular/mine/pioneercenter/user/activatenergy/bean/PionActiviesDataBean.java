package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionActiviesDataBean extends ResponseBean {
    @SerializedName("data")
    protected PionActiviesAfterBean data;

    public PionActiviesAfterBean getData() {
        return data;
    }

    public void setData(PionActiviesAfterBean data) {
        this.data = data;
    }
}
