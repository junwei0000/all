package com.longcheng.lifecareplan.modular.helpwith.reportbless.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class FQCenterDataBean extends ResponseBean {
    @SerializedName("data")
    protected CenterItemBean data;

    public CenterItemBean getData() {
        return data;
    }

    public void setData(CenterItemBean data) {
        this.data = data;
    }

    public class CenterItemBean{

        String noUserCount;
        String ingCount;
        boolean isCurrentSign;
        String bless_id;
        String complete_money;
        int helpType;//1 天下无癌  2 天下无债
        String wuhouName;
        String jieqi_pig;
        ArrayList<CenterItemBean> signDetaileds;
        String date;
        int is_sign;//是否已签到
        int number;

        public String getBless_id() {
            return bless_id;
        }

        public void setBless_id(String bless_id) {
            this.bless_id = bless_id;
        }

        public String getWuhouName() {
            return wuhouName;
        }

        public void setWuhouName(String wuhouName) {
            this.wuhouName = wuhouName;
        }

        public String getJieqi_pig() {
            return jieqi_pig;
        }

        public void setJieqi_pig(String jieqi_pig) {
            this.jieqi_pig = jieqi_pig;
        }

        public String getNoUserCount() {
            return noUserCount;
        }

        public void setNoUserCount(String noUserCount) {
            this.noUserCount = noUserCount;
        }

        public String getIngCount() {
            return ingCount;
        }

        public void setIngCount(String ingCount) {
            this.ingCount = ingCount;
        }

        public boolean isCurrentSign() {
            return isCurrentSign;
        }

        public void setCurrentSign(boolean currentSign) {
            isCurrentSign = currentSign;
        }

        public String getComplete_money() {
            return complete_money;
        }

        public void setComplete_money(String complete_money) {
            this.complete_money = complete_money;
        }

        public int getHelpType() {
            return helpType;
        }

        public void setHelpType(int helpType) {
            this.helpType = helpType;
        }

        public ArrayList<CenterItemBean> getSignDetaileds() {
            return signDetaileds;
        }

        public void setSignDetaileds(ArrayList<CenterItemBean> signDetaileds) {
            this.signDetaileds = signDetaileds;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getIs_sign() {
            return is_sign;
        }

        public void setIs_sign(int is_sign) {
            this.is_sign = is_sign;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
