package com.longcheng.lifecareplan.modular.home.domainname.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class domainAfterBean {

    private int is_bind;
    private domainItemBean banner;

    private domainItemBean commune;

    private List<String> notice;

    public int getIs_bind() {
        return is_bind;
    }

    public void setIs_bind(int is_bind) {
        this.is_bind = is_bind;
    }

    public domainItemBean getBanner() {
        return banner;
    }

    public void setBanner(domainItemBean banner) {
        this.banner = banner;
    }

    public domainItemBean getCommune() {
        return commune;
    }

    public void setCommune(domainItemBean commune) {
        this.commune = commune;
    }

    public List<String> getNotice() {
        return notice;
    }

    public void setNotice(List<String> notice) {
        this.notice = notice;
    }
}
