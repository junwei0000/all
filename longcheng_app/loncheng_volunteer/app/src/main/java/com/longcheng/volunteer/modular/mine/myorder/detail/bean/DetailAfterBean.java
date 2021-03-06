package com.longcheng.volunteer.modular.mine.myorder.detail.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class DetailAfterBean implements Serializable {
    private String order_id;
    private String consignee;
    private String mobile;
    private String address;
    private String type_name;
    private String goods_x_name;
    private String shop_goods_price_id;
    private String image;
    private String price;
    private int goods_id;
    private int action_id;
    private String h_user_id;
    private String msg_id;
    private String help_goods_id;
    private String order_status;
    private String shipping_status;
    private int type;//订单类型	1商城订单 2 生命能量订单 3 生活方式互祝订单,4 康农工程
    private String order_sn;
    private String date;
    private int is_show_perfect_info;
    private int is_show_consignee_info;
    private int is_show_help_info;
    private int is_pre_delivery;//1 显示押金

    private String pre_delivery_deposit;//押金

    private String knp_info_url;

    private int bottom_status;
    private String bottom_title;
    private int top_status;
    private String top_title;
    private String goods_info_url;

    private int action_status;//是否已下架：0已下架


    //押金信息
    private String avatar;
    private String user_asset;
    private String user_name;
    private int deposit_type;//2 坐堂医; 1 cho
    private int deposit;
    private String deposit_str;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_asset() {
        return user_asset;
    }

    public void setUser_asset(String user_asset) {
        this.user_asset = user_asset;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getDeposit_type() {
        return deposit_type;
    }

    public void setDeposit_type(int deposit_type) {
        this.deposit_type = deposit_type;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getDeposit_str() {
        return deposit_str;
    }

    public void setDeposit_str(String deposit_str) {
        this.deposit_str = deposit_str;
    }

    public String getKnp_info_url() {
        return knp_info_url;
    }

    public void setKnp_info_url(String knp_info_url) {
        this.knp_info_url = knp_info_url;
    }

    public int getIs_pre_delivery() {
        return is_pre_delivery;
    }

    public void setIs_pre_delivery(int is_pre_delivery) {
        this.is_pre_delivery = is_pre_delivery;
    }

    public String getPre_delivery_deposit() {
        return pre_delivery_deposit;
    }

    public void setPre_delivery_deposit(String pre_delivery_deposit) {
        this.pre_delivery_deposit = pre_delivery_deposit;
    }


    public String getShop_goods_price_id() {
        return shop_goods_price_id;
    }

    public void setShop_goods_price_id(String shop_goods_price_id) {
        this.shop_goods_price_id = shop_goods_price_id;
    }

    public int getAction_status() {
        return action_status;
    }

    public void setAction_status(int action_status) {
        this.action_status = action_status;
    }

    public int getAction_id() {
        return action_id;
    }

    public void setAction_id(int action_id) {
        this.action_id = action_id;
    }

    public String getGoods_info_url() {
        return goods_info_url;
    }

    public void setGoods_info_url(String goods_info_url) {
        this.goods_info_url = goods_info_url;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getGoods_x_name() {
        return goods_x_name;
    }

    public void setGoods_x_name(String goods_x_name) {
        this.goods_x_name = goods_x_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getH_user_id() {
        return h_user_id;
    }

    public void setH_user_id(String h_user_id) {
        this.h_user_id = h_user_id;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getHelp_goods_id() {
        return help_goods_id;
    }

    public void setHelp_goods_id(String help_goods_id) {
        this.help_goods_id = help_goods_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(String shipping_status) {
        this.shipping_status = shipping_status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIs_show_perfect_info() {
        return is_show_perfect_info;
    }

    public void setIs_show_perfect_info(int is_show_perfect_info) {
        this.is_show_perfect_info = is_show_perfect_info;
    }

    public int getIs_show_consignee_info() {
        return is_show_consignee_info;
    }

    public void setIs_show_consignee_info(int is_show_consignee_info) {
        this.is_show_consignee_info = is_show_consignee_info;
    }

    public int getIs_show_help_info() {
        return is_show_help_info;
    }

    public void setIs_show_help_info(int is_show_help_info) {
        this.is_show_help_info = is_show_help_info;
    }

    public int getBottom_status() {
        return bottom_status;
    }

    public void setBottom_status(int bottom_status) {
        this.bottom_status = bottom_status;
    }

    public String getBottom_title() {
        return bottom_title;
    }

    public void setBottom_title(String bottom_title) {
        this.bottom_title = bottom_title;
    }

    public int getTop_status() {
        return top_status;
    }

    public void setTop_status(int top_status) {
        this.top_status = top_status;
    }

    public String getTop_title() {
        return top_title;
    }

    public void setTop_title(String top_title) {
        this.top_title = top_title;
    }
}
