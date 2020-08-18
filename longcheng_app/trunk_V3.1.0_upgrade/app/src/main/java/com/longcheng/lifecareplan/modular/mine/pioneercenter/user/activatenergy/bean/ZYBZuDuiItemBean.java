package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class ZYBZuDuiItemBean implements Serializable {
    private String zhuyoubao_team_room_id;
    private String zhuyoubao_team_rule_id;
    private String money;
    private int process_status;//流程状态：0 人未满,1 人已满 2 生成订单 3 充值完成;
    private int is_update_entre;// 1可以修改导师

    private String zhuyoubao_team_item_id;
    private String entrepreneurs_id;
    private  String user_id;
    private String avatar;
    private String user_name;
    private int role;
    private int order_status;

    public int getIs_update_entre() {
        return is_update_entre;
    }

    public void setIs_update_entre(int is_update_entre) {
        this.is_update_entre = is_update_entre;
    }

    public String getEntrepreneurs_id() {
        return entrepreneurs_id;
    }

    public void setEntrepreneurs_id(String entrepreneurs_id) {
        this.entrepreneurs_id = entrepreneurs_id;
    }

    public String getZhuyoubao_team_item_id() {
        return zhuyoubao_team_item_id;
    }

    public void setZhuyoubao_team_item_id(String zhuyoubao_team_item_id) {
        this.zhuyoubao_team_item_id = zhuyoubao_team_item_id;
    }

    public int getProcess_status() {
        return process_status;
    }

    public void setProcess_status(int process_status) {
        this.process_status = process_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getZhuyoubao_team_room_id() {
        return zhuyoubao_team_room_id;
    }

    public void setZhuyoubao_team_room_id(String zhuyoubao_team_room_id) {
        this.zhuyoubao_team_room_id = zhuyoubao_team_room_id;
    }

    public String getZhuyoubao_team_rule_id() {
        return zhuyoubao_team_rule_id;
    }

    public void setZhuyoubao_team_rule_id(String zhuyoubao_team_rule_id) {
        this.zhuyoubao_team_rule_id = zhuyoubao_team_rule_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }
}
