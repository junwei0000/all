package com.longcheng.lifecareplan.modular.home.liveplay.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2019/10/29 15:12
 * 意图：
 */
public class LiveStatusInfo implements Serializable {
    private String live_user_id;
    private String user_id;
    private int is_charge;//是否收费 1：收费 2：免费
    private String cost;//收费金额 单位:skb
    private int type;//类型 1：短期（申请） 2：长期
    private int status;//审核状态 0：未审核 1：审核通过 -1：驳回

    private String pushUrl;
    private String live_room_id;

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getLive_room_id() {
        return live_room_id;
    }

    public void setLive_room_id(String live_room_id) {
        this.live_room_id = live_room_id;
    }

    public String getLive_user_id() {
        return live_user_id;
    }

    public void setLive_user_id(String live_user_id) {
        this.live_user_id = live_user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getIs_charge() {
        return is_charge;
    }

    public void setIs_charge(int is_charge) {
        this.is_charge = is_charge;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
