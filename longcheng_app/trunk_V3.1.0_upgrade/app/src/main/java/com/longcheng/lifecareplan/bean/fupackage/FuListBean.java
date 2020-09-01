package com.longcheng.lifecareplan.bean.fupackage;

import java.io.Serializable;

public class FuListBean implements Serializable {
    String sponsor_user_name;
    String receive_user_name;
    String receive_user_phone;
    String sponsor_super_ability;
    String receive_super_ability;

    String sponsor_user_avatar;
    String receive_user_avatar;
    int type;
    int create_time;
    int open_time;//收到的时间
    int status;//2 已接收


    String bless_bag_big_id;
    int total_number;
    int finsh_count;
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReceive_user_phone() {
        return receive_user_phone;
    }

    public void setReceive_user_phone(String receive_user_phone) {
        this.receive_user_phone = receive_user_phone;
    }

    public String getBless_bag_big_id() {
        return bless_bag_big_id;
    }

    public void setBless_bag_big_id(String bless_bag_big_id) {
        this.bless_bag_big_id = bless_bag_big_id;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public int getFinsh_count() {
        return finsh_count;
    }

    public void setFinsh_count(int finsh_count) {
        this.finsh_count = finsh_count;
    }

    public String getSponsor_user_avatar() {
        return sponsor_user_avatar;
    }

    public void setSponsor_user_avatar(String sponsor_user_avatar) {
        this.sponsor_user_avatar = sponsor_user_avatar;
    }

    public String getReceive_user_avatar() {
        return receive_user_avatar;
    }

    public void setReceive_user_avatar(String receive_user_avatar) {
        this.receive_user_avatar = receive_user_avatar;
    }

    public String getSponsor_user_name() {
        return sponsor_user_name;
    }

    public void setSponsor_user_name(String sponsor_user_name) {
        this.sponsor_user_name = sponsor_user_name;
    }

    public String getReceive_user_name() {
        return receive_user_name;
    }

    public void setReceive_user_name(String receive_user_name) {
        this.receive_user_name = receive_user_name;
    }

    public String getSponsor_super_ability() {
        return sponsor_super_ability;
    }

    public void setSponsor_super_ability(String sponsor_super_ability) {
        this.sponsor_super_ability = sponsor_super_ability;
    }

    public String getReceive_super_ability() {
        return receive_super_ability;
    }

    public void setReceive_super_ability(String receive_super_ability) {
        this.receive_super_ability = receive_super_ability;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getOpen_time() {
        return open_time;
    }

    public void setOpen_time(int open_time) {
        this.open_time = open_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
