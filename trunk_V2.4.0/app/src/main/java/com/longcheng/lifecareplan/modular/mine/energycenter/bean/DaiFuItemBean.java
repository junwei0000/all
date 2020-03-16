package com.longcheng.lifecareplan.modular.mine.energycenter.bean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class DaiFuItemBean {
    int position;
    private String user_zhufubao_order_id;
    private int user_status;
    private int bless_user_status;
    private String price;
    private String user_avatar;
    private String user_name;
    private String jieqi_name;
    private String phone;
    private String create_time;

    private ArrayList<String> img_all;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUser_zhufubao_order_id() {
        return user_zhufubao_order_id;
    }

    public void setUser_zhufubao_order_id(String user_zhufubao_order_id) {
        this.user_zhufubao_order_id = user_zhufubao_order_id;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public int getBless_user_status() {
        return bless_user_status;
    }

    public void setBless_user_status(int bless_user_status) {
        this.bless_user_status = bless_user_status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getJieqi_name() {
        return jieqi_name;
    }

    public void setJieqi_name(String jieqi_name) {
        this.jieqi_name = jieqi_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public ArrayList<String> getImg_all() {
        return img_all;
    }

    public void setImg_all(ArrayList<String> img_all) {
        this.img_all = img_all;
    }
}
