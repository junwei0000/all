package com.longcheng.lifecareplan.modular.mine.relationship.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Burning on 2018/9/1.
 */

public class RelationshipBook {
    @SerializedName("user_name")
    String name;
    @SerializedName("is_help")
    int isHelp;
    @SerializedName("first_mutual_aid_time")
    long helpTime;
    @SerializedName("mutual_help_person_num")
    int helpNum;
    @SerializedName("ability")
    int helpEnergy;
    @SerializedName("be_first_mutual_aid_time")
    long beHelpedTime;
    @SerializedName("be_mutual_aid_ability_num")
    int helpMeEnerty;
    @SerializedName("be_first_mutual_aid_user_name")
    String firtHelpMeName;
    @SerializedName("be_mutual_aid_user_num")
    int beHelpedNum;

    @SerializedName("register_time")
    long registerTime;
    @SerializedName("become_cho_time")
    long choTime;

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public long getChoTime() {
        return choTime;
    }

    public void setChoTime(long choTime) {
        this.choTime = choTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsHelp() {
        return isHelp;
    }

    public void setIsHelp(int isHelp) {
        this.isHelp = isHelp;
    }

    public long getHelpTime() {
        return helpTime;
    }

    public void setHelpTime(long helpTime) {
        this.helpTime = helpTime;
    }

    public int getHelpNum() {
        return helpNum;
    }

    public void setHelpNum(int helpNum) {
        this.helpNum = helpNum;
    }

    public int getHelpEnergy() {
        return helpEnergy;
    }

    public void setHelpEnergy(int helpEnergy) {
        this.helpEnergy = helpEnergy;
    }

    public long getBeHelpedTime() {
        return beHelpedTime;
    }

    public void setBeHelpedTime(long beHelpedTime) {
        this.beHelpedTime = beHelpedTime;
    }

    public int getBeHelpedNum() {
        return beHelpedNum;
    }

    public void setBeHelpedNum(int beHelpedNum) {
        this.beHelpedNum = beHelpedNum;
    }

    public String getFirtHelpMeName() {
        return firtHelpMeName;
    }

    public void setFirtHelpMeName(String firtHelpMeName) {
        this.firtHelpMeName = firtHelpMeName;
    }

    public int getHelpMeEnerty() {
        return helpMeEnerty;
    }

    public void setHelpMeEnerty(int helpMeEnerty) {
        this.helpMeEnerty = helpMeEnerty;
    }
}
