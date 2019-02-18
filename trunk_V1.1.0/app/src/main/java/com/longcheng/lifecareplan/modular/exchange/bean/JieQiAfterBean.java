package com.longcheng.lifecareplan.modular.exchange.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class JieQiAfterBean {
    @SerializedName("current_solar")
    private JieQiItemBean current_solar;

    @SerializedName("solar")
    private List<JieQiItemBean> solar;
    @SerializedName("categorys")
    private List<JieQiItemBean> categorys;

    public JieQiItemBean getCurrent_solar() {
        return current_solar;
    }

    public void setCurrent_solar(JieQiItemBean current_solar) {
        this.current_solar = current_solar;
    }

    public List<JieQiItemBean> getSolar() {
        return solar;
    }

    public void setSolar(List<JieQiItemBean> solar) {
        this.solar = solar;
    }

    public List<JieQiItemBean> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<JieQiItemBean> categorys) {
        this.categorys = categorys;
    }
}
