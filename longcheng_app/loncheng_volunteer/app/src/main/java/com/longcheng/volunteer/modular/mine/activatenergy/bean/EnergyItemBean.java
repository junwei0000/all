package com.longcheng.volunteer.modular.mine.activatenergy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class EnergyItemBean {
    @SerializedName("money")
    private String money;
    @SerializedName("total_energy")
    private String total_energy;
    @SerializedName("skb")
    private String skb;
    @SerializedName("first_energy")
    private String first_energy;
    @SerializedName("presenter_energy")
    private String presenter_energy;
    @SerializedName("is_default")
    private int is_default;


    public EnergyItemBean(String money) {
        this.money = money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setTotal_energy(String total_energy) {
        this.total_energy = total_energy;
    }

    public void setSkb(String skb) {
        this.skb = skb;
    }

    public void setFirst_energy(String first_energy) {
        this.first_energy = first_energy;
    }

    public void setPresenter_energy(String presenter_energy) {
        this.presenter_energy = presenter_energy;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public String getMoney() {
        return money;
    }

    public String getTotal_energy() {
        return total_energy;
    }

    public String getSkb() {
        return skb;
    }

    public String getFirst_energy() {
        return first_energy;
    }

    public String getPresenter_energy() {
        return presenter_energy;
    }

    public int getIs_default() {
        return is_default;
    }
}
