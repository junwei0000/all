package com.longcheng.lifecareplan.utils.myview.address;

public class SelctAeraInfo {
    int commune_id;
    int parent_id;
    String name;
    String short_name;

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public SelctAeraInfo(int parent_id, String name) {
        this.parent_id = parent_id;
        this.name = name;
    }

    public int getCommune_id() {
        return commune_id;
    }

    public void setCommune_id(int commune_id) {
        this.commune_id = commune_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }
}
