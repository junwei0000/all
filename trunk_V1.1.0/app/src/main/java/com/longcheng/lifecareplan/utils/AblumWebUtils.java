/**
 *
 */
package com.longcheng.lifecareplan.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.widget.TextView;
import android.widget.Toast;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.myview.MyDialog;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 作者：zoc
 * @Description 类描述：上传图片--不剪切
 */
public class AblumWebUtils {

    private Activity mContext;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;

    public AblumWebUtils(Activity mContext, ValueCallback<Uri> mUploadMessage, ValueCallback<Uri[]> mUploadCallbackAboveL) {
        super();
        this.mContext = mContext;
        this.mUploadMessage = mUploadMessage;
        this.mUploadCallbackAboveL = mUploadCallbackAboveL;
        cacheDir = new File(Environment.getExternalStorageDirectory(), mkdirsName);

    }

    public void setmUploadMessage(ValueCallback<Uri> mUploadMessage) {
        this.mUploadMessage = mUploadMessage;
    }

    public void setmUploadCallbackAboveL(ValueCallback<Uri[]> mUploadCallbackAboveL) {
        this.mUploadCallbackAboveL = mUploadCallbackAboveL;
    }

    /**
     *
     */
    public AblumWebUtils() {
        super();
    }

    /**
     * **************************上传头像*******************************************
     * **
     */
    public MyDialog selectDialog;

    public void onAddAblumClick() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_ablum);// 创建Dialog并设置样式主题
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
            TextView ablum_tv_photograph = (TextView) selectDialog.findViewById(R.id.ablum_tv_photograph);
            TextView ablum_tv_select = (TextView) selectDialog.findViewById(R.id.ablum_tv_select);
            TextView ablum_tv_cancel = (TextView) selectDialog.findViewById(R.id.ablum_tv_cancel);
            ablum_tv_cancel.setOnClickListener(mABlumClickListener);
            ablum_tv_photograph.setOnClickListener(mABlumClickListener);
            ablum_tv_select.setOnClickListener(mABlumClickListener);
            selectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    onActivityCallBackCancel();
                }
            });
        } else {
            selectDialog.show();
        }
    }

    OnClickListener mABlumClickListener = new OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ablum_tv_cancel:
                    onActivityCallBackCancel();
                    selectDialog.dismiss();
                    break;
                case R.id.ablum_tv_photograph:
                    camera(v);
                    break;
                case R.id.ablum_tv_select:
                    gallery(v);
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * 头像名称
     */
    private static String file_name;
    public static File tempFile;
    public static String mkdirsName = "longcheng";
    private Uri pictrueuri;
    File cacheDir;
    Uri mUriPath;

    /**
     * 手机相册
     *
     * @param view
     */
    public void gallery(View view) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        mContext.startActivityForResult(intent, RESULTGALLERY);
    }

    /**
     * 拍照
     *
     * @param view
     */
    public void camera(View view) {
        file_name = ConfigUtils.getINSTANCE().getRandomNumber(16) + ".jpg";
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            pictrueuri = Uri.fromFile(new File(cacheDir, file_name));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, pictrueuri);
        }
        mContext.startActivityForResult(intent, RESULTCAMERA);
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public final int RESULTCAMERA = 11;// 拍照
    public final int RESULTGALLERY = 22;// 相册

    public void setResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULTGALLERY && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                System.out.println("uri = " + uri);
                String picturePath = FilePathResolver.getPath(mContext, uri);
                tempFile = new File(picturePath);
                onActivityCallBack(uri);
            }

        } else if (requestCode == RESULTCAMERA && resultCode == Activity.RESULT_OK) {
            if (hasSdcard()) {
                mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, pictrueuri));
                File cacheDir = new File(Environment.getExternalStorageDirectory(), mkdirsName);
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs();
                }
                tempFile = new File(cacheDir, file_name);
                onActivityCallBack(Uri.fromFile(tempFile));
            } else {
                ToastUtils.showToast("未找到存储卡，无法存储照片！");
            }

        }
    }


    /**
     * 回调到网页
     *
     * @param uri
     */
    public void onActivityCallBack(Uri uri) {
        if (mUploadCallbackAboveL != null) {
            Uri[] uris = new Uri[]{uri};
            mUploadCallbackAboveL.onReceiveValue(uris);
            mUploadCallbackAboveL = null;
        } else if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(uri);
            mUploadMessage = null;
        } else {
            Toast.makeText(mContext, "图片上传失败", Toast.LENGTH_LONG).show();
        }
    }

    private void onActivityCallBackCancel() {
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
        }
        if (mUploadCallbackAboveL != null) {
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }
    }

}
