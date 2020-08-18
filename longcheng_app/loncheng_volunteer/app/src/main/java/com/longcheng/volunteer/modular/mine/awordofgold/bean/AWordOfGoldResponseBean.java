package com.longcheng.volunteer.modular.mine.awordofgold.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.volunteer.bean.ResponseBean;

/**
 * Created by Burning on 2018/9/4.
 */

public class AWordOfGoldResponseBean extends ResponseBean {

    @SerializedName("data")
    protected AWordOfGoldBean data;

    public AWordOfGoldBean getData() {
        return data;
    }

    public void setData(AWordOfGoldBean data) {
        this.data = data;
    }
}
