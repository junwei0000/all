package com.longcheng.lifecareplan.modular.helpwith.energydetail.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpItemBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class DetailItemBean implements Serializable {
    //action
    @SerializedName("image")
    private String image;
    @SerializedName("asset")
    private String asset;
    //current_jieqi
    @SerializedName("blessings_list")
    private List<DetailItemBean> blessings_list;
    @SerializedName("blessings")
    private String blessings;
    //group   公社信息
    @SerializedName("group_id")
    private String group_id;
    @SerializedName("group_name")
    private String group_name;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("name")
    private String name;
    @SerializedName("count")
    private String count;

    //rankings
    @SerializedName("mutual_help_ability_ranking_id")
    private String mutual_help_ability_ranking_id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("msg_id")
    private String msg_id;
    @SerializedName("price")
    private String price;
    @SerializedName("create_time")
    private int create_time;

    //commentList
    @SerializedName("mutual_help_comment_id")
    private int mutual_help_comment_id;
    @SerializedName("mutual_help_id")
    private String mutual_help_id;
    @SerializedName("one_order_id")
    private String one_order_id;
    @SerializedName("floor")
    private String floor;
    @SerializedName("be_comment_user_id")
    private String be_comment_user_id;
    @SerializedName("be_comment_user_name")
    private String be_comment_user_name;
    @SerializedName("be_comment_id")
    private String be_comment_id;
    @SerializedName("status")
    private String status;
    @SerializedName("content")
    private String content;
    @SerializedName("support_number")
    private String support_number;
    @SerializedName("type")
    private String type;
    @SerializedName("comment_date")
    private String comment_date;
    @SerializedName("replay_comments")
    private List<DetailItemBean> replay_comments;//评论回复

    private int position;
    //goods
    @SerializedName("goods_id")
    private String goods_id;
    @SerializedName("original_img")
    private String original_img;
    @SerializedName("goods_name")
    private String goods_name;
    @SerializedName("cat_id")
    private String cat_id;
    @SerializedName("goods_h5")
    private String goods_h5;

    //msg_info
    @SerializedName("id")
    private String id;
    @SerializedName("h_user")
    private String h_user;
    @SerializedName("m_time")
    private int m_time;
    @SerializedName("cumulative_number")
    private String cumulative_number;//次数
    @SerializedName("ability_price_action")
    private String ability_price_action;//收到生命能量
    @SerializedName("ability_price")
    private int ability_price;//目标生命能量
    @SerializedName("progress")
    private int progress;
    @SerializedName("activity_type")
    private int activity_type;//活动类型: 1 代言活动,2 邀请新用户代言活动3, 公益活动
    @SerializedName("description")
    private String description;
    @SerializedName("goods_x_name")
    private String goods_x_name;


    //金额
    @SerializedName("mutual_help_money_id")
    private String mutual_help_money_id;
    @SerializedName("money")
    private int money;
    @SerializedName("ability")
    private String ability = "0";
    @SerializedName("weight")
    private String weight;
    @SerializedName("is_default")
    private int is_default;
    @SerializedName("valid")
    private String valid;

    public String getGoods_h5() {
        return goods_h5;
    }

    public void setGoods_h5(String goods_h5) {
        this.goods_h5 = goods_h5;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getMutual_help_money_id() {
        return mutual_help_money_id;
    }

    public void setMutual_help_money_id(String mutual_help_money_id) {
        this.mutual_help_money_id = mutual_help_money_id;
    }

    public List<DetailItemBean> getBlessings_list() {
        return blessings_list;
    }

    public void setBlessings_list(List<DetailItemBean> blessings_list) {
        this.blessings_list = blessings_list;
    }

    public String getBlessings() {
        return blessings;
    }

    public void setBlessings(String blessings) {
        this.blessings = blessings;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<DetailItemBean> getReplay_comments() {
        return replay_comments;
    }

    public void setReplay_comments(List<DetailItemBean> replay_comments) {
        this.replay_comments = replay_comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoods_x_name() {
        return goods_x_name;
    }

    public void setGoods_x_name(String goods_x_name) {
        this.goods_x_name = goods_x_name;
    }

    public int getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(int activity_type) {
        this.activity_type = activity_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMutual_help_ability_ranking_id() {
        return mutual_help_ability_ranking_id;
    }

    public void setMutual_help_ability_ranking_id(String mutual_help_ability_ranking_id) {
        this.mutual_help_ability_ranking_id = mutual_help_ability_ranking_id;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getOriginal_img() {
        return original_img;
    }

    public void setOriginal_img(String original_img) {
        this.original_img = original_img;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getH_user() {
        return h_user;
    }

    public void setH_user(String h_user) {
        this.h_user = h_user;
    }

    public int getM_time() {
        return m_time;
    }

    public void setM_time(int m_time) {
        this.m_time = m_time;
    }

    public String getCumulative_number() {
        return cumulative_number;
    }

    public void setCumulative_number(String cumulative_number) {
        this.cumulative_number = cumulative_number;
    }

    public String getAbility_price_action() {
        return ability_price_action;
    }

    public void setAbility_price_action(String ability_price_action) {
        this.ability_price_action = ability_price_action;
    }

    public int getAbility_price() {
        return ability_price;
    }

    public void setAbility_price(int ability_price) {
        this.ability_price = ability_price;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "ShopCartItemBean{" +
                "image='" + image + '\'' +
                ", group_id='" + group_id + '\'' +
                ", group_name='" + group_name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", count='" + count + '\'' +
                ", mutual_help_ability_ranking_id='" + mutual_help_ability_ranking_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", price='" + price + '\'' +
                ", create_time=" + create_time +
                ", mutual_help_comment_id='" + mutual_help_comment_id + '\'' +
                ", mutual_help_id='" + mutual_help_id + '\'' +
                ", one_order_id='" + one_order_id + '\'' +
                ", floor='" + floor + '\'' +
                ", be_comment_user_id='" + be_comment_user_id + '\'' +
                ", be_comment_user_name='" + be_comment_user_name + '\'' +
                ", be_comment_id='" + be_comment_id + '\'' +
                ", status='" + status + '\'' +
                ", content='" + content + '\'' +
                ", support_number='" + support_number + '\'' +
                ", type='" + type + '\'' +
                ", comment_date='" + comment_date + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", original_img='" + original_img + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", cat_id='" + cat_id + '\'' +
                ", id='" + id + '\'' +
                ", h_user='" + h_user + '\'' +
                ", m_time=" + m_time +
                ", cumulative_number='" + cumulative_number + '\'' +
                ", ability_price_action='" + ability_price_action + '\'' +
                ", ability_price='" + ability_price + '\'' +
                ", progress=" + progress +
                ", activity_type=" + activity_type +
                ", description='" + description + '\'' +
                ", goods_x_name='" + goods_x_name + '\'' +
                '}';
    }
}
