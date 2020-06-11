package com.longcheng.lifecareplan.modular.mine.phosphor;

import com.longcheng.lifecareplan.bean.ResponseBean;

/**
 * 作者：jun on
 * 时间：2019/1/17 14:43
 * 意图：
 */

public class RedirectDataBean extends ResponseBean {

    private RedirectAfterBean data;

    public RedirectAfterBean getData() {
        return data;
    }

    public void setData(RedirectAfterBean data) {
        this.data = data;
    }
}
