package com.longcheng.volunteer.modular.helpwith.medalrank.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class ItemBean implements Serializable {

    private String help_ability_person_ranking_id;//
    private String user_id;
    private String mobile;
    private String user_name;
    private String group_id;
    private String group_name;
    private int ability;
    private String avatar;
    private int count;
    private int create_time;
    private int update_time;
    private String id;
    private String sort_rank;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort_rank() {
        return sort_rank;
    }

    public void setSort_rank(String sort_rank) {
        this.sort_rank = sort_rank;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getHelp_ability_person_ranking_id() {
        return help_ability_person_ranking_id;
    }

    public void setHelp_ability_person_ranking_id(String help_ability_person_ranking_id) {
        this.help_ability_person_ranking_id = help_ability_person_ranking_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getAbility() {
        return ability;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
