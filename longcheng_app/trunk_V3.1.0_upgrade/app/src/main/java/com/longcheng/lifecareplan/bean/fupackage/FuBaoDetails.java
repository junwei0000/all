package com.longcheng.lifecareplan.bean.fupackage;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

public class FuBaoDetails   extends ResponseBean {

    @SerializedName("data")
    private List<FuBaoDetailsListBean> data;

    public List<FuBaoDetailsListBean> getData() {
        return data;
    }

    public void setData(List<FuBaoDetailsListBean> data) {
        this.data = data;
    }
}
