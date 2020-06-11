package com.longcheng.lifecareplan.modular.helpwith.myDedication.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpItemBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class MyDedicationAfterBean implements Serializable {
    //列表
    private String count;
    private String ability;
    private String mutual_help_num;
    private List<ItemBean> mutualHelpMyBlessLists;


    //明细
    private List<ItemBean> mutualHelpMyBlessDetails;


    public List<ItemBean> getMutualHelpMyBlessDetails() {
        return mutualHelpMyBlessDetails;
    }

    public void setMutualHelpMyBlessDetails(List<ItemBean> mutualHelpMyBlessDetails) {
        this.mutualHelpMyBlessDetails = mutualHelpMyBlessDetails;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getMutual_help_num() {
        return mutual_help_num;
    }

    public void setMutual_help_num(String mutual_help_num) {
        this.mutual_help_num = mutual_help_num;
    }

    public List<ItemBean> getMutualHelpMyBlessLists() {
        return mutualHelpMyBlessLists;
    }

    public void setMutualHelpMyBlessLists(List<ItemBean> mutualHelpMyBlessLists) {
        this.mutualHelpMyBlessLists = mutualHelpMyBlessLists;
    }
}
