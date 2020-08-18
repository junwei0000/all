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
    private String avatar;
    private String user_name;
    private String create_time;
    private String price;
    private String jieqibao;
    private String jieqi_name;

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
    @SerializedName(value = "user_zhufubao_order_id", alternate = {"entrepreneurs_zhufubao_order_id"})
    private String user_zhufubao_order_id;
    private int status;

    private String b_avatar;
    private String b_user_name;
    private int run_time;


    private String zhuyoubao_team_rule_id;

    public String getZhuyoubao_team_rule_id() {
        return zhuyoubao_team_rule_id;
    }

    public void setZhuyoubao_team_rule_id(String zhuyoubao_team_rule_id) {
        this.zhuyoubao_team_rule_id = zhuyoubao_team_rule_id;
    }

    public String getJieqibao() {
        return jieqibao;
    }

    public void setJieqibao(String jieqibao) {
        this.jieqibao = jieqibao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getB_avatar() {
        return b_avatar;
    }

    public void setB_avatar(String b_avatar) {
        this.b_avatar = b_avatar;
    }

    public String getB_user_name() {
        return b_user_name;
    }

    public void setB_user_name(String b_user_name) {
        this.b_user_name = b_user_name;
    }

    public int getRun_time() {
        return run_time;
    }

    public void setRun_time(int run_time) {
        this.run_time = run_time;
    }

    public String getUser_zhufubao_order_id() {
        return user_zhufubao_order_id;
    }

    public void setUser_zhufubao_order_id(String user_zhufubao_order_id) {
        this.user_zhufubao_order_id = user_zhufubao_order_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getJieqi_name() {
        return jieqi_name;
    }

    public void setJieqi_name(String jieqi_name) {
        this.jieqi_name = jieqi_name;
    }

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
