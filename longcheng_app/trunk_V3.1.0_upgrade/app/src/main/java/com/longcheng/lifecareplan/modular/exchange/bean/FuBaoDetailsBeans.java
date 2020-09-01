package com.longcheng.lifecareplan.modular.exchange.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.RiceActiviesDataBean;

import java.util.ArrayList;

public class FuBaoDetailsBeans extends ResponseBean {
    @SerializedName("data")
    protected FuBaoItems data;

    public FuBaoItems getData() {
        return data;
    }

    public void setData(FuBaoItems data) {
        this.data = data;
    }


}
