package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.longcheng.lifecareplan.bean.ResponseBean;

public class EmergencysPayBean extends ResponseBean {
    public EmergencysPay2Bean data;

    public EmergencysPay2Bean getData() {
        return data;
    }

    public void setData(EmergencysPay2Bean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EmergencysPayBean{" +
                "data=" + data +
                '}';
    }
}
