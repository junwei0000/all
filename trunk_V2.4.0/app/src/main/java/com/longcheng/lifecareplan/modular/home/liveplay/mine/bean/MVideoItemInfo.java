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
    String video_url;


    String user_follow_id;
    String follow_user_id;
    String user_name;
    String avatar;
    String show_title;
    int position;
    String follow_number;
    String address;

    public String getFollow_number() {
        return follow_number;
    }

    public void setFollow_number(String follow_number) {
        this.follow_number = follow_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUser_follow_id() {
        return user_follow_id;
    }

    public void setUser_follow_id(String user_follow_id) {
        this.user_follow_id = user_follow_id;
    }

    public String getFollow_user_id() {
        return follow_user_id;
    }

    public void setFollow_user_id(String follow_user_id) {
        this.follow_user_id = follow_user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getShow_title() {
        return show_title;
    }

    public void setShow_title(String show_title) {
        this.show_title = show_title;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

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
