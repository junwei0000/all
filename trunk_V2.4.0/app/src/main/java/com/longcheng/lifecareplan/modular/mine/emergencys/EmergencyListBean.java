package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;


public class EmergencyListBean extends ResponseBean {
    public EmergencyListUserBean data;

    public EmergencyListUserBean getData() {
        return data;
    }

    public void setData(EmergencyListUserBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EmergencyListBean{" +
                "data=" + data +
                '}';
    }
}
