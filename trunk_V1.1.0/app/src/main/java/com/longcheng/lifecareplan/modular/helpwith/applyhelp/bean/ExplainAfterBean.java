package com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/23 17:44
 * 意图：
 */

public class ExplainAfterBean {
    @SerializedName("isJoinActivity")
    private String isJoinActivity;
    @SerializedName("manifesto")
    private List<String> manifesto;

    public String getIsJoinActivity() {
        return isJoinActivity;
    }

    public void setIsJoinActivity(String isJoinActivity) {
        this.isJoinActivity = isJoinActivity;
    }

    public List<String> getManifesto() {
        return manifesto;
    }

    public void setManifesto(List<String> manifesto) {
        this.manifesto = manifesto;
    }
}