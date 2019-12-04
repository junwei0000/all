package com.longcheng.lifecareplan.modular.home.liveplay.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2019/10/29 15:12
 * 意图：
 */
public class VideoItemInfo implements Serializable {
    @SerializedName(value = "short_video_id", alternate = {"live_room_id"})
    private String video_id;
    private String user_id;
    private String user_name;
    private String avatar;
    private String title;
    private String cover_url;
    private String address;
    private String video_url;
    private String create_time;
    @SerializedName(value = "follow_number", alternate = {"total_person_number"})
    private String total_number= "0";;
    private String status;
    private String broadcast_status;

    public String getTotal_number() {
        return total_number;
    }

    public void setTotal_number(String total_number) {
        this.total_number = total_number;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBroadcast_status() {
        return broadcast_status;
    }

    public void setBroadcast_status(String broadcast_status) {
        this.broadcast_status = broadcast_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
