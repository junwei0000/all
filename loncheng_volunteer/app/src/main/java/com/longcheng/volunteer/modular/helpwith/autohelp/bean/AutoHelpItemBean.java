package com.longcheng.volunteer.modular.helpwith.autohelp.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class AutoHelpItemBean implements Serializable {


    private int key;//
    private String value;


    private String automation_help_id;//
    private String user_id;
    private int is_automation_help;//是否开启智能互祝 1是 0 否
    private String automation_help_type;//目前只有 4 ：所有人 对应automationHelpType KEY
    private String automation_help_val;

    private int mutual_help_money_id;//生命能量j价格ID 对应 mutualHelpMoney KEY
    private String ability;

    private int is_less_ability_sms;
    private int total_help_number;
    private String total_ability;
    private int everyday_help_number;//每日互祝次数 对应 helpNumbers KEY
    private String everyday_already_help_number;
    private int is_automation_open_help;


    public String getAutomation_help_id() {
        return automation_help_id;
    }

    public void setAutomation_help_id(String automation_help_id) {
        this.automation_help_id = automation_help_id;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getIs_automation_help() {
        return is_automation_help;
    }

    public void setIs_automation_help(int is_automation_help) {
        this.is_automation_help = is_automation_help;
    }

    public String getAutomation_help_type() {
        return automation_help_type;
    }

    public void setAutomation_help_type(String automation_help_type) {
        this.automation_help_type = automation_help_type;
    }

    public String getAutomation_help_val() {
        return automation_help_val;
    }

    public void setAutomation_help_val(String automation_help_val) {
        this.automation_help_val = automation_help_val;
    }

    public int getMutual_help_money_id() {
        return mutual_help_money_id;
    }

    public void setMutual_help_money_id(int mutual_help_money_id) {
        this.mutual_help_money_id = mutual_help_money_id;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public int getIs_less_ability_sms() {
        return is_less_ability_sms;
    }

    public void setIs_less_ability_sms(int is_less_ability_sms) {
        this.is_less_ability_sms = is_less_ability_sms;
    }

    public int getTotal_help_number() {
        return total_help_number;
    }

    public void setTotal_help_number(int total_help_number) {
        this.total_help_number = total_help_number;
    }

    public String getTotal_ability() {
        return total_ability;
    }

    public void setTotal_ability(String total_ability) {
        this.total_ability = total_ability;
    }

    public int getEveryday_help_number() {
        return everyday_help_number;
    }

    public void setEveryday_help_number(int everyday_help_number) {
        this.everyday_help_number = everyday_help_number;
    }

    public String getEveryday_already_help_number() {
        return everyday_already_help_number;
    }

    public void setEveryday_already_help_number(String everyday_already_help_number) {
        this.everyday_already_help_number = everyday_already_help_number;
    }

    public int getIs_automation_open_help() {
        return is_automation_open_help;
    }

    public void setIs_automation_open_help(int is_automation_open_help) {
        this.is_automation_open_help = is_automation_open_help;
    }
}
