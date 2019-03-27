package com.longcheng.volunteer.modular.mine.fragment.genius;

/**
 * 作者：jun on
 * 时间：2019/3/12 16:27
 * 意图：
 */

public class FunctionGVItemBean {
    private String name;
    private int viewId;
    private int imgId;

    public FunctionGVItemBean(String name, int viewId, int imgId) {
        this.name = name;
        this.viewId = viewId;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
