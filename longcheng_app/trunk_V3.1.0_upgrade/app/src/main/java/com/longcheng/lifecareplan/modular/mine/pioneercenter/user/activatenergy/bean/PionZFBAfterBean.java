package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.SelectItemBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionZFBAfterBean implements Serializable {
    protected PionZFBItemBean daichong_order;
    protected ArrayList<EnergyItemBean> pushQueueItems;

    protected String uzhufubao;//
    protected String serviceCharge;//
    protected String fuqibao;//
    protected String userRechargeListUrl;//

    int zhuyoubaoTeamRoomId;//0时，代表没有房间

    ZYBZuDuiItemBean room;
    private List<ZYBZuDuiItemBean> items;
    ZYBZuDuiItemBean entre;

    public ZYBZuDuiItemBean getEntre() {
        return entre;
    }

    public void setEntre(ZYBZuDuiItemBean entre) {
        this.entre = entre;
    }

    public ZYBZuDuiItemBean getRoom() {
        return room;
    }

    public void setRoom(ZYBZuDuiItemBean room) {
        this.room = room;
    }

    public List<ZYBZuDuiItemBean> getItems() {
        return items;
    }

    public void setItems(List<ZYBZuDuiItemBean> items) {
        this.items = items;
    }

    public int getZhuyoubaoTeamRoomId() {
        return zhuyoubaoTeamRoomId;
    }

    public void setZhuyoubaoTeamRoomId(int zhuyoubaoTeamRoomId) {
        this.zhuyoubaoTeamRoomId = zhuyoubaoTeamRoomId;
    }

    public PionZFBItemBean getDaichong_order() {
        return daichong_order;
    }

    public void setDaichong_order(PionZFBItemBean daichong_order) {
        this.daichong_order = daichong_order;
    }

    public ArrayList<EnergyItemBean> getPushQueueItems() {
        return pushQueueItems;
    }

    public void setPushQueueItems(ArrayList<EnergyItemBean> pushQueueItems) {
        this.pushQueueItems = pushQueueItems;
    }

    public String getUzhufubao() {
        return uzhufubao;
    }

    public void setUzhufubao(String uzhufubao) {
        this.uzhufubao = uzhufubao;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getFuqibao() {
        return fuqibao;
    }

    public void setFuqibao(String fuqibao) {
        this.fuqibao = fuqibao;
    }

    public String getUserRechargeListUrl() {
        return userRechargeListUrl;
    }

    public void setUserRechargeListUrl(String userRechargeListUrl) {
        this.userRechargeListUrl = userRechargeListUrl;
    }
}
