package com.longcheng.lifecareplan.utils.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
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
    private static volatile ShareHelper INSTANCE;
    Activity activity;

    public static ShareHelper getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (ShareHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ShareHelper();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * @Author :MarkShuai
     * @DATE :2017/9/29 9:51
     * @Params 分享带链接 （缩略图 标题 简述）
     */
    public void shareActionAll(Activity activity, SHARE_MEDIA platform, String imgUrl, String text, String targetUrl, String title) {
        this.activity = activity;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap myBitmap = null;
                UMImage image;
                try {
                    myBitmap = Glide.with(activity)//上下文
                            .load(imgUrl)//url
                            .asBitmap() //必须
                            .into(120, 120)
                            .get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (myBitmap == null) {
                    image = new UMImage(activity, imgUrl);
                } else {
                    image = new UMImage(activity, myBitmap);
                }
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
        }).start();
    }

    /**
     * @Author :MarkShuai
     * @DATE :2017/9/29 9:51
     * @Params 分享带链接 （缩略图 标题 简述）
     */
    public void shareActionAll(Activity activity, SHARE_MEDIA platform, String text, String targetUrl, String title,int iconid) {
        this.activity = activity;
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), iconid);
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
    public void shareActionImageAndText(Activity activity, SHARE_MEDIA platform, String imgUrl, String text) {
        this.activity = activity;
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
    public void shareActionImage(Activity activity, SHARE_MEDIA platform, String imgUrl) {
        this.activity = activity;
        UMImage image = new UMImage(activity, imgUrl);
        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(image)
                .setCallback(shareListener)
                .share();
    }


    public UMShareListener shareListener = new UMShareListener() {
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
//            ToastUtils.showToast("分享成功");
            Log.e("ResponseBody", "分享成功=" + platform.toString());
            if (!TextUtils.isEmpty(BaoZhangActitvty.life_repay_id) && !BaoZhangActitvty.life_repay_id.equals("0")) {
                Intent intent2 = new Intent();
                intent2.setAction(ConstantManager.BroadcastReceiver_KNP_ACTION);
                if (BaoZhangActitvty.shareBackType.equals("knpDetail")) {
                    intent2.putExtra("errCode", BaoZhangActitvty.SHARETYPE_KNPDetailPay);
                    activity.sendBroadcast(intent2);
                } else if (BaoZhangActitvty.shareBackType.equals("lifeDetail")) {
                    intent2.putExtra("errCode", BaoZhangActitvty.SHARETYPE_lifeDetailPay);
                    activity.sendBroadcast(intent2);
                } else if (BaoZhangActitvty.shareBackType.equals("LifeBasicDetail")) {
                    intent2.putExtra("errCode", BaoZhangActitvty.SHARETYPE_LifeBasicDetailPay);
                    activity.sendBroadcast(intent2);
                } else if (BaoZhangActitvty.shareBackType.equals("Live")) {
                    intent2.setAction(ConstantManager.BroadcastReceiver_LIVE_ACTION);
                    intent2.putExtra("errCode", 10000);
                    activity.sendBroadcast(intent2);
                }
                Log.e("ResponseBody", "分享成功=BaoZhangActitvty.life_repay_id=" + BaoZhangActitvty.life_repay_id);
            }
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showToast("分享失败");
            Log.e("ResponseBody", "分享失败=" + platform.toString() + "  " + t.toString());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            ToastUtils.showToast("分享取消");
            Log.e("ResponseBody", "分享取消=" + platform.toString());
        }
    };


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
