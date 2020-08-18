package com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class AYApplyListAfterBean extends ResponseBean {
    @SerializedName("knpMsgList")
    private List<BasicItemBean> knpMsgList;


    public List<BasicItemBean> getKnpMsgList() {
        return knpMsgList;
    }

    public void setKnpMsgList(List<BasicItemBean> knpMsgList) {
        this.knpMsgList = knpMsgList;
    }

    public class BasicItemBean {

        String knp_msg_id;
        @SerializedName(value = "patient_name", alternate = {"user_name"})
        String patient_name;
        String jieqi_name;
        String avatar;
        @SerializedName(value = "patient_phone", alternate = {"phone"})
        String patient_phone;
        int create_time;
        int show_type;// 1 疗愈跟踪(未打卡完成) 2 （打卡完成，行动未完成） 3 上传资料(打卡完成，行动完成) 4 上传资料完成
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

        public String getPatient_phone() {
            return patient_phone;
        }

        public void setPatient_phone(String patient_phone) {
            this.patient_phone = patient_phone;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getShow_type() {
            return show_type;
        }

        public void setShow_type(int show_type) {
            this.show_type = show_type;
        }
    }
}
