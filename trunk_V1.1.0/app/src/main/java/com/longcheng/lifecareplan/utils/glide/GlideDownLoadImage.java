package com.longcheng.lifecareplan.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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
                .placeholder(R.mipmap.user_default_icon)
                .error(R.mipmap.user_default_icon)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }


    /**
     * 加载 本地头像
     *
     * @param resId
     * @param view
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleHeadImage(Context m, int resId, ImageView view) {
        Glide.with(m)
                .load(resId)
                .placeholder(R.mipmap.user_default_icon)
                .error(R.mipmap.user_default_icon)
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
     * @param url
     * @param view
     * @name 加载网络圆图
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(String url, ImageView view) {
        Glide.with(ExampleApplication.getContext())
                .load(url)
                .placeholder(R.mipmap.user_default_icon)
                .error(R.mipmap.user_default_icon)
                .bitmapTransform(new GlideCircleTransform(ExampleApplication.getContext()))
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
                .placeholder(R.mipmap.user_default_icon)
                .error(R.mipmap.user_default_icon)
                .bitmapTransform(new GlideCircleTransform(mComtext))
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
    public void loadCircleImageCommune(Context mComtext, String url, ImageView view) {
        Glide.with(mComtext)
                .load(url)
                .placeholder(R.mipmap.icon_commune_round)
                .error(R.mipmap.icon_commune_round)
                .bitmapTransform(new GlideCircleTransform(mComtext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
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
    public void loadCircleImageCommune(Context mContext, String url, ImageView view, int dp) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.icon_commune)
                .error(R.mipmap.icon_commune)
                .bitmapTransform(new GlideRoundTransform(mContext, dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * 互祝首页加载节气图标
     * @param mContext
     * @param url
     * @param view
     */
    public void loadCircleImageHelpIndex(Context mContext, String url, ImageView view) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.wisheach_icon_yushui)
                .error(R.mipmap.wisheach_icon_yushui)
                .bitmapTransform(new GlideRoundTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param mContext
     * @param resId
     * @param view
     * @param dp
     * @name 加载本地带角度的图片
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(Context mContext, int resId, ImageView view, int dp) {
        Glide.with(mContext)
                .load(resId)
                .placeholder(R.mipmap.moren_new)
                .error(R.mipmap.moren_new)
                .bitmapTransform(new GlideRoundTransform(mContext, dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param resId
     * @param view
     * @param dp
     * @name 加载本地带角度的图片
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(int resId, ImageView view, int dp) {
        Glide.with(ExampleApplication.getContext())
                .load(resId)
                .placeholder(R.mipmap.moren_new)
                .error(R.mipmap.moren_new)
                .bitmapTransform(new GlideRoundTransform(ExampleApplication.getContext(), dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
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

    public void loadCircleImageRoleGoods(Context mContext, String url, ImageView view, int dp) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.mall_icon_thedefault)
                .error(R.mipmap.mall_icon_thedefault)
                .bitmapTransform(new GlideRoundTransform(mContext, dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    public void loadCircleImageRoleGoodsDetail(Context mContext, String url, ImageView view, int dp) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.mall_icon_thedefault2)
                .error(R.mipmap.mall_icon_thedefault2)
                .bitmapTransform(new GlideRoundTransform(mContext, dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
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
    public void loadCircleImageRoleREf(Context mContext, String url, ImageView view, int dp) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.moren_new)
                .error(R.mipmap.moren_new)
                .bitmapTransform(new GlideRoundTransform(mContext, dp))
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
     * @name 加载网络带角度的图片
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRoleREf(Context mContext, String url, ImageView view) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.moren_new)
                .error(R.mipmap.moren_new)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        //图片加载完成
                        view.setImageDrawable(resource);
                    }
                });
    }
}
