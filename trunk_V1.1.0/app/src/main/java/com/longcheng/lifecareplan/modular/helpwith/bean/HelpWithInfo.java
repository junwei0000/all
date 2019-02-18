package com.longcheng.lifecareplan.modular.helpwith.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class HelpWithInfo implements Serializable {
    private String name;
    private int imgId;

    public HelpWithInfo(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
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
