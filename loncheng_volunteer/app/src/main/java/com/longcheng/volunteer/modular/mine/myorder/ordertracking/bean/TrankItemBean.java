package com.longcheng.volunteer.modular.mine.myorder.ordertracking.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2018/5/17 11:05
 * 意图：
 */

public class TrankItemBean implements Serializable {
    private String AcceptStation;
    private String AcceptTime;

    public String getAcceptStation() {
        return AcceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        AcceptStation = acceptStation;
    }

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        AcceptTime = acceptTime;
    }
}