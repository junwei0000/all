package com.longcheng.lifecareplan.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ExampleApplication;

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
     * @param
     * @name 加载本地图片的重载方法
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:10
     */
    public void loadImage(Context mContext, int resId, ImageView view) {
        Glide.with(mContext)
                .load(resId)
                .placeholder(R.mipmap.moren_new)
                .error(R.mipmap.moren_new)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param
     * @name 加载本地图片的重载方法
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:10
     */
    public void loadImage(Fragment fragment, int resId, ImageView view) {
        Glide.with(fragment)
                .load(resId)
                .placeholder(R.mipmap.moren_new)
                .error(R.mipmap.moren_new)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }


    /**
     * @param
     * @name 加载本地图片的重载方法
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:10
     */
    public void loadImage(Activity activity, int resId, ImageView view) {
        Glide.with(activity)
                .load(resId)
                .placeholder(R.mipmap.moren_new)
                .error(R.mipmap.moren_new)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param
     * @name 加载本地图片的重载方法 此方法慎用
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:10
     */
    public void loadImage(int resId, ImageView view) {
        Glide.with(ExampleApplication.getContext())
                .load(resId)
                .placeholder(R.mipmap.moren_new)
                .error(R.mipmap.moren_new)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * 加载网络图片
     *
     * @param url
     * @param view
     */
    public void loadImage(Context mContext, String url, ImageView view) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.moren_new)
                .error(R.mipmap.moren_new)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param activity
     * @param url
     * @param view
     * @name 加载网络图片重载方法
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadImage(Activity activity, String url, ImageView view) {
        Glide.with(activity)
                .load(url)
                .placeholder(R.mipmap.moren_new)
                .error(R.mipmap.moren_new)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param fragment
     * @param url
     * @param view
     * @name 加载网络图片重载方法
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadImage(Fragment fragment, String url, ImageView view) {
        Glide.with(fragment)
                .load(url)
                .placeholder(R.mipmap.moren_new)
                .error(R.mipmap.moren_new)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }


    /**
     * @param mContext
     * @param resId
     * @param view
     * @name 加载本地圆图
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Context mContext, int resId, ImageView view) {
        Glide.with(mContext)
                .load(resId)
                .placeholder(R.mipmap.moren_yuan)
                .error(R.mipmap.moren_yuan)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param activity
     * @param resId
     * @param view
     * @name 加载本地圆图
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Activity activity, int resId, ImageView view) {
        Glide.with(activity)
                .load(resId)
                .placeholder(R.mipmap.moren_yuan)
                .error(R.mipmap.moren_yuan)
                .bitmapTransform(new GlideCircleTransform(activity))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }


    /**
     * @param resId
     * @param view
     * @name 加载本地圆图
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(int resId, ImageView view) {
        Glide.with(ExampleApplication.getContext())
                .load(resId)
                .placeholder(R.mipmap.moren_yuan)
                .error(R.mipmap.moren_yuan)
                .bitmapTransform(new GlideCircleTransform(ExampleApplication.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param url
     * @param view
     * @name 加载网络圆图
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(String url, ImageView view) {
        Glide.with(ExampleApplication.getContext())
                .load(url)
                .placeholder(R.mipmap.moren_yuan)
                .error(R.mipmap.moren_yuan)
                .bitmapTransform(new GlideCircleTransform(ExampleApplication.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param activity
     * @param url
     * @param view
     * @name 加载网络圆图
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Activity activity, String url, ImageView view) {
        Glide.with(activity)
                .load(url)
                .placeholder(R.mipmap.moren_yuan)
                .error(R.mipmap.moren_yuan)
                .bitmapTransform(new GlideCircleTransform(activity))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param mComtext
     * @param url
     * @param view
     * @name 加载网络圆图
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Context mComtext, String url, ImageView view) {
        Glide.with(mComtext)
                .load(url)
                .placeholder(R.mipmap.moren_yuan)
                .error(R.mipmap.moren_yuan)
                .bitmapTransform(new GlideCircleTransform(mComtext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param fragment
     * @param url
     * @param view
     * @name 加载网络圆图
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Fragment fragment, String url, ImageView view) {
        Glide.with(fragment)
                .load(url)
                .placeholder(R.mipmap.moren_yuan)
                .error(R.mipmap.moren_yuan)
                .bitmapTransform(new GlideCircleTransform(fragment.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param fragment
     * @param resId
     * @param view
     * @param dp
     * @name 加载网络带角度的图片
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(Fragment fragment, int resId, ImageView view, int dp) {
        Glide.with(fragment)
                .load(resId)
                .placeholder(R.mipmap.moren_yuan)
                .error(R.mipmap.moren_yuan)
                .bitmapTransform(new GlideRoundTransform(fragment.getContext(), dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param activity
     * @param resId
     * @param view
     * @param dp
     * @name 加载网络带角度的图片
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(Activity activity, int resId, ImageView view, int dp) {
        Glide.with(activity)
                .load(resId)
                .placeholder(R.mipmap.moren_yuan)
                .error(R.mipmap.moren_yuan)
                .bitmapTransform(new GlideRoundTransform(activity, dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param mContext
     * @param resId
     * @param view
     * @param dp
     * @name 加载网络带角度的图片
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(Context mContext, int resId, ImageView view, int dp) {
        Glide.with(mContext)
                .load(resId)
                .placeholder(R.mipmap.moren_yuan)
                .error(R.mipmap.moren_yuan)
                .bitmapTransform(new GlideRoundTransform(mContext, dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param resId
     * @param view
     * @param dp
     * @name 加载网络带角度的图片
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole( int resId, ImageView view, int dp) {
        Glide.with(ExampleApplication.getContext())
                .load(resId)
                .placeholder(R.mipmap.moren_yuan)
                .error(R.mipmap.moren_yuan)
                .bitmapTransform(new GlideRoundTransform(ExampleApplication.getContext(), dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

}
