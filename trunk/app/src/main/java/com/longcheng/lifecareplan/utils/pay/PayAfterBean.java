package com.longcheng.lifecareplan.utils.pay;

/**
 * 作者：zhangjinqi
 * 时间 2017/10/17 15:18
 * 邮箱：mengchong.55@163.com
 * 类的意图：支付bean
 */

public class PayAfterBean {
//    "package": "Sign=WXPay",
//            "appid": "wx203b5956a0f6cfeb",
//            "sign": "FA59C3F89376E46E3DF577A43A2B2FCE53E6337EA81AAEE19CC79C7FF056E2B4",
//            "partnerid": "SH201710180857370045",
//            "prepayid": "wx201710180857299ed1873d3c0663696322",
//            "noncestr": "sGwGVNitM0E95Ax7",
//            "timestamp": "1508288271"

    public PayAfterBean(){

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

    private String packages;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    private String ordernum;

}
