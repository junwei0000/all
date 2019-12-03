package com.longcheng.lifecareplan.modular.helpwith.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class HelpIndexItemBean implements Serializable {

    private String user_id;

    private String user_name;
    private String is_cho;
    private String birthday;
    private String area_simple;
    private String birthday_zh;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getIs_cho() {
        return is_cho;
    }

    public void setIs_cho(String is_cho) {
        this.is_cho = is_cho;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getArea_simple() {
        return area_simple;
    }

    public void setArea_simple(String area_simple) {
        this.area_simple = area_simple;
    }

    public String getBirthday_zh() {
        return birthday_zh;
    }

    public void setBirthday_zh(String birthday_zh) {
        this.birthday_zh = birthday_zh;
    }
}
