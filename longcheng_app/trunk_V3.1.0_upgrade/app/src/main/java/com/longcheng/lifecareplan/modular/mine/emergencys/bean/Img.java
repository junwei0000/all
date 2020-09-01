package com.longcheng.lifecareplan.modular.mine.emergencys.bean;

import java.io.Serializable;

public class Img implements Serializable {
    public String name;
    public String type;
    public String image_html;
    public String image;

    @Override
    public String toString() {
        return "Img{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", image_html='" + image_html + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage_html() {
        return image_html;
    }

    public void setImage_html(String image_html) {
        this.image_html = image_html;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}