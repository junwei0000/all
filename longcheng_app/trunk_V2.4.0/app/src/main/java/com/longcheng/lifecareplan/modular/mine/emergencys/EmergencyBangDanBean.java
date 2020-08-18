package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanAfterBean;

public class EmergencyBangDanBean extends ResponseBean {
    @SerializedName("data")
    //
    private EmergencyBangDanList data;

    public EmergencyBangDanList getData() {
        return data;
    }

    public void setData(EmergencyBangDanList data) {
        this.data = data;
    }
}
