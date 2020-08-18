package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

public class CSRecordBean extends ResponseBean {
    @SerializedName("data")
    //
    private List<CSRecordList> data;

    public List<CSRecordList> getData() {
        return data;
    }

    public void setData(List<CSRecordList> data) {
        this.data = data;
    }
}
