package com.longcheng.volunteer.utils.share;

import android.app.Activity;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 作者：MarkShuai
 * 时间：2017/12/5 10:47
 * 邮箱：MarkShuai@163.com
 * 意图：友盟第三方登录工具类
 */

public class UMLoginUtils {

    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private volatile static UMLoginUtils instance = null;

    public UMLoginUtils() {
    }

    public synchronized static UMLoginUtils getInstance() {
        if (instance == null) {
            synchronized (UMLoginUtils.class) {
                if (instance == null) {
                    instance = new UMLoginUtils();
                }
            }
        }
        return instance;
    }


    /**
     * @param
     * @name 登录方法
     * @time 2017/12/5 11:18
     * @author MarkShuai
     */
    public void threadLogin(Activity activity, SHARE_MEDIA share_media, UMLoginListener listener) {
        UMShareAPI.get(activity).getPlatformInfo(activity, share_media, listener);
    }

    /**
     * @param
     * @name 撤销授权方法
     * @time 2017/12/5 11:18
     * @author MarkShuai
     */
    public void deleteOauth(Activity activity, SHARE_MEDIA share_media, UMLoginListener listener) {
        UMShareAPI.get(activity).deleteOauth(activity, share_media, listener);
    }

    /**
     * @param
     * @author MarkShuai
     * @name UMeng 登录的回调监听，可以自己进行扩展
     * @time 2017/12/5 11:20
     */
    public interface UMLoginListener extends UMAuthListener {

    }

    /**
     * @param
     * @name 销毁当前对象 防止内存泄漏
     * @time 2017/12/4 19:31
     * @author MarkShuai
     */
    public void release(Activity activity) {
        UMShareAPI.get(activity).release();
    }

}
