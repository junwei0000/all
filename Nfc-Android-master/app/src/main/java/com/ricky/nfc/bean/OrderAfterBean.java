package com.ricky.nfc.bean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class OrderAfterBean {

    private OrderItemBean user;
    private OrderItemBean msg;
    private String patient_name="";

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }
    public OrderItemBean getMsg() {
        return msg;
    }

    public void setMsg(OrderItemBean msg) {
        this.msg = msg;
    }

    public OrderItemBean getUser() {
        return user;
    }

    public void setUser(OrderItemBean user) {
        this.user = user;
    }
}