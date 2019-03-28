package com.longcheng.lifecareplan.config;

/**
 * Created by caodebo on 2017/4/19.
 */

public class Config {
    /**
     * 网络请求超时时间毫秒
     */
    public static final long DEFAULT_TIMEOUT = 20000;
    /**
     * 基础URL
     */
    public static final String VERSION = "v1_6_0/";
//    public static final String BASE_URL = "https://t.asdyf.com/api/";//正式
//    public static final String WEB_DOMAIN = ";domain=t.asdyf.com;path=/";
//    public static final String BASE_HEAD_URL = "https://t.asdyf.com/";
    //************测试地址***************
    public static final String BASE_URL = "http://test.t.asdyf.com/api/";//测试
    public static final String WEB_DOMAIN = ";domain=test.t.asdyf.com;path=/";
    public static final String BASE_HEAD_URL = "http://test.t.asdyf.com/";
}
