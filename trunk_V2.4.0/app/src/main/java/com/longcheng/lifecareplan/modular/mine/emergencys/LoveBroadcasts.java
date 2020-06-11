package com.longcheng.lifecareplan.modular.mine.emergencys;

import java.io.Serializable;

public class LoveBroadcasts implements Serializable {
    public String avatar;
    public String display_msg;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDisplay_msg() {
        return display_msg;
    }

    public void setDisplay_msg(String display_msg) {
        this.display_msg = display_msg;
    }

    @Override
    public String toString() {
        return "LoveBroadcasts{" +
                "avatar='" + avatar + '\'' +
                ", display_msg='" + display_msg + '\'' +
                '}';
    }
}
