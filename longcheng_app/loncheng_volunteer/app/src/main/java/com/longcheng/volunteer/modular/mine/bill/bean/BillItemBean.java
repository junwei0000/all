package com.longcheng.volunteer.modular.mine.bill.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Burning on 2018/8/30.
 */

public class BillItemBean {


    /**
     * 是否是每月汇总数据：1 是，0 不是
     */
    @SerializedName("is_month")
    private int is_month;

    /**
     * 金额来源名称 如：余额提现
     */
    @SerializedName("title")
    private String title;
    /**
     * 金额状态描述 如：审核中
     */
    @SerializedName("subtitle")
    private String subtitle;

    /**
     * 标记是收入还是支出
     */
    @SerializedName("is_income")
    private boolean is_income;
    /**
     * 时间
     */
    @SerializedName("time")
    private String time;
//    /**
//     * 支出
//     */
//    @SerializedName("expenditure")
//    private float expenditure;
//    /**
//     * 收入
//     */
//    @SerializedName("income")
//    private float income;


    /**
     * 金额（字符串）
     */
    @SerializedName("money")
    private String money;

    /**
     * 账单记录ID
     */
    @SerializedName("user_asset_record_id")
    private int user_asset_record_id;

    @SerializedName("source_type")
    private int source_type;
    @SerializedName("name")
    private String name;
    @SerializedName("source_str")
    private String source_str;

    public int getSource_type() {
        return source_type;
    }

    public void setSource_type(int source_type) {
        this.source_type = source_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource_str() {
        return source_str;
    }

    public void setSource_str(String source_str) {
        this.source_str = source_str;
    }

    public boolean isIs_income() {
        return is_income;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isIncome() {
        return is_income;
    }

    public void setIs_income(boolean is_income) {
        this.is_income = is_income;
    }

    public int getIs_month() {
        return is_month;
    }

    public void setIs_month(int is_month) {
        this.is_month = is_month;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUser_asset_record_id() {
        return user_asset_record_id;
    }

    public void setUser_asset_record_id(int user_asset_record_id) {
        this.user_asset_record_id = user_asset_record_id;
    }

    /**
     * 获取是否是每月汇总信息
     *
     * @return trur:是；false:否
     */
    public boolean isMonthInfo() {
        return is_month == 1;
    }
}
