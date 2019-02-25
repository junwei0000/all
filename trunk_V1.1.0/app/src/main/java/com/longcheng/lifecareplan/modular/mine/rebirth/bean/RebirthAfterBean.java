package com.longcheng.lifecareplan.modular.mine.rebirth.bean;

import com.longcheng.lifecareplan.modular.mine.changeinviter.bean.*;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class RebirthAfterBean implements Serializable {
    private String user_star_reset_card_id;

    private String user_id;
    private int status;//重生卡状态 0：未使用 1：已使用

    public String getUser_star_reset_card_id() {
        return user_star_reset_card_id;
    }

    public void setUser_star_reset_card_id(String user_star_reset_card_id) {
        this.user_star_reset_card_id = user_star_reset_card_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
