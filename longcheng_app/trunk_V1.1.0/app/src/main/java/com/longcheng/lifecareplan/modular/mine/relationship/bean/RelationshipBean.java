package com.longcheng.lifecareplan.modular.mine.relationship.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * Created by Burning on 2018/9/1.
 */

public class RelationshipBean extends ResponseBean {
    @SerializedName("data")
    protected RelationshipBookData data;

    public RelationshipBookData getData() {
        return data;
    }

    public void setData(RelationshipBookData data) {
        this.data = data;
    }
}
