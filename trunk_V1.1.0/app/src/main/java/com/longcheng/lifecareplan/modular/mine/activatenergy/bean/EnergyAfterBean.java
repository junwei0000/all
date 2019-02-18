package com.longcheng.lifecareplan.modular.mine.activatenergy.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class EnergyAfterBean {
    @SerializedName("asset")
    private String asset;
    @SerializedName("energys")
    private List<EnergyItemBean> energys;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public List<EnergyItemBean> getEnergys() {
        return energys;
    }

    public void setEnergys(List<EnergyItemBean> energys) {
        this.energys = energys;
    }
}
