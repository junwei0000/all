package com.longcheng.volunteer.modular.home.healthydelivery.list.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Burning on 2018/9/13.
 */

public class HealthyDeliveryData {
    @SerializedName("newsList")
    protected List<HealthyDeliveryBean> list;

    public List<HealthyDeliveryBean> getList() {
        return list;
    }

    public void setList(List<HealthyDeliveryBean> list) {
        this.list = list;
    }
}
