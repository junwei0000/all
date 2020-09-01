package com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class DocRewardListDataBean extends ResponseBean {
    @SerializedName("data")
    private ArrayList<DocItemBean> data;


    public ArrayList<DocItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<DocItemBean> data) {
        this.data = data;
    }

    public class DocItemBean {

        String avatar;
        String patient_name;
        String patient_phone;
        String jieqi_name;
        int create_time;
        int finish;//1 已完成
        String money;

        int selectstatus;
        String volunteer_user_name;
        String volunteer_user_phone;
        String volunteer_user_avatar;
        String doctor_relation_volunteer_id;


        public int getSelectstatus() {
            return selectstatus;
        }

        public void setSelectstatus(int selectstatus) {
            this.selectstatus = selectstatus;
        }

        public String getVolunteer_user_name() {
            return volunteer_user_name;
        }

        public void setVolunteer_user_name(String volunteer_user_name) {
            this.volunteer_user_name = volunteer_user_name;
        }

        public String getVolunteer_user_phone() {
            return volunteer_user_phone;
        }

        public void setVolunteer_user_phone(String volunteer_user_phone) {
            this.volunteer_user_phone = volunteer_user_phone;
        }

        public String getVolunteer_user_avatar() {
            return volunteer_user_avatar;
        }

        public void setVolunteer_user_avatar(String volunteer_user_avatar) {
            this.volunteer_user_avatar = volunteer_user_avatar;
        }

        public String getDoctor_relation_volunteer_id() {
            return doctor_relation_volunteer_id;
        }

        public void setDoctor_relation_volunteer_id(String doctor_relation_volunteer_id) {
            this.doctor_relation_volunteer_id = doctor_relation_volunteer_id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPatient_name() {
            return patient_name;
        }

        public void setPatient_name(String patient_name) {
            this.patient_name = patient_name;
        }

        public String getPatient_phone() {
            return patient_phone;
        }

        public void setPatient_phone(String patient_phone) {
            this.patient_phone = patient_phone;
        }

        public String getJieqi_name() {
            return jieqi_name;
        }

        public void setJieqi_name(String jieqi_name) {
            this.jieqi_name = jieqi_name;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
