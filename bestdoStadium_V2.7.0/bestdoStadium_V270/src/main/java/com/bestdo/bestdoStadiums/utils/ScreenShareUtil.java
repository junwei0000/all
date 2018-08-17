package com.bestdo.bestdoStadiums.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 进行截屏工具类
 */
public class ScreenShareUtil {
	/**
	 * 进行截取屏幕
	 *
	 * @param pActivity
	 * @return bitmap
	 */
	public static Bitmap takeScreenShot(Activity pActivity) {
		View view = pActivity.getWindow().getDecorView();
		return takeScreenShot(view, true);
	}

	/**
	 * 进行截取屏幕
	 *
	 * @param pActivity
	 * @return bitmap
	 */
	public static Bitmap takeScreenShot(View view, boolean curTop) {
		Bitmap bitmap = null;
		view.setDrawingCacheEnabled(true);
		// 如果绘图缓存无法，强制构建绘图缓存
		view.buildDrawingCache();
		// 返回这个缓存视图
		bitmap = view.getDrawingCache();
		// 获取状态栏高度
		Rect frame = new Rect();
		// 测量屏幕宽和高
		view.getWindowVisibleDisplayFrame(frame);
		int stautsHeight = frame.top;
		if (!curTop) {
			stautsHeight = 0;
		}
		Log.d("jiangqq", "状态栏的高度为:" + stautsHeight);

		int width = view.getWidth();
		int height = view.getHeight();
		// 根据坐标点和需要的宽和高创建bitmap
		bitmap = Bitmap.createBitmap(bitmap, 0, stautsHeight, width, height - stautsHeight);
		view.setDrawingCacheEnabled(false);
		view.buildDrawingCache(false);
		return bitmap;
	}

	/**
	 * 保存图片到sdcard中
	 *
	 * @param pBitmap
	 */
	private static boolean savePic(Bitmap pBitmap, String strName) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(strName);
			if (null != fos) {
				pBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
				fos.flush();
				fos.close();
				return true;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pBitmap.recycle();
			pBitmap = null;
		}
		return false;
	}

	/**
	 * 截图
	 *
	 * @param pActivity
	 * @return 截图并且保存sdcard成功返回true，否则返回false
	 */
	public static String shotBitmap(Activity pActivity) {
		File cacheDir = pActivity.getCacheDir();
		String savePath = cacheDir.getPath() + File.separator + System.currentTimeMillis() + ".png";
		System.out.println("-----" + savePath);
		savePic(takeScreenShot(pActivity), savePath);
		return savePath;
	}

	/**
	 * 截图
	 *
	 * @param pActivity
	 * @return 截图并且保存sdcard成功返回true，否则返回false
	 */
	public static String shotBitmap(View view) {
		File cacheDir = App.getContext().getCacheDir();
		String savePath = cacheDir.getPath() + File.separator + System.currentTimeMillis() + ".png";
		System.out.println("-----" + savePath);
		savePic(takeScreenShot(view, false), savePath);
		return savePath;
	}

}
