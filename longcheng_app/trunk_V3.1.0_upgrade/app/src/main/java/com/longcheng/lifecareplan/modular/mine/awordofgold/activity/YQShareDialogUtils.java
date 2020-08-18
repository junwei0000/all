package com.longcheng.lifecareplan.modular.mine.awordofgold.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.home.liveplay.framgent.VideoFramgent;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.VideoDownLoadUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.share.ShareHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.zxing.CreateQRImage;
import com.longcheng.lifecareplan.zxing.encoding.EncodingHandler;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 作者：jun on
 * 时间：2020/1/15 10:39
 * 意图：
 */
public class YQShareDialogUtils {
    private Activity mContext;
    MyDialog codeDialog;
    String cover_url_;
    LinearLayout pop_layout;

    public YQShareDialogUtils(Activity mContext) {
        this.mContext = mContext;
    }

    /**
     * 分享二维码
     */
    public void setShareDialog() {
        this.cover_url_ = UserUtils.getUserId(mContext);
        if (codeDialog == null) {
            codeDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_shareinvterfriend);// 创建Dialog并设置样式主题
            codeDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = codeDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            codeDialog.show();
            WindowManager m = mContext.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = codeDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            codeDialog.getWindow().setAttributes(p); //设置生效
            pop_layout = codeDialog.findViewById(R.id.pop_layout);
            LinearLayout layout_cover = codeDialog.findViewById(R.id.layout_cover);
            ImageView iv_qr = codeDialog.findViewById(R.id.iv_qr);
            TextView tv_name = codeDialog.findViewById(R.id.tv_name);
            TextView tv_wx = codeDialog.findViewById(R.id.tv_wx);
            TextView tv_wxquan = codeDialog.findViewById(R.id.tv_wxquan);
            TextView tv_cancel = codeDialog.findViewById(R.id.tv_cancel);
            tv_name.setText(UserUtils.getUserName(mContext));
            setQRImage(iv_qr);
            int wid = p.width - DensityUtil.dip2px(mContext, 100);
            layout_cover.setLayoutParams(new LinearLayout.LayoutParams(wid, (int) (wid * 1.1)));
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
        } else {
            codeDialog.show();
        }
        ConstantManager.setWeChatAppId(ConstantManager.WECHATAPPID);
        pop_layout.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = VideoDownLoadUtils.takeScreenShot(pop_layout, false);
                VideoDownLoadUtils.saveImageToGallery(mContext, cover_url_, bitmap);
            }
        });

    }

    private void setQRImage(ImageView iv_qr) {
        String invite_user_url = Config.BASE_HEAD_URL + "home/wxuser/index/shareuid/";
        invite_user_url = invite_user_url + UserUtils.getUserId(mContext);
        Log.e("invite_user_url", "invite_user_url=" + invite_user_url);
        CreateQRImage.createQRImage(invite_user_url, iv_qr);

    }
}
