package com.longcheng.lifecareplan.modular.exchange.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpItemBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class GoodsAfterBean {
    @SerializedName("list_count")
    private int count;

    @SerializedName("goods_list")
    private List<GoodsItemBean> goods_list;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<GoodsItemBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsItemBean> goods_list) {
        this.goods_list = goods_list;
    }
}
