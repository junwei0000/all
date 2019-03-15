package com.longcheng.lifecareplan.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class FileImage {

	// 图片SDCard缓存路径
	public static String path = Environment.getExternalStorageDirectory()
			.toString() + "/huayou/";

	public void saveFile(Bitmap bitmap, String imagename) {

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(CommonUtilImage.getPath() + imagename);
			if (fos != null) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void saveMyBitmap(String bitName, Bitmap mBitmap)
			throws Exception {

		if (CommonUtilImage.hasSDCard()) {

			File f = getFilePath(path, bitName);

			FileOutputStream fOut = null;

			fOut = new FileOutputStream(f);

			mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			Log.i("xiaoqiang", "image is save");

			fOut.flush();

			fOut.close();

		}
	}

	public static Bitmap useTheImage(String imagename) {

		return BitmapFactory.decodeFile(CommonUtilImage.getPath() + imagename);
	}

	public static void execFile(String imageUrl) {

		String imageSDCardPath = path + imageUrl;
		File file = new File(imageSDCardPath);
		if (file.exists()) {
			Log.i("xiaoqiang", "dele");
			file.delete();
		}
	}

	public static File getFilePath(String filePath, String fileName) {
		File file = null;
		makeRootDirectory(filePath);
		try {
			file = new File(filePath + fileName);
			Log.i("xiaoqiang", "new  yige  file" + file.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static void makeRootDirectory(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {

		}
	}

	public static Bitmap loadBitmap(String url, int pos) throws Exception {

		URL m;
		InputStream is = null;
		try {
			if (url != null) {
				m = new URL(url);
				// is = (InputStream) m.getContent();
				is = m.openStream();
				BufferedInputStream bis = null;
				if (is != null) {
					bis = new BufferedInputStream(is);
				} else {

					return null;
				}

				// 鏍囪鍏跺疄浣嶇疆锛屼緵reset鍙傝?
				// bis.mark(0);

				BitmapFactory.Options opts = new BitmapFactory.Options();
				// true,鍙槸璇诲浘鐗囧ぇ灏忥紝涓嶇敵璇穊itmap鍐呭瓨
				// opts.inJustDecodeBounds = true;
				// BitmapFactory.decodeStream(bis, null, opts);
				//
				// int size = (opts.outWidth * opts.outHeight);
				// int zoomRate = 0;
				// if( size > 600*600*4){
				// zoomRate = 16;
				// //zommRate缂╂斁姣旓紝鏍规嵁鎯呭喌鑷璁惧畾锛屽鏋滀负2鍒欑缉鏀句负鍘熸潵鐨?/2锛屽鏋滀负1涓嶇缉鏀?
				// opts.inSampleSize = zoomRate;
				// }else{
				//
				// opts.inSampleSize =2;
				// }
				opts.inSampleSize = pos;
				// 璁句负false锛岃繖娆′笉鏄璇诲彇鍥剧墖澶у皬锛岃?鏄繑鍥炵敵璇峰唴瀛橈紝bitmap鏁版嵁
				opts.inJustDecodeBounds = false;
				// 缂撳啿杈撳叆娴佸畾浣嶈嚦澶撮儴锛宮ark()
				// bis.reset();
				Bitmap bm = BitmapFactory.decodeStream(bis, null, opts);

				bis.close();
				is.close();

				return (bm == null) ? null : bm;
			}
		} catch (MalformedURLException e1) {

			e1.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}
}
