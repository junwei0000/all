package com.longcheng.lifecareplantv.utils.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.longcheng.lifecareplantv.R;
import com.longcheng.lifecareplantv.base.ExampleApplication;

/**
 * 作者：MarkMingShuai
 * 时间 2017-8-9 11:13
 * 邮箱：mark_mingshuai@163.com
 * <p>
 * 类的意图：Glide加载图片工具类
 */

public class GlideDownLoadImage {

    private static final String TAG = "ImageLoader";

    private static GlideDownLoadImage instance = new GlideDownLoadImage();

    public static GlideDownLoadImage getInstance() {
        return instance;
    }


    /**
     * 加载 网络头像,SimpleTarget防止无法显示需要刷新问题
     *
     * @param url
     * @param view
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleHeadImage(Context m, String url, ImageView view) {
        Glide.with(m)
                .load(url)
                .placeholder(R.mipmap.user_default_icon)
                .error(R.mipmap.user_default_icon)
                .priority(Priority.HIGH)
                .bitmapTransform(new GlideCircleTransform(ExampleApplication.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * 加载 网络头像,SimpleTarget防止无法显示需要刷新问题
     *
     * @param url
     * @param view
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleHeadImageCenter(Context m, String url, ImageView view) {
        Glide.with(m)
                .load(url)
                .placeholder(R.mipmap.user_default_icon)
                .error(R.mipmap.user_default_icon)
                .priority(Priority.HIGH)
                .bitmapTransform(new GlideCircleTransform(ExampleApplication.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        //图片加载完成
                        view.setImageDrawable(resource);
                    }
                });
    }




    /**
     * @param mContext
     * @param url
     * @param view
     * @param dp
     * @name 加载网络带角度的图片
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(Context mContext, String url, ImageView view, int dp) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.moren_new)
                .error(R.mipmap.moren_new)
                .bitmapTransform(new GlideRoundTransform(mContext, dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }


}
