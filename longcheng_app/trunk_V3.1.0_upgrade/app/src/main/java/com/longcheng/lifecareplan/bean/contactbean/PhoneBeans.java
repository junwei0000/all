package com.longcheng.lifecareplan.bean.contactbean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.bean.fupackage.FuBaoDetailsListBean;

import java.util.List;

public class PhoneBeans  extends ResponseBean {


    @SerializedName("data")
    private List<PhoneBean> data;

    public List<PhoneBean> getData() {
        return data;
    }

    public void setData(List<PhoneBean> data) {
        this.data = data;
    }
}
