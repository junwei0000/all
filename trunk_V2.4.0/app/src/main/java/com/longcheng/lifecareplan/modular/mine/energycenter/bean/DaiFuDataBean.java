package com.longcheng.lifecareplan.modular.mine.energycenter.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class DaiFuDataBean extends ResponseBean {
    @SerializedName("data")
    private DaiFuAfterBean data;

    public DaiFuAfterBean getData() {
        return data;
    }

    public void setData(DaiFuAfterBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }
}
