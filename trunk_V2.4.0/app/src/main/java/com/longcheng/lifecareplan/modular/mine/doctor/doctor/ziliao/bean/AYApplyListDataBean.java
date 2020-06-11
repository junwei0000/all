package com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class AYApplyListDataBean extends ResponseBean {
    @SerializedName("data")
    private AYApplyListAfterBean data;

    public AYApplyListAfterBean getData() {
        return data;
    }

    public void setData(AYApplyListAfterBean data) {
        this.data = data;
    }

}
