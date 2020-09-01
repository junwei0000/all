package com.longcheng.lifecareplan.modular.mine.pioneercenter.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

public class ExitutorSearchBean extends ResponseBean {
    @SerializedName("data")
    private ArrayList<ExitutorUserItem> data;

    public ArrayList<ExitutorUserItem> getData() {
        return data;
    }

    public void setData(ArrayList<ExitutorUserItem> data) {
        this.data = data;
    }

}
