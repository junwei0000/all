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
    private String content;
    private String cover_url;
    private String address;
    private String comment_number = "0";
    private String forward_number = "0";
    private String video_url;
    private String create_time;
    @SerializedName(value = "follow_number", alternate = {"total_person_number"})
    private String total_number = "0";
    private String status;
    private String broadcast_status;
    private int is_follow;//是否点赞
    private int is_user_follow;//新增是否关注 0：未关注  1：已关注
    private String jieqi_branch_name;

    private int is_display;//新增打赏标题是否展示 0：不展示 1：展示
    private String help_title;
    private String help_url;

    public int getIs_display() {
        return is_display;
    }

    public void setIs_display(int is_display) {
        this.is_display = is_display;
    }

    public String getHelp_title() {
        return help_title;
    }

    public void setHelp_title(String help_title) {
        this.help_title = help_title;
    }

    public String getHelp_url() {
        return help_url;
    }

    public void setHelp_url(String help_url) {
        this.help_url = help_url;
    }

    public int getIs_user_follow() {
        return is_user_follow;
    }

    public void setIs_user_follow(int is_user_follow) {
        this.is_user_follow = is_user_follow;
    }

    public String getJieqi_branch_name() {
        return jieqi_branch_name;
    }

    public void setJieqi_branch_name(String jieqi_branch_name) {
        this.jieqi_branch_name = jieqi_branch_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment_number() {
        return comment_number;
    }

    public void setComment_number(String comment_number) {
        this.comment_number = comment_number;
    }

    public String getForward_number() {
        return forward_number;
    }

    public void setForward_number(String forward_number) {
        this.forward_number = forward_number;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

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
