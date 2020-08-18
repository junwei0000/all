package com.longcheng.lifecareplan.modular.helpwith.reportbless.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class ReportAfterBean implements Serializable {
    @SerializedName("count")
    private int count;
    @SerializedName("exponent")
    private String exponent;
    @SerializedName("lists")
    private List<ReportItemBean> lists;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getExponent() {
        return exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

    public List<ReportItemBean> getLists() {
        return lists;
    }

    public void setLists(List<ReportItemBean> lists) {
        this.lists = lists;
    }
}
