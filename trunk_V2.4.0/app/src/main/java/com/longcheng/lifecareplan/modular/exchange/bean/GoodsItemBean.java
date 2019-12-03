package com.longcheng.lifecareplan.modular.exchange.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class GoodsItemBean implements Serializable {
    private String shop_goods_id;
    private String name;
    private String skb_price;
    private String thumb;
    private int stock;
    private int top_cid;
    private int cid;
    private int is_new;//是否最新 0：否 1：是
    private int is_hot;//是否最热 0：否 1：是
    private int is_selfmade;//是否自营 0：否 1：是
    private int sale_number;

    public String getShop_goods_id() {
        return shop_goods_id;
    }

    public void setShop_goods_id(String shop_goods_id) {
        this.shop_goods_id = shop_goods_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkb_price() {
        return skb_price;
    }

    public void setSkb_price(String skb_price) {
        this.skb_price = skb_price;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getTop_cid() {
        return top_cid;
    }

    public void setTop_cid(int top_cid) {
        this.top_cid = top_cid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getIs_new() {
        return is_new;
    }

    public void setIs_new(int is_new) {
        this.is_new = is_new;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getIs_selfmade() {
        return is_selfmade;
    }

    public void setIs_selfmade(int is_selfmade) {
        this.is_selfmade = is_selfmade;
    }

    public int getSale_number() {
        return sale_number;
    }

    public void setSale_number(int sale_number) {
        this.sale_number = sale_number;
    }
}
