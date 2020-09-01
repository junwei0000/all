package com.longcheng.lifecareplan.modular.mine.emergencys.bean;

import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

public class SubmitMoneyBean extends ResponseBean {
    public List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

}
