package com.longcheng.lifecareplan.modular.helpwith.connon.bean;

import java.io.Serializable;

public class CreateBean implements Serializable {
    String title;
    String subtitle;
    String ablity;

    public CreateBean(String title, String subtitle, String ablity) {
        this.title = title;
        this.subtitle = subtitle;
        this.ablity = ablity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAblity() {
        return ablity;
    }

    public void setAblity(String ablity) {
        this.ablity = ablity;
    }
}
