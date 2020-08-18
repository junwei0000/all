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
    String entreRenew;
    String zhuyoubaoBalance;
    String openEntre;
    String upgradeEntre;
    String flowOut;
    String buyBackFuqibao;
    String emergency;
    String betrothalGifts;
    String entrePayment;

    String money;
    ArrayList<CashFlowItemBean> data;

    public String getOpenEntre() {
        return openEntre;
    }

    public void setOpenEntre(String openEntre) {
        this.openEntre = openEntre;
    }

    public String getInflow() {
        return inflow;
    }

    public void setInflow(String inflow) {
        this.inflow = inflow;
    }

    public String getEntreRenew() {
        return entreRenew;
    }

    public void setEntreRenew(String entreRenew) {
        this.entreRenew = entreRenew;
    }

    public String getZhuyoubaoBalance() {
        return zhuyoubaoBalance;
    }

    public void setZhuyoubaoBalance(String zhuyoubaoBalance) {
        this.zhuyoubaoBalance = zhuyoubaoBalance;
    }

    public String getUpgradeEntre() {
        return upgradeEntre;
    }

    public void setUpgradeEntre(String upgradeEntre) {
        this.upgradeEntre = upgradeEntre;
    }

    public String getFlowOut() {
        return flowOut;
    }

    public void setFlowOut(String flowOut) {
        this.flowOut = flowOut;
    }

    public String getBuyBackFuqibao() {
        return buyBackFuqibao;
    }

    public void setBuyBackFuqibao(String buyBackFuqibao) {
        this.buyBackFuqibao = buyBackFuqibao;
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

    public String getEntrePayment() {
        return entrePayment;
    }

    public void setEntrePayment(String entrePayment) {
        this.entrePayment = entrePayment;
    }

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
}
