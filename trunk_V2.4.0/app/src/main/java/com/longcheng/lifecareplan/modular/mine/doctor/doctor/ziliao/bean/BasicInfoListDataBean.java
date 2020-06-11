package com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class BasicInfoListDataBean extends ResponseBean {
    @SerializedName("data")
    private List<BasicItemBean> data;

    public List<BasicItemBean> getData() {
        return data;
    }

    public void setData(List<BasicItemBean> data) {
        this.data = data;
    }

    public class BasicItemBean {
        int selectstatus;
        String user_id;
        String knp_msg_patient_id;
        String patient_name;
        String patient_phone;
        int patient_type;//1 爱友
        String avatar;
        String jieqi_name;
        String create_time;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getSelectstatus() {
            return selectstatus;
        }

        public void setSelectstatus(int selectstatus) {
            this.selectstatus = selectstatus;
        }

        public String getKnp_msg_patient_id() {
            return knp_msg_patient_id;
        }

        public void setKnp_msg_patient_id(String knp_msg_patient_id) {
            this.knp_msg_patient_id = knp_msg_patient_id;
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

        public int getPatient_type() {
            return patient_type;
        }

        public void setPatient_type(int patient_type) {
            this.patient_type = patient_type;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getJieqi_name() {
            return jieqi_name;
        }

        public void setJieqi_name(String jieqi_name) {
            this.jieqi_name = jieqi_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
