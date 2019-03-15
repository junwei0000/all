package com.longcheng.lifecareplan.bean;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 10:04
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class Bean {

    protected String url;
    protected String level;
    protected String version;

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

    @Override
    public String toString() {
        return "Bean{" +
                "url='" + url + '\'' +
                ", level='" + level + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
