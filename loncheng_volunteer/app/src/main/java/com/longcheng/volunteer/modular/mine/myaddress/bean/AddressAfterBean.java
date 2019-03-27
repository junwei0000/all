package com.longcheng.volunteer.modular.mine.myaddress.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class AddressAfterBean implements Serializable {
    @SerializedName("isCanEdit")
    private String isCanEdit;
    @SerializedName("addressList")
    private List<AddressItemBean> addressList;

    public String getIsCanEdit() {
        return isCanEdit;
    }

    public void setIsCanEdit(String isCanEdit) {
        this.isCanEdit = isCanEdit;
    }

    public List<AddressItemBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressItemBean> addressList) {
        this.addressList = addressList;
    }


    //--------------------------

    @SerializedName("user_id")
    private String user_id;
    @SerializedName("consignee")
    private String consignee;
    @SerializedName("province")
    private String province;
    @SerializedName("city")
    private String city;
    @SerializedName("district")
    private String district;
    @SerializedName("address")
    private String address;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("address_id")
    private String address_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }
}
