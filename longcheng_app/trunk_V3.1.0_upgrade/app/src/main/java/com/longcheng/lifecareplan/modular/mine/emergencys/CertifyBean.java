package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

public class CertifyBean extends ResponseBean {
    @SerializedName("data")
    //
    private Certify2Bean data;

    public Certify2Bean getData() {
        return data;
    }

    public void setData(Certify2Bean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CertifyBean{" +
                "data=" + data +
                '}';
    }
}