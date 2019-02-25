package com.longcheng.lifecareplan.modular.mine.rebirth.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.changeinviter.bean.InviteAfterBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class RebirthDataBean extends ResponseBean {
    @SerializedName("data")
    private RebirthAfterBean data;

    public RebirthAfterBean getData() {
        return data;
    }

    public void setData(RebirthAfterBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }
}
