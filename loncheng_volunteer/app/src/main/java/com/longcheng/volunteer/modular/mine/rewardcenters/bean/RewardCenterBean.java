package com.longcheng.volunteer.modular.mine.rewardcenters.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Burning on 2018/9/4.
 */

public class RewardCenterBean {
    @SerializedName("cho_count")
    int cho_count;
    @SerializedName("cho_reward")
    int cho_reward;
    @SerializedName("total")
    int total;
    @SerializedName("total_page")
    int total_page;
    @SerializedName("list")
    List<RewardDetail> list;


    public int getCho_count() {
        return cho_count;
    }

    public void setCho_count(int cho_count) {
        this.cho_count = cho_count;
    }

    public int getCho_reward() {
        return cho_reward;
    }

    public void setCho_reward(int cho_reward) {
        this.cho_reward = cho_reward;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<RewardDetail> getList() {
        return list;
    }

    public void setList(List<RewardDetail> list) {
        this.list = list;
    }
}
