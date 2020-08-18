package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.longcheng.lifecareplan.bean.ResponseBean;

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
