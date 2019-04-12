package com.longcheng.lifecareplanTv.home.dynamic.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class DynamicInfo implements Serializable {
    private String jieqiname;
    private String huaname;
    private String nextday;
    private int bgImgId;
    private int iconId;
    private String iconname;

    public DynamicInfo(){
        super();
    }
    public DynamicInfo(String jieqiname, String huaname, String nextday, int bgImgId, int iconId, String iconname) {
        this.jieqiname = jieqiname;
        this.huaname = huaname;
        this.nextday = nextday;
        this.bgImgId = bgImgId;
        this.iconId = iconId;
        this.iconname = iconname;
    }

    public String getJieqiname() {
        return jieqiname;
    }

    public void setJieqiname(String jieqiname) {
        this.jieqiname = jieqiname;
    }

    public String getHuaname() {
        return huaname;
    }

    public void setHuaname(String huaname) {
        this.huaname = huaname;
    }

    public String getNextday() {
        return nextday;
    }

    public void setNextday(String nextday) {
        this.nextday = nextday;
    }

    public int getBgImgId() {
        return bgImgId;
    }

    public void setBgImgId(int bgImgId) {
        this.bgImgId = bgImgId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getIconname() {
        return iconname;
    }

    public void setIconname(String iconname) {
        this.iconname = iconname;
    }
}
