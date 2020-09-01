package com.longcheng.lifecareplan.utils.myview.address.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wepon on 2017/12/4.
 * 数据模型
 */

public class YwpAddressBean implements Serializable {

    private List<AddressItemBean> province;
    private List<AddressItemBean> city;
    private List<AddressItemBean> district;
    private List<AddressItemBean> town;
    private List<AddressItemBean> village;
    public List<AddressItemBean> getProvince() {
        return province;
    }

    public void setProvince(List<AddressItemBean> province) {
        this.province = province;
    }

    public List<AddressItemBean> getCity() {
        return city;
    }

    public void setCity(List<AddressItemBean> city) {
        this.city = city;
    }

    public List<AddressItemBean> getDistrict() {
        return district;
    }

    public void setDistrict(List<AddressItemBean> district) {
        this.district = district;
    }

    public List<AddressItemBean> getTown() {
        return town;
    }

    public void setTown(List<AddressItemBean> town) {
        this.town = town;
    }

    public List<AddressItemBean> getVillage() {
        return village;
    }

    public void setVillage(List<AddressItemBean> village) {
        this.village = village;
    }

    public static class AddressItemBean implements Serializable {
        private String i;
        private String n;
        private String p;
        private String sm;

        public String getSm() {
            return sm;
        }

        public void setSm(String sm) {
            this.sm = sm;
        }

        public String getI() {
            return i;
        }

        public void setI(String i) {
            this.i = i;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }
    }
}
