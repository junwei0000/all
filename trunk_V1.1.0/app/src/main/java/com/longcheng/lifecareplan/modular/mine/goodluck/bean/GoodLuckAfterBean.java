package com.longcheng.lifecareplan.modular.mine.goodluck.bean;

import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.OpenRedAfterBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class GoodLuckAfterBean implements Serializable {
    private int count;
    private double money;
    private double skb;
    private List<GoodLuckBean> list;
    private List<GoodLuckBean> solar_terms_endorsement_star;

    public List<GoodLuckBean> getSolar_terms_endorsement_star() {
        return solar_terms_endorsement_star;
    }

    public void setSolar_terms_endorsement_star(List<GoodLuckBean> solar_terms_endorsement_star) {
        this.solar_terms_endorsement_star = solar_terms_endorsement_star;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getSkb() {
        return skb;
    }

    public void setSkb(double skb) {
        this.skb = skb;
    }

    public List<GoodLuckBean> getList() {
        return list;
    }

    public void setList(List<GoodLuckBean> list) {
        this.list = list;
    }

}
