package com.longcheng.lifecareplan.modular.mine.bill.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * Created by Burning on 2018/8/31.
 */

public class BillResultBean extends ResponseBean {
    @SerializedName("data")
    private BillAfterBean data;

//    /**
//     *
//     */
//    @SerializedName("total_page")
//    private int pageCountAll;
//
//    /**
//     * 账单来源
//     */
//    @SerializedName("source")
//    private int source;

    public BillAfterBean getData() {
        return data;
    }

    public void setData(BillAfterBean data) {
        this.data = data;
    }


//    public int getPageCountAll() {
//        return pageCountAll;
//    }
//
//    public void setPageCountAll(int pageCountAll) {
//        this.pageCountAll = pageCountAll;
//    }
//
//    public int getSource() {
//        return source;
//    }
//
//    public void setSource(int source) {
//        this.source = source;
//    }

}
