package com.longcheng.lifecareplan.modular.helpwith.myGratitude.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class MyAfterBean implements Serializable {
    //列表
    private String mutualHelpBlessMeSum;
    private String mutualHelpBlessMeCount;
    private List<ItemBean> mutualHelpBlessMeLists;

    //明细
    private List<ItemBean> mutualHelpBlessMeDetails;

    public String getMutualHelpBlessMeSum() {
        return mutualHelpBlessMeSum;
    }

    public void setMutualHelpBlessMeSum(String mutualHelpBlessMeSum) {
        this.mutualHelpBlessMeSum = mutualHelpBlessMeSum;
    }

    public String getMutualHelpBlessMeCount() {
        return mutualHelpBlessMeCount;
    }

    public void setMutualHelpBlessMeCount(String mutualHelpBlessMeCount) {
        this.mutualHelpBlessMeCount = mutualHelpBlessMeCount;
    }

    public List<ItemBean> getMutualHelpBlessMeLists() {
        return mutualHelpBlessMeLists;
    }

    public void setMutualHelpBlessMeLists(List<ItemBean> mutualHelpBlessMeLists) {
        this.mutualHelpBlessMeLists = mutualHelpBlessMeLists;
    }

    public List<ItemBean> getMutualHelpBlessMeDetails() {
        return mutualHelpBlessMeDetails;
    }

    public void setMutualHelpBlessMeDetails(List<ItemBean> mutualHelpBlessMeDetails) {
        this.mutualHelpBlessMeDetails = mutualHelpBlessMeDetails;
    }
}
