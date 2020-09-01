package com.longcheng.lifecareplan.bean.fupackage;

import java.util.List;

public class OneDayOneHoldBean {

    String title;
    String strtime;
    String content;
    int type;
    List<ImageBean> imageBeans;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStrtime() {
        return strtime;
    }

    public void setStrtime(String strtime) {
        this.strtime = strtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ImageBean> getImageBeans() {
        return imageBeans;
    }

    public void setImageBeans(List<ImageBean> imageBeans) {
        this.imageBeans = imageBeans;
    }

    public static class ImageBean {
        int imageurl;

        public int getImageurl() {
            return imageurl;
        }

        public void setImageurl(int imageurl) {
            this.imageurl = imageurl;
        }
    }


}
