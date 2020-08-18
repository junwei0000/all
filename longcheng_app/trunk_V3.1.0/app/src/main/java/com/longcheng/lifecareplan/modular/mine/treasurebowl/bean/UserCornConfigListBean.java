package com.longcheng.lifecareplan.modular.mine.treasurebowl.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserCornConfigListBean implements Parcelable {
    /**
     * cornucopia_config_id : 1
     * type : 1
     * name : 24节气
     * value : 24
     * rate : 0
     * status : 1
     * create_time : 0
     * update_time : 0
     */

    private int cornucopia_config_id;
    private int type;
    private String name;
    private int value;
    private int rate;
    private int status;
    private int create_time;
    private int update_time;

    public int getCornucopia_config_id() {
        return cornucopia_config_id;
    }

    public void setCornucopia_config_id(int cornucopia_config_id) {
        this.cornucopia_config_id = cornucopia_config_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cornucopia_config_id);
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeInt(this.value);
        dest.writeInt(this.rate);
        dest.writeInt(this.status);
        dest.writeInt(this.create_time);
        dest.writeInt(this.update_time);
    }

    public UserCornConfigListBean() {
    }

    protected UserCornConfigListBean(Parcel in) {
        this.cornucopia_config_id = in.readInt();
        this.type = in.readInt();
        this.name = in.readString();
        this.value = in.readInt();
        this.rate = in.readInt();
        this.status = in.readInt();
        this.create_time = in.readInt();
        this.update_time = in.readInt();
    }

    public static final Parcelable.Creator<UserCornConfigListBean> CREATOR = new Parcelable.Creator<UserCornConfigListBean>() {
        @Override
        public UserCornConfigListBean createFromParcel(Parcel source) {
            return new UserCornConfigListBean(source);
        }

        @Override
        public UserCornConfigListBean[] newArray(int size) {
            return new UserCornConfigListBean[size];
        }
    };
}