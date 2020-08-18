package com.longcheng.lifecareplan.modular.mine.emergencys;

import com.longcheng.lifecareplan.utils.ToastUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class CurrentHelpNeed implements Serializable {
    private String user_avatar;
    private String super_ability;
    private String user_name;
    private String user_branch_info;
    private String create_time;
    private int progress;
    private String ranking;
    private int help_need_id;
    private String cash_info;

    @Override
    public String toString() {
        return "CurrentHelpNeed{" +
                "user_avatar='" + user_avatar + '\'' +
                ", super_ability='" + super_ability + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_branch_info='" + user_branch_info + '\'' +
                ", create_time='" + create_time + '\'' +
                ", progress=" + progress +
                ", ranking='" + ranking + '\'' +
                ", help_need_id=" + help_need_id +
                ", cash_info='" + cash_info + '\'' +
                ", obtain_super_ability='" + obtain_super_ability + '\'' +
                ", user_identity_flag=" + user_identity_flag +
                ", status='" + status + '\'' +
                '}';
    }

    public String getCash_info() {
        return cash_info;
    }

    public void setCash_info(String cash_info) {
        this.cash_info = cash_info;
    }

    public int getHelp_need_id() {
        return help_need_id;
    }

    public void setHelp_need_id(int help_need_id) {
        this.help_need_id = help_need_id;
    }

    private String obtain_super_ability;
    private List<Img> user_identity_flag;
    private String status;


    public List<Img> getUser_identity_flag() {
        return user_identity_flag;
    }

    public void setUser_identity_flag(List<Img> user_identity_flag) {
        this.user_identity_flag = user_identity_flag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getSuper_ability() {
        return super_ability;
    }

    public void setSuper_ability(String super_ability) {
        this.super_ability = super_ability;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_branch_info() {
        return user_branch_info;
    }

    public void setUser_branch_info(String user_branch_info) {
        this.user_branch_info = user_branch_info;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getObtain_super_ability() {
        return obtain_super_ability;
    }

    public void setObtain_super_ability(String obtain_super_ability) {
        this.obtain_super_ability = obtain_super_ability;
    }


}
