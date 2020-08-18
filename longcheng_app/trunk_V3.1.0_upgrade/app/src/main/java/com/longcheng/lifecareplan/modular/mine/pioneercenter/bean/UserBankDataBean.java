package com.longcheng.lifecareplan.modular.mine.pioneercenter.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class UserBankDataBean extends ResponseBean {
    @SerializedName("data")
    private BankItem data;

    public BankItem getData() {
        return data;
    }

    public void setData(BankItem data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }

    public class BankItem {
        //银行卡信息
        private String knp_team_bind_card_id;
        private String cardholder_name;
        private String bank_name;
        private String bank_full_name;
        private String bank_no;

        public String getKnp_team_bind_card_id() {
            return knp_team_bind_card_id;
        }

        public void setKnp_team_bind_card_id(String knp_team_bind_card_id) {
            this.knp_team_bind_card_id = knp_team_bind_card_id;
        }

        public String getCardholder_name() {
            return cardholder_name;
        }

        public void setCardholder_name(String cardholder_name) {
            this.cardholder_name = cardholder_name;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_full_name() {
            return bank_full_name;
        }

        public void setBank_full_name(String bank_full_name) {
            this.bank_full_name = bank_full_name;
        }

        public String getBank_no() {
            return bank_no;
        }

        public void setBank_no(String bank_no) {
            this.bank_no = bank_no;
        }
    }
}
