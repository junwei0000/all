package com.longcheng.lifecareplan.modular.exchange.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LC on 2018/3/11.
 */

public class JieQiItemBean implements Serializable {
    private int solar_terms_id;
    private String solar_terms_name;
    private String solar_terms_en;
    private String logo;
    @SerializedName("en")
    private String current_solar_en;
    @SerializedName("cn")
    private String current_solar_cn;

    //----
    private int cid;
    private String name;
    boolean check;
    @SerializedName("child")
    private List<JieQiItemBean> child;


    public JieQiItemBean(String name, int cid) {
        this.name = name;
        this.cid = cid;
    }

    public JieQiItemBean(int solar_terms_id, String solar_terms_name) {
        this.solar_terms_id = solar_terms_id;
        this.solar_terms_name = solar_terms_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCurrent_solar_cn() {
        return current_solar_cn;
    }

    public void setCurrent_solar_cn(String current_solar_cn) {
        this.current_solar_cn = current_solar_cn;
    }

    public int getSolar_terms_id() {
        return solar_terms_id;
    }

    public void setSolar_terms_id(int solar_terms_id) {
        this.solar_terms_id = solar_terms_id;
    }

    public String getSolar_terms_name() {
        return solar_terms_name;
    }

    public void setSolar_terms_name(String solar_terms_name) {
        this.solar_terms_name = solar_terms_name;
    }

    public String getSolar_terms_en() {
        return solar_terms_en;
    }

    public void setSolar_terms_en(String solar_terms_en) {
        this.solar_terms_en = solar_terms_en;
    }

    public String getCurrent_solar_en() {
        return current_solar_en;
    }

    public void setCurrent_solar_en(String current_solar_en) {
        this.current_solar_en = current_solar_en;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public List<JieQiItemBean> getChild() {
        return child;
    }

    public void setChild(List<JieQiItemBean> child) {
        this.child = child;
    }
}
