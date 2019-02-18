package com.longcheng.lifecareplan.modular.helpwith.connonEngineering.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class ConnonAfterBean {
    @SerializedName("count")
    private String count;

    @SerializedName("help_msg")
    private List<ConnonItemBean> help_msg;


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ConnonItemBean> getHelp_msg() {
        return help_msg;
    }

    public void setHelp_msg(List<ConnonItemBean> help_msg) {
        this.help_msg = help_msg;
    }

}
