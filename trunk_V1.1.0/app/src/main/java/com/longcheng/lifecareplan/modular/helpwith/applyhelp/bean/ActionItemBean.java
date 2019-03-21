package com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：jun on
 * 时间：2018/8/23 17:44
 * 意图：
 */

public class ActionItemBean {
    @SerializedName("action_safety_id")
    private String action_safety_id;

    //行动列表 返回
    @SerializedName("ability_price")
    private String ability_price;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("num_limit")
    private String num_limit;
    @SerializedName("img")
    private String img;
    @SerializedName("name1")
    private String name1;
    @SerializedName("name2")
    private String name2;
    @SerializedName("activity_id")
    private String activity_id;//参与活动ID 等于1为代言行动

    @SerializedName("type")
    private int type;//2 虚拟商品
    @SerializedName("status")
    private String xiajia_status;//0下架


    @SerializedName("extend_type")
    private String extend_type;// 0：通用（无特殊扩展） 1：平安行动-资料填写 2：能量配-属性选择


    //申请互祝 返回
    @SerializedName("need_help_number")
    private String need_help_number;//申请行动后需互助次数
    @SerializedName("redirectMsgId")
    private String redirectMsgId;//申请成功后做任务跳转msgid 0：跳转到列表页 非0：跳转到行动详情页


    //申请行动完成做任务  返回
    @SerializedName("msg_id")
    private String msg_id;
    @SerializedName("apply_status")
    private String apply_status;//状态 0：没有要做的任务 ;1， 有要去互祝的任务 ;2，有未读的任务
    @SerializedName("remain_number")
    private String remain_number;//剩余互祝次数
    @SerializedName("mutual_help_apply_id")
    private String mutual_help_apply_id;


    //行动详情
    private String action_id;
    private String content;
    private String goods_id;


    public String getXiajia_status() {
        return xiajia_status;
    }

    public void setXiajia_status(String xiajia_status) {
        this.xiajia_status = xiajia_status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAction_id() {
        return action_id;
    }

    public void setAction_id(String action_id) {
        this.action_id = action_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getAction_safety_id() {
        return action_safety_id;
    }

    public void setAction_safety_id(String action_safety_id) {
        this.action_safety_id = action_safety_id;
    }

    public String getMutual_help_apply_id() {
        return mutual_help_apply_id;
    }

    public void setMutual_help_apply_id(String mutual_help_apply_id) {
        this.mutual_help_apply_id = mutual_help_apply_id;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getApply_status() {
        return apply_status;
    }

    public void setApply_status(String apply_status) {
        this.apply_status = apply_status;
    }

    public String getRemain_number() {
        return remain_number;
    }

    public void setRemain_number(String remain_number) {
        this.remain_number = remain_number;
    }

    public String getNeed_help_number() {
        return need_help_number;
    }

    public void setNeed_help_number(String need_help_number) {
        this.need_help_number = need_help_number;
    }

    public String getRedirectMsgId() {
        return redirectMsgId;
    }

    public void setRedirectMsgId(String redirectMsgId) {
        this.redirectMsgId = redirectMsgId;
    }

    public String getExtend_type() {
        return extend_type;
    }

    public void setExtend_type(String extend_type) {
        this.extend_type = extend_type;
    }

    public String getAbility_price() {
        return ability_price;
    }

    public void setAbility_price(String ability_price) {
        this.ability_price = ability_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNum_limit() {
        return num_limit;
    }

    public void setNum_limit(String num_limit) {
        this.num_limit = num_limit;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }
}