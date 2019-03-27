package com.longcheng.volunteer.modular.helpwith.medalrank.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class MyAfterBean implements Serializable {
    private ItemBean personal_ranking;
    private List<ItemBean> rankings;

    public ItemBean getPersonal_ranking() {
        return personal_ranking;
    }

    public void setPersonal_ranking(ItemBean personal_ranking) {
        this.personal_ranking = personal_ranking;
    }

    public List<ItemBean> getRankings() {
        return rankings;
    }

    public void setRankings(List<ItemBean> rankings) {
        this.rankings = rankings;
    }
}
