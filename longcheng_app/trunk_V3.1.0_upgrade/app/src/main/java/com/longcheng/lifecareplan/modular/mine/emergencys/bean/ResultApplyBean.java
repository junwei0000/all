package com.longcheng.lifecareplan.modular.mine.emergencys.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;


public class ResultApplyBean extends ResponseBean {
    //
    public List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
