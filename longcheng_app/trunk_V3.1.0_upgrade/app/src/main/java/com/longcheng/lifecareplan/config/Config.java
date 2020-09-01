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
    public static final String VERSION = "v1_8_0/";
    /**
     * **********正式地址***************
     */

//    public static final String BASE_URL = "https://t.asdyf.com/api/";
//    public static final String WEB_DOMAIN = ";domain=t.asdyf.com;path=/";
//    public static final String BASE_HEAD_URL = "https://t.asdyf.com/";
//    public static final String LIVE_BASE_URL = "https://video.dock.lifecareplan.cn/";
//    public static final String PAY_URL = "https://pay.asdyf.com/";//
//    public static final String WEB_DOMAIN_PAY = ";domain=pay.asdyf.com;path=/";
//    public static final String CHAT_SERVER_URL = "http://39.96.37.95:9502";
    /**
     * **********测试地址***************
     */
    public static final String BASE_URL = "http://test.t.asdyf.com/api/";
    public static final String WEB_DOMAIN = ";domain=test.t.asdyf.com;path=/";
    public static final String BASE_HEAD_URL = "http://test.t.asdyf.com/";
    public static final String LIVE_BASE_URL = "http://t.dock.lifecareplan.cn/";
    public static final String PAY_URL = "http://t.pay.asdyf.com/";
    public static final String WEB_DOMAIN_PAY = ";domain=t.pay.asdyf.com;path=/";
    public static final String CHAT_SERVER_URL = "http://47.94.131.243:9502";

}
