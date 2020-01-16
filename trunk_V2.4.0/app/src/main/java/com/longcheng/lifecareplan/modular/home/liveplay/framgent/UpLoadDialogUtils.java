package com.longcheng.lifecareplan.modular.home.liveplay.framgent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.VideoDownLoadUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.zxing.CreateQRImage;

/**
 * 作者：jun on
 * 时间：2020/1/15 10:39
 * 意图：
 */
public class UpLoadDialogUtils {
    private Activity mContext;
    MyDialog selectDialog;
    Handler mHandler;
    TextView tv_del;

    public UpLoadDialogUtils(Activity mContext, Handler mHandler) {
        this.mContext = mContext;
        this.mHandler = mHandler;
    }

    MyDialog codeDialog;
    ImageView iv_cover, iv_code;
    TextView tv_name, tv_cont;
    LinearLayout pop_layout;

    String cover_url_;

    /**
     * 分享二维码
     */
    public void setShareDialog(String cover_url, String name, String title, String codeurl) {
        this.cover_url_ = cover_url;
        if (codeDialog == null) {
            codeDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_sharecode);// 创建Dialog并设置样式主题
            codeDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = codeDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            codeDialog.show();
            WindowManager m = mContext.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = codeDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            codeDialog.getWindow().setAttributes(p); //设置生效
            iv_cover = (ImageView) codeDialog.findViewById(R.id.iv_cover);
            tv_name = (TextView) codeDialog.findViewById(R.id.tv_name);
            tv_cont = (TextView) codeDialog.findViewById(R.id.tv_cont);
            iv_code = (ImageView) codeDialog.findViewById(R.id.iv_code);
            ImageButton iv_btn = (ImageButton) codeDialog.findViewById(R.id.iv_btn);
            pop_layout = (LinearLayout) codeDialog.findViewById(R.id.pop_layout);
            RelativeLayout relat_cover = (RelativeLayout) codeDialog.findViewById(R.id.relat_cover);
            relat_cover.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.2)));
            TextView tv_save = (TextView) codeDialog.findViewById(R.id.tv_save);
            tv_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bitmap bitmap = VideoDownLoadUtils.takeScreenShot(pop_layout, false);
                        VideoDownLoadUtils.saveImageToGallery(mContext, cover_url_, bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    codeDialog.dismiss();
                    mHandler.sendEmptyMessage(VideoFramgent.addShareNum);
                }
            });
            pop_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bitmap bitmap = VideoDownLoadUtils.takeScreenShot(pop_layout, false);
                        VideoDownLoadUtils.saveImageToGallery(mContext, cover_url_, bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    codeDialog.dismiss();
                    mHandler.sendEmptyMessage(VideoFramgent.addShareNum);
                }
            });
            iv_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bitmap bitmap = VideoDownLoadUtils.takeScreenShot(pop_layout, false);
                        VideoDownLoadUtils.saveImageToGallery(mContext, cover_url_, bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    codeDialog.dismiss();
                    mHandler.sendEmptyMessage(VideoFramgent.addShareNum);
                }
            });
        } else {
            codeDialog.show();
        }
        tv_name.setText(name);
        tv_cont.setText(title);
        CreateQRImage.createQRImage(codeurl, iv_code);
        Log.e("fileName", "" + codeurl);
        GlideDownLoadImage.getInstance().loadCircleImageLive(cover_url, R.mipmap.sharecode_icon, iv_cover, 0);
    }


    public void setDialog(String video_user_id) {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_upload);// 创建Dialog并设置样式主题
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
            TextView tv_download = (TextView) selectDialog.findViewById(R.id.tv_download);
            tv_del = (TextView) selectDialog.findViewById(R.id.tv_del);
            TextView tv_share = (TextView) selectDialog.findViewById(R.id.tv_share);
            TextView tv_cancel = (TextView) selectDialog.findViewById(R.id.tv_cancel);
            tv_download.setOnClickListener(mClickListener);
            tv_del.setOnClickListener(mClickListener);
            tv_share.setOnClickListener(mClickListener);
            tv_cancel.setOnClickListener(mClickListener);
        } else {
            selectDialog.show();
        }
        Log.e("onPageSelected", "video_user_id=" + video_user_id);
        if (video_user_id.equals(UserUtils.getUserId(mContext))) {
            tv_del.setVisibility(View.VISIBLE);
        } else {
            tv_del.setVisibility(View.GONE);
        }
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cancel:
                    break;
                case R.id.tv_download:
                    mHandler.sendEmptyMessage(VideoFramgent.startdownload);
                    break;
                case R.id.tv_del:
                    mHandler.sendEmptyMessage(VideoFramgent.delVideo);
                    break;
                case R.id.tv_share:
                    mHandler.sendEmptyMessage(VideoFramgent.shareVideo);
                    break;
                default:
                    break;
            }
            selectDialog.dismiss();
        }
    };
}
