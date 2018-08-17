/**
 * 
 */
package com.bestdo.bestdoStadiums.control.photo.vedio;

import android.annotation.SuppressLint;
import android.app.Activity;
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
	public void playNetworkVideo(Activity mActivity, String videoPath) {

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

	/**
	 * 
	 * /storage/emulated/0/joke_essay/1486825444475.mp4
	 * 
	 * @param inputVideoPath
	 * @param outputVideoPath
	 */
	private Compressor com;
	private String cmd;

	public void convertMp4(Activity mActivity, String inputVideoPath, String outputVideoPath) {
		// cmd = "-y -i "
		// + inputVideoPath
		// + " -strict -2 -vcodec libx264 -preset ultrafast -crf 24 -acodec aac
		// -ar 44100 -ac 2 -b:a 96k -s 640x352 -aspect 16:9 "
		// + outputVideoPath;
		cmd = "-y -i " + inputVideoPath
				+ " -strict -2 -vcodec copy -preset ultrafast -crf 24 -acodec copy -ar 44100 -ac 2 -b:a 96k -s 640x352 -aspect 16:9 "
				+ outputVideoPath;
		com = new Compressor(mActivity);

		com.loadBinary(new InitListener() {
			@Override
			public void onLoadSuccess() {
				com.execCommand(cmd, new CompressListener() {
					@Override
					public void onExecSuccess(String message) {
						Log.i("success", message);
					}

					@Override
					public void onExecFail(String reason) {
						Log.i("fail", reason);
					}

					@Override
					public void onExecProgress(String message) {
						Log.i("progress", message);
					}
				});
			}

			@Override
			public void onLoadFail(String reason) {
				Log.i("fail", reason);
			}
		});
	}
}
