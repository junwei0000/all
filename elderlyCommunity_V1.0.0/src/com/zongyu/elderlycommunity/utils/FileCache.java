package com.zongyu.elderlycommunity.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午3:29:05
 * @Description 类描述：文件缓存
 */
public class FileCache {

	private File cacheDir;

	/**
	 * 初始化创建文件缓存目录
	 * 
	 * @param context
	 */
	public FileCache(Context context) {
		// 如果有SD卡则在SD卡中建一个LazyList的目录存放缓存的图片
		// 没有SD卡就放在系统的缓存目录中
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"bestdo");
		} else {
			cacheDir = context.getCacheDir();
		}
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	/**
	 * 根据url得到文件
	 * 
	 * @param url
	 * @return
	 */
	public File getFile(String url) {
		// 将url的hashCode作为缓存的文件名
		String filename = ConfigUtils.getInstance().MD5(url);
		// String filename = String.valueOf(url.hashCode());
		// String filename = url.substring(url.lastIndexOf("/") + 1)+".txt";
		File f = new File(cacheDir, filename);
		return f;
	}

	/**
	 * decode这个图片并且按比例缩放以减少内存消耗，虚拟机对每张图片的缓存大小也是有限制的
	 * 
	 * @param f
	 * @return
	 */
	public Bitmap decodeFile(File f, int showWidth, int showHeight) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			if (showHeight != 0 && showWidth != 0) {
			} else {
				int REQUIRED_SIZE = 210;
				int width_tmp = o.outWidth, height_tmp = o.outHeight;
				while (true) {
					if (width_tmp / 2 < REQUIRED_SIZE
							|| height_tmp / 2 < REQUIRED_SIZE)
						break;
					width_tmp /= 2;
					height_tmp /= 2;
					scale *= 2;
				}
			}
			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			o2.inPurgeable = true;
			o2.inInputShareable = true;
			Bitmap mBitmap = BitmapFactory.decodeStream(new FileInputStream(f),
					null, o2);
			if (mBitmap != null && showHeight != 0 && showWidth != 0) {
				mBitmap = zoomImg(mBitmap, showWidth, showHeight);
			}
			return mBitmap;
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	/**
	 * 处理图片
	 * 
	 * @param bm
	 *            所要转换的bitmap
	 * @param newWidth新的宽
	 * @param newHeight新的高
	 * @return 指定宽高的bitmap
	 */
	public Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

	/**
	 * 清除缓存
	 */
	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

}
