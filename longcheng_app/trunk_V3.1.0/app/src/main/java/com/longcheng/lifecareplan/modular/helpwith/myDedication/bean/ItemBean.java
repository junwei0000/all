package com.longcheng.lifecareplan.modular.helpwith.myDedication.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class ItemBean implements Serializable {

    private String sponsor_user_id;//
    private String receive_user_id;
    private String help_ability;
    private String user_name;
    private String group_name;
    private String avatar;

    private String user_id;
    private String goods_x_name;
    private String ability_one_price;
    private String pay_time;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGoods_x_name() {
        return goods_x_name;
    }

    public void setGoods_x_name(String goods_x_name) {
        this.goods_x_name = goods_x_name;
    }

    public String getAbility_one_price() {
        return ability_one_price;
    }

    public void setAbility_one_price(String ability_one_price) {
        this.ability_one_price = ability_one_price;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getSponsor_user_id() {
        return sponsor_user_id;
    }

    public void setSponsor_user_id(String sponsor_user_id) {
        this.sponsor_user_id = sponsor_user_id;
    }

    public String getReceive_user_id() {
        return receive_user_id;
    }

    public void setReceive_user_id(String receive_user_id) {
        this.receive_user_id = receive_user_id;
    }

    public String getHelp_ability() {
        return help_ability;
    }

    public void setHelp_ability(String help_ability) {
        this.help_ability = help_ability;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
