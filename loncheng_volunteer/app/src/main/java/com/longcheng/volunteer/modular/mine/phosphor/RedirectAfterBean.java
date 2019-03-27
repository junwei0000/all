package com.longcheng.volunteer.modular.mine.phosphor;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2019/1/17 14:43
 * 意图：
 */

public class RedirectAfterBean implements Serializable {

    private String redirectMsgId;

    public String getRedirectMsgId() {
        return redirectMsgId;
    }

    public void setRedirectMsgId(String redirectMsgId) {
        this.redirectMsgId = redirectMsgId;
    }
}
