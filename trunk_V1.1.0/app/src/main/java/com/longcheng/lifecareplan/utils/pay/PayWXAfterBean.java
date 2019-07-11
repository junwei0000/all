package com.longcheng.lifecareplan.utils.pay;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：zhangjinqi
 * 时间 2017/10/17 15:18
 * 邮箱：mengchong.55@163.com
 * 类的意图：支付bean
 */

public class PayWXAfterBean {
    //    "package": "Sign=WXPay",
//            "appid": "wx203b5956a0f6cfeb",
//            "sign": "FA59C3F89376E46E3DF577A43A2B2FCE53E6337EA81AAEE19CC79C7FF056E2B4",
//            "partnerid": "SH201710180857370045",
//            "prepayid": "wx201710180857299ed1873d3c0663696322",
//            "noncestr": "sGwGVNitM0E95Ax7",
//            "timestamp": "1508288271"
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("packages")
    private String packages;
    @SerializedName("appid")
    private String appid;
    @SerializedName("sign")
    private String sign;
    @SerializedName("partnerid")
    private String partnerid;
    @SerializedName("prepayid")
    private String prepayid;
    @SerializedName("noncestr")
    private String noncestr;
    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("one_order_id")
    private String one_order_id;

    @SerializedName("alipayPayInfo")
    private String payInfo;

    public PayWXAfterBean() {

    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOne_order_id() {
        return one_order_id;
    }

    public void setOne_order_id(String one_order_id) {
        this.one_order_id = one_order_id;
    }

    @Override
    public String toString() {
        return "SKBPayAfterBean{" +
                "prepayid='" + prepayid + '\'' +
                ", appid='" + appid + '\'' +
                '}';
    }
}
