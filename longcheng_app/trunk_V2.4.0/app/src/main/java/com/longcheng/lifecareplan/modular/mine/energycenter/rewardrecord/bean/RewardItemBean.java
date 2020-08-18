package com.longcheng.lifecareplan.modular.mine.energycenter.rewardrecord.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class RewardItemBean implements Serializable {
    private int position;
    private String user_zhufubao_reward_id;
    private String avatar;
    private String user_name;
    private String create_date;
    private String user_zhufubao_number;
    private String total_price;
    private String jieiqi_name;

    private String cardholder_name;
    private String bank_name;
    private String bank_no;
    private int status;
    private String bank_full_name;

    public String getBank_full_name() {
        return bank_full_name;
    }

    public void setBank_full_name(String bank_full_name) {
        this.bank_full_name = bank_full_name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUser_zhufubao_reward_id() {
        return user_zhufubao_reward_id;
    }

    public void setUser_zhufubao_reward_id(String user_zhufubao_reward_id) {
        this.user_zhufubao_reward_id = user_zhufubao_reward_id;
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

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUser_zhufubao_number() {
        return user_zhufubao_number;
    }

    public void setUser_zhufubao_number(String user_zhufubao_number) {
        this.user_zhufubao_number = user_zhufubao_number;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getJieiqi_name() {
        return jieiqi_name;
    }

    public void setJieiqi_name(String jieiqi_name) {
        this.jieiqi_name = jieiqi_name;
    }

    public String getCardholder_name() {
        return cardholder_name;
    }

    public void setCardholder_name(String cardholder_name) {
        this.cardholder_name = cardholder_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}