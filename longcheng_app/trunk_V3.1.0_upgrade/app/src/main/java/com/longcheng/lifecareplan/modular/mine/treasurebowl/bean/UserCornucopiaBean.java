package com.longcheng.lifecareplan.modular.mine.treasurebowl.bean;

import android.os.Parcel;
import android.os.Parcelable;

public  class UserCornucopiaBean implements Parcelable {

    /**
     * user_id : 834
     * user_name : 张阳
     * shoukangyuan : 0
     * jieqibao : 0
     * fuqibao : 0
     * uzhufubao : 0
     * score : 0
     * total_score : 0
     * limit : 0
     * total_limit : 0
     * super_shoukangyuan : 0
     * ability : 0
     * status : 1
     * create_time : 1597121552
     * update_time : 1597121552
     */

    private String user_id;
    private String user_name;
    private int shoukangyuan;
    private int jieqibao;
    private int fuqibao;
    private int uzhufubao;
    private int score;
    private int total_score;
    private int limit;
    private int total_limit;
    private int super_shoukangyuan;
    private int ability;
    private int status;
    private int create_time;
    private int update_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getShoukangyuan() {
        return shoukangyuan;
    }

    public void setShoukangyuan(int shoukangyuan) {
        this.shoukangyuan = shoukangyuan;
    }

    public int getJieqibao() {
        return jieqibao;
    }

    public void setJieqibao(int jieqibao) {
        this.jieqibao = jieqibao;
    }

    public int getFuqibao() {
        return fuqibao;
    }

    public void setFuqibao(int fuqibao) {
        this.fuqibao = fuqibao;
    }

    public int getUzhufubao() {
        return uzhufubao;
    }

    public void setUzhufubao(int uzhufubao) {
        this.uzhufubao = uzhufubao;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal_limit() {
        return total_limit;
    }

    public void setTotal_limit(int total_limit) {
        this.total_limit = total_limit;
    }

    public int getSuper_shoukangyuan() {
        return super_shoukangyuan;
    }

    public void setSuper_shoukangyuan(int super_shoukangyuan) {
        this.super_shoukangyuan = super_shoukangyuan;
    }

    public int getAbility() {
        return ability;
    }

    public void setAbility(int ability) {
        this.ability = ability;
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
        dest.writeString(this.user_id);
        dest.writeString(this.user_name);
        dest.writeInt(this.shoukangyuan);
        dest.writeInt(this.jieqibao);
        dest.writeInt(this.fuqibao);
        dest.writeInt(this.uzhufubao);
        dest.writeInt(this.score);
        dest.writeInt(this.total_score);
        dest.writeInt(this.limit);
        dest.writeInt(this.total_limit);
        dest.writeInt(this.super_shoukangyuan);
        dest.writeInt(this.ability);
        dest.writeInt(this.status);
        dest.writeInt(this.create_time);
        dest.writeInt(this.update_time);
    }

    public UserCornucopiaBean() {
    }

    protected UserCornucopiaBean(Parcel in) {
        this.user_id = in.readString();
        this.user_name = in.readString();
        this.shoukangyuan = in.readInt();
        this.jieqibao = in.readInt();
        this.fuqibao = in.readInt();
        this.uzhufubao = in.readInt();
        this.score = in.readInt();
        this.total_score = in.readInt();
        this.limit = in.readInt();
        this.total_limit = in.readInt();
        this.super_shoukangyuan = in.readInt();
        this.ability = in.readInt();
        this.status = in.readInt();
        this.create_time = in.readInt();
        this.update_time = in.readInt();
    }

    public static final Parcelable.Creator<UserCornucopiaBean> CREATOR = new Parcelable.Creator<UserCornucopiaBean>() {
        @Override
        public UserCornucopiaBean createFromParcel(Parcel source) {
            return new UserCornucopiaBean(source);
        }

        @Override
        public UserCornucopiaBean[] newArray(int size) {
            return new UserCornucopiaBean[size];
        }
    };
}