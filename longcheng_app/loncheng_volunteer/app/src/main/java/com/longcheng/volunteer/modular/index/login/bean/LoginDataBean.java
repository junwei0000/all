package com.longcheng.volunteer.modular.index.login.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class LoginDataBean extends ResponseBean {
    @SerializedName("data")
    private LoginAfterBean data;

    public LoginAfterBean getData() {
        return data;
    }

    public void setData(LoginAfterBean data) {
        this.data = data;
    }

}
