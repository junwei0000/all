package com.longcheng.volunteer.modular.helpwith.lifestyledetail.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class LifeStyleDetailItemBean implements Serializable {
    //user
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("shoukangyuan")
    private String shoukangyuan;

    @SerializedName("status")
    private int status = 1;
    //help_goods
    @SerializedName("receive_group_name")
    private String receive_group_name;
    @SerializedName("receive_user_name")
    private String receive_user_name;
    @SerializedName("goods_id")
    private int goods_id;
    @SerializedName("goods_name")
    private String goods_name;
    @SerializedName("goods_img")
    private String goods_img;
    @SerializedName("apply_goods_number")
    private String apply_goods_number;
    @SerializedName("skb_total_price")
    private String skb_total_price;
    @SerializedName("cumulative_number")
    private String cumulative_number;
    @SerializedName("skb_cumulative_price")
    private String skb_cumulative_price;
    @SerializedName("progress")
    private int progress;
    @SerializedName("date")
    private String date;
    @SerializedName("skb_unit_price")
    private String skb_unit_price;
    @SerializedName("remark")
    private String remark;

    @SerializedName("blessings")
    private String blessings;

    //group
    @SerializedName("group_id")
    private String group_id;
    @SerializedName("group_name")
    private String group_name;
    @SerializedName("count")
    private String count;
    @SerializedName("name")
    private String name;

    //金额
    @SerializedName("help_goods_skb_money_id")
    private String help_goods_skb_money_id;
    @SerializedName("skb_price")
    private int skb_price;
    @SerializedName("is_default")
    private int is_default;


    private int position;
    //commentList
    @SerializedName("mutual_help_comment_id")
    private int mutual_help_comment_id;
    @SerializedName("mutual_help_id")
    private String mutual_help_id;
    @SerializedName("one_order_id")
    private String one_order_id;

    @SerializedName("price")
    private String price;
    @SerializedName("floor")
    private String floor;

    @SerializedName("be_comment_user_id")
    private String be_comment_user_id;
    @SerializedName("be_comment_user_name")
    private String be_comment_user_name;
    @SerializedName("be_comment_id")
    private String be_comment_id;
    @SerializedName("content")
    private String content;
    @SerializedName("support_number")
    private String support_number;
    @SerializedName("type")
    private String type;
    @SerializedName("comment_date")
    private String comment_date;
    @SerializedName("replay_comments")
    private List<LifeStyleDetailItemBean> replay_comments;//评论回复


    public String getApply_goods_number() {
        return apply_goods_number;
    }

    public void setApply_goods_number(String apply_goods_number) {
        this.apply_goods_number = apply_goods_number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getShoukangyuan() {
        return shoukangyuan;
    }

    public void setShoukangyuan(String shoukangyuan) {
        this.shoukangyuan = shoukangyuan;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHelp_goods_skb_money_id() {
        return help_goods_skb_money_id;
    }

    public void setHelp_goods_skb_money_id(String help_goods_skb_money_id) {
        this.help_goods_skb_money_id = help_goods_skb_money_id;
    }

    public int getSkb_price() {
        return skb_price;
    }

    public void setSkb_price(int skb_price) {
        this.skb_price = skb_price;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public int getMutual_help_comment_id() {
        return mutual_help_comment_id;
    }

    public void setMutual_help_comment_id(int mutual_help_comment_id) {
        this.mutual_help_comment_id = mutual_help_comment_id;
    }

    public String getMutual_help_id() {
        return mutual_help_id;
    }

    public void setMutual_help_id(String mutual_help_id) {
        this.mutual_help_id = mutual_help_id;
    }

    public String getOne_order_id() {
        return one_order_id;
    }

    public void setOne_order_id(String one_order_id) {
        this.one_order_id = one_order_id;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBe_comment_user_id() {
        return be_comment_user_id;
    }

    public void setBe_comment_user_id(String be_comment_user_id) {
        this.be_comment_user_id = be_comment_user_id;
    }

    public String getBe_comment_user_name() {
        return be_comment_user_name;
    }

    public void setBe_comment_user_name(String be_comment_user_name) {
        this.be_comment_user_name = be_comment_user_name;
    }

    public String getBe_comment_id() {
        return be_comment_id;
    }

    public void setBe_comment_id(String be_comment_id) {
        this.be_comment_id = be_comment_id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSupport_number() {
        return support_number;
    }

    public void setSupport_number(String support_number) {
        this.support_number = support_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public List<LifeStyleDetailItemBean> getReplay_comments() {
        return replay_comments;
    }

    public void setReplay_comments(List<LifeStyleDetailItemBean> replay_comments) {
        this.replay_comments = replay_comments;
    }

    public String getReceive_group_name() {
        return receive_group_name;
    }

    public void setReceive_group_name(String receive_group_name) {
        this.receive_group_name = receive_group_name;
    }

    public String getReceive_user_name() {
        return receive_user_name;
    }

    public void setReceive_user_name(String receive_user_name) {
        this.receive_user_name = receive_user_name;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getSkb_total_price() {
        return skb_total_price;
    }

    public void setSkb_total_price(String skb_total_price) {
        this.skb_total_price = skb_total_price;
    }

    public String getCumulative_number() {
        return cumulative_number;
    }

    public void setCumulative_number(String cumulative_number) {
        this.cumulative_number = cumulative_number;
    }

    public String getSkb_cumulative_price() {
        return skb_cumulative_price;
    }

    public void setSkb_cumulative_price(String skb_cumulative_price) {
        this.skb_cumulative_price = skb_cumulative_price;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSkb_unit_price() {
        return skb_unit_price;
    }

    public void setSkb_unit_price(String skb_unit_price) {
        this.skb_unit_price = skb_unit_price;
    }

    public String getBlessings() {
        return blessings;
    }

    public void setBlessings(String blessings) {
        this.blessings = blessings;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}
