package com.longcheng.lifecareplan.modular.helpwith.energy.activity;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.utils.DensityUtil;

/**
 * 作者：jun on
 * 时间：2018/8/28 17:12
 * 意图：
 */

public class ProgressUtils {
    Context context;

    public ProgressUtils(Context context) {
        this.context = context;
    }

    public void setTextCont(int progress, int max, TextView item_pb_num) {
        String mCurrentDrawText = String.format("%d", progress * 100 / max);
        mCurrentDrawText = mCurrentDrawText + "%";
        item_pb_num.setText(mCurrentDrawText);
    }

    public void showNum(int progress, int max, TextView item_pb_num) {
        setTextCont(progress, max, item_pb_num);
        int contentlen = DensityUtil.dip2px(context, 30);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                contentlen,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        int progresslen = DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 46);
        float left = progresslen * progress / 100;
        if (progresslen - left <= contentlen) {
            left = progresslen - contentlen + DensityUtil.dip2px(context, 6);
        } else if (progress > 10) {
            left = left;
        } else if (progress <= 10) {
            left = left - 2;
        }
        Log.e("progress", "progress=" + progress
                + " ;progresslen==" + progresslen + " ;contentlen==" + contentlen + " ;left==" + left);
        if (left < 0) {
            left = 0;
        }
        params.setMargins((int) left, 0, 0, 0);
        item_pb_num.setLayoutParams(params);
    }

    /**
     * 混合状态 超级显示,防止重合
     *
     * @param progress
     * @param max
     * @param item_pb_num
     */
    public void showNummixSuper(int progress, int Super_ability_proportion, int max, TextView item_pb_num) {
        setTextCont(progress, max, item_pb_num);
        int contentlen = DensityUtil.dip2px(context, 30);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                contentlen,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        int progresslen = DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 46);
        float leftsuper = progresslen * Super_ability_proportion / 100;
        float left = progresslen * progress / 100;
        if (leftsuper == left || (leftsuper - left) <= contentlen) {
            left = leftsuper - contentlen;
        } else {
            if (progresslen - left <= contentlen) {
                left = progresslen - contentlen + DensityUtil.dip2px(context, 6);
            } else if (progress > 10) {
                left = left;
            } else if (progress <= 10) {
                left = left - 2;
            }
        }
        Log.e("progress", "progress=" + progress
                + " ;progresslen==" + progresslen + " ;contentlen==" + contentlen + " ;left==" + left);
        if (left < 0) {
            left = 0;
        }
        params.setMargins((int) left, 0, 0, 0);
        item_pb_num.setLayoutParams(params);
    }
}
