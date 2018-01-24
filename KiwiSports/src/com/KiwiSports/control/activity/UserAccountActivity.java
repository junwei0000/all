package com.KiwiSports.control.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.entity.mime.content.FileBody;

import com.KiwiSports.R;
import com.KiwiSports.business.UserAccountUpdateAblumBusiness;
import com.KiwiSports.business.UserAccountUpdateBusiness;
import com.KiwiSports.business.VenuesHobbyBusiness;
import com.KiwiSports.business.UserAccountUpdateAblumBusiness.GetAccountUpdateAblumCallback;
import com.KiwiSports.business.UserAccountUpdateBusiness.GetAccountUpdateCallback;
import com.KiwiSports.business.VenuesHobbyBusiness.GetVenuesHobbyCallback;
import com.KiwiSports.control.adapter.VenuesHobbyAdapter;
import com.KiwiSports.model.HobbyInfo;
import com.KiwiSports.utils.CircleImageView;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.Constans;
import com.KiwiSports.utils.ImageLoader;
import com.KiwiSports.utils.LanguageUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午2:32:14
 * @Description 类描述：我的账户信息
 */
public class UserAccountActivity extends BaseActivity {
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_back;
	private RelativeLayout useraccount_relay_avatar, useraccount_relay_nickname, useraccount_relay_sex;
	private CircleImageView useraccount_iv_avatar;
	private TextView useraccount_tv_nickname, useraccount_tv_sex;

