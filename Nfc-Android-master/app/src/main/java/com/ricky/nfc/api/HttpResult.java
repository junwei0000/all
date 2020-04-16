package com.ricky.nfc.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liukun on 16/3/5.
 */
public class HttpResult<T> {

    // code 为返回的状态码, message 为返回的消息, 演示的没有这两个字段，考虑到真实的环境中基本包含就在这里写定值
    private int status;
    // this will receive message or status, msg as message field
    @SerializedName("msg")
    private String msg;

    public int getCode() {
        return status;
    }

    public String getMessage() {
        return msg;
    }

    //用来模仿Data
    @SerializedName("data")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
