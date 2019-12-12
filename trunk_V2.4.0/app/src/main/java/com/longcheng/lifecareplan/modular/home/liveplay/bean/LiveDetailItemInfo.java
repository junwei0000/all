package com.longcheng.lifecareplan.modular.home.liveplay.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2019/10/29 15:12
 * 意图：
 */
public class LiveDetailItemInfo implements Serializable {
    //info
    private String live_room_id;
    private String user_id;
    private String user_name;
    private String avatar;
    private String title;
    private String cover_url;
    private String current_jieqi_cn;
    private String address;
    private String follow_number = "0";
    private String comment_number = "0";
    private String forward_number = "0";
    private String total_person_number = "0";
    private String online_number = "0";
    private String live_broadcast_url;
    private String rebroadcast_url;
    private String video_url;
    private String status;
    private String broadcast_status;
    private String create_time;
    private int is_user_follow;
    //playUrl
    private String rtmpurl;
    private String flvurl;
    private String m3u8url;

    //gift
    private String live_gift_id;
    private String pic_url;
    private String skb;
    private String sort;
    private int currentpage;
    private int position;
    //comment
    private String live_room_comment_id;
    private int type;
    private String content;


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPage() {
        return currentpage;
    }

    public void setPage(int currentpage) {
        this.currentpage = currentpage;
    }

    public String getLive_room_comment_id() {
        return live_room_comment_id;
    }

    public void setLive_room_comment_id(String live_room_comment_id) {
        this.live_room_comment_id = live_room_comment_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIs_user_follow() {
        return is_user_follow;
    }

    public void setIs_user_follow(int is_user_follow) {
        this.is_user_follow = is_user_follow;
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

    public String getCurrent_jieqi_cn() {
        return current_jieqi_cn;
    }

    public void setCurrent_jieqi_cn(String current_jieqi_cn) {
        this.current_jieqi_cn = current_jieqi_cn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFollow_number() {
        return follow_number;
    }

    public void setFollow_number(String follow_number) {
        this.follow_number = follow_number;
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

    public String getTotal_person_number() {
        return total_person_number;
    }

    public void setTotal_person_number(String total_person_number) {
        this.total_person_number = total_person_number;
    }

    public String getOnline_number() {
        return online_number;
    }

    public void setOnline_number(String online_number) {
        this.online_number = online_number;
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

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getRtmpurl() {
        return rtmpurl;
    }

    public void setRtmpurl(String rtmpurl) {
        this.rtmpurl = rtmpurl;
    }

    public String getFlvurl() {
        return flvurl;
    }

    public void setFlvurl(String flvurl) {
        this.flvurl = flvurl;
    }

    public String getM3u8url() {
        return m3u8url;
    }

    public void setM3u8url(String m3u8url) {
        this.m3u8url = m3u8url;
    }

    public String getLive_gift_id() {
        return live_gift_id;
    }

    public void setLive_gift_id(String live_gift_id) {
        this.live_gift_id = live_gift_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getSkb() {
        return skb;
    }

    public void setSkb(String skb) {
        this.skb = skb;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