	private String uid;
	private SharedPreferences bestDoInfoSharedPrefs;
	private View parent;
	private String nick_name;
	private String album_url;
	private int sex;
	private String token;
	private String access_token;
	private RelativeLayout useraccount_relay_sporttype;
	private TextView useraccount_tv_sporttype;
	private String hobby;
	protected HashMap<String, String> mHobbyMap;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.useraccount_relay_avatar:
			onDateClick();
			break;
		case R.id.useraccount_relay_nickname:
			toUpdateInfo();
			break;
		case R.id.useraccount_relay_sex:
			toUpdateSex();
			break;
		case R.id.useraccount_relay_sporttype:
			toUpdateHobby();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_account);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		parent = (LinearLayout) findViewById(R.id.useraccount_layout);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		useraccount_relay_avatar = (RelativeLayout) findViewById(R.id.useraccount_relay_avatar);
		useraccount_iv_avatar = (CircleImageView) findViewById(R.id.useraccount_iv_avatar);
		useraccount_relay_nickname = (RelativeLayout) findViewById(R.id.useraccount_relay_nickname);
		useraccount_tv_nickname = (TextView) findViewById(R.id.useraccount_tv_nickname);
		useraccount_relay_sex = (RelativeLayout) findViewById(R.id.useraccount_relay_sex);
		useraccount_tv_sex = (TextView) findViewById(R.id.useraccount_tv_sex);
		useraccount_relay_sporttype = (RelativeLayout) findViewById(R.id.useraccount_relay_sporttype);
		useraccount_tv_sporttype = (TextView) findViewById(R.id.useraccount_tv_sporttype);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);

		useraccount_relay_avatar.setOnClickListener(this);
		useraccount_relay_nickname.setOnClickListener(this);
		useraccount_relay_sex.setOnClickListener(this);
		useraccount_relay_sporttype.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		pagetop_tv_name.setText(getString(R.string.user_account_title));
		getInfo();
	}

	private void toUpdateInfo() {
		Intent intent = new Intent(this, UserAccountUpdateActivity.class);
		intent.putExtra("nick_name", nick_name);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		CommonUtils.getInstance().setPageIntentAnim(intent, this);
	}

	private void toUpdateSex() {
		Intent intent = new Intent(this, UserAccountUpdateSexActivity.class);
		intent.putExtra("sex", sex);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		CommonUtils.getInstance().setPageIntentAnim(intent, this);
	}

	private void toUpdateHobby() {
		Intent intent = new Intent(this, UserAccountHobbyActivity.class);
		intent.putExtra("hobby", hobby);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		CommonUtils.getInstance().setPageIntentAnim(intent, this);
	}

	private void getInfo() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		nick_name = bestDoInfoSharedPrefs.getString("nick_name", "");
		album_url = bestDoInfoSharedPrefs.getString("album_url", "");
		sex = bestDoInfoSharedPrefs.getInt("sex", 1);
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");
		hobby = bestDoInfoSharedPrefs.getString("hobby", "");
		useraccount_tv_nickname.setText(nick_name);
		if (sex == Constans.getInstance().SEX_MALE) {
			useraccount_tv_sex.setText(getString(R.string.useraccount_sex_tv_male));
		} else {
		}
		if (!TextUtils.isEmpty(album_url)) {
			ImageLoader asyncImageLoader = new ImageLoader(context, "headImg");
			asyncImageLoader.DisplayImage(album_url, useraccount_iv_avatar);
		} else {
			useraccount_iv_avatar.setBackgroundResource(R.drawable.ic_launcher);
		}
		if (!LanguageUtil.idChLanguage(context)) {
			useraccount_tv_sporttype.setText(hobby);
		} else {
			getHobby();

		}
	}

	protected void getHobby() {
		mhashmap = new HashMap<String, String>();
		Log.e("TESTLOG", "------------hobby------------" + hobby);
		new VenuesHobbyBusiness(this, mhashmap, new GetVenuesHobbyCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mHobbyMap = (HashMap<String, String>) dataMap.get("mHobbyMap");
					}
				}
				StringBuffer showhobby = new StringBuffer();
				if (mHobbyMap != null && mHobbyMap.size() > 0 && !TextUtils.isEmpty(hobby)) {
					String[] ss = hobby.split(",");
					for (int i = 0; i < ss.length; i++) {
						String z = mHobbyMap.get(ss[i]);
						showhobby.append(z + ",");
					}
					String showhobby_ = showhobby.substring(0, showhobby.length() - 1);
					useraccount_tv_sporttype.setText(showhobby_);
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	private HashMap<String, String> mhashmap;
	private ProgressDialog mDialog;

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(this);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processUpdateInfo() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("file_name", tempFile.getName());
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("type", "image/jpeg");
		mhashmap.put("size", tempFile.length() + "");
		Log.e("mhashmap----", mhashmap.toString());
		new UserAccountUpdateAblumBusiness(this, mhashmap, tempFile, new GetAccountUpdateAblumCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						album_url = (String) dataMap.get("ablum");
						if (!TextUtils.isEmpty(album_url)) {
							mHandler.sendEmptyMessage(SAVEABLUM);
						}
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});
	}

	private void saveAblum() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("album_url", album_url + "");
		System.err.println(mhashmap);
		new UserAccountUpdateBusiness(this, "saveablum", mhashmap, new GetAccountUpdateCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance()
								.getBestDoInfoSharedPrefs(context);
						Editor bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
						bestDoInfoEditor.putString("album_url", album_url);
						bestDoInfoEditor.commit();
						mHandler.sendEmptyMessage(UPDATESUCCESS);
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		getInfo();
	}

	private void doBack() {
		if (datePopWindow != null && datePopWindow.isShowing()) {
			datePopWindow.dismiss();
		} else {
			finish();
			CommonUtils.getInstance().setPageBackAnim(this);
		}
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doBack();
		}
		return false;
	}

	/**
	 * **************************上传头像*******************************************
	 * **
	 */
	PopupWindow datePopWindow;

	private void onDateClick() {
		if (datePopWindow == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View mMenuView = layoutInflater.inflate(R.layout.user_account_ablum_dialog, null);
			TextView useraccount_ablum_tv_photos = (TextView) mMenuView.findViewById(R.id.useraccount_ablum_tv_photos);
			TextView useraccount_ablum_tv_selects = (TextView) mMenuView
					.findViewById(R.id.useraccount_ablum_tv_selects);
			TextView useraccount_ablum_tv_cancel = (TextView) mMenuView.findViewById(R.id.useraccount_ablum_tv_cancel);
			LinearLayout pop_layout = (LinearLayout) mMenuView.findViewById(R.id.pop_layout);
			datePopWindow = new PopupWindow(mMenuView, WindowManager.LayoutParams.FILL_PARENT,
					WindowManager.LayoutParams.FILL_PARENT);
			datePopWindow.setBackgroundDrawable(new BitmapDrawable());
			datePopWindow.setAnimationStyle(R.style.MyDialogStyleBottom);
			useraccount_ablum_tv_cancel.setOnClickListener(mABlumClickListener);
			useraccount_ablum_tv_photos.setOnClickListener(mABlumClickListener);
			useraccount_ablum_tv_selects.setOnClickListener(mABlumClickListener);
			pop_layout.setOnClickListener(mABlumClickListener);
		}
		final Window window = getWindow();
		final WindowManager.LayoutParams lp = window.getAttributes();
		// 设置透明度为0.3
		lp.alpha = 0.4f;
		window.setAttributes(lp);
		datePopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		datePopWindow.setOnDismissListener(new OnDismissListener() {
			public void onDismiss() {
				lp.alpha = 1.0f;
				window.setAttributes(lp);
			}
		});
	}

	OnClickListener mABlumClickListener = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.pop_layout:
			case R.id.useraccount_ablum_tv_cancel:
				break;
			case R.id.useraccount_ablum_tv_photos:
				camera(v);
				break;
			case R.id.useraccount_ablum_tv_selects:
				gallery(v);
				break;
			default:
				break;
			}
			datePopWindow.dismiss();
		}
	};

	/** 头像名称 */
	private String file_name = "temp_photo.jpg";
	private Bitmap bitmap;
	private File tempFile;

	/**
	 * 手机相册
	 * 
	 * @param view
	 */
	public void gallery(View view) {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, RESULTGALLERY);
	}

	/**
	 * 拍照
	 * 
	 * @param view
	 */
	public void camera(View view) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
		if (hasSdcard()) {
			file_name = ConfigUtils.getInstance().getRandomNumber(16) + "jpg";
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment.getExternalStorageDirectory(), file_name)));
		}
		startActivityForResult(intent, RESULTCAMERA);
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
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 160);
		intent.putExtra("outputY", 160);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, RESULTCROP);
	}

	private final int RESULTCAMERA = 1;// 拍照
	private final int RESULTGALLERY = 2;// 相册
	private final int RESULTCROP = 3;// 剪切

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESULTGALLERY && resultCode == Activity.RESULT_OK) {
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				System.out.println("uri = " + uri);
				crop(uri);
			}

		} else if (requestCode == RESULTCAMERA && resultCode == Activity.RESULT_OK) {
			if (hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(), file_name);
				crop(Uri.fromFile(tempFile));
			} else {
				// Toast.makeText(this, "未找到存储卡，无法存储照片！", 0).show();
			}

		} else if (requestCode == RESULTCROP && resultCode == Activity.RESULT_OK) {
			try {
				bitmap = data.getParcelableExtra("data");
				saveBitmapFile(bitmap);
				mHandler.sendEmptyMessage(UPDATEABLUM);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Bitmap对象保存味图片文件
	 * 
	 * @param bitmap
	 */
	private void saveBitmapFile(Bitmap bitmap) {
		try {
			tempFile = new File(Environment.getExternalStorageDirectory(), file_name);// 将要保存图片的路径
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private final int SAVEABLUM = 0;// 保存头像
	private final int UPDATEABLUM = 1;// 上传头像
	private final int UPDATESUCCESS = 2;// 上传头像
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATEABLUM:
				if (tempFile != null) {
					System.out.println("tempFile.getName() = " + tempFile.getName());
					processUpdateInfo();
				}
				break;
			case SAVEABLUM:
				saveAblum();
				break;
			case UPDATESUCCESS:
				useraccount_iv_avatar.setImageBitmap(bitmap);
				boolean delete = tempFile.delete();
				System.out.println("delete = " + delete);
				break;
			}
		}
	};
	/**
	 * ***********************************************************************
	 */
}
