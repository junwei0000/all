package com.longcheng.lifecareplan.widget.dialog;

/**
 * 作者：jun on
 * 时间：2019/9/3 15:19
 * 意图：防止加载中动画OOM异常崩溃的问题
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.InputStream;

public class SceneAnimation {
    private ImageView mImageView;
    private int[] mFrameRess;
    private int[] mDurations;
    private int mDuration;

    private int mLastFrameNo;
    private long mBreakDelay;
    Context context;

    public SceneAnimation(ImageView pImageView, int[] pFrameRess,
                          int[] pDurations) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDurations = pDurations;
        mLastFrameNo = pFrameRess.length - 1;

//        mImageView.setBackgroundResource(mFrameRess[0]);
        setBg(mFrameRess[0]);
        play(1);
    }

    BitmapFactory.Options opt;

    public SceneAnimation(Context context, ImageView pImageView, int[] pFrameRess, int pDuration) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        this.context = context;
        mLastFrameNo = pFrameRess.length - 1;
        opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
//        mImageView.setBackgroundResource(mFrameRess[0]);
        setBg(mFrameRess[0]);
        playConstant(1);
    }

    public SceneAnimation(ImageView pImageView, int[] pFrameRess,
                          int pDuration, long pBreakDelay) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        mLastFrameNo = pFrameRess.length - 1;
        mBreakDelay = pBreakDelay;

//        mImageView.setBackgroundResource(mFrameRess[0]);
        setBg(mFrameRess[0]);
        playConstant(1);
    }

    private void play(final int pFrameNo) {
        mImageView.postDelayed(new Runnable() {
            public void run() {
//                mImageView.setBackgroundResource(mFrameRess[pFrameNo]);
                setBg(mFrameRess[pFrameNo]);
                if (pFrameNo == mLastFrameNo)
                    play(0);
                else
                    play(pFrameNo + 1);
            }
        }, mDurations[pFrameNo]);
    }

    private void playConstant(final int pFrameNo) {
        mImageView.postDelayed(new Runnable() {
            public void run() {
//                mImageView.setBackgroundResource(mFrameRess[pFrameNo]);
                setBg(mFrameRess[pFrameNo]);
                if (pFrameNo == mLastFrameNo)
                    playConstant(0);
                else
                    playConstant(pFrameNo + 1);
            }
        }, pFrameNo == mLastFrameNo && mBreakDelay > 0 ? mBreakDelay
                : mDuration);
    }


    private void setBg(int id) {
        InputStream is = context.getResources().openRawResource(id);
        if (is != null) {
            Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
            BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
            if (mImageView != null)
                mImageView.setBackgroundDrawable(bd);
        }
    }
}