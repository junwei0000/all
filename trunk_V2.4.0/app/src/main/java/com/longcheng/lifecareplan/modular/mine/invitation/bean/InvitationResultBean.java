package com.longcheng.lifecareplan.modular.mine.invitation.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * Created by Burning on 2018/9/5.
 */

public class InvitationResultBean extends ResponseBean {
    @SerializedName("data")
    protected InvitationBean data;

    public InvitationBean getData() {
        return data;
    }

    public void setData(InvitationBean data) {
        this.data = data;
    }
}
