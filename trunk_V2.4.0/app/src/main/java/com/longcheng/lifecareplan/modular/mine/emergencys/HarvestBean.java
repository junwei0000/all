package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

public class HarvestBean extends ResponseBean {
    @SerializedName("data")
    //
    private HavestList data;

    public HavestList getData() {
        return data;
    }

    public void setData(HavestList data) {
        this.data = data;
    }
}
