package com.longcheng.lifecareplan.modular.helpwith.reportbless.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class MessageListDataBean extends ResponseBean {
    @SerializedName("data")
    protected ArrayList<MessageListItemBean> data;

    public ArrayList<MessageListItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<MessageListItemBean> data) {
        this.data = data;
    }
}
