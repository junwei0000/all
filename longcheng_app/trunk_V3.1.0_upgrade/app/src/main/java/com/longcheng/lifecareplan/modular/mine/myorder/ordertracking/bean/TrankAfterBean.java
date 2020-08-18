package com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class TrankAfterBean implements Serializable {

    private String shipping_name;
    private String tracking_number;
    private List<TrankItemBean> traces;

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getShipping_code() {
        return tracking_number;
    }

    public void setShipping_code(String shipping_code) {
        this.tracking_number = shipping_code;
    }

    public List<TrankItemBean> getTraces() {
        return traces;
    }

    public void setTraces(List<TrankItemBean> traces) {
        this.traces = traces;
    }
}
