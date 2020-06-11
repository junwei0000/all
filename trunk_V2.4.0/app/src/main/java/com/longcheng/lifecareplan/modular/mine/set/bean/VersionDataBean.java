package com.longcheng.lifecareplan.modular.mine.set.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class VersionDataBean extends ResponseBean {
    @SerializedName("data")
    protected VersionAfterBean data;

    public VersionAfterBean getData() {
        return data;
    }

    public void setData(VersionAfterBean data) {
        this.data = data;
    }
}
