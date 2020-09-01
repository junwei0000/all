package com.longcheng.lifecareplan.bean.contactbean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


import com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.pinyinutil.PinyinUtil;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.pinyinutil.PyEntity;

public class PhoneBean implements PyEntity, Parcelable {

    private String faceUrl;
    private String name;
    private String phone;
    private String sortKey;
    private int id;
    private String pinyin;
    private boolean isadd = false;
    private boolean isshow = false;
    private int is_new = 0;//1没有注册平台
    private int sms = 0;//0、可以发短信   1、不能发短信

    public PhoneBean(int id, String name, String phone, String sortKey, boolean isadd, boolean isshow) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sortKey = sortKey;
        this.isadd = isadd;
        this.isshow = isshow;
    }

    public PhoneBean(String name, String phone, int is_new, int sms) {
        this.name = name;
        this.is_new = is_new;
        this.phone = phone;
        this.sms = sms;
    }

    public PhoneBean(String name, String phone, boolean isadd) {
        this.name = name;
        this.phone = phone;
        this.isadd = isadd;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String getPinyin() {
        if (pinyin == null) {
            pinyin = PinyinUtil.getPingYin(name);
        }
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PhoneBean) {
            if (this.phone.equals(((PhoneBean) obj).getPhone())) {
                return true;
            }
        }
        return super.equals(obj);
    }

    public boolean isIsadd() {
        return isadd;
    }

    public void setIsadd(boolean isadd) {
        this.isadd = isadd;
    }

    public boolean isIsshow() {
        return isshow;
    }

    public void setIsshow(boolean isshow) {
        this.isshow = isshow;
    }

    public int getIs_new() {
        return is_new;
    }

    public void setIs_new(int is_new) {
        this.is_new = is_new;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.faceUrl);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.sortKey);
        dest.writeInt(this.id);
        dest.writeString(this.pinyin);
        dest.writeByte(this.isadd ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isshow ? (byte) 1 : (byte) 0);
        dest.writeInt(this.is_new);
        dest.writeInt(this.sms);
    }

    protected PhoneBean(Parcel in) {
        this.faceUrl = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.sortKey = in.readString();
        this.id = in.readInt();
        this.pinyin = in.readString();
        this.isadd = in.readByte() != 0;
        this.isshow = in.readByte() != 0;
        this.is_new = in.readInt();
        this.sms = in.readInt();
    }

    public static final Parcelable.Creator<PhoneBean> CREATOR = new Parcelable.Creator<PhoneBean>() {
        @Override
        public PhoneBean createFromParcel(Parcel source) {
            return new PhoneBean(source);
        }

        @Override
        public PhoneBean[] newArray(int size) {
            return new PhoneBean[size];
        }
    };
}
