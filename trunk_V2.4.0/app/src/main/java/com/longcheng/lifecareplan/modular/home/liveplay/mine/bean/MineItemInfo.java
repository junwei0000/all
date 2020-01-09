package com.longcheng.lifecareplan.modular.home.liveplay.mine.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2019/10/29 15:12
 * 意图：
 */
public class MineItemInfo implements Serializable {
    private MineItemInfo userExtra;
    private String user_extra_id;
    private String user_id;
    private String like_number;
    private String skb;
    private String show_title;
    private String user_name;
    private String avatar;

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

    public MineItemInfo getUserExtra() {
        return userExtra;
    }

    public void setUserExtra(MineItemInfo userExtra) {
        this.userExtra = userExtra;
    }

    public String getUser_extra_id() {
        return user_extra_id;
    }

    public void setUser_extra_id(String user_extra_id) {
        this.user_extra_id = user_extra_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLike_number() {
        return like_number;
    }

    public void setLike_number(String like_number) {
        this.like_number = like_number;
    }

    public String getSkb() {
        return skb;
    }

    public void setSkb(String skb) {
        this.skb = skb;
    }

    public String getShow_title() {
        return show_title;
    }

    public void setShow_title(String show_title) {
        this.show_title = show_title;
    }
}
