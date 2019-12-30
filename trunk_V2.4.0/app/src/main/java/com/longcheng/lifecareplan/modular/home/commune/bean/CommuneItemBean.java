package com.longcheng.lifecareplan.modular.home.commune.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class CommuneItemBean implements Serializable {

    //加入公社列表
    private String group_name;
    private String avatar;
    private String owner_id;
    private String likes_number;
    private String count;
    private String user_id;
    private String user_name;
    private String ability;
    //我的公社信息
    private int group_id;
    private int team_id;
    private int role;//1：主任 2：执行主任
    private int is_head;
    private String team_name;
    private String custom_name="";
    private String help_ability_group_ranking_id;

    private String group_notice_id;
    private String content;
    private String create_time;

    //能量排行
    private String sponsor_ability;

    private String phone;
    private int is_cho;
    private int star_level;


    private String value;
    private String text;
    private String solar_terms_en;


    public String getCustom_name() {
        return custom_name;
    }

    public void setCustom_name(String custom_name) {
        this.custom_name = custom_name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSolar_terms_en() {
        return solar_terms_en;
    }

    public void setSolar_terms_en(String solar_terms_en) {
        this.solar_terms_en = solar_terms_en;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIs_cho() {
        return is_cho;
    }

    public void setIs_cho(int is_cho) {
        this.is_cho = is_cho;
    }

    public int getStar_level() {
        return star_level;
    }

    public void setStar_level(int star_level) {
        this.star_level = star_level;
    }

    public String getSponsor_ability() {
        return sponsor_ability;
    }

    public void setSponsor_ability(String sponsor_ability) {
        this.sponsor_ability = sponsor_ability;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getIs_head() {
        return is_head;
    }

    public void setIs_head(int is_head) {
        this.is_head = is_head;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getHelp_ability_group_ranking_id() {
        return help_ability_group_ranking_id;
    }

    public void setHelp_ability_group_ranking_id(String help_ability_group_ranking_id) {
        this.help_ability_group_ranking_id = help_ability_group_ranking_id;
    }

    public String getGroup_notice_id() {
        return group_notice_id;
    }

    public void setGroup_notice_id(String group_notice_id) {
        this.group_notice_id = group_notice_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getLikes_number() {
        return likes_number;
    }

    public void setLikes_number(String likes_number) {
        this.likes_number = likes_number;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }
}
