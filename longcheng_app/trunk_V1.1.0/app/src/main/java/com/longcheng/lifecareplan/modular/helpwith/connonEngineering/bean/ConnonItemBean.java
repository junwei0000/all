package com.longcheng.lifecareplan.modular.helpwith.connonEngineering.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class ConnonItemBean implements Serializable {

    private String id;//互助ID
    private String goods_id;
    private String h_user_id;
    private String h_user;//接福人
    private String gs_name;//公社名称
    private int progress;//进度
    private String ability_price_action;//生命能量
    private String action_image;
    private String group_img;//公社头像
    private int my_bless;//我祝福
    private int bless_me;//祝福我
    private String goods_x_name;
    private String date;//时间

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getH_user_id() {
        return h_user_id;
    }

    public void setH_user_id(String h_user_id) {
        this.h_user_id = h_user_id;
    }

    public String getGoods_x_name() {
        return goods_x_name;
    }

    public void setGoods_x_name(String goods_x_name) {
        this.goods_x_name = goods_x_name;
    }

    public void setH_user(String h_user) {
        this.h_user = h_user;
    }

    public void setAbility_price_action(String ability_price_action) {
        this.ability_price_action = ability_price_action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup_img() {
        return group_img;
    }

    public void setGroup_img(String group_img) {
        this.group_img = group_img;
    }

    public String getGs_name() {
        return gs_name;
    }

    public void setGs_name(String gs_name) {
        this.gs_name = gs_name;
    }

    public int getMy_bless() {
        return my_bless;
    }

    public void setMy_bless(int my_bless) {
        this.my_bless = my_bless;
    }

    public int getBless_me() {
        return bless_me;
    }

    public void setBless_me(int bless_me) {
        this.bless_me = bless_me;
    }

    public String getAction_image() {
        return action_image;
    }

    public void setAction_image(String action_image) {
        this.action_image = action_image;
    }

    public String getAction_name() {
        return goods_x_name;
    }


    public String getH_user() {
        return h_user;
    }


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getAbility_price_action() {
        return ability_price_action;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
