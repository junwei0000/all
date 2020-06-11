package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PionDaiCAfterBean {
    @SerializedName(value = "lists", alternate = {"order"})
    private List<PionDaiCItemBean> lists;

    public List<PionDaiCItemBean> getLists() {
        return lists;
    }

    public void setLists(List<PionDaiCItemBean> lists) {
        this.lists = lists;
    }
}
