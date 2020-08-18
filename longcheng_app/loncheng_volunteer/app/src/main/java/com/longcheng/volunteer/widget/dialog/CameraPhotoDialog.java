package com.longcheng.volunteer.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.utils.FileImage;

import java.io.File;

/**
 * 作者：MarkShuai
 * 时间：2017/12/11 17:32
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class CameraPhotoDialog extends Dialog {
    /**
     * LoadDialog
     */
    private static CameraPhotoDialog loadDialog;

    public static final int INTENTPICKPHOTO = 99;
    public static final int INTENTTAKEPHOTO = 100;

    private static File tempFile;

    public CameraPhotoDialog(@NonNull final Context context) {
        super(context);
        this.getContext().setTheme(R.style.dialog_capture_style);
        setContentView(R.layout.changehead_dialog);
        RelativeLayout mRLPickPhoto = findViewById(R.id.rl_pick_photo);

        mRLPickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);

                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                ((Activity) context).startActivityForResult(intent, INTENTPICKPHOTO);
                dismiss();
            }
        });

        RelativeLayout mRLTakePhoto = findViewById(R.id.rl_take_photo);

        mRLTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后照片的储存路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(tempFile));

                ((Activity) context).startActivityForResult(intent, INTENTTAKEPHOTO);
                dismiss();
            }
        });
        setParams();
    }

    /**
     * @param
     * @name 设置ViewParams
     * @auhtor MarkMingShuai
     * @Data 2017-9-19 16:52
     */
    private void setParams() {
        this.setCanceledOnTouchOutside(false);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        // Dialog宽度
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);
        window.getDecorView().getBackground().setAlpha(0);
    }

    /**
     * show the dialog
     *
     * @param context
     */
    public static void show(Context context, File tempFiles) {
        tempFile = tempFiles;
        show(context, null, true);
    }

    /**
     * show the dialog
     *
     * @param context
     */
    public static void show(Context context) {
        show(context, null, true);
    }


    /**
     * show the dialog
     *
     * @param context    Context
     * @param message    String, show the message to user when isCancel is true.
     * @param cancelable boolean, true is can't dimiss，false is can dimiss
     */
    private static void show(Context context, String message, boolean cancelable) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            return;
        }
        loadDialog = new CameraPhotoDialog(context);
        loadDialog.show();
    }

    /**
     * dismiss the dialog
     */
    public static void dismiss(Context context) {
        try {

            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    loadDialog = null;
                    return;
                }
            }

            if (loadDialog != null && loadDialog.isShowing()) {
                Context loadContext = loadDialog.getContext();
                if (loadContext != null && loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        loadDialog = null;
                        return;
                    }

                }

                loadDialog.dismiss();
                loadDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadDialog = null;
        }
    }

    public File getTempFile() {
        return tempFile;
    }

    public void setTempFile(File tempFile) {
        this.tempFile = tempFile;
    }


    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    private static void startPhotoZoom(Context mContext, Uri uri, int size, int startCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);
        ((Activity) mContext).startActivityForResult(intent, startCode);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    private void setPicToView(Intent picdata, ImageView mImageView, SavePicListener listener) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            if (photo != null) {
                mImageView.setImageBitmap(photo);
                try {
                    FileImage.saveMyBitmap(123 + ".png", photo);
                    listener.saveSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.saveError("异常出现");
                }
            }
        }
        listener.saveError("数据不可为空");
    }


    /**
     * @param
     * @author MarkShuai
     * @name 保存图片的回调监听
     * @time 2017/12/11 19:19
     */
    public interface SavePicListener {
        void saveSuccess();

        void saveError(String errorString);
    }

}
