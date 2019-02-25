package com.longcheng.lifecareplan.modular.helpwith.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class HelpWithInfo implements Serializable {
    private String name;
    private String name2;
    private int bgColorId;
    private int imgId;
    public HelpWithInfo(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public HelpWithInfo(String name, String name2, int bgColorId) {
        this.name = name;
        this.name2 = name2;
        this.bgColorId = bgColorId;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getBgColorId() {
        return bgColorId;
    }

    public void setBgColorId(int bgColorId) {
        this.bgColorId = bgColorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
