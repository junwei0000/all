package com.longcheng.volunteer.modular.helpwith.lifestyle.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class LifeStyleAfterBean {
    @SerializedName("count")
    private String count;

    @SerializedName("help_goods")
    private List<LifeStyleItemBean> help_goods;


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<LifeStyleItemBean> getHelp_goods() {
        return help_goods;
    }

    public void setHelp_goods(List<LifeStyleItemBean> help_goods) {
        this.help_goods = help_goods;
    }
}
