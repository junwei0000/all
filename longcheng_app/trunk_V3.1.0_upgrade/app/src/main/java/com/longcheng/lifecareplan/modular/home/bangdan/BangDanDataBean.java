package com.longcheng.lifecareplan.modular.home.bangdan;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回数组
 */

public class BangDanDataBean extends ResponseBean {
    @SerializedName("data")
    private BangDanAfterBean data;

    public BangDanAfterBean getData() {
        return data;
    }

    public void setData(BangDanAfterBean data) {
        this.data = data;
    }
}
