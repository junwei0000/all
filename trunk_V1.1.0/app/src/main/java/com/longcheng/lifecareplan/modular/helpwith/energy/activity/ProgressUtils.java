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

    public void showNum(int progress, int max, TextView item_pb_num) {
        String mCurrentDrawText = String.format("%d", progress * 100 / max);
        mCurrentDrawText = mCurrentDrawText + "%";
        item_pb_num.setText(mCurrentDrawText);
        int contentlen = DensityUtil.dip2px(context, 30);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                contentlen,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        int progresslen = DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 46);
        float ww = progresslen * progress / 100;
        if (progresslen - ww <= contentlen) {
            ww = progresslen - contentlen + DensityUtil.dip2px(context, 6);
        } else if (progress > 10) {
            ww = ww;
        } else if (progress <= 10) {
            ww = ww - 2;
        }
        Log.e("progress", "progress=" + progress
                + " ;progresslen==" + progresslen + " ;contentlen==" + contentlen + " ;ww==" + ww);
        if (ww < 0) {
            ww = 0;
        }
        params.setMargins((int) ww, 0, 0, 0);
        item_pb_num.setLayoutParams(params);
    }
}
