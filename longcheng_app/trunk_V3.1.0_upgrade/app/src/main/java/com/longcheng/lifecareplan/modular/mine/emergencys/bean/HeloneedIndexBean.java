package com.longcheng.lifecareplan.modular.mine.emergencys.bean;

import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.HeloneedBean;

public class HeloneedIndexBean extends ResponseBean {

    public HeloneedBean data;

    public HeloneedBean getData() {
        return data;
    }

    public void setData(HeloneedBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HeloneedIndexBean{" +
                "data=" + data +
                '}';
    }
}
