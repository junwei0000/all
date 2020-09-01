package com.longcheng.lifecareplan.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class HuoDongBean implements Parcelable {

    String imageUrl;
    String name;
    String hdid;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHdid() {
        return hdid;
    }

    public void setHdid(String hdid) {
        this.hdid = hdid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
        dest.writeString(this.name);
        dest.writeString(this.hdid);
    }

    public HuoDongBean() {
    }

    protected HuoDongBean(Parcel in) {
        this.imageUrl = in.readString();
        this.name = in.readString();
        this.hdid = in.readString();
    }

    public static final Parcelable.Creator<HuoDongBean> CREATOR = new Parcelable.Creator<HuoDongBean>() {
        @Override
        public HuoDongBean createFromParcel(Parcel source) {
            return new HuoDongBean(source);
        }

        @Override
        public HuoDongBean[] newArray(int size) {
            return new HuoDongBean[size];
        }
    };
}
