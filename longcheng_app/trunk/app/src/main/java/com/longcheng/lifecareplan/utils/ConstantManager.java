package com.longcheng.lifecareplan.utils;

import android.os.Environment;

import java.io.File;

/**
 * 作者：MarkShuai
 * 时间：2017/11/24 15:30
 * 邮箱：MarkShuai@163.com
 * 意图：常量管理类
 */

public class ConstantManager {

    public static final String USER_ID = "";

    /////////////////////上下拉的字段
    public static final String PullDownLabel = "下拉刷新...";
    public static final String RefreshingDownLabel = "正在刷新...";
    public static final String ReleaseDownLabel = "下拉加载更多...";
    public static final String PullUpLabel = "上拉加载...";
    public static final String RefreshingUpLabel = "正在加载...";
    public static final String ReleaseUpLabel = "上拉加载更多...";
    public static final String REFRESH_RELEASE = "松开可刷新";
    /////////////////////上下拉的字段

    //微信
    public static final String WECHATAPPID = "wxaa9690f972196106";
    public static final String WECHATSECRET = "f972a428a19609d236a7ce66a210d1bc";
    //友盟APPID
    public static final String UMENGAPPID = "5a25fe0df29d983549000075";

    //小米推送
    public static final String XM_APP_ID = "2882303761517613509";
    public static final String XM_APP_KEY = "5991761324509";

    //更新APK Name
    public static final String UPLOAD_APK_NAME = "test.apk";

    //增量更新
    public static final String PATCH_FILE = "apk.patch";
    public static final String SD_CARD = Environment.getExternalStorageDirectory() + File.separator;
    //新版本apk的目录
    public static final String NEW_APK_PATH = SD_CARD + "BsDiff.apk";

    //判断是不是H5来的支付
    public static int isHtmlPayMethod = 0;


}
