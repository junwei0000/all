package com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/23 17:44
 * 意图：
 */

public class LifeNeedItemBean {
    @SerializedName(value = "skbGoodsInfo", alternate = {"shopGoods"})
    private LifeNeedItemBean skbGoodsInfo;
    @SerializedName(value = "name", alternate = {"goodsName"})
    private String name;
    private String price_name;
    @SerializedName("apply_help_max_limit")
    private int apply_help_max_limit;
    private int apply_type;//1 skb ;2 超能
    private String apply_help_price;//弹层显示价格
    private String skb_price;

    @SerializedName("chatuser")
    private LifeNeedItemBean chatuser;
    private String super_ability;
    private String shoukangyuan;


    //申请互祝 返回
    @SerializedName("needHelpGoodsNumber")
    private int needhelpGoodsnumber;//申请行动后需互助次数
    @SerializedName("need_help_number")
    private int need_help_number;//申请行动后需互助次数

    @SerializedName("appointHelpGoods")
    private LifeNeedItemBean appointHelpGoods;
    @SerializedName("help_goods_id")
    private String redirectMsgId;//申请成功后做任务跳转help_goods_id 0：跳转到列表页 非0：跳转到行动详情页
    @SerializedName("is_read")
    private int is_read = 1;//是否已读

    @SerializedName("applySuccess")
    private LifeNeedItemBean applySuccess;


    @SerializedName("helpGoodsTemp")
    List<LifeNeedItemBean> helpGoodsTemp;
    @SerializedName("help_goods_temp_id")
    private String help_goods_temp_id;
    @SerializedName("content")
    private String content;


    public String getPrice_name() {
        return price_name;
    }

    public void setPrice_name(String price_name) {
        this.price_name = price_name;
    }

    public String getSkb_price() {
        return skb_price;
    }

    public void setSkb_price(String skb_price) {
        this.skb_price = skb_price;
    }

    public LifeNeedItemBean getChatuser() {
        return chatuser;
    }

    public void setChatuser(LifeNeedItemBean chatuser) {
        this.chatuser = chatuser;
    }

    public String getSuper_ability() {
        return super_ability;
    }

    public void setSuper_ability(String super_ability) {
        this.super_ability = super_ability;
    }

    public String getShoukangyuan() {
        return shoukangyuan;
    }

    public void setShoukangyuan(String shoukangyuan) {
        this.shoukangyuan = shoukangyuan;
    }

    public int getApply_type() {
        return apply_type;
    }

    public void setApply_type(int apply_type) {
        this.apply_type = apply_type;
    }

    public String getApply_help_price() {
        return apply_help_price;
    }

    public void setApply_help_price(String apply_help_price) {
        this.apply_help_price = apply_help_price;
    }

    public LifeNeedItemBean getApplySuccess() {
        return applySuccess;
    }

    public void setApplySuccess(LifeNeedItemBean applySuccess) {
        this.applySuccess = applySuccess;
    }

    public int getApply_help_max_limit() {
        return apply_help_max_limit;
    }

    public void setApply_help_max_limit(int apply_help_max_limit) {
        this.apply_help_max_limit = apply_help_max_limit;
    }

    public LifeNeedItemBean getSkbGoodsInfo() {
        return skbGoodsInfo;
    }

    public void setSkbGoodsInfo(LifeNeedItemBean skbGoodsInfo) {
        this.skbGoodsInfo = skbGoodsInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getNeedhelpGoodsnumber() {
        return needhelpGoodsnumber;
    }

    public void setNeedhelpGoodsnumber(int needhelpGoodsnumber) {
        this.needhelpGoodsnumber = needhelpGoodsnumber;
    }

    public int getNeed_help_number() {
        return need_help_number;
    }

    public void setNeed_help_number(int need_help_number) {
        this.need_help_number = need_help_number;
    }

    public LifeNeedItemBean getAppointHelpGoods() {
        return appointHelpGoods;
    }

    public void setAppointHelpGoods(LifeNeedItemBean appointHelpGoods) {
        this.appointHelpGoods = appointHelpGoods;
    }

    public String getRedirectMsgId() {
        return redirectMsgId;
    }

    public void setRedirectMsgId(String redirectMsgId) {
        this.redirectMsgId = redirectMsgId;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public List<LifeNeedItemBean> getHelpGoodsTemp() {
        return helpGoodsTemp;
    }

    public void setHelpGoodsTemp(List<LifeNeedItemBean> helpGoodsTemp) {
        this.helpGoodsTemp = helpGoodsTemp;
    }

    public String getHelp_goods_temp_id() {
        return help_goods_temp_id;
    }

    public void setHelp_goods_temp_id(String help_goods_temp_id) {
        this.help_goods_temp_id = help_goods_temp_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}