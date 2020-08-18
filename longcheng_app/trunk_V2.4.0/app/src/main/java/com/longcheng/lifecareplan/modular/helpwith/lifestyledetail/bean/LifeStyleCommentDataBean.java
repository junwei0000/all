package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailItemBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class LifeStyleCommentDataBean extends ResponseBean {
    @SerializedName("data")
    private LifeStyleDetailItemBean data;

    public LifeStyleDetailItemBean getData() {
        return data;
    }

    public void setData(LifeStyleDetailItemBean data) {
        this.data = data;
    }
}
