package com.longcheng.lifecareplan.modular.home.liveplay.bean;

import java.io.Serializable;

/**
 * 作者：jun on
 * 时间：2019/10/29 15:12
 * 意图：
 */
public class VideoGetSignatureInfo implements Serializable {
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
