package com.longcheng.lifecareplan.modular.helpwith.energydetail.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PayAfterBean implements Serializable {
    private String backToPrePage;
    private String isDisplayCho;
    private PayItemBean endorsement_star;

    public String getBackToPrePage() {
        return backToPrePage;
    }

    public void setBackToPrePage(String backToPrePage) {
        this.backToPrePage = backToPrePage;
    }

    public String getIsDisplayCho() {
        return isDisplayCho;
    }

    public void setIsDisplayCho(String isDisplayCho) {
        this.isDisplayCho = isDisplayCho;
    }

    public PayItemBean getEndorsement_star() {
        return endorsement_star;
    }

    public void setEndorsement_star(PayItemBean endorsement_star) {
        this.endorsement_star = endorsement_star;
    }
}
