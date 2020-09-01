package com.longcheng.lifecareplan.bean.fupackage;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

public class HoldBean   extends ResponseBean {

    @SerializedName("data")
    private List<OneDayOneHoldBean> data;

    public List<OneDayOneHoldBean> getData() {
        return data;
    }

    public void setData(List<OneDayOneHoldBean> data) {
        this.data = data;
    }
}
