package com.longcheng.lifecareplan.modular.home.domainname.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class CommuDetailDataBean extends ResponseBean {
    @SerializedName("data")
    private DeItemInfo data;

    public DeItemInfo getData() {
        return data;
    }

    public void setData(DeItemInfo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "data =" + data + " , msg = " + msg + " , status = " + status;
    }

    public class DeItemInfo {


        DeItemInfo wechat;
        DeItemInfo shoukangyuan;
        DeItemInfo fuqibao;
        String balance;
        String price;


        String commune_id;
        String short_name;
        String commune_name;//所属公社
        String brigade_user_name;//大队长姓名
        String commune_user_name;//主任姓名
        int brigade_count;//大队数
        int cho_count;
        String lat;
        String lng;
        String address;
        int level;
        int is_sold;//是否可抢购；1：已持有， 2可抢购
        int is_own;//是否是自己的；1：是
        int end_time;

        public DeItemInfo getWechat() {
            return wechat;
        }

        public void setWechat(DeItemInfo wechat) {
            this.wechat = wechat;
        }

        public DeItemInfo getShoukangyuan() {
            return shoukangyuan;
        }

        public void setShoukangyuan(DeItemInfo shoukangyuan) {
            this.shoukangyuan = shoukangyuan;
        }

        public DeItemInfo getFuqibao() {
            return fuqibao;
        }

        public void setFuqibao(DeItemInfo fuqibao) {
            this.fuqibao = fuqibao;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBrigade_user_name() {
            return brigade_user_name;
        }

        public void setBrigade_user_name(String brigade_user_name) {
            this.brigade_user_name = brigade_user_name;
        }

        public String getCommune_user_name() {
            return commune_user_name;
        }

        public void setCommune_user_name(String commune_user_name) {
            this.commune_user_name = commune_user_name;
        }


        public int getIs_own() {
            return is_own;
        }

        public void setIs_own(int is_own) {
            this.is_own = is_own;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public int getCho_count() {
            return cho_count;
        }

        public void setCho_count(int cho_count) {
            this.cho_count = cho_count;
        }

        public String getCommune_id() {
            return commune_id;
        }

        public void setCommune_id(String commune_id) {
            this.commune_id = commune_id;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public String getCommune_name() {
            return commune_name;
        }

        public void setCommune_name(String commune_name) {
            this.commune_name = commune_name;
        }

        public int getBrigade_count() {
            return brigade_count;
        }

        public void setBrigade_count(int brigade_count) {
            this.brigade_count = brigade_count;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getIs_sold() {
            return is_sold;
        }

        public void setIs_sold(int is_sold) {
            this.is_sold = is_sold;
        }
    }
}
