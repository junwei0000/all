package com.longcheng.volunteer.modular.home.healthydelivery.list.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

/**
 * Created by Burning on 2018/9/13.
 */

public class HealthyDeliveryResultBean extends ResponseBean {
    @SerializedName("data")
    protected HealthyDeliveryData data;

    public HealthyDeliveryData getData() {
        return data;
    }

    public void setData(HealthyDeliveryData data) {
        this.data = data;
    }
}
