package com.longcheng.lifecareplan.modular.home.domainname.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class domainItemBean implements Serializable {
    private String url;
    private String commune_name;
    private String brigade_name;
    private String address;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommune_name() {
        return commune_name;
    }

    public void setCommune_name(String commune_name) {
        this.commune_name = commune_name;
    }

    public String getBrigade_name() {
        return brigade_name;
    }

    public void setBrigade_name(String brigade_name) {
        this.brigade_name = brigade_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
