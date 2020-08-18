package com.longcheng.lifecareplan.modular.home.liveplay.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2019/10/29 15:12
 * 意图：
 */
public class LiveDetailInfo implements Serializable {
    private LiveDetailItemInfo info;
    private ArrayList<LiveDetailItemInfo> gift;
    private LiveDetailItemInfo playUrl;

    private ArrayList<LiveDetailItemInfo> ranking;
    private ArrayList<LiveDetailItemInfo> comment;

    public ArrayList<LiveDetailItemInfo> getRanking() {
        return ranking;
    }

    public void setRanking(ArrayList<LiveDetailItemInfo> ranking) {
        this.ranking = ranking;
    }

    public ArrayList<LiveDetailItemInfo> getComment() {
        return comment;
    }

    public void setComment(ArrayList<LiveDetailItemInfo> comment) {
        this.comment = comment;
    }

    public LiveDetailItemInfo getInfo() {
        return info;
    }

    public void setInfo(LiveDetailItemInfo info) {
        this.info = info;
    }

    public ArrayList<LiveDetailItemInfo> getGift() {
        return gift;
    }

    public void setGift(ArrayList<LiveDetailItemInfo> gift) {
        this.gift = gift;
    }

    public LiveDetailItemInfo getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(LiveDetailItemInfo playUrl) {
        this.playUrl = playUrl;
    }
}
