package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class PionDaiFuItemBean {
    int position;
    int showGroupPay;//0 不显示；1 显示
    @SerializedName(value = "user_zhufubao_order_id", alternate = {"entrepreneurs_zhufubao_order_id"})
    private String user_zhufubao_order_id;
    private int user_status;
    private int bless_user_status;
    private int consult_status;
    private int user_consult_status;
    private int bless_consult_status;
    private int match_status;
    private String price;
    private String user_id;
    private String user_avatar;
    private String user_name;
    private String jieqi_name;
    private String phone;
    private String create_time;
    private ArrayList<String> img_all;
    //多人组队订单   支付信息
    private int is_group_order;//是否组队订单：1 是, 0 否；=1的时候祝福师和用户取消按钮去掉
    ArrayList<PionDaiFuItemBean> otherBlessInfo;
    private PionDaiFuItemBean blessInfo;
    private PionDaiFuItemBean pay_info;


    //支付信息
    PionDaiFuItemBean pay_lists;
    PionDaiFuItemBean weixin_pay;
    PionDaiFuItemBean alipay_pay;
    private String account;
    private String real_name;


    //银行卡信息
    private String zhufubao_bank_id;
    private String account_name;
    private String bank_name;
    private String branch_name;
    private String bank_no;


    //祝福宝奖励
    private String ranking;
    private String zhufubao_number_limit;
    private String balance_number_limit;
    private String unit_price;

    private String knp_team_bind_card_id;
    private String bank_url;


    //奖励记录
    private String ranking_date;
    private String create_date;
    private String user_zhufubao_number;
    private String total_price;


    public int getShowGroupPay() {
        return showGroupPay;
    }

    public void setShowGroupPay(int showGroupPay) {
        this.showGroupPay = showGroupPay;
    }

    public int getIs_group_order() {
        return is_group_order;
    }

    public void setIs_group_order(int is_group_order) {
        this.is_group_order = is_group_order;
    }

    public ArrayList<PionDaiFuItemBean> getOtherBlessInfo() {
        return otherBlessInfo;
    }

    public void setOtherBlessInfo(ArrayList<PionDaiFuItemBean> otherBlessInfo) {
        this.otherBlessInfo = otherBlessInfo;
    }

    public PionDaiFuItemBean getBlessInfo() {
        return blessInfo;
    }

    public void setBlessInfo(PionDaiFuItemBean blessInfo) {
        this.blessInfo = blessInfo;
    }

    public PionDaiFuItemBean getPay_info() {
        return pay_info;
    }

    public void setPay_info(PionDaiFuItemBean pay_info) {
        this.pay_info = pay_info;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getMatch_status() {
        return match_status;
    }

    public void setMatch_status(int match_status) {
        this.match_status = match_status;
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

    public String getRanking_date() {
        return ranking_date;
    }

    public void setRanking_date(String ranking_date) {
        this.ranking_date = ranking_date;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUser_zhufubao_number() {
        return user_zhufubao_number;
    }

    public void setUser_zhufubao_number(String user_zhufubao_number) {
        this.user_zhufubao_number = user_zhufubao_number;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getZhufubao_number_limit() {
        return zhufubao_number_limit;
    }

    public void setZhufubao_number_limit(String zhufubao_number_limit) {
        this.zhufubao_number_limit = zhufubao_number_limit;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getBalance_number_limit() {
        return balance_number_limit;
    }

    public void setBalance_number_limit(String balance_number_limit) {
        this.balance_number_limit = balance_number_limit;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getKnp_team_bind_card_id() {
        return knp_team_bind_card_id;
    }

    public void setKnp_team_bind_card_id(String knp_team_bind_card_id) {
        this.knp_team_bind_card_id = knp_team_bind_card_id;
    }

    public String getBank_url() {
        return bank_url;
    }

    public void setBank_url(String bank_url) {
        this.bank_url = bank_url;
    }

    public String getZhufubao_bank_id() {
        return zhufubao_bank_id;
    }

    public void setZhufubao_bank_id(String zhufubao_bank_id) {
        this.zhufubao_bank_id = zhufubao_bank_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    public int getBless_consult_status() {
        return bless_consult_status;
    }

    public void setBless_consult_status(int bless_consult_status) {
        this.bless_consult_status = bless_consult_status;
    }

    public int getUser_consult_status() {
        return user_consult_status;
    }

    public void setUser_consult_status(int user_consult_status) {
        this.user_consult_status = user_consult_status;
    }

    public int getConsult_status() {
        return consult_status;
    }

    public void setConsult_status(int consult_status) {
        this.consult_status = consult_status;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public ArrayList<String> getImg_all() {
        return img_all;
    }

    public void setImg_all(ArrayList<String> img_all) {
        this.img_all = img_all;
    }
}
