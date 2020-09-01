package com.longcheng.lifecareplan.push.jpush.message.bean;

import com.longcheng.lifecareplan.bean.ResponseBean;

public class PairDataBean extends ResponseBean {
    private PairBean data;

    public PairBean getData() {
        return data;
    }

    public void setData(PairBean data) {
        this.data = data;
    }
}
