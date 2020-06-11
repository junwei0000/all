package com.longcheng.lifecareplan.modular.bottommenu;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.date.DatesUtils;

/**
 * 作者：jun on
 * 时间：2019/11/9 14:46
 * 意图：根据时间变换颜色
 */
public class ColorChangeByTime {
    private static ColorChangeByTime instance;

    private ColorChangeByTime() {
    }

    public static synchronized ColorChangeByTime getInstance() {
        if (instance == null) {
            instance = new ColorChangeByTime();
        }
        return instance;
    }

    public int backColor(Context activity) {
        String hh = DatesUtils.getInstance().getNowTime("H");
        int h_ = Integer.parseInt(hh);
        if (h_ >= 3 && h_ < 4) {
            return activity.getResources().getColor(R.color.lichuncolors);
        } else if (h_ >= 4 && h_ < 5) {
            return activity.getResources().getColor(R.color.yushuicolors);
        } else if (h_ >= 5 && h_ < 6) {
            return activity.getResources().getColor(R.color.jingzecolors);
        } else if (h_ >= 6 && h_ < 7) {
            return activity.getResources().getColor(R.color.chunfencolors);
        } else if (h_ >= 7 && h_ < 8) {
            return activity.getResources().getColor(R.color.qingmingcolors);
        } else if (h_ >= 8 && h_ < 9) {
            return activity.getResources().getColor(R.color.guyucolors);
        } else if (h_ >= 9 && h_ < 10) {
            return activity.getResources().getColor(R.color.lixiacolors);
        } else if (h_ >= 10 && h_ < 11) {
            return activity.getResources().getColor(R.color.xiaomancolors);
        } else if (h_ >= 11 && h_ < 12) {
            return activity.getResources().getColor(R.color.mangzhongcolors);
        } else if (h_ >= 12 && h_ < 13) {
            return activity.getResources().getColor(R.color.xiazhicolors);
        } else if (h_ >= 13 && h_ < 14) {
            return activity.getResources().getColor(R.color.xiaoshucolors);
        } else if (h_ >= 14 && h_ < 15) {
            return activity.getResources().getColor(R.color.dashucolors);
        } else if (h_ >= 15 && h_ < 16) {
            return activity.getResources().getColor(R.color.liqiucolors);
        } else if (h_ >= 16 && h_ < 17) {
            return activity.getResources().getColor(R.color.chushucolors);
        } else if (h_ >= 17 && h_ < 18) {
            return activity.getResources().getColor(R.color.bailucolors);
        } else if (h_ >= 18 && h_ < 19) {
            return activity.getResources().getColor(R.color.qiufencolors);
        } else if (h_ >= 19 && h_ < 20) {
            return activity.getResources().getColor(R.color.hanlucolors);
        } else if (h_ >= 20 && h_ < 21) {
            return activity.getResources().getColor(R.color.shuangjiangcolors);
        } else if (h_ >= 21 && h_ < 22) {
            return activity.getResources().getColor(R.color.lidongcolors);
        } else if (h_ >= 22 && h_ < 23) {
            return activity.getResources().getColor(R.color.xiaoxuecolors);
        } else if (h_ >= 0 && h_ < 1) {
            return activity.getResources().getColor(R.color.dongzhicolors);
        } else if (h_ >= 1 && h_ < 2) {
            return activity.getResources().getColor(R.color.xioahancolors);
        } else if (h_ >= 2 && h_ < 3) {
            return activity.getResources().getColor(R.color.dahancolors);
        } else {
            return activity.getResources().getColor(R.color.daxuecolors);
        }
    }

    /**
     * drawable.setStroke(1, Color.parseColor("#ff0000"));//设置边框的宽度和颜色
     *
     * @param view
     */
    public void changeDrawable(Context activity, View view) {
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        String hh = DatesUtils.getInstance().getNowTime("H");
        int h_ = Integer.parseInt(hh);
        if (h_ >= 3 && h_ < 4) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.lichuncolors));
        } else if (h_ >= 4 && h_ < 5) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.yushuicolors));
        } else if (h_ >= 5 && h_ < 6) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.jingzecolors));
        } else if (h_ >= 6 && h_ < 7) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.chunfencolors));
        } else if (h_ >= 7 && h_ < 8) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.qingmingcolors));
        } else if (h_ >= 8 && h_ < 9) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.guyucolors));
        } else if (h_ >= 9 && h_ < 10) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.lixiacolors));
        } else if (h_ >= 10 && h_ < 11) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.xiaomancolors));
        } else if (h_ >= 11 && h_ < 12) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.mangzhongcolors));
        } else if (h_ >= 12 && h_ < 13) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.xiazhicolors));
        } else if (h_ >= 13 && h_ < 14) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.xiaoshucolors));
        } else if (h_ >= 14 && h_ < 15) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.dashucolors));
        } else if (h_ >= 15 && h_ < 16) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.liqiucolors));
        } else if (h_ >= 16 && h_ < 17) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.chushucolors));
        } else if (h_ >= 17 && h_ < 18) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.bailucolors));
        } else if (h_ >= 18 && h_ < 19) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.qiufencolors));
        } else if (h_ >= 19 && h_ < 20) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.hanlucolors));
        } else if (h_ >= 20 && h_ < 21) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.shuangjiangcolors));
        } else if (h_ >= 21 && h_ < 22) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.lidongcolors));
        } else if (h_ >= 22 && h_ < 23) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.xiaoxuecolors));
        } else if (h_ >= 23) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.daxuecolors));
        } else if (h_ >= 0 && h_ < 1) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.dongzhicolors));
        } else if (h_ >= 1 && h_ < 2) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.xioahancolors));
        } else if (h_ >= 2 && h_ < 3) {
            gradientDrawable.setColor(activity.getResources().getColor(R.color.dahancolors));
        }

    }

    public void changeDrawableToClolor(Context activity, View view, int color) {
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        gradientDrawable.setColor(activity.getResources().getColor(color));
    }
}
