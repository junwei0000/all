package com.longcheng.volunteer.modular.helpwith.energydetail.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class OpenRedAfterBean implements Serializable {
    private String mutual_help_user_red_packet_id;
    private String user_id;
    private String mutual_help_red_packet_id;
    private int type;
    private String money_type;
    private String red_packet_money;
    private String red_packet_status;
    private String pay_status;
    private String msg_id;
    private String one_order_id;
    private String receive_red_packet_time;
    private int create_time;
    private int update_time;


    private String totalNumber;
    private String totalMoney;
    private String totalSkb;

    public String getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(String totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTotalSkb() {
        return totalSkb;
    }

    public void setTotalSkb(String totalSkb) {
        this.totalSkb = totalSkb;
    }

    public String getMutual_help_user_red_packet_id() {
        return mutual_help_user_red_packet_id;
    }

    public void setMutual_help_user_red_packet_id(String mutual_help_user_red_packet_id) {
        this.mutual_help_user_red_packet_id = mutual_help_user_red_packet_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMutual_help_red_packet_id() {
        return mutual_help_red_packet_id;
    }

    public void setMutual_help_red_packet_id(String mutual_help_red_packet_id) {
        this.mutual_help_red_packet_id = mutual_help_red_packet_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMoney_type() {
        return money_type;
    }

    public void setMoney_type(String money_type) {
        this.money_type = money_type;
    }

    public String getRed_packet_money() {
        return red_packet_money;
    }

    public void setRed_packet_money(String red_packet_money) {
        this.red_packet_money = red_packet_money;
    }

    public String getRed_packet_status() {
        return red_packet_status;
    }

    public void setRed_packet_status(String red_packet_status) {
        this.red_packet_status = red_packet_status;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getOne_order_id() {
        return one_order_id;
    }

    public void setOne_order_id(String one_order_id) {
        this.one_order_id = one_order_id;
    }

    public String getReceive_red_packet_time() {
        return receive_red_packet_time;
    }

    public void setReceive_red_packet_time(String receive_red_packet_time) {
        this.receive_red_packet_time = receive_red_packet_time;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }
}
