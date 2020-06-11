package com.longcheng.lifecareplan.modular.mine.pioneercenter.bean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PioneerItemBean {
    int position;
    private String entrepreneurs_id;
    private String zhufubao;
    private String fuqibao;
    private String rebate;
    private String displayDiffTime;
    private String user_name;
    private String avatar;
    private String branch_info;
    int receive_order_status;//营业状态
    private ArrayList<String> identity_flag;

    private int diffTime;
    private String create_time_detail;
    private String money;

    public String getCreate_time_detail() {
        return create_time_detail;
    }

    public void setCreate_time_detail(String create_time_detail) {
        this.create_time_detail = create_time_detail;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getReceive_order_status() {
        return receive_order_status;
    }

    public void setReceive_order_status(int receive_order_status) {
        this.receive_order_status = receive_order_status;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getEntrepreneurs_id() {
        return entrepreneurs_id;
    }

    public void setEntrepreneurs_id(String entrepreneurs_id) {
        this.entrepreneurs_id = entrepreneurs_id;
    }

    public String getZhufubao() {
        return zhufubao;
    }

    public void setZhufubao(String zhufubao) {
        this.zhufubao = zhufubao;
    }

    public String getFuqibao() {
        return fuqibao;
    }

    public void setFuqibao(String fuqibao) {
        this.fuqibao = fuqibao;
    }

    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    public String getDisplayDiffTime() {
        return displayDiffTime;
    }

    public void setDisplayDiffTime(String displayDiffTime) {
        this.displayDiffTime = displayDiffTime;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBranch_info() {
        return branch_info;
    }

    public void setBranch_info(String branch_info) {
        this.branch_info = branch_info;
    }

    public ArrayList<String> getIdentity_flag() {
        return identity_flag;
    }

    public void setIdentity_flag(ArrayList<String> identity_flag) {
        this.identity_flag = identity_flag;
    }

    public int getDiffTime() {
        return diffTime;
    }

    public void setDiffTime(int diffTime) {
        this.diffTime = diffTime;
    }
}
