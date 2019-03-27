package com.longcheng.volunteer.modular.mine.invitation.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Burning on 2018/9/5.
 */

public class InvitationBean {
    @SerializedName("choCount")
    protected int choCount;
    @SerializedName("invitationCount")
    protected int invitationCount;
    @SerializedName("list")
    List<InvitationDetail> list;

    public int getChoCount() {
        return choCount;
    }

    public void setChoCount(int choCount) {
        this.choCount = choCount;
    }

    public int getInvitationCount() {
        return invitationCount;
    }

    public void setInvitationCount(int invitationCount) {
        this.invitationCount = invitationCount;
    }

    public List<InvitationDetail> getList() {
        return list;
    }

    public void setList(List<InvitationDetail> list) {
        this.list = list;
    }
}
