package com.longcheng.volunteer.modular.helpwith.myfamily.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class MyAfterBean implements Serializable {
    private ItemBean user;
    private List<ItemBean> familys;


    public ItemBean getUser() {
        return user;
    }

    public void setUser(ItemBean user) {
        this.user = user;
    }

    public List<ItemBean> getFamilys() {
        return familys;
    }

    public void setFamilys(List<ItemBean> familys) {
        this.familys = familys;
    }
}
