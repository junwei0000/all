package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuItemBean;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PionDaiCItemBean {
    int position;
    @SerializedName(value = "user_zhufubao_order_id", alternate = {"entrepreneurs_zhufubao_order_id"})
    private String user_zhufubao_order_id;
    private int user_status;
    private int bless_user_status;
    private int consult_status;
    private int user_consult_status;
    private int bless_consult_status;
    private String price;
    private String avatar;
    private String user_name;
    private String jieqi_name;
    private String phone;
    @SerializedName(value = "create_time", alternate = {"create_time_date"})
    private String create_time_date;
    private ArrayList<String> img_all;
    private int agree_expire_time;//剩余时间
    private int is_bless_agree_recharge;
    private int is_group_order;
    private String team_leader_user_name;
    private String team_leader_avatar;
    private String team_leader_phone;

    //
    PionDaiFuItemBean pay_lists;
    PionDaiFuItemBean weixin_pay;
    PionDaiFuItemBean alipay_pay;
    private String account;
    private String real_name;

    public String getTeam_leader_user_name() {
        return team_leader_user_name;
    }

    public void setTeam_leader_user_name(String team_leader_user_name) {
        this.team_leader_user_name = team_leader_user_name;
    }

    public String getTeam_leader_avatar() {
        return team_leader_avatar;
    }

    public void setTeam_leader_avatar(String team_leader_avatar) {
        this.team_leader_avatar = team_leader_avatar;
    }

    public String getTeam_leader_phone() {
        return team_leader_phone;
    }

    public void setTeam_leader_phone(String team_leader_phone) {
        this.team_leader_phone = team_leader_phone;
    }

    public int getIs_group_order() {
        return is_group_order;
    }

    public void setIs_group_order(int is_group_order) {
        this.is_group_order = is_group_order;
    }

    public PionDaiFuItemBean getPay_lists() {
        return pay_lists;
    }

    public void setPay_lists(PionDaiFuItemBean pay_lists) {
        this.pay_lists = pay_lists;
    }

    public PionDaiFuItemBean getWeixin_pay() {
        return weixin_pay;
    }

    public void setWeixin_pay(PionDaiFuItemBean weixin_pay) {
        this.weixin_pay = weixin_pay;
    }

    public PionDaiFuItemBean getAlipay_pay() {
        return alipay_pay;
    }

    public void setAlipay_pay(PionDaiFuItemBean alipay_pay) {
        this.alipay_pay = alipay_pay;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public int getIs_bless_agree_recharge() {
        return is_bless_agree_recharge;
    }

    public void setIs_bless_agree_recharge(int is_bless_agree_recharge) {
        this.is_bless_agree_recharge = is_bless_agree_recharge;
    }

    public int getAgree_expire_time() {
        return agree_expire_time;
    }

    public void setAgree_expire_time(int agree_expire_time) {
        this.agree_expire_time = agree_expire_time;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUser_zhufubao_order_id() {
        return user_zhufubao_order_id;
    }

    public void setUser_zhufubao_order_id(String user_zhufubao_order_id) {
        this.user_zhufubao_order_id = user_zhufubao_order_id;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public int getBless_user_status() {
        return bless_user_status;
    }

    public void setBless_user_status(int bless_user_status) {
        this.bless_user_status = bless_user_status;
    }

    public int getConsult_status() {
        return consult_status;
    }

    public void setConsult_status(int consult_status) {
        this.consult_status = consult_status;
    }

    public int getUser_consult_status() {
        return user_consult_status;
    }

    public void setUser_consult_status(int user_consult_status) {
        this.user_consult_status = user_consult_status;
    }

    public int getBless_consult_status() {
        return bless_consult_status;
    }

    public void setBless_consult_status(int bless_consult_status) {
        this.bless_consult_status = bless_consult_status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getJieqi_name() {
        return jieqi_name;
    }

    public void setJieqi_name(String jieqi_name) {
        this.jieqi_name = jieqi_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreate_time_date() {
        return create_time_date;
    }

    public void setCreate_time_date(String create_time_date) {
        this.create_time_date = create_time_date;
    }

    public ArrayList<String> getImg_all() {
        return img_all;
    }

    public void setImg_all(ArrayList<String> img_all) {
        this.img_all = img_all;
    }
}
