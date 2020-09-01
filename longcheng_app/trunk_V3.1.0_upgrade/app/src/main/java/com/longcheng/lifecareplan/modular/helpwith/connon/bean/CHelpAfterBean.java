package com.longcheng.lifecareplan.modular.helpwith.connon.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class CHelpAfterBean {
    //列表信息
    private int ing_num;//进行中
    private int com_num;//已完成
    private int isUserCertificationCommon;//是否需要实名  1需要
    private List<CHelpItemBean> jieqis;
    //创建互祝组信息
    private List<CHelpItemBean> patients;
    private List<CHelpItemBean> knp_team_numbers;
    private List<CHelpItemBean> knp_team_moneys;
    private CHelpItemBean chatuser;


    //详情
    private List<CHelpDetailItemBean> knpTeamRoomItems;
    private List<CHelpDetailItemBean> knpTeamNumber;
    private CHelpDetailItemBean current_jieqi;
    private CHelpDetailItemBean knpTeamRoom;


    //成功详情
    private List<CHelpDetailItemBean> knpTeamItems;
    private String patientName;
    private String totalAbility;
    private String jump_url;

    public int getIsUserCertificationCommon() {
        return isUserCertificationCommon;
    }

    public void setIsUserCertificationCommon(int isUserCertificationCommon) {
        this.isUserCertificationCommon = isUserCertificationCommon;
    }

    public List<CHelpDetailItemBean> getKnpTeamItems() {
        return knpTeamItems;
    }

    public void setKnpTeamItems(List<CHelpDetailItemBean> knpTeamItems) {
        this.knpTeamItems = knpTeamItems;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTotalAbility() {
        return totalAbility;
    }

    public void setTotalAbility(String totalAbility) {
        this.totalAbility = totalAbility;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }

    public List<CHelpDetailItemBean> getKnpTeamRoomItems() {
        return knpTeamRoomItems;
    }

    public void setKnpTeamRoomItems(List<CHelpDetailItemBean> knpTeamRoomItems) {
        this.knpTeamRoomItems = knpTeamRoomItems;
    }

    public List<CHelpDetailItemBean> getKnpTeamNumber() {
        return knpTeamNumber;
    }

    public void setKnpTeamNumber(List<CHelpDetailItemBean> knpTeamNumber) {
        this.knpTeamNumber = knpTeamNumber;
    }

    public CHelpDetailItemBean getCurrent_jieqi() {
        return current_jieqi;
    }

    public void setCurrent_jieqi(CHelpDetailItemBean current_jieqi) {
        this.current_jieqi = current_jieqi;
    }

    public CHelpDetailItemBean getKnpTeamRoom() {
        return knpTeamRoom;
    }

    public void setKnpTeamRoom(CHelpDetailItemBean knpTeamRoom) {
        this.knpTeamRoom = knpTeamRoom;
    }

    public int getIng_num() {
        return ing_num;
    }

    public void setIng_num(int ing_num) {
        this.ing_num = ing_num;
    }

    public int getCom_num() {
        return com_num;
    }

    public void setCom_num(int com_num) {
        this.com_num = com_num;
    }

    public List<CHelpItemBean> getJieqis() {
        return jieqis;
    }

    public void setJieqis(List<CHelpItemBean> jieqis) {
        this.jieqis = jieqis;
    }

    public List<CHelpItemBean> getPatients() {
        return patients;
    }

    public void setPatients(List<CHelpItemBean> patients) {
        this.patients = patients;
    }

    public List<CHelpItemBean> getKnp_team_numbers() {
        return knp_team_numbers;
    }

    public void setKnp_team_numbers(List<CHelpItemBean> knp_team_numbers) {
        this.knp_team_numbers = knp_team_numbers;
    }

    public List<CHelpItemBean> getKnp_team_moneys() {
        return knp_team_moneys;
    }

    public void setKnp_team_moneys(List<CHelpItemBean> knp_team_moneys) {
        this.knp_team_moneys = knp_team_moneys;
    }

    public CHelpItemBean getChatuser() {
        return chatuser;
    }

    public void setChatuser(CHelpItemBean chatuser) {
        this.chatuser = chatuser;
    }
}
