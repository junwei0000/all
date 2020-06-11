package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.imguide;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

/**
 * 作者：jun on
 * 时间：2020/6/1 12:20
 * 意图：
 */
public class IMGuideUtils {
    Activity context;

    public IMGuideUtils(Activity context) {
        this.context = context;
    }

    /**
     * 设置普通用户端引导
     */
    public void setCommGuide() {
        SharedPreferencesHelper.put(context, "CommGuideStatus", true);
    }

    public boolean getCommGuide() {
        return (boolean) SharedPreferencesHelper.get(context, "CommGuideStatus", false);
    }

    /**
     * 设置祝福师端引导
     */
    public void setTeachGuide() {
        SharedPreferencesHelper.put(context, "TeachGuideStatus", true);
    }

    public boolean getTeachGuide() {
        return (boolean) SharedPreferencesHelper.get(context, "TeachGuideStatus", false);
    }

    MyDialog mGuideDialog;
    ImageView iv_img;
    LinearLayout layout_bg;

    boolean firstzfbclick = false;

    public void showGuideDialog(String type) {
        if (mGuideDialog == null) {
            mGuideDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_imguide);// 创建Dialog并设置样式主题
            mGuideDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mGuideDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mGuideDialog.show();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mGuideDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            p.height = d.getHeight();
            mGuideDialog.getWindow().setAttributes(p); //设置生效
            iv_img = mGuideDialog.findViewById(R.id.iv_img);
            layout_bg = mGuideDialog.findViewById(R.id.layout_bg);
            iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type.equals("pionner")) {
                        if (!firstzfbclick) {
                            firstzfbclick = true;
                            iv_img.setBackgroundResource(R.mipmap.im_guide_1);
                            int right = DensityUtil.dip2px(context, 13);
                            layout_bg.setPadding(0, right, right, 0);
                        } else {
                            mGuideDialog.dismiss();
                        }
                    } else {
                        mGuideDialog.dismiss();
                    }
                }
            });
            mGuideDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (type.equals("user")) {
                        setCommGuide();
                    } else if (type.equals("pionner")) {
                        setTeachGuide();
                    }
                }
            });
        } else {
            mGuideDialog.show();
        }

        if (type.equals("user")) {
            iv_img.setBackgroundResource(R.mipmap.im_guide_3);
            int right = DensityUtil.dip2px(context, 13);
            layout_bg.setPadding(0, right, right, 0);
        } else if (type.equals("pionner")) {
            iv_img.setBackgroundResource(R.mipmap.im_guide_2);
            int right = DensityUtil.dip2px(context, 15);
            int top = DensityUtil.dip2px(context, 163);
            layout_bg.setPadding(0, top, right, 0);
        }
    }
}
