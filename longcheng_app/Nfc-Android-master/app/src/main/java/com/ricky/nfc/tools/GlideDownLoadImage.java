package com.ricky.nfc.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ricky.nfc.R;

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
     * @param url
     * @param view
     * @name 加载网络圆图
     * @auhtor MarkMingShuai
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Context context,String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.user_default_icon)
                .error(R.mipmap.user_default_icon)
                .bitmapTransform(new GlideCircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }


}
