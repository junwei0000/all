package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：zhangjinqi
 * 时间 2017/10/17 15:18
 * 邮箱：mengchong.55@163.com
 * 类的意图：支付bean
 */

public class SKBPayAfterBean {
    @SerializedName("help_goods_order_id")
    private String help_goods_order_id;
    @SerializedName("sponsor_user_name")
    private String sponsor_user_name;
    @SerializedName("receive_user_name")
    private String receive_user_name;
    @SerializedName("skb_price")
    private String skb_price;

    public String getHelp_goods_order_id() {
        return help_goods_order_id;
    }

    public void setHelp_goods_order_id(String help_goods_order_id) {
        this.help_goods_order_id = help_goods_order_id;
    }

    public String getSponsor_user_name() {
        return sponsor_user_name;
    }

    public void setSponsor_user_name(String sponsor_user_name) {
        this.sponsor_user_name = sponsor_user_name;
    }

    public String getReceive_user_name() {
        return receive_user_name;
    }

    public void setReceive_user_name(String receive_user_name) {
        this.receive_user_name = receive_user_name;
    }

    public String getSkb_price() {
        return skb_price;
    }

    public void setSkb_price(String skb_price) {
        this.skb_price = skb_price;
    }
}
