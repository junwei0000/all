package com.longcheng.lifecareplan.modular.mine.emergencys;

import java.io.Serializable;

public class CSRecordList implements Serializable {
    public String user_avatar;
    public String user_name;
    public String user_branch_info;
    public String user_money;
    public String cardholder_name;
    public String bank_name;
    public String bank_no;
    public String money;
    public String super_ability;
    public String obtain_money;
    public String obtain_super_ability;
    public String diff_money;
    public String diff_super_ability;
    public String progress_time;
    public String bank_full_name;
    public String solar_start_time;
    public String create_date;
    public String solar_term_en;
    public String solar_term_cn;

    public String getBank_full_name() {
        return bank_full_name;
    }

    public void setBank_full_name(String bank_full_name) {
        this.bank_full_name = bank_full_name;
    }

    public String over_time;
    public String cash_time;
    public int status;
    public int progress;
    public int cumulative_number;
    public int help_need_id;
    public int rotation;
    public int update_time;
    public int create_time;
    public int rotation2;
    public int help_need_recommend_id;
    public int user_id;

    public String getOver_time() {
        return over_time;
    }

    public void setOver_time(String over_time) {
        this.over_time = over_time;
    }

    @Override
    public String toString() {
        return "CSRecordList{" +
                "user_avatar='" + user_avatar + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_branch_info='" + user_branch_info + '\'' +
                ", user_money='" + user_money + '\'' +
                ", cardholder_name='" + cardholder_name + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", bank_no='" + bank_no + '\'' +
                ", money='" + money + '\'' +
                ", super_ability='" + super_ability + '\'' +
                ", obtain_money='" + obtain_money + '\'' +
                ", obtain_super_ability='" + obtain_super_ability + '\'' +
                ", diff_money='" + diff_money + '\'' +
                ", diff_super_ability='" + diff_super_ability + '\'' +
                ", progress_time='" + progress_time + '\'' +
                ", solar_start_time='" + solar_start_time + '\'' +
                ", create_date='" + create_date + '\'' +
                ", solar_term_en='" + solar_term_en + '\'' +
                ", solar_term_cn='" + solar_term_cn + '\'' +
                ", cash_time=" + cash_time +
                ", status=" + status +
                ", progress=" + progress +
                ", cumulative_number=" + cumulative_number +
                ", help_need_id=" + help_need_id +
                ", rotation=" + rotation +
                ", update_time=" + update_time +
                ", create_time=" + create_time +
                ", rotation2=" + rotation2 +
                ", help_need_recommend_id=" + help_need_recommend_id +
                ", user_id=" + user_id +
                '}';
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

    public String getUser_branch_info() {
        return user_branch_info;
    }

    public void setUser_branch_info(String user_branch_info) {
        this.user_branch_info = user_branch_info;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getCardholder_name() {
        return cardholder_name;
    }

    public void setCardholder_name(String cardholder_name) {
        this.cardholder_name = cardholder_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSuper_ability() {
        return super_ability;
    }

    public void setSuper_ability(String super_ability) {
        this.super_ability = super_ability;
    }

    public String getObtain_money() {
        return obtain_money;
    }

    public void setObtain_money(String obtain_money) {
        this.obtain_money = obtain_money;
    }

    public String getObtain_super_ability() {
        return obtain_super_ability;
    }

    public void setObtain_super_ability(String obtain_super_ability) {
        this.obtain_super_ability = obtain_super_ability;
    }

    public String getDiff_money() {
        return diff_money;
    }

    public void setDiff_money(String diff_money) {
        this.diff_money = diff_money;
    }

    public String getDiff_super_ability() {
        return diff_super_ability;
    }

    public void setDiff_super_ability(String diff_super_ability) {
        this.diff_super_ability = diff_super_ability;
    }

    public String getProgress_time() {
        return progress_time;
    }

    public void setProgress_time(String progress_time) {
        this.progress_time = progress_time;
    }

    public String getSolar_start_time() {
        return solar_start_time;
    }

    public void setSolar_start_time(String solar_start_time) {
        this.solar_start_time = solar_start_time;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getSolar_term_en() {
        return solar_term_en;
    }

    public void setSolar_term_en(String solar_term_en) {
        this.solar_term_en = solar_term_en;
    }

    public String getSolar_term_cn() {
        return solar_term_cn;
    }

    public void setSolar_term_cn(String solar_term_cn) {
        this.solar_term_cn = solar_term_cn;
    }


    public String getCash_time() {
        return cash_time;
    }

    public void setCash_time(String cash_time) {
        this.cash_time = cash_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCumulative_number() {
        return cumulative_number;
    }

    public void setCumulative_number(int cumulative_number) {
        this.cumulative_number = cumulative_number;
    }

    public int getHelp_need_id() {
        return help_need_id;
    }

    public void setHelp_need_id(int help_need_id) {
        this.help_need_id = help_need_id;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getRotation2() {
        return rotation2;
    }

    public void setRotation2(int rotation2) {
        this.rotation2 = rotation2;
    }

    public int getHelp_need_recommend_id() {
        return help_need_recommend_id;
    }

    public void setHelp_need_recommend_id(int help_need_recommend_id) {
        this.help_need_recommend_id = help_need_recommend_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
