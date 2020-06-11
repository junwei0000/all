package com.longcheng.lifecareplan.modular.mine.pioneercenter.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PioneerAfterBean {
    PioneerItemBean entrepreneurs;
    PioneerItemBean user;
    private ArrayList<String> solarTermsEnsImg;

    public ArrayList<String> getSolarTermsEnsImg() {
        return solarTermsEnsImg;
    }

    int isApplyEntrepreneursMoney;//0未申请   1已申请未审核通过 2已申请 审核通过
    int isWxCardBag;//1 已绑卡
    int isAliCardBag;
    int isNewentreFuqibaoLess;//是否显示福祺宝额度不足弹层

    private String applyMoney;


    private String total_money;
    private ArrayList<PioneerItemBean> records;

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public ArrayList<PioneerItemBean> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<PioneerItemBean> records) {
        this.records = records;
    }

    public int getIsNewentreFuqibaoLess() {
        return isNewentreFuqibaoLess;
    }

    public void setIsNewentreFuqibaoLess(int isNewentreFuqibaoLess) {
        this.isNewentreFuqibaoLess = isNewentreFuqibaoLess;
    }

    public String getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(String applyMoney) {
        this.applyMoney = applyMoney;
    }

    public int getIsWxCardBag() {
        return isWxCardBag;
    }

    public void setIsWxCardBag(int isWxCardBag) {
        this.isWxCardBag = isWxCardBag;
    }

    public int getIsAliCardBag() {
        return isAliCardBag;
    }

    public void setIsAliCardBag(int isAliCardBag) {
        this.isAliCardBag = isAliCardBag;
    }

    public int getIsApplyEntrepreneursMoney() {
        return isApplyEntrepreneursMoney;
    }

    public void setIsApplyEntrepreneursMoney(int isApplyEntrepreneursMoney) {
        this.isApplyEntrepreneursMoney = isApplyEntrepreneursMoney;
    }

    public void setSolarTermsEnsImg(ArrayList<String> solarTermsEnsImg) {
        this.solarTermsEnsImg = solarTermsEnsImg;
    }

    public PioneerItemBean getEntrepreneurs() {
        return entrepreneurs;
    }

    public void setEntrepreneurs(PioneerItemBean entrepreneurs) {
        this.entrepreneurs = entrepreneurs;
    }

    public PioneerItemBean getUser() {
        return user;
    }

    public void setUser(PioneerItemBean user) {
        this.user = user;
    }
}
