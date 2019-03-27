package com.longcheng.volunteer.modular.helpwith.autohelp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class AutoHelpAfterBean implements Serializable {
    private AutoHelpItemBean automationHelpInfo;
    private List<AutoHelpItemBean> automationHelpType;
    private List<AutoHelpItemBean> mutualHelpMoney;
    private List<AutoHelpItemBean> helpNumbers;

    public AutoHelpItemBean getAutomationHelpInfo() {
        return automationHelpInfo;
    }

    public void setAutomationHelpInfo(AutoHelpItemBean automationHelpInfo) {
        this.automationHelpInfo = automationHelpInfo;
    }

    public List<AutoHelpItemBean> getAutomationHelpType() {
        return automationHelpType;
    }

    public void setAutomationHelpType(List<AutoHelpItemBean> automationHelpType) {
        this.automationHelpType = automationHelpType;
    }

    public List<AutoHelpItemBean> getMutualHelpMoney() {
        return mutualHelpMoney;
    }

    public void setMutualHelpMoney(List<AutoHelpItemBean> mutualHelpMoney) {
        this.mutualHelpMoney = mutualHelpMoney;
    }

    public List<AutoHelpItemBean> getHelpNumbers() {
        return helpNumbers;
    }

    public void setHelpNumbers(List<AutoHelpItemBean> helpNumbers) {
        this.helpNumbers = helpNumbers;
    }
}
