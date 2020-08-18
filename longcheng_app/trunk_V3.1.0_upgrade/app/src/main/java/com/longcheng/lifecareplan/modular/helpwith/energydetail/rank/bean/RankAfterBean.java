package com.longcheng.lifecareplan.modular.helpwith.energydetail.rank.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class RankAfterBean {
    @SerializedName("helpAbilityRanking")
    private List<RankItemBean> helpAbilityRanking;
    @SerializedName("supplierUserAndActionMappingJson")
    private List<RankItemBean> supplierUserAndActionMappingJson;
    @SerializedName("count")
    private int count;

    public List<RankItemBean> getSupplierUserAndActionMappingJson() {
        return supplierUserAndActionMappingJson;
    }

    public void setSupplierUserAndActionMappingJson(List<RankItemBean> supplierUserAndActionMappingJson) {
        this.supplierUserAndActionMappingJson = supplierUserAndActionMappingJson;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RankItemBean> getHelpAbilityRanking() {
        return helpAbilityRanking;
    }

    public void setHelpAbilityRanking(List<RankItemBean> helpAbilityRanking) {
        this.helpAbilityRanking = helpAbilityRanking;
    }
}
