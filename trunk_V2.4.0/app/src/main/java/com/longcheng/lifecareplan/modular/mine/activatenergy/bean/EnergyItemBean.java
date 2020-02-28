package com.longcheng.lifecareplan.modular.mine.activatenergy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class EnergyItemBean {
    @SerializedName("asset")
    private String asset;

    @SerializedName("activate_ability_config_id")
    private String activate_ability_config_id;
    @SerializedName("money")
    private String money;
    @SerializedName("reward_value")
    private String reward_value;
    @SerializedName("give_value")
    private String give_value;
    @SerializedName("is_default")
    private int is_default;
    @SerializedName("type")
    private int type;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getActivate_ability_config_id() {
        return activate_ability_config_id;
    }

    public void setActivate_ability_config_id(String activate_ability_config_id) {
        this.activate_ability_config_id = activate_ability_config_id;
    }

    public String getReward_value() {
        return reward_value;
    }

    public void setReward_value(String reward_value) {
        this.reward_value = reward_value;
    }

    public String getGive_value() {
        return give_value;
    }

    public void setGive_value(String give_value) {
        this.give_value = give_value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public EnergyItemBean(String money) {
        this.money = money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public String getMoney() {
        return money;
    }

    public int getIs_default() {
        return is_default;
    }
}
