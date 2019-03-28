package com.longcheng.lifecareplan.modular.helpwith.energydetail.bean;

import com.google.gson.annotations.SerializedName;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpItemBean;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class DetailAfterBean {
    @SerializedName("msg_info")
    private DetailItemBean msg_info;
    @SerializedName("goods")
    private DetailItemBean goods;
    @SerializedName("is_applying_help")
    private int is_applying_help;//是否有互助正在进行
    @SerializedName("mutual_help_money")
    private int mutual_help_money;//默认最低互助金额
    @SerializedName("count")
    private String count;
    @SerializedName("rankings")
    private List<DetailItemBean> rankings;
    @SerializedName("group")
    private DetailItemBean group;
    @SerializedName("wx_share_url")
    private String wx_share_url;

    //----康农详情
    @SerializedName("title")
    private String knp_sharetitle;
    @SerializedName("link")
    private String knp_shareurl;
    @SerializedName("sharePic")
    private String knp_sharePic;
    @SerializedName("desc")
    private String knp_sharedesc;
    @SerializedName("mutual_help_money_default")
    private DetailItemBean knp_helpmoneydefault;
    @SerializedName("blessings_list")
    private List<DetailItemBean> knp_blessings_list;

    //当前节气
    @SerializedName("current_jieqi")
    private DetailItemBean current_jieqi;

    //金额
    @SerializedName(value = "mutual_help_money_all", alternate = {"life_money_all"})
    private List<DetailItemBean> mutual_help_money_all;
    @SerializedName("user_info")
    private DetailItemBean user_info;
    //评论列表
    @SerializedName("list")
    private List<DetailItemBean> list;
    @SerializedName("action")
    private DetailItemBean action;

    public DetailItemBean getAction() {
        return action;
    }

    public void setAction(DetailItemBean action) {
        this.action = action;
    }

    public String getWx_share_url() {
        return wx_share_url;
    }

    public List<DetailItemBean> getKnp_blessings_list() {
        return knp_blessings_list;
    }

    public void setKnp_blessings_list(List<DetailItemBean> knp_blessings_list) {
        this.knp_blessings_list = knp_blessings_list;
    }

    public DetailItemBean getKnp_helpmoneydefault() {
        return knp_helpmoneydefault;
    }

    public void setKnp_helpmoneydefault(DetailItemBean knp_helpmoneydefault) {
        this.knp_helpmoneydefault = knp_helpmoneydefault;
    }

    public String getKnp_sharedesc() {
        return knp_sharedesc;
    }

    public void setKnp_sharedesc(String knp_sharedesc) {
        this.knp_sharedesc = knp_sharedesc;
    }

    public String getKnp_sharetitle() {
        return knp_sharetitle;
    }

    public void setKnp_sharetitle(String knp_sharetitle) {
        this.knp_sharetitle = knp_sharetitle;
    }

    public String getKnp_shareurl() {
        return knp_shareurl;
    }

    public void setKnp_shareurl(String knp_shareurl) {
        this.knp_shareurl = knp_shareurl;
    }

    public String getKnp_sharePic() {
        return knp_sharePic;
    }

    public void setKnp_sharePic(String knp_sharePic) {
        this.knp_sharePic = knp_sharePic;
    }

    public void setWx_share_url(String wx_share_url) {
        this.wx_share_url = wx_share_url;
    }

    public DetailItemBean getUser_info() {
        return user_info;
    }

    public DetailItemBean getCurrent_jieqi() {
        return current_jieqi;
    }

    public void setCurrent_jieqi(DetailItemBean current_jieqi) {
        this.current_jieqi = current_jieqi;
    }

    public void setUser_info(DetailItemBean user_info) {
        this.user_info = user_info;
    }

    public List<DetailItemBean> getMutual_help_money_all() {
        return mutual_help_money_all;
    }

    public void setMutual_help_money_all(List<DetailItemBean> mutual_help_money_all) {
        this.mutual_help_money_all = mutual_help_money_all;
    }

    public List<DetailItemBean> getList() {
        return list;
    }

    public void setList(List<DetailItemBean> list) {
        this.list = list;
    }

    public DetailItemBean getMsg_info() {
        return msg_info;
    }

    public void setMsg_info(DetailItemBean msg_info) {
        this.msg_info = msg_info;
    }

    public DetailItemBean getGoods() {
        return goods;
    }

    public void setGoods(DetailItemBean goods) {
        this.goods = goods;
    }

    public int getIs_applying_help() {
        return is_applying_help;
    }

    public void setIs_applying_help(int is_applying_help) {
        this.is_applying_help = is_applying_help;
    }

    public int getMutual_help_money() {
        return mutual_help_money;
    }

    public void setMutual_help_money(int mutual_help_money) {
        this.mutual_help_money = mutual_help_money;
    }


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<DetailItemBean> getRankings() {
        return rankings;
    }

    public void setRankings(List<DetailItemBean> rankings) {
        this.rankings = rankings;
    }

    public DetailItemBean getGroup() {
        return group;
    }

    public void setGroup(DetailItemBean group) {
        this.group = group;
    }
}
