package com.longcheng.lifecareplan.modular.helpwith.connon.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class CHelpListDataBean extends ResponseBean {
    @SerializedName("data")
    private ArrayList<CHelpListItemBean> data;

    public ArrayList<CHelpListItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<CHelpListItemBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }

    public static class CHelpListItemBean {

        String user_id;
        String patient_name;
        String patient_avatar;
        String user_name;
        @SerializedName(value = "user_avatar", alternate = {"avatar"})
        String user_avatar;
        String create_time;
        int person_number;
        int already_person_number;
        String team_number_name;
        String knp_group_room_id;
        String entrepreneurs_team_item_id;
        int grant_status;//0未完成 ；1已完成
        int process_status;
        int process_status_item;//流程状态：0 人未满,1 人已满 2 已开桌 3 已完成

        int type;//1结缘 ；2结伴
        int role;//1队长 ；2队员
        ArrayList<CHelpListItemBean> items;
        int position;

        public int getProcess_status_item() {
            return process_status_item;
        }

        public void setProcess_status_item(int process_status_item) {
            this.process_status_item = process_status_item;
        }

        public int getProcess_status() {
            return process_status;
        }

        public void setProcess_status(int process_status) {
            this.process_status = process_status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getAlready_person_number() {
            return already_person_number;
        }

        public void setAlready_person_number(int already_person_number) {
            this.already_person_number = already_person_number;
        }

        public String getEntrepreneurs_team_item_id() {
            return entrepreneurs_team_item_id;
        }

        public void setEntrepreneurs_team_item_id(String entrepreneurs_team_item_id) {
            this.entrepreneurs_team_item_id = entrepreneurs_team_item_id;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }


        public String getKnp_group_room_id() {
            return knp_group_room_id;
        }

        public void setKnp_group_room_id(String knp_group_room_id) {
            this.knp_group_room_id = knp_group_room_id;
        }

        public String getTeam_number_name() {
            return team_number_name;
        }

        public void setTeam_number_name(String team_number_name) {
            this.team_number_name = team_number_name;
        }

        public int getGrant_status() {
            return grant_status;
        }

        public void setGrant_status(int grant_status) {
            this.grant_status = grant_status;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getPerson_number() {
            return person_number;
        }

        public void setPerson_number(int person_number) {
            this.person_number = person_number;
        }

        public ArrayList<CHelpListItemBean> getItems() {
            return items;
        }

        public void setItems(ArrayList<CHelpListItemBean> items) {
            this.items = items;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
