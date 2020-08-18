package com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/23 17:44
 * 意图：
 */

public class ExplainItemBean {
    @SerializedName("goods_specs_id")
    private int goods_specs_id;
    @SerializedName("goods_id")
    private String goods_id;
    @SerializedName("goods_specs_name")
    private String goods_specs_name;

    public int getGoods_specs_id() {
        return goods_specs_id;
    }

    public void setGoods_specs_id(int goods_specs_id) {
        this.goods_specs_id = goods_specs_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_specs_name() {
        return goods_specs_name;
    }

    public void setGoods_specs_name(String goods_specs_name) {
        this.goods_specs_name = goods_specs_name;
    }
}