package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class LifeStyleDetailAfterBean {
    @SerializedName("help_goods_id")
    private String help_goods_id;

    @SerializedName("user")
    private LifeStyleDetailItemBean user;
    @SerializedName("help_goods")
    private LifeStyleDetailItemBean help_goods;
    @SerializedName("goods")
    private LifeStyleDetailItemBean goods;
    @SerializedName("rankings")
    private List<LifeStyleDetailItemBean> rankings;
    @SerializedName("temps")
    private List<LifeStyleDetailItemBean> temps;

    //金额
    @SerializedName("skb_moneys")
    private List<LifeStyleDetailItemBean> skb_moneys;

    @SerializedName("group")
    private LifeStyleDetailItemBean group;

    @SerializedName("is_apply")
    private int is_apply;//是否有互助正在进行
    @SerializedName("apply_money")
    private int apply_money;//互祝寿康宝数量 (申请任务时需互祝)
    @SerializedName("count")
    private String count;
    @SerializedName("title")
    private String title;
    @SerializedName("desc")
    private String desc;
    @SerializedName("wx_share_url")
    private String wx_share_url;

    //评论列表
    @SerializedName("list")
    private List<LifeStyleDetailItemBean> list;


    public String getWx_share_url() {
        return wx_share_url;
    }

    public void setWx_share_url(String wx_share_url) {
        this.wx_share_url = wx_share_url;
    }

    public String getHelp_goods_id() {
        return help_goods_id;
    }

    public void setHelp_goods_id(String help_goods_id) {
        this.help_goods_id = help_goods_id;
    }

    public LifeStyleDetailItemBean getUser() {
        return user;
    }

    public void setUser(LifeStyleDetailItemBean user) {
        this.user = user;
    }

    public LifeStyleDetailItemBean getHelp_goods() {
        return help_goods;
    }

    public void setHelp_goods(LifeStyleDetailItemBean help_goods) {
        this.help_goods = help_goods;
    }

    public LifeStyleDetailItemBean getGoods() {
        return goods;
    }

    public void setGoods(LifeStyleDetailItemBean goods) {
        this.goods = goods;
    }

    public List<LifeStyleDetailItemBean> getRankings() {
        return rankings;
    }

    public void setRankings(List<LifeStyleDetailItemBean> rankings) {
        this.rankings = rankings;
    }

    public List<LifeStyleDetailItemBean> getTemps() {
        return temps;
    }

    public void setTemps(List<LifeStyleDetailItemBean> temps) {
        this.temps = temps;
    }

    public List<LifeStyleDetailItemBean> getSkb_moneys() {
        return skb_moneys;
    }

    public void setSkb_moneys(List<LifeStyleDetailItemBean> skb_moneys) {
        this.skb_moneys = skb_moneys;
    }

    public LifeStyleDetailItemBean getGroup() {
        return group;
    }

    public void setGroup(LifeStyleDetailItemBean group) {
        this.group = group;
    }

    public int getIs_apply() {
        return is_apply;
    }

    public void setIs_apply(int is_apply) {
        this.is_apply = is_apply;
    }

    public int getApply_money() {
        return apply_money;
    }

    public void setApply_money(int apply_money) {
        this.apply_money = apply_money;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<LifeStyleDetailItemBean> getList() {
        return list;
    }

    public void setList(List<LifeStyleDetailItemBean> list) {
        this.list = list;
    }
}
