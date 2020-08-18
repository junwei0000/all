package com.longcheng.volunteer.modular.exchange.malldetail.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class DetailItemBean implements Serializable {

    boolean check;
    private int goodsNum;//购物车相同商品并且相同规格的数量
    //goodsInfo
    private String shop_goods_id;
    private String name;
    private String thumb;
    private String skb_price;
    private String stock;
    private int is_selfmade;
    private String sale_number;
    private String content;
    private int is_allow_apply_help;
    //goodsSolarTerms
    private int solar_terms_id;
    private String solar_terms_name;
    private String pic;

    //goodsPrice
    private String shop_goods_price_id;
    //    private String shop_goods_id;
    private String price_name;
//    private String skb_price;


    public int getIs_allow_apply_help() {
        return is_allow_apply_help;
    }

    public void setIs_allow_apply_help(int is_allow_apply_help) {
        this.is_allow_apply_help = is_allow_apply_help;
    }

    public int getSolar_terms_id() {
        return solar_terms_id;
    }

    public void setSolar_terms_id(int solar_terms_id) {
        this.solar_terms_id = solar_terms_id;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getShop_goods_id() {
        return shop_goods_id;
    }

    public void setShop_goods_id(String shop_goods_id) {
        this.shop_goods_id = shop_goods_id;
    }

    public String getPrice_name() {
        return price_name;
    }

    public void setPrice_name(String price_name) {
        this.price_name = price_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getSkb_price() {
        return skb_price;
    }

    public void setSkb_price(String skb_price) {
        this.skb_price = skb_price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getIs_selfmade() {
        return is_selfmade;
    }

    public void setIs_selfmade(int is_selfmade) {
        this.is_selfmade = is_selfmade;
    }

    public String getSale_number() {
        return sale_number;
    }

    public void setSale_number(String sale_number) {
        this.sale_number = sale_number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSolar_terms_name() {
        return solar_terms_name;
    }

    public void setSolar_terms_name(String solar_terms_name) {
        this.solar_terms_name = solar_terms_name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getShop_goods_price_id() {
        return shop_goods_price_id;
    }

    public void setShop_goods_price_id(String shop_goods_price_id) {
        this.shop_goods_price_id = shop_goods_price_id;
    }

    @Override
    public String toString() {
        return "ShopCartItemBean{" +
                "shop_goods_id='" + shop_goods_id + '\'' +
                ", name='" + name + '\'' +
                ", thumb='" + thumb + '\'' +
                ", skb_price='" + skb_price + '\'' +
                ", stock='" + stock + '\'' +
                ", is_selfmade=" + is_selfmade +
                ", sale_number='" + sale_number + '\'' +
                ", content='" + content + '\'' +
                ", solar_terms_name='" + solar_terms_name + '\'' +
                ", pic='" + pic + '\'' +
                ", shop_goods_price_id='" + shop_goods_price_id + '\'' +
                ", price_name='" + price_name + '\'' +
                '}';
    }
}
