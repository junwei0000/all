package com.longcheng.lifecareplantv.utils.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.longcheng.lifecareplantv.R;
import com.longcheng.lifecareplantv.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 作者:MarkShuai
 * 时间：2017/9/29 09:36
 * 邮箱:18610922052@163.com
 * 类的意图: 分享
 */

public class ShareHelper {

    /**
     * @Author :MarkShuai
     * @DATE :2017/9/29 9:51
     * @Params 分享带链接 （缩略图 标题 简述）
     */
    public static void shareActionAll(Activity activity, SHARE_MEDIA platform, String imgUrl, String text, String targetUrl, String title) {
        UMImage image = new UMImage(activity, imgUrl);
        UMWeb web = new UMWeb(targetUrl);
        web.setTitle(title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(text);//描述
        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(web)
                .setCallback(shareListener)
                .share();
    }

    /**
     * @Author :MarkShuai
     * @DATE :2017/9/29 9:51
     * @Params 分享带链接 （缩略图 标题 简述）
     */
    public static void shareActionAll(Activity activity, SHARE_MEDIA platform, String text, String targetUrl, String title) {
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.share_icon);
        UMImage image = new UMImage(activity, bitmap);
        UMWeb web = new UMWeb(targetUrl);
        web.setTitle(title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(text);//描述
        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(web)
                .setCallback(shareListener)
                .share();
    }

    /**
     * @Author :MarkShuai
     * @DATE :2017/9/29 9:51
     * @Params 分享不带链接 （缩略图 标题 简述）
     */
    public static void shareActionImageAndText(Activity activity, SHARE_MEDIA platform, String imgUrl, String text) {
        UMImage image = new UMImage(activity, imgUrl);
        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(image)
                .withText(text)//描述
                .setCallback(shareListener)
                .share();
    }

    /**
     * @Author :MarkShuai
     * @DATE :2017/9/29 9:51
     * @Params 分享图片
     */
    public static void shareActionImage(Activity activity, SHARE_MEDIA platform, String imgUrl) {
        UMImage image = new UMImage(activity, imgUrl);
        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(image)
                .setCallback(shareListener)
                .share();
    }


    public static UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
//            ToastUtils.showToast(platform + "分享成功了");
            ToastUtils.showToast("分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            ToastUtils.showToast(platform + "分享失败了" + t.toString());
            ToastUtils.showToast("分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            ToastUtils.showToast(platform + "分享取消了");
            ToastUtils.showToast("分享取消");
        }
    };


    /**
     * @param
     * @name 销毁当前对象 防止内存泄漏
     * @time 2017/12/4 19:31
     * @author MarkShuai
     */
    public static void release(Activity activity) {
        UMShareAPI.get(activity).release();
    }
}
