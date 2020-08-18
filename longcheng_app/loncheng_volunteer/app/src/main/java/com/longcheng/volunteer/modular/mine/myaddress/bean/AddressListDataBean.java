package com.longcheng.volunteer.modular.mine.myaddress.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class AddressListDataBean extends ResponseBean {
    @SerializedName("data")
    protected AddressAfterBean data;

    public AddressAfterBean getData() {
        return data;
    }

    public void setData(AddressAfterBean data) {
        this.data = data;
    }
}
