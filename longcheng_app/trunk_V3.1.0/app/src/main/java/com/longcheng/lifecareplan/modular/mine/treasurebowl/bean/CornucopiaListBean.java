package com.longcheng.lifecareplan.modular.mine.treasurebowl.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

import java.util.List;

public class CornucopiaListBean extends ResponseBean {
    /**
     * data : [{"cornucopia_item_id":5,"cornucopia_item_no":123456789,"user_id":234032,"cornucopia_config_id":2,"type":1,"config_value":24,"rate":0.2,"total_number":24,"group_number":1,"shoukangyuan":24,"fuqibao":24,"zhuyoubao":24,"jieqibao":24,"status":1,"frozen_end_time":1659326006,"create_time":1597118006,"update_time":1597118006,"jieqi_num":47}]
     * status : 200
     */
    @SerializedName("data")
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cornucopia_item_id : 5
         * cornucopia_item_no : 123456789
         * user_id : 234032
         * cornucopia_config_id : 2
         * type : 1
         * config_value : 24
         * rate : 0.2
         * total_number : 24
         * group_number : 1
         * shoukangyuan : 24
         * fuqibao : 24
         * zhuyoubao : 24
         * jieqibao : 24
         * status : 1
         * frozen_end_time : 1659326006
         * create_time : 1597118006
         * update_time : 1597118006
         * jieqi_num : 47
         */

        private String cornucopia_item_id;
        private String cornucopia_item_no;
        private String user_id;
        private String cornucopia_config_id;
        private String type;
        private String config_value;
        private double rate;
        private String total_number;
        private String group_number;
        private String shoukangyuan;
        private String fuqibao;
        private String zhuyoubao;
        private String jieqibao;
        @SerializedName("status")
        private String statusX;
        private String frozen_end_time;
        private String create_time;
        private String update_time;
        private String jieqi_num;

        public String getCornucopia_item_id() {
            return cornucopia_item_id;
        }

        public void setCornucopia_item_id(String cornucopia_item_id) {
            this.cornucopia_item_id = cornucopia_item_id;
        }

        public String getCornucopia_item_no() {
            return cornucopia_item_no;
        }

        public void setCornucopia_item_no(String cornucopia_item_no) {
            this.cornucopia_item_no = cornucopia_item_no;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCornucopia_config_id() {
            return cornucopia_config_id;
        }

        public void setCornucopia_config_id(String cornucopia_config_id) {
            this.cornucopia_config_id = cornucopia_config_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getConfig_value() {
            return config_value;
        }

        public void setConfig_value(String config_value) {
            this.config_value = config_value;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public String getTotal_number() {
            return total_number;
        }

        public void setTotal_number(String total_number) {
            this.total_number = total_number;
        }

        public String getGroup_number() {
            return group_number;
        }

        public void setGroup_number(String group_number) {
            this.group_number = group_number;
        }

        public String getShoukangyuan() {
            return shoukangyuan;
        }

        public void setShoukangyuan(String shoukangyuan) {
            this.shoukangyuan = shoukangyuan;
        }

        public String getFuqibao() {
            return fuqibao;
        }

        public void setFuqibao(String fuqibao) {
            this.fuqibao = fuqibao;
        }

        public String getZhuyoubao() {
            return zhuyoubao;
        }

        public void setZhuyoubao(String zhuyoubao) {
            this.zhuyoubao = zhuyoubao;
        }

        public String getJieqibao() {
            return jieqibao;
        }

        public void setJieqibao(String jieqibao) {
            this.jieqibao = jieqibao;
        }

        public String getStatusX() {
            return statusX;
        }

        public void setStatusX(String statusX) {
            this.statusX = statusX;
        }

        public String getFrozen_end_time() {
            return frozen_end_time;
        }

        public void setFrozen_end_time(String frozen_end_time) {
            this.frozen_end_time = frozen_end_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getJieqi_num() {
            return jieqi_num;
        }

        public void setJieqi_num(String jieqi_num) {
            this.jieqi_num = jieqi_num;
        }
    }


}
