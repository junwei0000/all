package com.longcheng.lifecareplanTv.api;

/**
 * @author YorkYu
 * @version V1.0
 * @Project: Supplier
 * @Package mall.b2b.meixun.com.supplier.net
 * @Description:
 * @time 2016/8/11 9:20
 */
public class ServerException extends RuntimeException {
    // 异常处理，为速度，不必要设置getter和setter
    public String status;
    public String message;

    public ServerException(String message, String status) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
