/**
 * 
 */
package com.bestdo.bestdoStadiums.control.photo.vedio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.photo.activity.ImageFile;
import com.bestdo.bestdoStadiums.control.photo.util.Bimp;
import com.bestdo.bestdoStadiums.control.photo.util.ImageItem;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.MyDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-10-11 上午10:36:33
 * @Description 类描述：上传图片
 */
public class AblumUtils {

	private Activity mContext;
	Handler mHandler;
	int mHandlerId;

	/**
	 * 是否选择相册
	 */
	public Boolean selectAblumStatus = false;
	/**
	 * 是否选择视频
	 */
	public Boolean selectVedioStatus = false;

	public AblumUtils(Activity mContext, Handler mHandler, int mHandlerId) {
		super();
		this.mContext = mContext;
		this.mHandler = mHandler;
		this.mHandlerId = mHandlerId;
	}

	/**
	 * 
	 */
	public AblumUtils() {
		super();
	}

	/**
	 * **************************上传头像*******************************************
	 * **
	 */
	private MyDialog tEventDialog;

	public void onAddAblumClick() {
		file_name = ConfigUtils.getInstance().getRandomNumber(16) + ".jpg";
		if (tEventDialog == null) {

			tEventDialog = new MyDialog(mContext, R.style.dialog, R.layout.user_account_ablum_dialog);// 创建Dialog并设置样式主题
			Window dialogWindow = tEventDialog.getWindow();
			// WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
			dialogWindow.setWindowAnimations(R.style.showAnDialog);
			tEventDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
			tEventDialog.show();
			TextView useraccount_ablum_tv_photos = (TextView) tEventDialog
					.findViewById(R.id.useraccount_ablum_tv_photos);
			TextView useraccount_ablum_tv_selects = (TextView) tEventDialog
					.findViewById(R.id.useraccount_ablum_tv_selects);
			TextView useraccount_ablum_tv_cancel = (TextView) tEventDialog
					.findViewById(R.id.useraccount_ablum_tv_cancel);
			TextView useraccount_ablum_tv_vedio = (TextView) tEventDialog.findViewById(R.id.useraccount_ablum_tv_vedio);
			useraccount_ablum_tv_vedio.setVisibility(View.VISIBLE);
			LinearLayout pop_layout = (LinearLayout) tEventDialog.findViewById(R.id.pop_layout);
			pop_layout.setLayoutParams(new LinearLayout.LayoutParams(
					ConfigUtils.getInstance().getPhoneWidHeigth(mContext).widthPixels, LayoutParams.WRAP_CONTENT));
			useraccount_ablum_tv_cancel.setOnClickListener(mABlumClickListener);
			useraccount_ablum_tv_photos.setOnClickListener(mABlumClickListener);
			useraccount_ablum_tv_vedio.setOnClickListener(mABlumClickListener);
			useraccount_ablum_tv_selects.setOnClickListener(mABlumClickListener);
			pop_layout.setOnClickListener(mABlumClickListener);
		} else {
			tEventDialog.show();
		}
	}

