package com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class NFCDetailListDataBean extends ResponseBean {
    @SerializedName("data")
    private ArrayList<NFCDetailItemBean> data;

    public ArrayList<NFCDetailItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<NFCDetailItemBean> data) {
        this.data = data;
    }
}
