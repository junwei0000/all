package com.longcheng.volunteer.modular.helpwith.energy.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class HelpEnergyAfterBean {
    @SerializedName("count")
    private String count;

    @SerializedName("help_msg")
    private List<HelpItemBean> help_msg;


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<HelpItemBean> getHelp_msg() {
        return help_msg;
    }

    public void setHelp_msg(List<HelpItemBean> help_msg) {
        this.help_msg = help_msg;
    }

}
