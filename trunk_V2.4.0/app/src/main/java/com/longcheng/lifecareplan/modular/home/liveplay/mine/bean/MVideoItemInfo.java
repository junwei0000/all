package com.longcheng.lifecareplan.modular.home.liveplay.mine.bean;

import com.google.gson.annotations.SerializedName;

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
    String content;
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
    String comment_number;
    String forward_number;
    String address;
    String short_video_id;
    @SerializedName(value = "is_follow", alternate = {"is_current_user_follow"})
    int is_follow;

    String short_video_comment_id;
    int create_time;

    private int is_user_follow;//新增是否关注 0：未关注  1：已关注
    private String jieqi_branch_name;

    private int is_display;//新增打赏标题是否展示 0：不展示 1：展示
    private String help_title;
    private String help_url;

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

    public String getShort_video_comment_id() {
        return short_video_comment_id;
    }

    public void setShort_video_comment_id(String short_video_comment_id) {
        this.short_video_comment_id = short_video_comment_id;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
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

    public String getShort_video_id() {
        return short_video_id;
    }

    public void setShort_video_id(String short_video_id) {
        this.short_video_id = short_video_id;
    }

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
