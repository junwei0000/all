package com.longcheng.lifecareplan.modular.helpwith.lifestyle.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class LifeStyleItemBean implements Serializable {

    private String help_wares_id;//互助ID

    private String group_avatar;//公社头像
    private String receive_group_name;//公社名称
    @SerializedName("img")
    private String goods_img;
    private String goods_name;
    private String receive_user_name;//接福人

    private int progress;//进度
    private String date;//创建时间

    public String getHelp_wares_id() {
        return help_wares_id;
    }

    public void setHelp_wares_id(String help_wares_id) {
        this.help_wares_id = help_wares_id;
    }

    public String getGroup_avatar() {
        return group_avatar;
    }

    public void setGroup_avatar(String group_avatar) {
        this.group_avatar = group_avatar;
    }

    public String getReceive_group_name() {
        return receive_group_name;
    }

    public void setReceive_group_name(String receive_group_name) {
        this.receive_group_name = receive_group_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getReceive_user_name() {
        return receive_user_name;
    }

    public void setReceive_user_name(String receive_user_name) {
        this.receive_user_name = receive_user_name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
