package com.longcheng.volunteer.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LC on 2018/3/11.
 */

public class ResponseBean {
    @SerializedName("msg")
    protected String msg;
    @SerializedName("status")
    protected String status;

    public String getMsg() {
        return msg;
    }

    protected void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "msg='" + msg + '\'' +
                ", status='" + status + "}";
    }
}
