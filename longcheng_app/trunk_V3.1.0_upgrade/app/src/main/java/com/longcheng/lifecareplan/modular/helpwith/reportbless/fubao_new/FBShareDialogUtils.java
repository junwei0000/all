package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.VideoDownLoadUtils;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.zxing.CreateQRImage;
import com.makeramen.roundedimageview.RoundedImageView;

public class FBShareDialogUtils {

    private Activity mContext;
    MyDialog codeDialog;
    String invite_user_url;
    RelativeLayout pop_layout;
    ImageView iv_qr = null;
    int pagetype = 1;

    public String getInvite_user_url() {
        return invite_user_url;
    }

    public void setInvite_user_url(String invite_user_url) {
        this.invite_user_url = invite_user_url;
    }

    public int getPagetype() {
        return pagetype;
    }

    public void setPagetype(int pagetype) {
        this.pagetype = pagetype;
    }

    public FBShareDialogUtils(Activity mContext) {
        this.mContext = mContext;
    }

    /**
     * 分享二维码
     */
    public void setShareDialog() {

        if (codeDialog == null) {
            codeDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_sharefufriend);// 创建Dialog并设置样式主题
            codeDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = codeDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            codeDialog.show();

        } else {
            codeDialog.show();
        }
        initView(codeDialog);
        setQRImage(iv_qr);
//        ConstantManager.setWeChatAppId(ConstantManager.WECHATAPPID);
        pop_layout.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = VideoDownLoadUtils.takeScreenShot(pop_layout, false);
                VideoDownLoadUtils.saveImageToGallery(mContext, invite_user_url, bitmap);
            }
        });

    }

    private void initView(MyDialog codeDialog) {
        WindowManager m = mContext.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = codeDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth(); //宽度设置为屏幕
        codeDialog.getWindow().setAttributes(p); //设置生效
        pop_layout = codeDialog.findViewById(R.id.pop_layout);
        RoundedImageView layout_cover = codeDialog.findViewById(R.id.iv_cover);
        LinearLayout bottom_layout = codeDialog.findViewById(R.id.bottom_layout);
        LinearLayout imag_layout = codeDialog.findViewById(R.id.image_layout);
        iv_qr = codeDialog.findViewById(R.id.iv_qr);
        TextView tv_name = codeDialog.findViewById(R.id.tv_name);
        TextView tv_wx = codeDialog.findViewById(R.id.tv_wx);
        TextView tv_hinit_qr = codeDialog.findViewById(R.id.tv_hinit_qr);
        TextView tv_lines = codeDialog.findViewById(R.id.tv_lines);
        TextView tv_hinit_diloag = codeDialog.findViewById(R.id.tv_hinit_diloag);
        TextView tv_action = codeDialog.findViewById(R.id.tv_action);

        TextView tv_wxquan = codeDialog.findViewById(R.id.tv_wxquan);
        TextView tv_cancel = codeDialog.findViewById(R.id.tv_cancel);
        int wid = p.width - DensityUtil.dip2px(mContext, 80);
        if (pagetype == 1) {
            imag_layout.setLayoutParams(new RelativeLayout.LayoutParams(wid, ((d.getHeight() / 5) * 3)));
            // 等比缩放图片比例
            layout_cover.setLayoutParams(new LinearLayout.LayoutParams(wid, (int) (wid * 1.0467)));
            layout_cover.setBackgroundResource(R.mipmap.top_bg_fu_shared);
            String name = UserUtils.getUserName(mContext);
            name = CommonUtil.setName(name);
            tv_name.setText(mContext.getString(R.string.hinit_fu_name, name));
            tv_lines.setVisibility(View.VISIBLE);
            tv_hinit_diloag.setVisibility(View.VISIBLE);
            tv_action.setVisibility(View.VISIBLE);
            tv_hinit_qr.setText(mContext.getResources().getString(R.string.hinit_fu_send_sucess));
            // 设置底部控件高度及位置
            RelativeLayout.LayoutParams flp = new RelativeLayout.LayoutParams(wid, (int) ((int) ((wid * 1.0467) / 5) * (2.2)));
            flp.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.image_layout);
            flp.addRule(RelativeLayout.CENTER_IN_PARENT);
            bottom_layout.setLayoutParams(flp);
        } else if (pagetype == 2) {
            imag_layout.setLayoutParams(new RelativeLayout.LayoutParams(wid, (int) ((d.getHeight() / 5) * 2.8)));
            // 等比缩放图片比例
            layout_cover.setLayoutParams(new LinearLayout.LayoutParams(wid, (int) (wid * 0.8363)));
            layout_cover.setBackgroundResource(R.mipmap.second_fu_bg_top);
            tv_lines.setVisibility(View.GONE);
            tv_hinit_diloag.setVisibility(View.GONE);
            tv_action.setVisibility(View.GONE);
            tv_name.setText(R.string.hinit_fu_hinit_sucess);
            tv_hinit_qr.setText(mContext.getResources().getString(R.string.hinit_fu_qr_sucess));
            // 设置底部控件高度及位置
            RelativeLayout.LayoutParams flp = new RelativeLayout.LayoutParams(wid, (int) ((int) ((wid * 0.8363) / 5) * (2.8)));
            flp.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.image_layout);
            bottom_layout.setLayoutParams(flp);
        }
        tv_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeDialog.dismiss();
                Intent lan = mContext.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(lan.getComponent());
                mContext.startActivity(intent);
            }
        });
        tv_wxquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeDialog.dismiss();
                Intent lan = mContext.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(lan.getComponent());
                mContext.startActivity(intent);
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeDialog.dismiss();
            }
        });
    }


    private void setQRImage(ImageView iv_qr) {
        if (invite_user_url != null) {
            CreateQRImage.createQRImage(invite_user_url, iv_qr);
        }

    }
}
