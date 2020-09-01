package com.longcheng.lifecareplan.modular.mine.emergencys.bean;

import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

public class CheckNeedOrder extends ResponseBean {
    public List<CheckNeedOrderContent> data;

    public List<CheckNeedOrderContent> getData() {
        return data;
    }

    public void setData(List<CheckNeedOrderContent> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CheckNeedOrder{" +
                "data=" + data +
                '}';
    }
}
