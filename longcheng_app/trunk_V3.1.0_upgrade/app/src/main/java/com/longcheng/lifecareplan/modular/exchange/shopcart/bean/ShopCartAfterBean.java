package com.longcheng.lifecareplan.modular.exchange.shopcart.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.modular.exchange.bean.GoodsItemBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class ShopCartAfterBean {
    @SerializedName("hotGoodsList")
    private List<GoodsItemBean> hotGoodsList;


    private String highestIdentity;
    private int highestIdentityLevel;
    private List<String> shopGoodsIds;


    private String jieqiBranchAllName;

    public String getJieqiBranchAllName() {
        return jieqiBranchAllName;
    }

    public void setJieqiBranchAllName(String jieqiBranchAllName) {
        this.jieqiBranchAllName = jieqiBranchAllName;
    }

    public String getHighestIdentity() {
        return highestIdentity;
    }

    public void setHighestIdentity(String highestIdentity) {
        this.highestIdentity = highestIdentity;
    }

    public int getHighestIdentityLevel() {
        return highestIdentityLevel;
    }

    public void setHighestIdentityLevel(int highestIdentityLevel) {
        this.highestIdentityLevel = highestIdentityLevel;
    }

    public List<String> getShopGoodsIds() {
        return shopGoodsIds;
    }

    public void setShopGoodsIds(List<String> shopGoodsIds) {
        this.shopGoodsIds = shopGoodsIds;
    }

    public List<GoodsItemBean> getHotGoodsList() {
        return hotGoodsList;
    }

    public void setHotGoodsList(List<GoodsItemBean> hotGoodsList) {
        this.hotGoodsList = hotGoodsList;
    }
}
