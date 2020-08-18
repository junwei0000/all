package com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PionOpenSetRecordDataBean extends ResponseBean {
    @SerializedName("data")
    protected ArrayList<PionOpenSetRecordItemBean> data;

    public ArrayList<PionOpenSetRecordItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<PionOpenSetRecordItemBean> data) {
        this.data = data;
    }

    public class PionOpenSetRecordItemBean {

        String user_id;
        String user_avatar;
        String user_name;
        String create_time;
        String user_branch_info;


        private String entrepreneurs_apply_rebate_id;
        private String money;
        private String avatar;
        private String jieqi_name;
        private String apply_time;
        private String bank_user_name;
        private String bank_name;
        private String bank_branch;
        private String bank_account;
        private int status;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getJieqi_name() {
            return jieqi_name;
        }

        public void setJieqi_name(String jieqi_name) {
            this.jieqi_name = jieqi_name;
        }

        public String getEntrepreneurs_apply_rebate_id() {
            return entrepreneurs_apply_rebate_id;
        }

        public void setEntrepreneurs_apply_rebate_id(String entrepreneurs_apply_rebate_id) {
            this.entrepreneurs_apply_rebate_id = entrepreneurs_apply_rebate_id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getApply_time() {
            return apply_time;
        }

        public void setApply_time(String apply_time) {
            this.apply_time = apply_time;
        }

        public String getBank_user_name() {
            return bank_user_name;
        }

        public void setBank_user_name(String bank_user_name) {
            this.bank_user_name = bank_user_name;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_branch() {
            return bank_branch;
        }

        public void setBank_branch(String bank_branch) {
            this.bank_branch = bank_branch;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUser_branch_info() {
            return user_branch_info;
        }

        public void setUser_branch_info(String user_branch_info) {
            this.user_branch_info = user_branch_info;
        }
    }
}
