package com.longcheng.lifecareplan.modular.mine.pioneercenter.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PioneerCounterDataBean extends ResponseBean {
    @SerializedName("data")
    private PionCounterItemBean data;

    public PionCounterItemBean getData() {
        return data;
    }

    public void setData(PionCounterItemBean data) {
        this.data = data;
    }

    public class PionCounterItemBean extends ResponseBean {
        String zhufubao_balance_num;//祝福宝平衡数量
        String fuqibao_balance_num;//福气宝平衡数量
        int zhufubao_balance_status;//祝福宝平衡状态 平衡状态 0：未知 1：平衡 -1 不平衡
        int fuqibao_balance_status;//福气宝平衡状态 平衡状态 0：未知 1：平衡 -1 不平衡
        String zhufubao;//祝福余额
        String fuqibao;//福气宝余额
        String fuqibao_limit_fixed;//福气宝平衡目标
        String zhufubao_limit_fixed;//祝福宝平衡目标
        String rebate;//财礼

        String bank_user_name;
        String bank_name;
        String bank_branch;
        String bank_account;

        public String getRebate() {
            return rebate;
        }

        public void setRebate(String rebate) {
            this.rebate = rebate;
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

        public String getZhufubao_balance_num() {
            return zhufubao_balance_num;
        }

        public void setZhufubao_balance_num(String zhufubao_balance_num) {
            this.zhufubao_balance_num = zhufubao_balance_num;
        }

        public String getFuqibao_balance_num() {
            return fuqibao_balance_num;
        }

        public void setFuqibao_balance_num(String fuqibao_balance_num) {
            this.fuqibao_balance_num = fuqibao_balance_num;
        }

        public int getZhufubao_balance_status() {
            return zhufubao_balance_status;
        }

        public void setZhufubao_balance_status(int zhufubao_balance_status) {
            this.zhufubao_balance_status = zhufubao_balance_status;
        }

        public int getFuqibao_balance_status() {
            return fuqibao_balance_status;
        }

        public void setFuqibao_balance_status(int fuqibao_balance_status) {
            this.fuqibao_balance_status = fuqibao_balance_status;
        }

        public String getZhufubao() {
            return zhufubao;
        }

        public void setZhufubao(String zhufubao) {
            this.zhufubao = zhufubao;
        }

        public String getFuqibao() {
            return fuqibao;
        }

        public void setFuqibao(String fuqibao) {
            this.fuqibao = fuqibao;
        }

        public String getFuqibao_limit_fixed() {
            return fuqibao_limit_fixed;
        }

        public void setFuqibao_limit_fixed(String fuqibao_limit_fixed) {
            this.fuqibao_limit_fixed = fuqibao_limit_fixed;
        }

        public String getZhufubao_limit_fixed() {
            return zhufubao_limit_fixed;
        }

        public void setZhufubao_limit_fixed(String zhufubao_limit_fixed) {
            this.zhufubao_limit_fixed = zhufubao_limit_fixed;
        }
    }
}
