package com.longcheng.lifecareplan.modular.mine.cashflow.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class CashFlowAfterBean {
    String inflow;
    String byZhufubao;
    String reward;
    String renew;
    String flowOut;
    String recoveryZhufubao;
    String emergency;
    String betrothalGifts;
    String subCharge;
    String subpay;

    String money;
    ArrayList<CashFlowItemBean> data;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public ArrayList<CashFlowItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<CashFlowItemBean> data) {
        this.data = data;
    }

    public String getInflow() {
        return inflow;
    }

    public void setInflow(String inflow) {
        this.inflow = inflow;
    }

    public String getByZhufubao() {
        return byZhufubao;
    }

    public void setByZhufubao(String byZhufubao) {
        this.byZhufubao = byZhufubao;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getRenew() {
        return renew;
    }

    public void setRenew(String renew) {
        this.renew = renew;
    }

    public String getFlowOut() {
        return flowOut;
    }

    public void setFlowOut(String flowOut) {
        this.flowOut = flowOut;
    }

    public String getRecoveryZhufubao() {
        return recoveryZhufubao;
    }

    public void setRecoveryZhufubao(String recoveryZhufubao) {
        this.recoveryZhufubao = recoveryZhufubao;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getBetrothalGifts() {
        return betrothalGifts;
    }

    public void setBetrothalGifts(String betrothalGifts) {
        this.betrothalGifts = betrothalGifts;
    }

    public String getSubCharge() {
        return subCharge;
    }

    public void setSubCharge(String subCharge) {
        this.subCharge = subCharge;
    }

    public String getSubpay() {
        return subpay;
    }

    public void setSubpay(String subpay) {
        this.subpay = subpay;
    }
}
