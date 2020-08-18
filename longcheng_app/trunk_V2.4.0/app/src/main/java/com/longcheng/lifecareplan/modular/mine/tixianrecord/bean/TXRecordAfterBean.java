package com.longcheng.lifecareplan.modular.mine.tixianrecord.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class TXRecordAfterBean implements Serializable {
    @SerializedName("count")
    private int count;
    @SerializedName("list")
    private List<TXRecordItemBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<TXRecordItemBean> getList() {
        return list;
    }

    public void setList(List<TXRecordItemBean> list) {
        this.list = list;
    }
}
