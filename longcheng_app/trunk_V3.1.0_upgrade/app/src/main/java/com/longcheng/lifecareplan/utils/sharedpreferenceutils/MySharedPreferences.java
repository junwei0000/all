package com.longcheng.lifecareplan.utils.sharedpreferenceutils;

import android.content.Context;
import android.content.SharedPreferences;

import com.longcheng.lifecareplan.base.ExampleApplication;

/**
 * 作者：jun on
 * 时间：2018/3/15 10:25
 * 意图：是否开启引导页状态的存储
 */

public class MySharedPreferences {

    public static final String NAME = "preferenceLongCheng";
    private volatile static SharedPreferences sharedPreferences;
    private volatile static MySharedPreferences instance;

    /**
     * 引导页
     */
    private static final String ISFIRSTIN = "isFirstIn";
    /**
     * 是否已退出登录
     */
    private static final String ISLOGOUT = "isLogout";
    /**
     * 是否显示我家人情账弹层
     */
    private static final String ISSHOWRQZ = "isLShowRQZ";


    /**
     * 首页弹层只显示一次
     */
    private static final String showDialogStatus = "showDialogStatus";

    /**
     * 视频上传时间间隔
     */
    private static final String uploadVideoTime = "uploadVideoTime";
    /**
     * 搜索历史 --公社
     */
    private static final String SEARCH_COMMUE = "SEARCH_COMMUE";
    /**
     * 搜索历史 --大队
     */
    private static final String SEARCH_DADUI = "SEARCH_DADUI";

    public static MySharedPreferences getInstance() {
        if (instance == null) {
            synchronized (MySharedPreferences.class) {
                if (instance == null) {
                    instance = new MySharedPreferences();
                }
            }
        }
        return instance;
    }

    private MySharedPreferences() {
        sharedPreferences = ExampleApplication.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setUploadVideoTime(long isShowRQZ) {
        sharedPreferences.edit().putLong(uploadVideoTime, isShowRQZ).commit();
    }

    public long getUploadVideoTime() {
        return sharedPreferences.getLong(uploadVideoTime, 0);
    }


    public void showDialogStatus(boolean isShowRQZ) {
        sharedPreferences.edit().putBoolean(showDialogStatus, isShowRQZ).commit();
    }

    public boolean getshowDialogStatus() {
        return sharedPreferences.getBoolean(showDialogStatus, true);
    }

    public void saveIsShowRQZ(boolean isShowRQZ) {
        sharedPreferences.edit().putBoolean(ISSHOWRQZ, isShowRQZ).commit();
    }

    public boolean getIsShowRQZ() {
        return sharedPreferences.getBoolean(ISSHOWRQZ, true);
    }

    /**
     * @param
     * @Name 存储是否第一次进入App
     * @Data 2018/2/1 10:27
     * @Author :MarkShuai
     */
    public void saveIsFirstIn(boolean isFirstIn) {
        sharedPreferences.edit().putBoolean(ISFIRSTIN, isFirstIn).commit();
    }

    /**
     * @param
     * @Name 获取是否第一次进入App
     * @Data 2018/2/1 10:31
     * @Author :MarkShuai
     */
    public boolean getIsFirstIn() {
        return sharedPreferences.getBoolean(ISFIRSTIN, true);
    }

    /**
     * 是否已退出
     *
     * @param isLogout
     */
    public void saveIsLogout(boolean isLogout) {
        sharedPreferences.edit().putBoolean(ISLOGOUT, isLogout).commit();
    }

    public boolean getIsLogout() {
        return sharedPreferences.getBoolean(ISLOGOUT, true);
    }


    public void setSearchCommue(String contlist) {
        sharedPreferences.edit().putString(SEARCH_COMMUE, contlist).commit();
    }

    public String getSearchCommue() {
        return sharedPreferences.getString(SEARCH_COMMUE, "");
    }

    public void setSearchDaDui(String contlist) {
        sharedPreferences.edit().putString(SEARCH_DADUI, contlist).commit();
    }

    public String getSearchDaDui() {
        return sharedPreferences.getString(SEARCH_DADUI, "");
    }

}
