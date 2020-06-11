package com.longcheng.lifecareplan.modular.mine.doctor.volunteer.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class AYRecordListDataBean extends ResponseBean {
    @SerializedName("data")
    private ArrayList<DocItemBean> data;


    public ArrayList<DocItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<DocItemBean> data) {
        this.data = data;
    }

    public class DocItemBean {

        String knp_msg_id;
        String patient_name;
        String patient_phone;
        String jieqi_name;
        String avatar;
        int create_time;
        String sign_start_day;
        String sign_end_day;

        public String getSign_start_day() {
            return sign_start_day;
        }

        public void setSign_start_day(String sign_start_day) {
            this.sign_start_day = sign_start_day;
        }

        public String getSign_end_day() {
            return sign_end_day;
        }

        public void setSign_end_day(String sign_end_day) {
            this.sign_end_day = sign_end_day;
        }

        public String getKnp_msg_id() {
            return knp_msg_id;
        }

        public void setKnp_msg_id(String knp_msg_id) {
            this.knp_msg_id = knp_msg_id;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}
