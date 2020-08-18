package com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class NFCDetailAfterBean {
    @SerializedName("user")
    private NFCDetailItemBean user;
    @SerializedName("msg")
    private NFCDetailItemBean msg;
    @SerializedName("currentUser")
    private NFCDetailItemBean currentUser;
    @SerializedName("notStr")
    private String notStr;
    private String usageMethod;
    private int diffRecordNum;
    private int totalRecordNum;
    private int isComplete;
    private ArrayList<NFCDetailItemBean> mutual_help_money_all;
    private ArrayList<NFCDetailItemBean> helpAbilityRanking;
    @SerializedName("orderUserInfo")
    private NFCDetailItemBean orderUserInfo;



    public int getTotalRecordNum() {
        return totalRecordNum;
    }

    public void setTotalRecordNum(int totalRecordNum) {
        this.totalRecordNum = totalRecordNum;
    }

    public String getUsageMethod() {
        return usageMethod;
    }

    public void setUsageMethod(String usageMethod) {
        this.usageMethod = usageMethod;
    }

    public int getDiffRecordNum() {
        return diffRecordNum;
    }

    public void setDiffRecordNum(int diffRecordNum) {
        this.diffRecordNum = diffRecordNum;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    public NFCDetailItemBean getOrderUserInfo() {
        return orderUserInfo;
    }

    public void setOrderUserInfo(NFCDetailItemBean orderUserInfo) {
        this.orderUserInfo = orderUserInfo;
    }

    public ArrayList<NFCDetailItemBean> getHelpAbilityRanking() {
        return helpAbilityRanking;
    }

    public void setHelpAbilityRanking(ArrayList<NFCDetailItemBean> helpAbilityRanking) {
        this.helpAbilityRanking = helpAbilityRanking;
    }

    public ArrayList<NFCDetailItemBean> getMutual_help_money_all() {
        return mutual_help_money_all;
    }

    public void setMutual_help_money_all(ArrayList<NFCDetailItemBean> mutual_help_money_all) {
        this.mutual_help_money_all = mutual_help_money_all;
    }

    public NFCDetailItemBean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(NFCDetailItemBean currentUser) {
        this.currentUser = currentUser;
    }

    public NFCDetailItemBean getUser() {
        return user;
    }

    public void setUser(NFCDetailItemBean user) {
        this.user = user;
    }

    public NFCDetailItemBean getMsg() {
        return msg;
    }

    public void setMsg(NFCDetailItemBean msg) {
        this.msg = msg;
    }

    public String getNotStr() {
        return notStr;
    }

    public void setNotStr(String notStr) {
        this.notStr = notStr;
    }

}
