package com.longcheng.lifecareplan.modular.mine.awordofgold.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Burning on 2018/9/4.
 */

public class AWordOfGoldBean {
    @SerializedName("area_simple")
    protected String area;
    @SerializedName("birthday")
    protected String birthday;
    @SerializedName("user_name")
    protected String userName;
    @SerializedName("zangfus")
    List<String> info;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }
}
