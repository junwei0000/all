package com.longcheng.volunteer.modular.mine.rewardcenters.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

/**
 * Created by Burning on 2018/9/4.
 */

public class RewardCentersResultBean extends ResponseBean {
    @SerializedName("data")
    protected RewardCenterBean data;

    public RewardCenterBean getData() {
        return data;
    }

    public void setData(RewardCenterBean data) {
        this.data = data;
    }
}
