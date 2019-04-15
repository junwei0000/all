package com.longcheng.lifecareplanTv.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.longcheng.lifecareplanTv.R;
import com.longcheng.lifecareplanTv.base.MyApplication;

/**
 * 作者：jun on
 * 时间：2019/4/15 16:49
 * 意图：
 */

public class AnimUtils {
    private static volatile AnimUtils INSTANCE;


    public static AnimUtils getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (AnimUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AnimUtils();
                }
            }
        }
        return INSTANCE;
    }

    Animation myleftAnim;
    Animation myrightAnim;

    /**
     * 淡入淡出动画方法
     */
    public void alpha(View view) {
        // 创建透明度动画，第一个参数是开始的透明度，第二个参数是要转换到的透明度
        AlphaAnimation alphaAni = new AlphaAnimation(0.4f, 1f);
        //设置动画执行的时间，单位是毫秒
        alphaAni.setDuration(800);
        // 设置动画结束后停止在哪个状态（true表示动画完成后的状态）
//        alphaAni.setFillAfter(false);
        // true动画结束后回到开始状态
        alphaAni.setFillBefore(true);
        // 设置动画重复次数 // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        alphaAni.setRepeatCount(0);
        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        alphaAni.setRepeatMode(Animation.REVERSE); // 启动动画
        view.startAnimation(alphaAni);
    }

    /**
     * 设置从左向右移动
     */
    public void setMoveToRight(View view) {
        if (myrightAnim == null) {
            myrightAnim = AnimationUtils.loadAnimation(MyApplication.getContext(), R.anim.out_to_rigth);
            myrightAnim.setFillAfter(false);//android动画结束后停在结束位置
        }
        view.startAnimation(myrightAnim);
    }

    /**
     * 设置从右向左移动
     */
    public void setMoveToLeft(View view) {
        if (myleftAnim == null) {
            myleftAnim = AnimationUtils.loadAnimation(MyApplication.getContext(), R.anim.out_to_left);
            myleftAnim.setFillAfter(false);//android动画结束后停在结束位置
        }
        view.startAnimation(myleftAnim);
    }
}
