package com.longcheng.lifecareplan.modular.mine.activatenergy.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class EnergyAfterBean {
    EnergyItemBean chatuser;
    @SerializedName("assetConfig")
    private List<EnergyItemBean> energys;
    @SerializedName("youzanConfig")
    private List<EnergyItemBean> youzanConfig;
    private int identityType;//3 祝福师   2普通用户

    private String userRechargeListUrl;
    private String cookie_key;
    private String cookie_value;

    private int status;//无异常订单status为2
    private String callbackUrl;

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserRechargeListUrl() {
        return userRechargeListUrl;
    }

    public void setUserRechargeListUrl(String userRechargeListUrl) {
        this.userRechargeListUrl = userRechargeListUrl;
    }

    public EnergyItemBean getChatuser() {
        return chatuser;
    }

    public void setChatuser(EnergyItemBean chatuser) {
        this.chatuser = chatuser;
    }

    public List<EnergyItemBean> getYouzanConfig() {
        return youzanConfig;
    }

    public void setYouzanConfig(List<EnergyItemBean> youzanConfig) {
        this.youzanConfig = youzanConfig;
    }

    public int getIdentityType() {
        return identityType;
    }

    public void setIdentityType(int identityType) {
        this.identityType = identityType;
    }

    public String getCookie_key() {
        return cookie_key;
    }

    public void setCookie_key(String cookie_key) {
        this.cookie_key = cookie_key;
    }

    public String getCookie_value() {
        return cookie_value;
    }

    public void setCookie_value(String cookie_value) {
        this.cookie_value = cookie_value;
    }


    public List<EnergyItemBean> getEnergys() {
        return energys;
    }

    public void setEnergys(List<EnergyItemBean> energys) {
        this.energys = energys;
    }
}
