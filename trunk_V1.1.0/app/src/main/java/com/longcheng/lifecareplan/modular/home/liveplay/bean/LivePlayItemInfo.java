package com.longcheng.lifecareplan.modular.home.liveplay.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2019/10/29 15:12
 * 意图：
 */
public class LivePlayItemInfo extends ResponseBean {
    @SerializedName("pushurl")
    private String pushurl;

    private String rtmpurl;
    private String flvurl;
    private String m3u8url;

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

    public String getPushurl() {
        return pushurl;
    }

    public void setPushurl(String pushurl) {
        this.pushurl = pushurl;
    }
}
