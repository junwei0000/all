package com.longcheng.lifecareplan.modular.helpwith.reportbless.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui.RiceActivitesActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui.ZYBZuActivitesActivity;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.myview.MyDialog;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class JieQisUtils {

    Activity mActivity;

    public JieQisUtils(Activity mActivity) {
        this.mActivity = mActivity;
    }

    MyDialog mPayTypeDialog;
    GifDrawable gifDrawable;

    public void showFQBialog() {
        if (mPayTypeDialog == null) {
            mPayTypeDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_fuquan_lingqu);// 创建Dialog并设置样式主题
            mPayTypeDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mPayTypeDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mPayTypeDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mPayTypeDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            mPayTypeDialog.getWindow().setAttributes(p); //设置生效
            ImageView iv_gifbg = mPayTypeDialog.findViewById(R.id.iv_gifbg);
            GifImageView iv_gif = mPayTypeDialog.findViewById(R.id.iv_gif);
            iv_gifbg.setLayoutParams(new RelativeLayout.LayoutParams(p.width, p.width));
            iv_gif.setLayoutParams(new RelativeLayout.LayoutParams(p.width, p.width));
            gifDrawable = (GifDrawable) iv_gif.getDrawable();
            gifDrawable.setLoopCount(1);
            gifDrawable.setSpeed(2);
            LinearLayout layout_cancel = mPayTypeDialog.findViewById(R.id.layout_cancel);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayTypeDialog.dismiss();
                }
            });
        } else {
            mPayTypeDialog.show();
            gifDrawable.reset();
        }
    }


    MyDialog mRiceDialog;
    GifDrawable gifRiceDrawable;
    Animation wuxinganimation;
    ImageView iv_yuan;

    public void setmRiceDialogdismiss() {
        if (mRiceDialog != null && mRiceDialog.isShowing()) {
            mRiceDialog.dismiss();
        }
    }

    /**
     * 大米活动
     */
    public void showRiceBialog() {
        if (mRiceDialog == null) {
            mRiceDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_activity_rice);// 创建Dialog并设置样式主题
            mRiceDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mRiceDialog.getWindow();
            window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
            mRiceDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mRiceDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 8 / 9; //宽度设置为屏幕
            mRiceDialog.getWindow().setAttributes(p); //设置生效
            ImageView iv_gifbg = mRiceDialog.findViewById(R.id.iv_gifbg);
            iv_yuan = mRiceDialog.findViewById(R.id.iv_yuan);
            GifImageView iv_gif = mRiceDialog.findViewById(R.id.iv_gif);
            iv_gifbg.setLayoutParams(new RelativeLayout.LayoutParams(p.width, (int) (p.width * 1.523)));
            iv_gif.setLayoutParams(new RelativeLayout.LayoutParams(p.width, (int) (p.width * 1.523)));
            iv_yuan.setLayoutParams(new LinearLayout.LayoutParams(p.width - DensityUtil.dip2px(mActivity, 80), p.width - DensityUtil.dip2px(mActivity, 80)));
            gifRiceDrawable = (GifDrawable) iv_gif.getDrawable();
            gifRiceDrawable.setLoopCount(0);

            wuxinganimation = AnimationUtils.loadAnimation(mActivity, R.anim.wuxing_whirl);
            wuxinganimation.setInterpolator(new LinearInterpolator());//实现匀速动画
            wuxinganimation.setRepeatCount(-1);
            ImageView iv_cancel = mRiceDialog.findViewById(R.id.iv_cancel);
            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRiceDialog.dismiss();
                }
            });
            iv_gifbg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRiceDialog.dismiss();
                    if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHome)) {
                        Intent intent = new Intent(mActivity, RiceActivitesActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        mActivity.startActivity(intent);
                    }
                }
            });
        } else {
            mRiceDialog.show();
            gifRiceDrawable.reset();
        }
        iv_yuan.startAnimation(wuxinganimation);
    }
}
