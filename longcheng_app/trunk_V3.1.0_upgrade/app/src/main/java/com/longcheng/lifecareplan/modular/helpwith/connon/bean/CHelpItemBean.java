package com.longcheng.lifecareplan.modular.helpwith.connon.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class CHelpItemBean implements Serializable {


    private String solar_terms_en;
    private String solar_terms_name;
    private String jieqi_name;

    private String knp_msg_id;
    private String avatar;
    @SerializedName(value = "name", alternate = {"user_name"})
    private String name;
    private String patient_jieqi_branch_name;
    private String knp_team_number_id;

    private String knp_team_money_id;
    private String super_ability;
    private  String score;
    private int card_number;

    public String getJieqi_name() {
        return jieqi_name;
    }

    public void setJieqi_name(String jieqi_name) {
        this.jieqi_name = jieqi_name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public CHelpItemBean(String name) {
        this.name = name;
    }

    public String getSolar_terms_en() {
        return solar_terms_en;
    }

    public void setSolar_terms_en(String solar_terms_en) {
        this.solar_terms_en = solar_terms_en;
    }

    public String getSolar_terms_name() {
        return solar_terms_name;
    }

    public void setSolar_terms_name(String solar_terms_name) {
        this.solar_terms_name = solar_terms_name;
    }

    public String getKnp_msg_id() {
        return knp_msg_id;
    }

    public void setKnp_msg_id(String knp_msg_id) {
        this.knp_msg_id = knp_msg_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatient_jieqi_branch_name() {
        return patient_jieqi_branch_name;
    }

    public void setPatient_jieqi_branch_name(String patient_jieqi_branch_name) {
        this.patient_jieqi_branch_name = patient_jieqi_branch_name;
    }

    public String getKnp_team_number_id() {
        return knp_team_number_id;
    }

    public void setKnp_team_number_id(String knp_team_number_id) {
        this.knp_team_number_id = knp_team_number_id;
    }

    public String getKnp_team_money_id() {
        return knp_team_money_id;
    }

    public void setKnp_team_money_id(String knp_team_money_id) {
        this.knp_team_money_id = knp_team_money_id;
    }

    public String getSuper_ability() {
        return super_ability;
    }

    public void setSuper_ability(String super_ability) {
        this.super_ability = super_ability;
    }

    public int getCard_number() {
        return card_number;
    }

    public void setCard_number(int card_number) {
        this.card_number = card_number;
    }
}
