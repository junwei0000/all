package com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class HealTeackDataBean extends ResponseBean {
    @SerializedName("data")
    private HealTeackAfterBean data;

    public HealTeackAfterBean getData() {
        return data;
    }

    public void setData(HealTeackAfterBean data) {
        this.data = data;
    }

}
