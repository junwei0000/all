package com.longcheng.volunteer.modular.mine.changeinviter.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class InviteAfterBean implements Serializable {
    private int isChangeInvitation;//是否做过邀请人变更 0：否 1：是 ，只能变更联系人一次
    private InviteItemBean commendInvitationInfo;

    private InviteItemBean user;
    private int isCurrentLoginUser;
    private int isCurrentInvitationUser;
    private int isMyInvitationUser;

    public InviteItemBean getUser() {
        return user;
    }

    public void setUser(InviteItemBean user) {
        this.user = user;
    }

    public int getIsCurrentLoginUser() {
        return isCurrentLoginUser;
    }

    public void setIsCurrentLoginUser(int isCurrentLoginUser) {
        this.isCurrentLoginUser = isCurrentLoginUser;
    }

    public int getIsCurrentInvitationUser() {
        return isCurrentInvitationUser;
    }

    public void setIsCurrentInvitationUser(int isCurrentInvitationUser) {
        this.isCurrentInvitationUser = isCurrentInvitationUser;
    }

    public int getIsMyInvitationUser() {
        return isMyInvitationUser;
    }

    public void setIsMyInvitationUser(int isMyInvitationUser) {
        this.isMyInvitationUser = isMyInvitationUser;
    }

    public int getIsChangeInvitation() {
        return isChangeInvitation;
    }

    public void setIsChangeInvitation(int isChangeInvitation) {
        this.isChangeInvitation = isChangeInvitation;
    }

    public InviteItemBean getCommendInvitationInfo() {
        return commendInvitationInfo;
    }

    public void setCommendInvitationInfo(InviteItemBean commendInvitationInfo) {
        this.commendInvitationInfo = commendInvitationInfo;
    }
}
