package com.longcheng.lifecareplan.modular.mine.recovercash.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderAfterBean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class AcountAfterBean implements Serializable {
    protected String account_balance;
    protected String max_fee;
    protected String max_withdraw;
    protected String keepMaxStarLevel;
    protected String user_withdraw_star_level_ratio;
    protected String withdraw_ability_ratio;

    public String getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(String account_balance) {
        this.account_balance = account_balance;
    }

    public String getMax_fee() {
        return max_fee;
    }

    public void setMax_fee(String max_fee) {
        this.max_fee = max_fee;
    }

    public String getMax_withdraw() {
        return max_withdraw;
    }

    public void setMax_withdraw(String max_withdraw) {
        this.max_withdraw = max_withdraw;
    }

    public String getKeepMaxStarLevel() {
        return keepMaxStarLevel;
    }

    public void setKeepMaxStarLevel(String keepMaxStarLevel) {
        this.keepMaxStarLevel = keepMaxStarLevel;
    }

    public String getUser_withdraw_star_level_ratio() {
        return user_withdraw_star_level_ratio;
    }

    public void setUser_withdraw_star_level_ratio(String user_withdraw_star_level_ratio) {
        this.user_withdraw_star_level_ratio = user_withdraw_star_level_ratio;
    }

    public String getWithdraw_ability_ratio() {
        return withdraw_ability_ratio;
    }

    public void setWithdraw_ability_ratio(String withdraw_ability_ratio) {
        this.withdraw_ability_ratio = withdraw_ability_ratio;
    }
}
