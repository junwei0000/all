package com.longcheng.lifecareplan.modular.helpwith.autohelp.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class AutoHelpDataBean extends ResponseBean {
    @SerializedName("data")
    private AutoHelpAfterBean data;

    public AutoHelpAfterBean getData() {
        return data;
    }

    public void setData(AutoHelpAfterBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }
}
