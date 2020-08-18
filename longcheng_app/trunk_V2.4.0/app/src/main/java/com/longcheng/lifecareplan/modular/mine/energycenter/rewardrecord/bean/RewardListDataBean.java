package com.longcheng.lifecareplan.modular.mine.energycenter.rewardrecord.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class RewardListDataBean extends ResponseBean {
    @SerializedName("data")
    protected RewardAfterBean data;

    public RewardAfterBean getData() {
        return data;
    }

    public void setData(RewardAfterBean data) {
        this.data = data;
    }
}
