package com.longcheng.volunteer.modular.helpwith.applyhelp.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回数组
 */

public class ExplainDataBean extends ResponseBean {
    @SerializedName("data")
    private ExplainAfterBean data;

    public ExplainAfterBean getData() {
        return data;
    }

    public void setData(ExplainAfterBean data) {
        this.data = data;
    }
}
