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


    private List<ListItemBean> blessCards;
    private List<ListItemBean> broadcastLists;


    //爱的成就
    private String complete_money="0";
    private List<LoveItemBean> response;
    private List<LoveItemBean> recive;




    public String getComplete_money() {
        return complete_money;
    }

    public void setComplete_money(String complete_money) {
        this.complete_money = complete_money;
    }

    public List<LoveItemBean> getResponse() {
        return response;
    }

    public void setResponse(List<LoveItemBean> response) {
        this.response = response;
    }

    public List<LoveItemBean> getRecive() {
        return recive;
    }

    public void setRecive(List<LoveItemBean> recive) {
        this.recive = recive;
    }

    public List<ListItemBean> getBlessCards() {
        return blessCards;
    }

    public void setBlessCards(List<ListItemBean> blessCards) {
        this.blessCards = blessCards;
    }

    public List<ListItemBean> getBroadcastLists() {
        return broadcastLists;
    }

    public void setBroadcastLists(List<ListItemBean> broadcastLists) {
        this.broadcastLists = broadcastLists;
    }

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
