package com.longcheng.lifecareplan.utils.pay;

/**
 * 作者：zhangjinqi
 * 时间 2017/10/17 15:27
 * 邮箱：mengchong.55@163.com
 * 类的意图：用户下单前的bean
 */

public class PayBeforeBean {

    private int userid;
    private int money;
    private String ip;
    private int productid;

    public PayBeforeBean() {

    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "PayBeforeBean{" +
                "userid=" + userid +
                ", money=" + money +
                ", ip='" + ip + '\'' +
                ", productid=" + productid +
                '}';
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }


}
