package com.longcheng.volunteer.modular.helpwith.lifestyle.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class LifeStyleItemBean implements Serializable {

    private String help_goods_id;//互助ID

    private String group_img;//公社头像
    private String receive_group_name;//公社名称
    private String goods_img;
    private String goods_name;
    private String goods_id;
    private String receive_user_name;//接福人

    private int progress;//进度
    private String skb_cumulative_price;//累计完成寿康宝数量
    private int my_bless;//我祝福
    private int bless_me;//祝福我
    private String date;//创建时间

    public String getHelp_goods_id() {
        return help_goods_id;
    }

    public void setHelp_goods_id(String help_goods_id) {
        this.help_goods_id = help_goods_id;
    }

    public String getGroup_img() {
        return group_img;
    }

    public void setGroup_img(String group_img) {
        this.group_img = group_img;
    }

    public String getReceive_group_name() {
        return receive_group_name;
    }

    public void setReceive_group_name(String receive_group_name) {
        this.receive_group_name = receive_group_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getReceive_user_name() {
        return receive_user_name;
    }

    public void setReceive_user_name(String receive_user_name) {
        this.receive_user_name = receive_user_name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getSkb_cumulative_price() {
        return skb_cumulative_price;
    }

    public void setSkb_cumulative_price(String skb_cumulative_price) {
        this.skb_cumulative_price = skb_cumulative_price;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
