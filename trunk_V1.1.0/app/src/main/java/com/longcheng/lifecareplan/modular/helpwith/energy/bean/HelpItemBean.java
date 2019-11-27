package com.longcheng.lifecareplan.modular.helpwith.energy.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class HelpItemBean implements Serializable {

    private String id;//互助ID
    private String goods_id;
    private String h_user_id;
    private String h_user;//接福人
    private String gs_name;//公社名称
    private String ability_price_action;//生命能量
    private String action_image;
    private String group_img;//公社头像
    private int my_bless;//我祝福
    private int bless_me;//祝福我
    private String goods_x_name;
    private String date;//时间
    private int progress;//进度


    private int ability_type;// 互祝类型 1普通  2超级 3混合
    private int ability_proportion;//普通能量比例
    private int normal_progress;//普通能量比例-实际互助的比例
    private int super_ability_proportion;//超级能量值比例
    private int super_progress;//超级能量值比例-实际互助的比例


    public int getAbility_type() {
        return ability_type;
    }

    public void setAbility_type(int ability_type) {
        if (ability_type == 0) {
            ability_type = 1;
        }
        this.ability_type = ability_type;
    }

    public int getAbility_proportion() {
        return ability_proportion;
    }

    public void setAbility_proportion(String ability_proportion) {
        this.ability_proportion = Integer.parseInt(ability_proportion);
    }

    public int getNormal_progress() {
        return normal_progress;
    }

    public void setNormal_progress(int normal_progress) {
        this.normal_progress = normal_progress;
    }

    public int getSuper_ability_proportion() {
        return super_ability_proportion;
    }

    public void setSuper_ability_proportion(String super_ability_proportion) {
        this.super_ability_proportion = Integer.parseInt(super_ability_proportion);
    }

    public int getSuper_progress() {
        return super_progress;
    }

    public void setSuper_progress(int super_progress) {
        this.super_progress = super_progress;
    }

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
