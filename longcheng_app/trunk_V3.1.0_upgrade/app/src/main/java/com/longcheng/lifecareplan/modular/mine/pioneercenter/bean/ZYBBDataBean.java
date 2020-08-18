package com.longcheng.lifecareplan.modular.mine.pioneercenter.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class ZYBBDataBean extends ResponseBean {
    @SerializedName("data")
    private AfterBean data;

    public AfterBean getData() {
        return data;
    }

    public void setData(AfterBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }

    public class AfterBean {

        String zhufubao_balance_num;
        int is_ing_order;

        AfterBean bank;
        String entrepreneurs_bank_id;
        String account_name;
        String bank_name;
        String branch_name;
        String bank_no;
        String new_bank_no;
        String payment_img;

        public String getEntrepreneurs_bank_id() {
            return entrepreneurs_bank_id;
        }

        public void setEntrepreneurs_bank_id(String entrepreneurs_bank_id) {
            this.entrepreneurs_bank_id = entrepreneurs_bank_id;
        }

        public String getPayment_img() {
            return payment_img;
        }

        public void setPayment_img(String payment_img) {
            this.payment_img = payment_img;
        }

        public String getZhufubao_balance_num() {
            return zhufubao_balance_num;
        }

        public void setZhufubao_balance_num(String zhufubao_balance_num) {
            this.zhufubao_balance_num = zhufubao_balance_num;
        }

        public int getIs_ing_order() {
            return is_ing_order;
        }

        public void setIs_ing_order(int is_ing_order) {
            this.is_ing_order = is_ing_order;
        }

        public AfterBean getBank() {
            return bank;
        }

        public void setBank(AfterBean bank) {
            this.bank = bank;
        }

        public String getAccount_name() {
            return account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getBank_no() {
            return bank_no;
        }

        public void setBank_no(String bank_no) {
            this.bank_no = bank_no;
        }

        public String getNew_bank_no() {
            return new_bank_no;
        }

        public void setNew_bank_no(String new_bank_no) {
            this.new_bank_no = new_bank_no;
        }

//        public AfterBean getOrder() {
//            return order;
//        }
//
//        public void setOrder(AfterBean order) {
//            this.order = order;
//        }
    }
}
