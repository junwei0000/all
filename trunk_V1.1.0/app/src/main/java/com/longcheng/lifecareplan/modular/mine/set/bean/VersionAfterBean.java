package com.longcheng.lifecareplan.modular.mine.set.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class VersionAfterBean implements Serializable {
    protected String url;
    protected String level;
    protected String version;
    protected ArrayList<String> description;

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
