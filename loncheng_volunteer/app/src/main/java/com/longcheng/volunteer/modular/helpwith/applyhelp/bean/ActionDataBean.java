package com.longcheng.volunteer.modular.helpwith.applyhelp.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回数组
 */

public class ActionDataBean extends ResponseBean {
    @SerializedName("data")
    private ActionItemBean data;

    public ActionItemBean getData() {
        return data;
    }

    public void setData(ActionItemBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }


}
