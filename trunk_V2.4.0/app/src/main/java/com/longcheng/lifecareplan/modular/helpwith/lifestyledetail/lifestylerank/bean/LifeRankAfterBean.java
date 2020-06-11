package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.lifestylerank.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class LifeRankAfterBean {
    @SerializedName("helpGoodsRanking")
    private List<LifeRankItemBean> helpGoodsRanking;
    @SerializedName("count")
    private int count;

    public List<LifeRankItemBean> getHelpGoodsRanking() {
        return helpGoodsRanking;
    }

    public void setHelpGoodsRanking(List<LifeRankItemBean> helpGoodsRanking) {
        this.helpGoodsRanking = helpGoodsRanking;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
