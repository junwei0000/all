package com.longcheng.volunteer.modular.mine.set.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class PushAfterBean implements Serializable {
    protected int push_need_received;

    public int getPush_need_received() {
        return push_need_received;
    }

    public void setPush_need_received(int push_need_received) {
        this.push_need_received = push_need_received;
    }
}
