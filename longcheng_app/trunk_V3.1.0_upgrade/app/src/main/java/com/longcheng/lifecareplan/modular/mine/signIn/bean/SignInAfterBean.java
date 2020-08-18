package com.longcheng.lifecareplan.modular.mine.signIn.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class SignInAfterBean implements Serializable {
    private String rewardSkb;
    private String startDay;
    private String endDay;
    private String jieqi;
    private int isSign;

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getRewardSkb() {
        return rewardSkb;
    }

    public void setRewardSkb(String rewardSkb) {
        this.rewardSkb = rewardSkb;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getJieqi() {
        return jieqi;
    }

    public void setJieqi(String jieqi) {
        this.jieqi = jieqi;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }
}
