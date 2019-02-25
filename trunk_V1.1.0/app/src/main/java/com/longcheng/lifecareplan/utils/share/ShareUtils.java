package com.longcheng.lifecareplan.utils.share;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 作者：jun on
 * 时间：2018/9/1 16:51
 * 意图：
 */

public class ShareUtils {
    private Activity mContext;
    MyDialog selectDialog;
    String text = "";
    String targetUrl;
    String title = "";
    String thumb;

    public ShareUtils(Activity mContext) {
        this.mContext = mContext;
    }

    public void setShare(String text, String thumb, String targetUrl, String title) {
        this.text = text;
        this.thumb = thumb;
        this.targetUrl = targetUrl;
        this.title = title;
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_share);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = mContext.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_wx = (LinearLayout) selectDialog.findViewById(R.id.layout_wx);
            LinearLayout layout_wxquan = (LinearLayout) selectDialog.findViewById(R.id.layout_wxquan);
            TextView tv_cancel = (TextView) selectDialog.findViewById(R.id.tv_cancel);
            layout_wx.setOnClickListener(mClickListener);
            layout_wxquan.setOnClickListener(mClickListener);
            tv_cancel.setOnClickListener(mClickListener);
        } else {
            selectDialog.show();
        }
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cancel:
                    break;
                case R.id.layout_wxquan:
                    if (!TextUtils.isEmpty(thumb)) {
                        ShareHelper.shareActionAll(mContext, SHARE_MEDIA.WEIXIN_CIRCLE, thumb, text, targetUrl, text);
                    } else {
                        ShareHelper.shareActionAll(mContext, SHARE_MEDIA.WEIXIN_CIRCLE, text, targetUrl, text);
                    }
                    break;
                case R.id.layout_wx:
                    if (!TextUtils.isEmpty(thumb)) {
                        ShareHelper.shareActionAll(mContext, SHARE_MEDIA.WEIXIN, thumb, text, targetUrl, title);
                    } else {
                        ShareHelper.shareActionAll(mContext, SHARE_MEDIA.WEIXIN, text, targetUrl, title);
                    }
                    break;
                default:
                    break;
            }
            selectDialog.dismiss();
        }
    };
}
