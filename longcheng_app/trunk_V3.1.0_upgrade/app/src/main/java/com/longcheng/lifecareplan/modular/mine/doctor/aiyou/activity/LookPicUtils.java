package com.longcheng.lifecareplan.modular.mine.doctor.aiyou.activity;

import android.app.Activity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;

public class LookPicUtils {

    Activity context;
    MyDialog selectDialog;
    ImageView iv_img;

    public LookPicUtils(Activity context) {
        this.context = context;
    }


    public void showLookPicDialog(String Pic_url) {
        if (selectDialog == null) {
            selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_bigpic);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            p.height = d.getHeight();
            selectDialog.getWindow().setAttributes(p); //设置生效
            iv_img = selectDialog.findViewById(R.id.iv_img);
            iv_img.setLayoutParams(new LinearLayout.LayoutParams(p.width, p.height));
            iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
        } else {
            selectDialog.show();
        }
        GlideDownLoadImage.getInstance().loadCircleImageLive(Pic_url, R.mipmap.my_icon_shangchuan, iv_img, 0);

    }
}
