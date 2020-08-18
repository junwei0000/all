package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class AcountAfterBean implements Serializable {
    protected String account_balance;//账户余额
    protected String max_fee;//最大手续费
    protected String max_withdraw;//账户最大可提现金额
    protected String keepMaxStarLevel;//连续保持的最大星级等级
    protected String user_withdraw_star_level_ratio;//用户当前星级 人民币比例
    protected String withdraw_ability_ratio;//当前星级 生命能量比例

    private  int isHaveMatch;
    private  int isFaceSwiping;//1 需要刷脸认证  0 不需要刷脸

    //钱包信息
    protected ArrayList<AcountItemBean> userApplyCashList;
    @SerializedName(value = "allowAsset", alternate = {"fuqibao"})
    protected String allowAsset;// 余额
    protected int is_apply_cash;//是否允许提现，不在提现范围时间内不予许
    protected AcountItemBean userMatchInfo;
    protected ArrayList<EnergyItemBean> pushQueueItems;

    protected String jieqibao;//
    protected String fuqibao_limit;//


    protected String todayFuqibao;//
    protected ArrayList<AcountItemBean> proportion;//
    protected String faceMoney;//

    protected String cashNum;
    protected ArrayList<EnergyItemBean> cashInfo;
    private  String ranking;


    public int getIsFaceSwiping() {
        return isFaceSwiping;
    }

    public void setIsFaceSwiping(int isFaceSwiping) {
        this.isFaceSwiping = isFaceSwiping;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public int getIsHaveMatch() {
        return isHaveMatch;
    }

    public void setIsHaveMatch(int isHaveMatch) {
        this.isHaveMatch = isHaveMatch;
    }

    public String getCashNum() {
        return cashNum;
    }

    public void setCashNum(String cashNum) {
        this.cashNum = cashNum;
    }

    public ArrayList<EnergyItemBean> getCashInfo() {
        return cashInfo;
    }

    public void setCashInfo(ArrayList<EnergyItemBean> cashInfo) {
        this.cashInfo = cashInfo;
    }

    public String getFaceMoney() {
        return faceMoney;
    }

    public void setFaceMoney(String faceMoney) {
        this.faceMoney = faceMoney;
    }

    public String getFuqibao_limit() {
        return fuqibao_limit;
    }

    public void setFuqibao_limit(String fuqibao_limit) {
        this.fuqibao_limit = fuqibao_limit;
    }

    public String getTodayFuqibao() {
        return todayFuqibao;
    }

    public void setTodayFuqibao(String todayFuqibao) {
        this.todayFuqibao = todayFuqibao;
    }

    public ArrayList<AcountItemBean> getProportion() {
        return proportion;
    }

    public void setProportion(ArrayList<AcountItemBean> proportion) {
        this.proportion = proportion;
    }

    public String getJieqibao() {
        return jieqibao;
    }

    public void setJieqibao(String jieqibao) {
        this.jieqibao = jieqibao;
    }

    public ArrayList<AcountItemBean> getUserApplyCashList() {
        return userApplyCashList;
    }

    public void setUserApplyCashList(ArrayList<AcountItemBean> userApplyCashList) {
        this.userApplyCashList = userApplyCashList;
    }

    public String getAllowAsset() {
        return allowAsset;
    }

    public void setAllowAsset(String allowAsset) {
        this.allowAsset = allowAsset;
    }

    public int getIs_apply_cash() {
        return is_apply_cash;
    }

    public void setIs_apply_cash(int is_apply_cash) {
        this.is_apply_cash = is_apply_cash;
    }

    public AcountItemBean getUserMatchInfo() {
        return userMatchInfo;
    }

    public void setUserMatchInfo(AcountItemBean userMatchInfo) {
        this.userMatchInfo = userMatchInfo;
    }

    public ArrayList<EnergyItemBean> getPushQueueItems() {
        return pushQueueItems;
    }

    public void setPushQueueItems(ArrayList<EnergyItemBean> pushQueueItems) {
        this.pushQueueItems = pushQueueItems;
    }

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
