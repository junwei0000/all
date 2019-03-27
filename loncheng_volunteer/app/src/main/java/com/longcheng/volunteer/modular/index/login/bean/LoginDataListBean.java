package com.longcheng.volunteer.modular.index.login.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回数组
 */

public class LoginDataListBean extends ResponseBean {
    @SerializedName("data")
    private List<LoginAfterBean> data;

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }
}
