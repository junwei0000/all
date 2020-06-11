package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PionDaiFuAfterBean {
    private List<PionDaiFuItemBean> lists;
    PionDaiFuItemBean bankInfo;
    String zhufubao_monry;


    //祝福宝奖励
    PionDaiFuItemBean knp_team_bind_card;
    PionDaiFuItemBean user_zhufubao_reward_limit;


    //奖励记录
    private List<PionDaiFuItemBean> rewardList;

    public List<PionDaiFuItemBean> getRewardList() {
        return rewardList;
    }

    public void setRewardList(List<PionDaiFuItemBean> rewardList) {
        this.rewardList = rewardList;
    }

    public PionDaiFuItemBean getKnp_team_bind_card() {
        return knp_team_bind_card;
    }

    public void setKnp_team_bind_card(PionDaiFuItemBean knp_team_bind_card) {
        this.knp_team_bind_card = knp_team_bind_card;
    }

    public PionDaiFuItemBean getUser_zhufubao_reward_limit() {
        return user_zhufubao_reward_limit;
    }

    public void setUser_zhufubao_reward_limit(PionDaiFuItemBean user_zhufubao_reward_limit) {
        this.user_zhufubao_reward_limit = user_zhufubao_reward_limit;
    }

    public PionDaiFuItemBean getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(PionDaiFuItemBean bankInfo) {
        this.bankInfo = bankInfo;
    }

    public String getZhufubao_monry() {
        return zhufubao_monry;
    }

    public void setZhufubao_monry(String zhufubao_monry) {
        this.zhufubao_monry = zhufubao_monry;
    }

    public List<PionDaiFuItemBean> getLists() {
        return lists;
    }

    public void setLists(List<PionDaiFuItemBean> lists) {
        this.lists = lists;
    }
}
