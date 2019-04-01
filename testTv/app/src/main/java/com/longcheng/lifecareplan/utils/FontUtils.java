package com.longcheng.lifecareplan.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 作者：jun on
 * 时间：2019/3/29 16:15
 * 意图：
 */

public class FontUtils {

    public static void setFontKaiTi(Context mContext, TextView mTextView) {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "kaiti.ttf");
        mTextView.setTypeface(tf);
    }
}
