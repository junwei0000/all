package com.longcheng.lifecareplan.modular.mine.pioneercenter.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PioneerXuFeiDataBean extends ResponseBean {
    @SerializedName("data")
    private ArrayList<PioneerXuFeiItemBean> data;

    public ArrayList<PioneerXuFeiItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<PioneerXuFeiItemBean> data) {
        this.data = data;
    }

    public class PioneerXuFeiItemBean extends ResponseBean {
        String entrepreneurs_renew_config_id;
        String name;
        String money;
        String save_money;

        public String getSave_money() {
            return save_money;
        }

        public void setSave_money(String save_money) {
            this.save_money = save_money;
        }

        public String getEntrepreneurs_renew_config_id() {
            return entrepreneurs_renew_config_id;
        }

        public void setEntrepreneurs_renew_config_id(String entrepreneurs_renew_config_id) {
            this.entrepreneurs_renew_config_id = entrepreneurs_renew_config_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
