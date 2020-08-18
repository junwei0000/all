package com.longcheng.lifecareplan.modular.mine.energycenter.rewardrecord.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class RewardAfterBean implements Serializable {
    @SerializedName("rewardList")
    private List<RewardItemBean> rewardList;

    public List<RewardItemBean> getRewardList() {
        return rewardList;
    }

    public void setRewardList(List<RewardItemBean> rewardList) {
        this.rewardList = rewardList;
    }
}
