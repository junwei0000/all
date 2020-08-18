package com.longcheng.volunteer.http.basebean;


import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class BasicResponse<T> {

    protected String msg;
    @SerializedName(value = "status", alternate = {"code"})
    protected String status;
    private T data;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
