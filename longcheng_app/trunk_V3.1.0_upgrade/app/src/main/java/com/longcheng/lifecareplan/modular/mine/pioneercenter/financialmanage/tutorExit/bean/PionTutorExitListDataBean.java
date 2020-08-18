package com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.tutorExit.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionTutorExitListDataBean extends ResponseBean {
    @SerializedName("data")
    private List<PionTutorExitItemBean> data;

    public List<PionTutorExitItemBean> getData() {
        return data;
    }

    public void setData(List<PionTutorExitItemBean> data) {
        this.data = data;
    }
}
