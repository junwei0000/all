package com.longcheng.lifecareplan.modular.home.liveplay.bean;

import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2019/10/29 15:12
 * 意图：
 */
public class LivePlayItemInfo extends ResponseBean {
    private String uid;
    private int thumb;
    private String playTile;
    private String name;
    private String jieqi;
    private String time;

    public LivePlayItemInfo(String uid, int thumb, String playTile, String name,String jieqi,String time) {
        this.uid = uid;
        this.playTile = playTile;
        this.name = name;
        this.jieqi = jieqi;
        this.thumb = thumb;
        this.time = time;
    }

    public String getJieqi() {
        return jieqi;
    }

    public void setJieqi(String jieqi) {
        this.jieqi = jieqi;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getThumb() {
        return thumb;
    }

    public void setThumb(int thumb) {
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlayTile() {
        return playTile;
    }

    public void setPlayTile(String playTile) {
        this.playTile = playTile;
    }
}
