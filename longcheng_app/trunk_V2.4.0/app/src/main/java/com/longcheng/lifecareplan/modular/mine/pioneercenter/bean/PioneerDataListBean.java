package com.longcheng.lifecareplan.modular.mine.pioneercenter.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PioneerDataListBean extends ResponseBean {
    @SerializedName("data")
    private ArrayList<RecordBean> data;

    public ArrayList<RecordBean> getData() {
        return data;
    }

    public void setData(ArrayList<RecordBean> data) {
        this.data = data;
    }

   public class  RecordBean{
        String create_time;
        String money;
        int status;//订单状态 0:审核中,1:已完成

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
