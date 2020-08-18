package com.longcheng.lifecareplan.modular.mine.message.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class MessageAfterBean implements Serializable {
    private int count;
    private List<MessageItemBean> pushs;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MessageItemBean> getPushs() {
        return pushs;
    }

    public void setPushs(List<MessageItemBean> pushs) {
        this.pushs = pushs;
    }
}
