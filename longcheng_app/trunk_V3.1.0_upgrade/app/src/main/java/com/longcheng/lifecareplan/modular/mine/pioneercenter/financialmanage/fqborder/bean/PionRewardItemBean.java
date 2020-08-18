package com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.fqborder.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionRewardItemBean implements Serializable {
    private int position;
    private String entrepreneurs_fuqibao_sell_order_id;
    private String user_avatar;
    private String user_name;
    private String user_branch_info;
    private String create_time;
    private String fuqibao;

    private String bank_user_name;
    private String bank_name;
    private String bank_branch;
    private String bank_account;
    private int status;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getEntrepreneurs_fuqibao_sell_order_id() {
        return entrepreneurs_fuqibao_sell_order_id;
    }

    public void setEntrepreneurs_fuqibao_sell_order_id(String entrepreneurs_fuqibao_sell_order_id) {
        this.entrepreneurs_fuqibao_sell_order_id = entrepreneurs_fuqibao_sell_order_id;
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

    public String getUser_branch_info() {
        return user_branch_info;
    }

    public void setUser_branch_info(String user_branch_info) {
        this.user_branch_info = user_branch_info;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFuqibao() {
        return fuqibao;
    }

    public void setFuqibao(String fuqibao) {
        this.fuqibao = fuqibao;
    }

    public String getBank_user_name() {
        return bank_user_name;
    }

    public void setBank_user_name(String bank_user_name) {
        this.bank_user_name = bank_user_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_branch() {
        return bank_branch;
    }

    public void setBank_branch(String bank_branch) {
        this.bank_branch = bank_branch;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}