package com.longcheng.lifecareplan.bean.contactbean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SelectPhone implements Parcelable {

    private List<PhoneBean> data;

    public List<PhoneBean> getData() {
        return data;
    }

    public void setData(List<PhoneBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.data);
    }

    public SelectPhone() {
    }

    protected SelectPhone(Parcel in) {
        this.data = in.createTypedArrayList(PhoneBean.CREATOR);
    }

    public static final Parcelable.Creator<SelectPhone> CREATOR = new Parcelable.Creator<SelectPhone>() {
        @Override
        public SelectPhone createFromParcel(Parcel source) {
            return new SelectPhone(source);
        }

        @Override
        public SelectPhone[] newArray(int size) {
            return new SelectPhone[size];
        }
    };
}
