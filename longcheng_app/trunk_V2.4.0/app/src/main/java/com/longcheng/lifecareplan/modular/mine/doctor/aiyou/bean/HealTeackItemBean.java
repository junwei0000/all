package com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean;

/**
 * 作者：jun on
 * 时间：2020/6/9 11:15
 * 意图：
 */
public class HealTeackItemBean {

    String day;
    String proposal;
    String knp_msg_follow_item_id;

    String id;
    int status;// 审核状态:0 审核中  1已审核 2已驳回
    //followItemInfos
    /**
     * 1.喝太赫兹水
     * 2.节气水果汁
     * 3.24节气餐
     * 4.吃细胞平衡营养素-早
     * 5.吃细胞平衡营养素-中
     * 6.吃细胞平衡营养素-晚
     */
    int type;
    String pic_url;
    int confirm_status;//1 是；0 否
    String knp_msg_follow_item_info_id;
    boolean notsendUrlStatus;//本地是否已发送  默认有图片地址已上传 false
    int index;

    String sign_start_day;
    String sign_end_day;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKnp_msg_follow_item_id() {
        return knp_msg_follow_item_id;
    }

    public void setKnp_msg_follow_item_id(String knp_msg_follow_item_id) {
        this.knp_msg_follow_item_id = knp_msg_follow_item_id;
    }

    public HealTeackItemBean(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isNotsendUrlStatus() {
        return notsendUrlStatus;
    }

    public void setNotsendUrlStatus(boolean notsendUrlStatus) {
        this.notsendUrlStatus = notsendUrlStatus;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getConfirm_status() {
        return confirm_status;
    }

    public void setConfirm_status(int confirm_status) {
        this.confirm_status = confirm_status;
    }

    public String getKnp_msg_follow_item_info_id() {
        return knp_msg_follow_item_info_id;
    }

    public void setKnp_msg_follow_item_info_id(String knp_msg_follow_item_info_id) {
        this.knp_msg_follow_item_info_id = knp_msg_follow_item_info_id;
    }

    public String getSign_start_day() {
        return sign_start_day;
    }

    public void setSign_start_day(String sign_start_day) {
        this.sign_start_day = sign_start_day;
    }

    public String getSign_end_day() {
        return sign_end_day;
    }

    public void setSign_end_day(String sign_end_day) {
        this.sign_end_day = sign_end_day;
    }
}