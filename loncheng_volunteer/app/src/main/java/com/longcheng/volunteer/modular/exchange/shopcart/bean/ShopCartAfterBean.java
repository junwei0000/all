package com.longcheng.volunteer.modular.exchange.shopcart.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.modular.exchange.bean.GoodsItemBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class ShopCartAfterBean {
    @SerializedName("hotGoodsList")
    private List<GoodsItemBean> hotGoodsList;

    public List<GoodsItemBean> getHotGoodsList() {
        return hotGoodsList;
    }

    public void setHotGoodsList(List<GoodsItemBean> hotGoodsList) {
        this.hotGoodsList = hotGoodsList;
    }
}
