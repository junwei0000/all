package com.longcheng.lifecareplan.modular.helpwith.bean;

import java.io.Serializable;

/**
 * Created by LC on 2018/3/11.
 */

public class HelpWithInfo implements Serializable {
    private String name;
    private String name2;
    private int textColorId;
    private int bgColorId;
    private int imgId;
    private String skipurl;
    private String applyurl;

    public HelpWithInfo(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public HelpWithInfo(String name, String name2, int textColorId, int bgColorId, String skipurl) {
        this.name = name;
        this.name2 = name2;
        this.textColorId = textColorId;
        this.bgColorId = bgColorId;
        this.skipurl = skipurl;
    }

    public HelpWithInfo(String name, String name2, int textColorId, int bgColorId, String skipurl, String applyurl) {
        this.name = name;
        this.name2 = name2;
        this.textColorId = textColorId;
        this.bgColorId = bgColorId;
        this.skipurl = skipurl;
        this.applyurl = applyurl;
    }

    public String getApplyurl() {
        return applyurl;
    }

    public void setApplyurl(String applyurl) {
        this.applyurl = applyurl;
    }

    public int getTextColorId() {
        return textColorId;
    }

    public void setTextColorId(int textColorId) {
        this.textColorId = textColorId;
    }

    public String getSkipurl() {
        return skipurl;
    }

    public void setSkipurl(String skipurl) {
        this.skipurl = skipurl;
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
