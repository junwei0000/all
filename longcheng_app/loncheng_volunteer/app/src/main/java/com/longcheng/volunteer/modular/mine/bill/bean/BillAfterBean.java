package com.longcheng.volunteer.modular.mine.bill.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Burning on 2018/8/30.
 */

public class BillAfterBean {
    @SerializedName("lists")
    private List<BillItemBean> bills;
    @SerializedName("source_type_all")
    private List<BillItemBean> source_type_allList;


    public List<BillItemBean> getSource_type_allList() {
        return source_type_allList;
    }

    public void setSource_type_allList(List<BillItemBean> source_type_allList) {
        this.source_type_allList = source_type_allList;
    }

    public List<BillItemBean> getBills() {
        return bills;
    }

    public void setBills(List<BillItemBean> bills) {
        this.bills = bills;
    }
}
