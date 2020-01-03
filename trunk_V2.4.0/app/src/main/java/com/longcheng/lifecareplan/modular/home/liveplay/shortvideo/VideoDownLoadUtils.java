package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.FileCache;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.tencent.liteav.demo.videouploader.common.view.NumberProgressBar;

import java.io.File;

import cn.finalteam.okhttpfinal.FileDownloadCallback;
import cn.finalteam.okhttpfinal.HttpRequest;

/**
 * 作者：jun on
 * 时间：2020/1/2 16:23
 * 意图：
 */
public class VideoDownLoadUtils {
    File saveFile;

    Activity mActivity;
    String mVideoPath;
    String mCoverImagePath;
    int mVideoDuration;
    Handler mHandler;
    int mHandlerID;

    public boolean loading = false;

    int cancelDowmload = -1;

    public VideoDownLoadUtils(Activity mActivity, Handler mHandler, int mHandlerID) {
        this.mActivity = mActivity;
        this.mHandler = mHandler;
        this.mHandlerID = mHandlerID;
    }

    public void setInit(String mVideoPath, String mCoverImagePath, int mVideoDuration) {
        this.mVideoPath = mVideoPath;
        this.mCoverImagePath = mCoverImagePath;
        this.mVideoDuration = mVideoDuration;
    }

    // 下载具体操作
    public void DOWNLOAD() {
        File appDir = new File(FileCache.path);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        // 下载后的文件名
        int i = mVideoPath.lastIndexOf("/"); // 取的最后一个斜杠后的字符串为名
        String fileName = mVideoPath.substring(i);
        saveFile = new File(appDir, fileName);
        if (saveFile.exists()) {
            // 如果已经存在, 就不下载了, 去播放
            ToastUtils.showToast("已保存相册文件夹");
        } else {
            loading = true;
            showPopupWindow(cancelDowmload);
//            ToastUtils.showToast("视频下载中…");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpRequest.download(mVideoPath, saveFile, new FileDownloadCallback() {

                        //开始下载
                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        //下载进度
                        @Override
                        public void onProgress(int progress, long networkSpeed) {
                            super.onProgress(progress, networkSpeed);
                            Log.e("onProgress", "progress=" + progress);
                            if (uploader_pb_loading != null)
                                uploader_pb_loading.setProgress(progress);
                        }

                        //下载失败
                        @Override
                        public void onFailure() {
                            super.onFailure();
                            loading = false;
                            ToastUtils.showToast("下载失败");
                            if (selectDialog != null) {
                                selectDialog.dismiss();
                                uploader_pb_loading.setProgress(0);
                            }
                            if (saveFile != null) {
                                saveFile.delete();
                            }
                        }

                        //下载完成（下载成功）
                        @Override
                        public void onDone() {
                            super.onDone();
                            loading = false;
                            ToastUtils.showToast("已保存相册文件夹");
                            if (selectDialog != null) {
                                selectDialog.dismiss();
                                uploader_pb_loading.setProgress(0);
                            }
                            mHandler.sendEmptyMessage(mHandlerID);
                        }
                    });
                }
            }).start();
        }
    }

    /**
     * 插入视频缩略图
     *
     * @param videoPath
     * @param coverPath
     */
    public void insertVideoThumb(String videoPath, String coverPath) {
        //以下是查询上面插入的数据库Video的id（用于绑定缩略图）
        //根据路径查询
        Cursor cursor = mActivity.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Thumbnails._ID},//返回id列表
                String.format("%s = ?", MediaStore.Video.Thumbnails.DATA), //根据路径查询数据库
                new String[]{videoPath}, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails._ID));
                //查询到了Video的id
                ContentValues thumbValues = new ContentValues();
                thumbValues.put(MediaStore.Video.Thumbnails.DATA, coverPath);//缩略图路径
                thumbValues.put(MediaStore.Video.Thumbnails.VIDEO_ID, videoId);//video的id 用于绑定
                //Video的kind一般为1
                thumbValues.put(MediaStore.Video.Thumbnails.KIND,
                        MediaStore.Video.Thumbnails.MINI_KIND);
                //只返回图片大小信息，不返回图片具体内容
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeFile(coverPath, options);
                if (bitmap != null) {
                    thumbValues.put(MediaStore.Video.Thumbnails.WIDTH, bitmap.getWidth());//缩略图宽度
                    thumbValues.put(MediaStore.Video.Thumbnails.HEIGHT, bitmap.getHeight());//缩略图高度
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
                mActivity.getContentResolver().insert(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, //缩略图数据库
                        thumbValues);
            }
            cursor.close();
        }
    }


    /**
     * 是否添加到相册
     *
     * @return
     */
    public ContentValues initCommonContentValues() {
        ContentValues values = new ContentValues();
        long currentTimeInSeconds = System.currentTimeMillis();
        values.put(MediaStore.MediaColumns.TITLE, saveFile.getName());
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, saveFile.getName());
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, currentTimeInSeconds);
        values.put(MediaStore.MediaColumns.DATE_ADDED, currentTimeInSeconds);
        values.put(MediaStore.MediaColumns.DATA, saveFile.getAbsolutePath());
        values.put(MediaStore.MediaColumns.SIZE, saveFile.length());

        values.put(MediaStore.Video.VideoColumns.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.VideoColumns.DURATION, mVideoDuration);//时长
        mActivity.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        insertVideoThumb(saveFile.getPath(), mCoverImagePath);
        return values;
    }


    MyDialog selectDialog;
    NumberProgressBar uploader_pb_loading;
    public boolean isCancelPublish = false;
    TextView uploader_tv_msg;
    ImageView uploader_iv_stop;

    public void showPopupWindow(int mHandlerID_) {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mActivity, R.style.dialog, R.layout.live_videouploader_progress);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效

            uploader_tv_msg = (TextView) selectDialog.findViewById(R.id.uploader_tv_msg);
            uploader_iv_stop = (ImageView) selectDialog.findViewById(R.id.uploader_iv_stop);
            uploader_pb_loading = (NumberProgressBar) selectDialog.findViewById(R.id.uploader_pb_loading);
            uploader_pb_loading.setReachedBarColor(mActivity.getResources().getColor(R.color.red));
            uploader_pb_loading.setProgressTextColor(mActivity.getResources().getColor(R.color.white));
            uploader_iv_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mHandlerID_ == cancelDowmload) {
                        HttpRequest.cancel(mVideoPath);
                        HttpRequest.delete(mVideoPath);
                        if (saveFile != null) {
                            saveFile.delete();
                        }
                        loading = false;
                    } else {
                        mHandler.sendEmptyMessage(mHandlerID_);
                        isCancelPublish = true;
                        uploader_pb_loading.setProgress(0);
                    }
                    selectDialog.dismiss();
                }
            });
        } else {
            selectDialog.show();
        }
        isCancelPublish = false;
        uploader_pb_loading.setProgress(0);
        if (mHandlerID_ == cancelDowmload) {
            uploader_tv_msg.setText("视频下载中...");
            uploader_iv_stop.setVisibility(View.GONE);
        } else {
            uploader_tv_msg.setText("发布中...");
            uploader_iv_stop.setVisibility(View.VISIBLE);
        }
    }
}
