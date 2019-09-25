package com.longcheng.lifecareplan.utils;

import android.app.Activity;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

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
    public static final String ReleaseDownLabel = "下拉刷新...";
    public static final String PullUpLabel = "上拉加载...";
    public static final String RefreshingUpLabel = "正在加载...";
    public static final String ReleaseUpLabel = "上拉加载...";
    /////////////////////上下拉的字段

    /**
     * 默认微信appid
     */
    public static final String WECHATAPPID = "wxaa9690f972196106";
    public static final String WECHATSECRET = "5cfff365b5eb89fb12b4d97cdd00338f";
    /**
     * 生活保障支付   微信appid（工会）
     */
    public static final String WECHATAPPIDBaoZhang = "wxf0f9de997bdf640b";
    public static final String WECHATSECRETBaoZhang = "27c8812bbf6f60975550439e7b3e8035";
    /**
     * 默认渠道
     */
    public static String WeChatAppType = "";

    public static String getWeChatAppId() {
        if (!TextUtils.isEmpty(WeChatAppType) && WeChatAppType.equals(WECHATAPPIDBaoZhang)) {
            return WECHATAPPIDBaoZhang;
        } else {
            return WECHATAPPID;
        }
    }


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


    //个人信息result回调
    public static final int USERINFO_FORRESULT_BINGYI = 1;
    public static final int USERINFO_FORRESULT_POLITICAL = 2;
    public static final int USERINFO_FORRESULT_NAME = 3;
    public static final int USERINFO_FORRESULT_PHONE = 4;
    public static final int USERINFO_FORRESULT_DATE = 5;

    //申请互祝result回调
    public static final int APPLYHELP_FORRESULT_ACTION = 1;
    public static final int APPLYHELP_FORRESULT_PEOPLE = 2;
    public static final int APPLYHELP_FORRESULT_ADDRESS = 3;
    public static final int APPLYHELP_FORRESULT_EXPLAIN = 4;

    //地址管理
    public static final int ADDRESS_HANDLE_SETMOREN = 1;
    public static final int ADDRESS_HANDLE_DEL = 2;
    public static final int ADDRESS_HANDLE_EDIT = 3;

    //订单列表
    public static final int ORDER_HANDLE_cancelAction = 1;
    public static final int ORDER_HANDLE_ConfirmReceipt = 2;
    public static final int ORDER_HANDLE_SendBlessing = 3;
    public static final int ORDER_HANDLE_SendBlessingLifeStyle = 4;
    public static final int ORDER_HANDLE_TiXian = 5;
    //**************************************广播********************************************
    /**
     * 首页切换tab
     */
    public static final String MAINMENU_ACTION = "MAINMENU_ACTION";
    public static final String MAIN_ACTION_TYPE_HELPWITH = "MAIN_ACTION_TYPE_HELPWITH";
    public static final String MAIN_ACTION_TYPE_CENTER = "MAIN_ACTION_TYPE_CENTER";
    public static final String MAIN_ACTION_TYPE_BACK = "MAIN_ACTION_TYPE_BACK";
    public static final String MAIN_ACTION_TYPE_NEXT = "MAIN_ACTION_TYPE_NEXT";
    public static final String MAIN_ACTION_TYPE_UPDATEPW500 = "action_home_type_updatepw500";
    public static final String MAIN_ACTION_TYPE_EXCHANGE = "MAIN_ACTION_TYPE_EXCHANGE";
    public static final String MAIN_ACTION_TYPE_HOME = "MAIN_ACTION_TYPE_HOME";
    public static final String MAIN_ACTION_UpdateVerDisAllDialog = "MAIN_ACTION_UpdateVerDisAllDialog";
    /**
     * jpush接收信息
     */
    public static final String MAIN_ACTION_TYPE_JPUSHMESSAGE = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    /**
     * 支付
     */
    public static final String BroadcastReceiver_PAY_ACTION = "BroadcastReceiver_PAY_ACTION";
    /**
     * 康农支付红包底部按钮通知js
     */
    public static final String BroadcastReceiver_KNP_ACTION = "BroadcastReceiver_KNP_ACTION";
    /**
     * 能量列表
     */
    public static final String BroadcastReceiver_ENGRYLIST_ACTION = "BroadcastReceiver_engryList_ACTION";
    /**
     * 好运来
     */
    public static final String BroadcastReceiver_GOODLUCK_ACTION = "BroadcastReceiver_GOODLUCK_ACTION";
    /**
     * 开红包跳转-返回申请互祝页面
     */
    public static final String skipType_OPENRED = "OPENRED";
    /**
     * 天下无债广告行动申请
     */
    public static final String skipType_LifeRepayInfo_GG = "LifeRepayInfo_GG";
    /**
     * 开红包跳转行动列表选择能量配-返回申请互祝页面
     */
    public static final String skipType_OPENREDACTION = "ACTIONPEI";
    /**
     * 订单
     */
    public static final String BroadcastReceiver_ORDER_ACTION = "BroadcastReceiver_ORDER_ACTION";
    /**
     * 已登录
     */
    public static final String loginStatus = "logingOk";
    public static final String loginSkipToMainNext = "loginSkipToMainNext";
    public static final String loginSkipToMessage = "loginSkipToMessage";//跳转消息页面
    public static final String loginSkipToHelpWithEnergy = "loginSkipToHelpWithEnergy";//跳转能量互祝列表
    public static final String loginSkipToHealthyDeli = "loginSkipToHealthyDeli";//跳转健康速递详情
    public static final String loginSkipToActivatEnergy = "loginSkipToActivatEnergy";//跳转激活能量
    public static final String loginSkipToEnergyDetail = "loginSkipToEnergyDetail";//跳转互祝能量详情
    public static final String loginSkipToBangDan = "loginSkipToBangDan";//跳转榜单h5
    public static final String loginSkipToCommuneJoinList = "loginSkipToCommuneJoinList";//跳转加入公社
    public static final String loginSkipToHome = "loginSkipToHome";//登录后返回首页
    /**
     * 提现-----转换生命能量的倍数
     */
    public static final String RECOVERCASH_ENGRY = "9";
    /**
     * 提现-----手续费
     */
    public static final String RECOVERCASH_FEE = "0.0005";


    /**
     * 显示图片的角度
     */
    public static final int image_angle = 3;

}
