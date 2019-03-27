package com.longcheng.volunteer.modular.helpwith.energydetail.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PayItemBean implements Serializable {
    private String solar_terms_name;
    private String start_name;
    private String photo;
    private int type;
    private String url;
    private String slogan;
    private String advertisement_photo_url;
    private int action_goods_id;
    private String qiming_user_id;

    public int getAction_goods_id() {
        return action_goods_id;
    }

    public void setAction_goods_id(int action_goods_id) {
        this.action_goods_id = action_goods_id;
    }

    public String getQiming_user_id() {
        return qiming_user_id;
    }

    public void setQiming_user_id(String qiming_user_id) {
        this.qiming_user_id = qiming_user_id;
    }

    public String getSolar_terms_name() {
        return solar_terms_name;
    }

    public void setSolar_terms_name(String solar_terms_name) {
        this.solar_terms_name = solar_terms_name;
    }

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAdvertisement_photo_url() {
        return advertisement_photo_url;
    }

    public void setAdvertisement_photo_url(String advertisement_photo_url) {
        this.advertisement_photo_url = advertisement_photo_url;
    }
}
