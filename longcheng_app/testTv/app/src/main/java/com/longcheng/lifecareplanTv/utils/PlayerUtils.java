/**
 *
 */
package com.longcheng.lifecareplanTv.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

/**
 * @author 作者：zoc
 * @date 创建时间：2017-3-10 上午10:15:00
 * @Description 类描述：视频工具类
 */
@SuppressLint("NewApi")
public class PlayerUtils {
    private static volatile PlayerUtils INSTANCE;

    public static PlayerUtils getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (PlayerUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PlayerUtils();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 播放本地视频 "/storage/emulated/0/joke_essay/1486844342617.mp4"
     */
    public void playNativeVideo(Activity mActivity, String videoPath) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type = "video/mp4";
        Uri uri = Uri.parse(videoPath);
        intent.setDataAndType(uri, type);
        mActivity.startActivity(intent);
    }

    /**
     * 播放网络视频
     */
    public void playNetworkVideo(Context mActivity, String videoPath) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type = "video/*";

        Uri uri = Uri.parse(videoPath);
        intent.setDataAndType(uri, type);
        mActivity.startActivity(intent);
    }

    /**
     * 这样会默认用浏览器打开这个URL！
     */
    public void playNetworkVideoDefault(Activity mActivity, String videoPath) {

        Intent intent = new Intent(Intent.ACTION_VIEW);

        Uri uri = Uri.parse(videoPath);
        intent.setData(uri);
        mActivity.startActivity(intent);
    }

    /**
     * 获取视频时长
     *
     * @param videoPath
     * @return
     */
    public String getDuration(String videoPath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(videoPath);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
        return duration;
    }

    /**
     * 获取视频缩略图
     *
     * @param videoPath
     * @return
     */
    @SuppressLint("NewApi")
    public Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }

}
