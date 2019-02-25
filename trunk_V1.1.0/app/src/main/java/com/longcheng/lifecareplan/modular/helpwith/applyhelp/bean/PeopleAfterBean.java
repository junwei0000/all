package com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/23 17:44
 * 意图：
 */

public class PeopleAfterBean {
    @SerializedName("searchRole")
    private String searchRole;
    @SerializedName("FamilyList")
    private List<PeopleItemBean> FamilyList;

    public String getSearchRole() {
        return searchRole;
    }

    public void setSearchRole(String searchRole) {
        this.searchRole = searchRole;
    }

    public List<PeopleItemBean> getFamilyList() {
        return FamilyList;
    }

    public void setFamilyList(List<PeopleItemBean> familyList) {
        FamilyList = familyList;
    }

    @Override
    public String toString() {
        return "HomeAfterBean{" +
                ", searchRole='" + searchRole + '\'' +
                ", FamilyList=" + FamilyList +
                '}';
    }
}