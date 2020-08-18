package com.longcheng.lifecareplan.utils.myview.address;

import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

public class SelctAeraDataInfo extends ResponseBean {
    private ArrayList<SelctAeraInfo> data;

    public ArrayList<SelctAeraInfo> getData() {
        return data;
    }

    public void setData(ArrayList<SelctAeraInfo> data) {
        this.data = data;
    }



}
