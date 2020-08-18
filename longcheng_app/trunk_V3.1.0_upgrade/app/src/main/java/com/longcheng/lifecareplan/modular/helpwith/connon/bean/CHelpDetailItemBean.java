package com.longcheng.lifecareplan.modular.helpwith.connon.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LC on 2018/3/11.
 */

public class CHelpDetailItemBean implements Serializable {


    private String knp_team_room_id;
    private String ability;
    int card_number;
    private String knp_team_money_id;
    private String knp_team_number_id;
    private String patient_name;
    private String patient_avatar;
    private String patient_jieqi_branch_name;
    private String patient_jieqi_name_cn;
    private ArrayList<String> patient_jieqi_defect;
    private int person_number;
    int grant_status;
    int is_super_ability;

    private String cn;
    private String day_diff;


    private String user_id;
    private String user_name;
    private String user_avatar;
    private String grant_points;
    int position;
    int role_type;//1 队长

    public String getGrant_points() {
        return grant_points;
    }

    public void setGrant_points(String grant_points) {
        this.grant_points = grant_points;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getRole_type() {
        return role_type;
    }

    public void setRole_type(int role_type) {
        this.role_type = role_type;
    }

    public String getKnp_team_room_id() {
        return knp_team_room_id;
    }

    public void setKnp_team_room_id(String knp_team_room_id) {
        this.knp_team_room_id = knp_team_room_id;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public int getCard_number() {
        return card_number;
    }

    public void setCard_number(int card_number) {
        this.card_number = card_number;
    }

    public String getKnp_team_money_id() {
        return knp_team_money_id;
    }

    public void setKnp_team_money_id(String knp_team_money_id) {
        this.knp_team_money_id = knp_team_money_id;
    }

    public String getKnp_team_number_id() {
        return knp_team_number_id;
    }

    public void setKnp_team_number_id(String knp_team_number_id) {
        this.knp_team_number_id = knp_team_number_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_avatar() {
        return patient_avatar;
    }

    public void setPatient_avatar(String patient_avatar) {
        this.patient_avatar = patient_avatar;
    }

    public String getPatient_jieqi_branch_name() {
        return patient_jieqi_branch_name;
    }

    public void setPatient_jieqi_branch_name(String patient_jieqi_branch_name) {
        this.patient_jieqi_branch_name = patient_jieqi_branch_name;
    }

    public String getPatient_jieqi_name_cn() {
        return patient_jieqi_name_cn;
    }

    public void setPatient_jieqi_name_cn(String patient_jieqi_name_cn) {
        this.patient_jieqi_name_cn = patient_jieqi_name_cn;
    }

    public ArrayList<String> getPatient_jieqi_defect() {
        return patient_jieqi_defect;
    }

    public void setPatient_jieqi_defect(ArrayList<String> patient_jieqi_defect) {
        this.patient_jieqi_defect = patient_jieqi_defect;
    }

    public int getPerson_number() {
        return person_number;
    }

    public void setPerson_number(int person_number) {
        this.person_number = person_number;
    }

    public int getGrant_status() {
        return grant_status;
    }

    public void setGrant_status(int grant_status) {
        this.grant_status = grant_status;
    }

    public int getIs_super_ability() {
        return is_super_ability;
    }

    public void setIs_super_ability(int is_super_ability) {
        this.is_super_ability = is_super_ability;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getDay_diff() {
        return day_diff;
    }

    public void setDay_diff(String day_diff) {
        this.day_diff = day_diff;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
