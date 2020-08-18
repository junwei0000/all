package com.longcheng.volunteer.modular.home.commune.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class CommuneAfterBean {

    //加入公社列表
    @SerializedName("isLeader")
    private int isLeader;//是否领导 0：否 1：是 是领导的时候不允许加入公社
    @SerializedName("groupList")
    private List<CommuneItemBean> groupList;

    //我的公社信息
    @SerializedName("groupDirector")
    private String groupDirector;
    @SerializedName("groupDeputyDirector")
    private String groupDeputyDirector;
    @SerializedName("groupChoUserNum")
    private String groupChoUserNum;
    @SerializedName("userInfo")
    private CommuneItemBean userInfo;
    @SerializedName("groupInfo")
    private CommuneItemBean groupInfo;
    @SerializedName("teamInfo")
    private CommuneItemBean teamInfo;
    @SerializedName("groupAbilityInfo")
    private CommuneItemBean groupAbilityInfo;
    @SerializedName("groupTeamUsers")
    private List<CommuneItemBean> groupTeamUsers;
    @SerializedName("noticeInfo")
    private CommuneItemBean noticeInfo;

    @SerializedName("isGroupLikes")
    private int isGroupLikes;

    // 能量排行
    @SerializedName("ranking")
    private List<CommuneItemBean> ranking;

    //大队列表
    @SerializedName("teamList")
    private List<CommuneItemBean> teamList;


    //社员列表
    @SerializedName("teamMemberList")
    private List<CommuneItemBean> teamMemberList;


    @SerializedName("teamNameList")
    private List<CommuneItemBean> teamNameList;
    @SerializedName("teamLeaderUser")
    private CommuneItemBean teamLeaderUser;


    @SerializedName("teamRotation")
    private int teamRotation;
    @SerializedName("rotation")
    private int rotation;

    public int getTeamRotation() {
        return teamRotation;
    }

    public void setTeamRotation(int teamRotation) {
        this.teamRotation = teamRotation;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public List<CommuneItemBean> getTeamNameList() {
        return teamNameList;
    }

    public void setTeamNameList(List<CommuneItemBean> teamNameList) {
        this.teamNameList = teamNameList;
    }

    public CommuneItemBean getTeamLeaderUser() {
        return teamLeaderUser;
    }

    public void setTeamLeaderUser(CommuneItemBean teamLeaderUser) {
        this.teamLeaderUser = teamLeaderUser;
    }

    public List<CommuneItemBean> getTeamMemberList() {
        return teamMemberList;
    }

    public void setTeamMemberList(List<CommuneItemBean> teamMemberList) {
        this.teamMemberList = teamMemberList;
    }

    public List<CommuneItemBean> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<CommuneItemBean> teamList) {
        this.teamList = teamList;
    }

    public int getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(int isLeader) {
        this.isLeader = isLeader;
    }

    public List<CommuneItemBean> getRanking() {/**/
        return ranking;
    }

    public void setRanking(List<CommuneItemBean> ranking) {
        this.ranking = ranking;
    }

    public String getGroupDirector() {
        return groupDirector;
    }

    public void setGroupDirector(String groupDirector) {
        this.groupDirector = groupDirector;
    }

    public String getGroupDeputyDirector() {
        return groupDeputyDirector;
    }

    public void setGroupDeputyDirector(String groupDeputyDirector) {
        this.groupDeputyDirector = groupDeputyDirector;
    }

    public String getGroupChoUserNum() {
        return groupChoUserNum;
    }

    public void setGroupChoUserNum(String groupChoUserNum) {
        this.groupChoUserNum = groupChoUserNum;
    }

    public CommuneItemBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(CommuneItemBean userInfo) {
        this.userInfo = userInfo;
    }

    public CommuneItemBean getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(CommuneItemBean groupInfo) {
        this.groupInfo = groupInfo;
    }

    public CommuneItemBean getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(CommuneItemBean teamInfo) {
        this.teamInfo = teamInfo;
    }

    public CommuneItemBean getGroupAbilityInfo() {
        return groupAbilityInfo;
    }

    public void setGroupAbilityInfo(CommuneItemBean groupAbilityInfo) {
        this.groupAbilityInfo = groupAbilityInfo;
    }

    public List<CommuneItemBean> getGroupTeamUsers() {
        return groupTeamUsers;
    }

    public void setGroupTeamUsers(List<CommuneItemBean> groupTeamUsers) {
        this.groupTeamUsers = groupTeamUsers;
    }

    public CommuneItemBean getNoticeInfo() {
        return noticeInfo;
    }

    public void setNoticeInfo(CommuneItemBean noticeInfo) {
        this.noticeInfo = noticeInfo;
    }

    public int getIsGroupLikes() {
        return isGroupLikes;
    }

    public void setIsGroupLikes(int isGroupLikes) {
        this.isGroupLikes = isGroupLikes;
    }

    public List<CommuneItemBean> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<CommuneItemBean> groupList) {
        this.groupList = groupList;
    }
}
