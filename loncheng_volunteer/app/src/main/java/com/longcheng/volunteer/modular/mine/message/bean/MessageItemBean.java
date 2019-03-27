package com.longcheng.volunteer.modular.mine.message.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LC on 2018/3/11.
 */

public class MessageItemBean implements Serializable {

    private String app_push_id;//
    private String user_id;
    private String title;
    private String content;
    private String help_action_id;

    private String type;
    private int help_type;
    private String info_url;
    private String date;

    private List<MessageItemBean> info;

    private String key;
    private String value;

    public String getInfo_url() {
        return info_url;
    }

    public void setInfo_url(String info_url) {
        this.info_url = info_url;
    }

    public String getApp_push_id() {
        return app_push_id;
    }

    public void setApp_push_id(String app_push_id) {
        this.app_push_id = app_push_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHelp_action_id() {
        return help_action_id;
    }

    public void setHelp_action_id(String help_action_id) {
        this.help_action_id = help_action_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHelp_type() {
        return help_type;
    }

    public void setHelp_type(int help_type) {
        this.help_type = help_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<MessageItemBean> getInfo() {
        return info;
    }

    public void setInfo(List<MessageItemBean> info) {
        this.info = info;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
