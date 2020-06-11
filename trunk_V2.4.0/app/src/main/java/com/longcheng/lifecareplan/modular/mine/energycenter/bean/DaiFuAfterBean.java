package com.longcheng.lifecareplan.modular.mine.energycenter.bean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class DaiFuAfterBean {
    private List<DaiFuItemBean> lists;
    DaiFuItemBean bankInfo;
    String zhufubao_monry;


    //祝福宝奖励
    DaiFuItemBean knp_team_bind_card;
    DaiFuItemBean user_zhufubao_reward_limit;


    //奖励记录
    private List<DaiFuItemBean> rewardList;

    public List<DaiFuItemBean> getRewardList() {
        return rewardList;
    }

    public void setRewardList(List<DaiFuItemBean> rewardList) {
        this.rewardList = rewardList;
    }

    public DaiFuItemBean getKnp_team_bind_card() {
        return knp_team_bind_card;
    }

    public void setKnp_team_bind_card(DaiFuItemBean knp_team_bind_card) {
        this.knp_team_bind_card = knp_team_bind_card;
    }

    public DaiFuItemBean getUser_zhufubao_reward_limit() {
        return user_zhufubao_reward_limit;
    }

    public void setUser_zhufubao_reward_limit(DaiFuItemBean user_zhufubao_reward_limit) {
        this.user_zhufubao_reward_limit = user_zhufubao_reward_limit;
    }

    public DaiFuItemBean getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(DaiFuItemBean bankInfo) {
        this.bankInfo = bankInfo;
    }

    public String getZhufubao_monry() {
        return zhufubao_monry;
    }

    public void setZhufubao_monry(String zhufubao_monry) {
        this.zhufubao_monry = zhufubao_monry;
    }

    public List<DaiFuItemBean> getLists() {
        return lists;
    }

    public void setLists(List<DaiFuItemBean> lists) {
        this.lists = lists;
    }
}
