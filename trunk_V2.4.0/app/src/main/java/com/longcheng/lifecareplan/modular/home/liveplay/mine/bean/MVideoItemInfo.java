package com.longcheng.lifecareplan.modular.home.liveplay.mine.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2019/10/29 15:12
 * 意图：
 */
public class MVideoItemInfo implements Serializable {
    String live_room_id;
    String user_id;
    String title;
    String cover_url;
    String total_person_number;
    String live_broadcast_url;
    String rebroadcast_url;
    String broadcast_status;

    public String getLive_room_id() {
        return live_room_id;
    }

    public void setLive_room_id(String live_room_id) {
        this.live_room_id = live_room_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getTotal_person_number() {
        return total_person_number;
    }

    public void setTotal_person_number(String total_person_number) {
        this.total_person_number = total_person_number;
    }

    public String getLive_broadcast_url() {
        return live_broadcast_url;
    }

    public void setLive_broadcast_url(String live_broadcast_url) {
        this.live_broadcast_url = live_broadcast_url;
    }

    public String getRebroadcast_url() {
        return rebroadcast_url;
    }

    public void setRebroadcast_url(String rebroadcast_url) {
        this.rebroadcast_url = rebroadcast_url;
    }

    public String getBroadcast_status() {
        return broadcast_status;
    }

    public void setBroadcast_status(String broadcast_status) {
        this.broadcast_status = broadcast_status;
    }
}
