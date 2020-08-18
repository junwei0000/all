package com.longcheng.lifecareplan.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;

import freemarker.core.ReturnInstruction;

/**
 * 作者：jun on
 * 时间：2018/9/29 17:32
 * 意图：
 */

public class BitmapUtil {
    public static Bitmap getBitmapFromResources(Activity context) {

        //加载xml布局文件  
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.share_icon, null);
        //启用绘图缓存  
        view.setDrawingCacheEnabled(true);
        //调用下面这个方法非常重要，如果没有调用这个方法，得到的bitmap为null  
        view.measure(View.MeasureSpec.makeMeasureSpec(96, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(96, View.MeasureSpec.EXACTLY));
        //这个方法也非常重要，设置布局的尺寸和位置  
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        //获得绘图缓存中的Bitmap  
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
