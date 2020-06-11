package com.longcheng.lifecareplan.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Adapter;
import android.widget.TextView;

import com.longcheng.lifecareplan.base.BaseAdapterHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：jun on
 * 时间：2020/4/29 16:58
 * 意图：刷新倒计时
 */
public class TimeCountdownUtils {
    Timer timer;

    Activity context;

    public TimeCountdownUtils(Activity context) {
        this.context = context;
    }

    /**
     * 退出时清除
     */
    public void clearTimer() {
        if (timer != null)
            timer.cancel();
    }


    /**
     * 列表倒计时
     */
    public void startTime(BaseAdapterHelper adapter) {
            if (timer == null) {
                timer = new Timer();
            }
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }, 0, 1000);
    }


    public void setTimeShow(long useTime, TextView textView) {
        int hour = (int) (useTime / 3600);
        int min = (int) (useTime / 60 % 60);
        int second = (int) (useTime % 60);
        int day = (int) (useTime / 3600 / 24);
        String mDay, mHour, mMin, mSecond;//天，小时，分钟，秒
        second--;
        if (second < 0) {
            min--;
            second = 59;
            if (min < 0) {
                min = 59;
                hour--;
            }
        }
        if (hour < 10) {
            mHour = "0" + hour;
        } else {
            mHour = "" + hour;
        }
        if (min < 10) {
            mMin = "0" + min;
        } else {
            mMin = "" + min;
        }
        if (second < 10) {
            mSecond = "0" + second;
        } else {
            mSecond = "" + second;
        }
        String strTime = mHour + ":" + mMin + ":" + mSecond + "";
        textView.setText(strTime);
    }

}
