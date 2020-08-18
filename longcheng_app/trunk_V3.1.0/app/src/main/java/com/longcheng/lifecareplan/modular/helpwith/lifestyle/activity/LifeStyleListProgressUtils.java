package com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity;

import android.app.Activity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;

/**
 * 作者：jun on
 * 时间：2018/8/28 17:12
 * 意图：
 */

public class LifeStyleListProgressUtils {
    Activity context;

    public LifeStyleListProgressUtils(Activity context) {
        this.context = context;
    }

    public void showNum(int progress, NumberProgressBar mNumberProgressBar, TextView item_pb_num) {
        int max = mNumberProgressBar.getMax();
        String mCurrentDrawText = String.format("%d", progress * 100 / max);
        mCurrentDrawText = mCurrentDrawText + "%";
        item_pb_num.setText(mCurrentDrawText);

        item_pb_num.post(new Runnable() {
            @Override
            public void run() {
                int contentlen = item_pb_num.getWidth();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        contentlen,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                int progresslen = mNumberProgressBar.getWidth();
                float ww = progresslen * progress / 100;
                Log.e("showNum", "progresslen=" + progresslen + ";ww=" + ww
                        + ";contentlen=" + contentlen + ";progress=" + progress);
                if (progresslen - ww <= contentlen) {
                    ww = progresslen - contentlen;
                } else {
                    ww = ww - 2;
                }
                if (ww < 0) {
                    ww = 0;
                }
                params.setMargins((int) ww, 0, 0, 0);
                item_pb_num.setLayoutParams(params);
            }
        });

    }
}
