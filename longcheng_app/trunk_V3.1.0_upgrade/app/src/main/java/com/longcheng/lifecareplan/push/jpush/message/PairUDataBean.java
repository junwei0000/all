package com.longcheng.lifecareplan.push.jpush.message;

import com.longcheng.lifecareplan.bean.ResponseBean;

public class PairUDataBean extends ResponseBean {
    private PairUBean data;

    public PairUBean getData() {
        return data;
    }

    public void setData(PairUBean data) {
        this.data = data;
    }
}
