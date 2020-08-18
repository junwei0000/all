package com.longcheng.lifecareplan.modular.mine.myorder.detail.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;

/**
 * Created by jun on 2019/9/25.
 */

public class CertDialogUtils {
    MyDialog CertificatDialog;
    Activity mContext;

    public CertDialogUtils(Activity mContext) {
        this.mContext = mContext;
    }

    public void showCertificatDialog() {
        if (CertificatDialog == null) {
            CertificatDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_malldetail_certificat);// 创建Dialog并设置样式主题
            CertificatDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = CertificatDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            CertificatDialog.show();
            WindowManager m = mContext.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = CertificatDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            CertificatDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_cancel = (LinearLayout) CertificatDialog.findViewById(R.id.layout_cancel);
            TextView btn_jihuo = (TextView) CertificatDialog.findViewById(R.id.btn_jihuo);
            TextView tv_tishi=(TextView) CertificatDialog.findViewById(R.id.tv_tishi);
            TextView tv_tishi2=(TextView) CertificatDialog.findViewById(R.id.tv_tishi2);
            tv_tishi.setVisibility(View.GONE);
            tv_tishi2.setVisibility(View.VISIBLE);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CertificatDialog.dismiss();
                }
            });
            btn_jihuo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", MineFragment.Certification_url);
                    mContext.startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mContext);
                    CertificatDialog.dismiss();
                }
            });
        } else {
            CertificatDialog.show();
        }
    }
}
