package com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class HealTeackAfterBean extends ResponseBean {

    private int identityFlag;//1爱友  2坐堂医 3志愿者

    ArrayList<HealTeackItemBean> validFollowItems;//对号（有效的）
    ArrayList<HealTeackItemBean> followItemInfos;//6张图片
    ArrayList<HealTeackItemBean> currentFollowItem;
    HealTeackItemBean knp_msg;
    HealTeackItemBean knp_msg_new_report;

    public HealTeackItemBean getKnp_msg_new_report() {
        return knp_msg_new_report;
    }

    public void setKnp_msg_new_report(HealTeackItemBean knp_msg_new_report) {
        this.knp_msg_new_report = knp_msg_new_report;
    }

    public int getIdentityFlag() {
        return identityFlag;
    }

    public void setIdentityFlag(int identityFlag) {
        this.identityFlag = identityFlag;
    }

    public ArrayList<HealTeackItemBean> getValidFollowItems() {
        return validFollowItems;
    }

    public void setValidFollowItems(ArrayList<HealTeackItemBean> validFollowItems) {
        this.validFollowItems = validFollowItems;
    }

    public ArrayList<HealTeackItemBean> getFollowItemInfos() {
        return followItemInfos;
    }

    public void setFollowItemInfos(ArrayList<HealTeackItemBean> followItemInfos) {
        this.followItemInfos = followItemInfos;
    }

    public ArrayList<HealTeackItemBean> getCurrentFollowItem() {
        return currentFollowItem;
    }

    public void setCurrentFollowItem(ArrayList<HealTeackItemBean> currentFollowItem) {
        this.currentFollowItem = currentFollowItem;
    }

    public HealTeackItemBean getKnp_msg() {
        return knp_msg;
    }

    public void setKnp_msg(HealTeackItemBean knp_msg) {
        this.knp_msg = knp_msg;
    }
}