	OnClickListener mABlumClickListener = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.pop_layout:
			case R.id.useraccount_ablum_tv_cancel:
				selectAblumStatus = false;
				break;
			case R.id.useraccount_ablum_tv_photos:
				selectAblumStatus = false;
				camera(v);
				break;
			case R.id.useraccount_ablum_tv_selects:
				// gallery(v);
				selectAblumStatus = true;
				Intent intent = new Intent(mContext, ImageFile.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				mContext.startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, mContext);
				break;
			case R.id.useraccount_ablum_tv_vedio:
				if (Bimp.tempSelectBitmap.size() == 1)
					chooseVideo();
				else {
					CommonUtils.getInstance().initToast(mContext, "图片和视频不能同时上传哦");
				}
				break;
			default:
				break;
			}
			tEventDialog.dismiss();
		}
	};

	/**
	 * 选择本地视频
	 */
	private void chooseVideo() {
		Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
		// intent.setType("image/*");
		// intent.setType("audio/*"); //选择音频
		// intent.setType("video/*;image/*");//同时选择视频和图片
		intent.setType("video/*"); // 选择视频 （mp4 3gp 是android支持的视频格式）
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
		mContext.startActivityForResult(intent, RESULTVEDIO);
	}

	/** 头像名称 */
	private static String file_name = "temp_photo.jpg";
	// private Bitmap bitmap;
	private static File tempFile;
	public static String mkdirsName = "bestdo";
	private Uri pictrueuri;

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
		file_name = ConfigUtils.getInstance().getRandomNumber(16) + ".jpg";
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
		if (hasSdcard()) {
			File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), mkdirsName);
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

	/**
	 * 剪切图片
	 * 
	 * @param uri
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 4);
		intent.putExtra("aspectY", 3);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 400);
		intent.putExtra("outputY", 300);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		mContext.startActivityForResult(intent, RESULTCROP);
	}

	/**
	 * 不裁剪直接使用，处理图片文件大小
	 */
	private void unCrop() {
		if (tempFile.length() > 0) {
			saveBitmapFile(setFileToBitmap(tempFile));
			Message msgMessage = new Message();
			msgMessage.obj = tempFile;
			msgMessage.what = mHandlerId;
			mHandler.sendMessage(msgMessage);
			msgMessage = null;
		}
	}

	/**
	 * 读取照片exif信息中的旋转角度
	 * 
	 * @param path
	 *            照片路径
	 * @return角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static Bitmap toturn(Bitmap img, int jioadu) {
		Matrix matrix = new Matrix();
		matrix.postRotate(jioadu); /* +90 翻转90度 */
		int width = img.getWidth();
		int height = img.getHeight();
		img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
		return img;
	}

	public final int RESULTCAMERA = 11;// 拍照
	public final int RESULTGALLERY = 22;// 相册
	public final int RESULTCROP = 33;// 剪切
	public final int RESULTVEDIO = 10;// 视频

	public void setResult(int requestCode, int resultCode, Intent data) {
		System.err.println("onAResultonAResultonAResultonAResult");
		if (requestCode == RESULTGALLERY && resultCode == Activity.RESULT_OK) {
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				System.out.println("uri = " + uri);
				String picturePath = FilePathResolver.getPath(mContext, uri);
				tempFile = new File(picturePath);
				unCrop();
			}

		} else if (requestCode == RESULTCAMERA && resultCode == Activity.RESULT_OK) {
			if (hasSdcard()) {
				mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, pictrueuri));
				// ----------不使用裁剪-------------
				File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), mkdirsName);
				if (!cacheDir.exists()) {
					cacheDir.mkdirs();
				}
				tempFile = new File(cacheDir, file_name);
				addEditBimp(mContext, tempFile);
				unCrop();
				// crop(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(mContext, "未找到存储卡，无法存储照片！", 0).show();
			}

		} else if (requestCode == RESULTCROP) {
			try {
				// bitmap = data.getParcelableExtra("data");
				// saveBitmapFile(bitmap);
				// Message msgMessage = new Message();
				// msgMessage.obj = tempFile;
				// msgMessage.what = mHandlerId;
				// mHandler.sendMessage(msgMessage);
				// msgMessage = null;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 添加单张图片
	 * 
	 * @param mContext
	 * @param tempFile
	 */
	public void addEditBimp(Context mContext, File tempFile) {
		Bitmap bmp;
		ImageItem mImageItem = new ImageItem();
		if (tempFile == null) {
			Resources res = mContext.getResources();
			bmp = BitmapFactory.decodeResource(res, R.drawable.campaignpublishedit_img_addablum);
			mImageItem.setBitmap(bmp);
			File mFile = saveBitmapFile(bmp);
			mImageItem.imagePath = mFile.getPath();
			Bimp.tempSelectBitmap.add(mImageItem);
		} else {
			bmp = setFileToBitmap(tempFile);
			Bimp.tempSelectBitmap.remove(Bimp.tempSelectBitmap.size() - 1);
			mImageItem.setBitmap(bmp);
			mImageItem.setImagePath(tempFile.getPath());
			Bimp.tempSelectBitmap.add(mImageItem);
			addEditBimp(mContext, null);
		}
	}

	/**
	 * 添加多张图片
	 * 
	 * @param mContext
	 * @param selectedDataList
	 */
	public static void addMoreEditBimp(Context mContext, ArrayList<ImageItem> selectedDataList) {
		if (selectedDataList == null) {
			ImageItem mImageItem = new ImageItem();
			Resources res = mContext.getResources();
			Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.campaignpublishedit_img_addablum);
			mImageItem.setBitmap(bmp);
			File mFile = saveBitmapFile(bmp);
			mImageItem.imagePath = mFile.getPath();
			Bimp.tempSelectBitmap.add(mImageItem);
		} else {
			Bimp.tempSelectBitmap.remove(Bimp.tempSelectBitmap.size() - 1);
			Bimp.tempSelectBitmap.addAll(selectedDataList);
			addMoreEditBimp(mContext, null);
		}
	}

	/**
	 * Bitmap对象保存味图片文件
	 * 
	 * @param bitmap
	 */
	public static File saveBitmapFile(Bitmap bitmap) {
		try {
			File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), mkdirsName);
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			file_name = ConfigUtils.getInstance().getRandomNumber(16) + ".jpg";
			tempFile = new File(cacheDir, file_name);// 将要保存图片的路径
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempFile;
	}

	/**
	 * File文件转Bitmap
	 * 
	 * @param ablumFile
	 * @return
	 */
	// public Bitmap setFileToBitmap(File ablumFile) {
	// Bitmap bitmap = BitmapFactory.decodeFile(ablumFile.getPath(),
	// getBitmapOption(2)); // 将图片的长和宽缩小味原来的1/2
	// return bitmap;
	// }
	//
	// private Options getBitmapOption(int inSampleSize) {
	// System.gc();
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inPurgeable = true;
	// options.inSampleSize = inSampleSize;
	// return options;
	// }
	/**
	 * decode这个图片并且按比例缩放以减少内存消耗，虚拟机对每张图片的缓存大小也是有限制的
	 * 
	 * @param f
	 * @return
	 */
	public static Bitmap setFileToBitmap(File f) {
		Bitmap mBitmap = null;
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			int REQUIRED_SIZE = 550;// 上传大小
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			while (true) {
				if (width_tmp / scale <= REQUIRED_SIZE || height_tmp / scale <= REQUIRED_SIZE)
					break;
				scale += 1;
			}
			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			o2.inPurgeable = true;
			o2.inInputShareable = true;
			mBitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
			int jiaodu = readPictureDegree(f.getPath());
			mBitmap = toturn(mBitmap, jiaodu);
			return mBitmap;
		} catch (FileNotFoundException e) {
		}
		return null;
	}
	/**
	 * ***********************************************************************
	 */
}
