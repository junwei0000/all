package com.longcheng.lifecareplan.modular.exchange.malldetail.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class DetailAfterBean {
    @SerializedName("goodsInfo")
    private DetailItemBean goodsInfo;//商品数据

    @SerializedName("goodsPhoto")
    private List<String> goodsPhoto;//商品轮播图数据
    @SerializedName("goodsSolarTerms")
    private List<DetailItemBean> goodsSolarTerms;//商品24节气数据
    @SerializedName("goodsPrice")
    private List<DetailItemBean> goodsPrice;//规格
    @SerializedName("userStarLevel")
    private int userStarLevel;
    @SerializedName("isExistsHelpGoods")
    private int isExistsHelpGoods;
    @SerializedName("shareUrl")
    private String shareUrl;
    @SerializedName("helpGoodsId")
    private String helpGoodsId;

    public String getHelpGoodsId() {
        return helpGoodsId;
    }

    public void setHelpGoodsId(String helpGoodsId) {
        this.helpGoodsId = helpGoodsId;
    }

    public int getIsExistsHelpGoods() {
        return isExistsHelpGoods;
    }

    public void setIsExistsHelpGoods(int isExistsHelpGoods) {
        this.isExistsHelpGoods = isExistsHelpGoods;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public List<DetailItemBean> getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(List<DetailItemBean> goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public DetailItemBean getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(DetailItemBean goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public List<String> getGoodsPhoto() {
        return goodsPhoto;
    }

    public void setGoodsPhoto(List<String> goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }

    public List<DetailItemBean> getGoodsSolarTerms() {
        return goodsSolarTerms;
    }

    public void setGoodsSolarTerms(List<DetailItemBean> goodsSolarTerms) {
        this.goodsSolarTerms = goodsSolarTerms;
    }

    public int getUserStarLevel() {
        return userStarLevel;
    }

    public void setUserStarLevel(int userStarLevel) {
        this.userStarLevel = userStarLevel;
    }
}
