package com.longcheng.volunteer.modular.home.healthydelivery.list.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Burning on 2018/9/13.
 */

public class HealthyDeliveryBean {
    @SerializedName("new_id")
    protected int id;
    @SerializedName("new_name")
    protected String title;
    @SerializedName("des")
    protected String des;
    @SerializedName("new_num")
    protected String new_num;
    @SerializedName("new_content")
    protected String content;
    @SerializedName("pic")
    protected String pic;//封面图
    @SerializedName("new_zan")
    protected String zanCount;
    @SerializedName("type")
    protected int type;
    @SerializedName("add_time")
    protected String addTime;
    @SerializedName("update_time")
    protected long updateTime;
    @SerializedName("status")
    protected int status;
    @SerializedName("type_name")
    protected String typeName;
    @SerializedName("create_time")
    protected String createTime;
    @SerializedName("info_url")
    protected String urlDetail;

    public String getUrlDetail() {
        return urlDetail;
    }

    public void setUrlDetail(String urlDetail) {
        this.urlDetail = urlDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getNew_num() {
        return new_num;
    }

    public void setNew_num(String new_num) {
        this.new_num = new_num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getZanCount() {
        return zanCount;
    }

    public void setZanCount(String zanCount) {
        this.zanCount = zanCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
