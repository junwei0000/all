package com.KiwiSports.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

import com.KiwiSports.R;
import com.KiwiSports.control.activity.UserLoginActivity;
import com.KiwiSports.control.locationService.Utils;
import com.KiwiSports.utils.volley.RequestUtils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午3:30:43
 * @Description 类描述：项目公共类
 */
public class CommonUtils {

	private static CommonUtils instance;

	private CommonUtils() {
	}

	public static synchronized CommonUtils getInstance() {
		if (instance == null) {
			instance = new CommonUtils();
		}
		return instance;
	}

	/**
	 * 获取Stirng值
	 * 
	 * @param content
	 * @param stringId
	 * @return
	 */
	public String getString(Activity content, int stringId) {
		return content.getString(stringId);

	}

	/**
	 * 自定义退出应用提示
	 * 
	 * @param content
	 * @param mHandler
	 *            exit退出,cancel注销
	 */
	public void defineBackPressed(final Activity content,
			final Handler mHandler, final int mHandlerID,
			final String btnClickEvent) {
		final MyDialog selectDialog = new MyDialog(content, R.style.dialog,
				R.layout.dialog_myexit);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView myexit_text_title = (TextView) selectDialog
				.findViewById(R.id.myexit_text_title);
		TextView text_off = (TextView) selectDialog
				.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog
				.findViewById(R.id.myexit_text_sure);// 确定
		final String exit = Constants.getInstance().exit;
		final String cancel = Constants.getInstance().cancel;

		if (btnClickEvent.equals(cancel)) {
			myexit_text_title.setText(getString(content,
					R.string.myexit_cancel_title));
		}

		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				if (btnClickEvent.equals(exit)) {
					content.sendBroadcast(Utils.getCloseBrodecastIntent());
					getInstance().exit();
				} else if (btnClickEvent.equals(cancel)) {
					mHandler.sendEmptyMessage(mHandlerID);
				}
			}
		});
	}

	/**
	 * 请求返回403时，提示并跳转登录
	 * 
	 * @param context
	 */
	public void setLoginBack403(Activity context) {
		Intent in = new Intent(context, UserLoginActivity.class);
		in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		context.startActivity(in);
		CommonUtils.getInstance().setPageIntentAnim(in, context);
		context.finish();
	}

	/**
	 * 清除返回的数据缓存
	 * 
	 * @param mhashmap
	 * @param dataMap
	 */
	public void setClearCacheBackDate(HashMap<String, String> mhashmap,
			HashMap<String, Object> dataMap) {
		if (mhashmap != null) {
			mhashmap.clear();
			mhashmap = null;
		}
		if (dataMap != null) {
			dataMap.clear();
			dataMap = null;
		}
	}

	public void setClearCacheDialog(Dialog mDialog) {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	/**
	 * 没有数据时
	 * 
	 * @param mAdapter
	 */
	public void doLoadDateNot(Adapter mAdapter, LinearLayout layout,
			TextView cont, String loadstatus) {
		// 当列表中没有数据时
		if (mAdapter == null || (mAdapter != null && mAdapter.getCount() == 0)) {
			layout.setVisibility(View.VISIBLE);
			if (cont != null) {
				if (loadstatus.equals("loadnot_date")) {
					cont.setVisibility(View.VISIBLE);
				} else {
					cont.setVisibility(View.INVISIBLE);
				}
			}
		} else {
			layout.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置头部的高度，防止输入框键盘显示时把头部变型
	 * 
	 * @param context
	 * @param page_top_layout
	 */
	@SuppressWarnings("deprecation")
	public void setViewTopHeigth(Context context, View page_top_layout) {
		double hiegh = ConfigUtils.getInstance().getPhoneWidHeigth(context).heightPixels / (12);
		page_top_layout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, (int) hiegh));
	}

	public TranslateAnimation showAnimBottom2Top() {
		// 菜单进入动画
		TranslateAnimation showAnim = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.0f);
		showAnim.setDuration(300);
		return showAnim;
	}

	public TranslateAnimation showAnimTop2Bottom() {
		// 菜单退出动画
		TranslateAnimation hideAnim = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 1.0f);
		hideAnim.setDuration(200);
		return hideAnim;
	}

	/**
	 * 初始化 软引用
	 * Android开发中Activity与Service之间getSharedPreferences不同步的解决方法  Context.MODE_MULTI_PROCESS
	 * @param context
	 * @return
	 */
	public SharedPreferences getBestDoInfoSharedPrefs(Context context) {
		String key = Constants.getInstance().bestDoInfoSharedPrefsKey;
		SharedPreferences bestDoInfoSharedPrefs = context.getSharedPreferences(
				key, Context.MODE_MULTI_PROCESS);
		return bestDoInfoSharedPrefs;
	}

	/**
	 * 注销时清除 软引用
	 * 
	 * @param context
	 */
	public void clearAllBestDoInfoSharedPrefs(Activity context) {
		SharedPreferences searchinfo_chaxun = getBestDoInfoSharedPrefs(context);
		Editor searchinfo_chaxunEditor = searchinfo_chaxun.edit();
		searchinfo_chaxunEditor.clear();
		searchinfo_chaxunEditor.commit();
	}

	/**
	 * 设置页面跳转效果 实现两个 Activity 切换时的动画。 在Activity中使用 有两个参数：进入动画和出去的动画。 注意 1、必须在
	 * StartActivity() 或 finish() 之后立即调用。 2、而且在 2.1 以上版本有效 3、手机设置-显示-动画，要开启状态
	 * 第二个参数为当前页面的效果
	 */
	public void setPageIntentAnim(Intent startIntent, Activity mActivity) {
		// 设置切换动画，从右边进入，左边退出
		mActivity.overridePendingTransition(R.anim.page_intent_from_right,
				R.anim.page_now);
		startIntent = null;
	}

	/**
	 * 设置页面返回效果
	 * 
	 * @param mActivity
	 */
	public void setPageBackAnim(Activity mActivity) {
		CommonUtils.getInstance().removeActivity(mActivity);
		// 设置切换动画，从右边进入，左边退出
		mActivity.overridePendingTransition(R.anim.page_now,
				R.anim.page_back_to_left);
	}

	/**
	 * 当前activity
	 */
	public Activity mCurrentActivity;
	public Activity mHomeActivity;
	/**
	 * 正常退出
	 */
	private List<Activity> activityList = new LinkedList<Activity>();
	public List<Activity> payPage = new LinkedList<Activity>();

	/**
	 * 退出项目时整体销毁 并设置当前显示的最新页面
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		mCurrentActivity = activity;
		activityList.add(activity);
	}

	/**
	 * 关闭当前页面时移除
	 * 
	 * @param activity
	 */
	public void removeActivity(Activity activity) {
		if (activityList.contains(activity)) {
			activityList.remove(activity);
		}
		if (payPage.contains(activity)) {
			payPage.remove(activity);
		}
	}

	/**
	 * 用于 登录注册期间;
	 * 
	 * @param activity
	 */
	public void addPayPageActivity(Activity activity) {
		payPage.add(activity);
	}

	/**
	 * 关闭 页
	 */
	public void exitPayPage() {
		for (Activity activity : payPage) {
			activity.finish();
		}
		payPage.clear();
	}

	/**
	 * 退出应用
	 */
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		activityList.clear();
		System.exit(0);
	}

	/**
	 * 关闭除了首页的其他页面
	 */
	public void clear() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		activityList.clear();
	}

	/**
	 * 注销时清理信息
	 */
	public void zhuXiao() {
		CommonUtils.getInstance().clearAllBestDoInfoSharedPrefs(mHomeActivity);
		CommonUtils.getInstance().clear();
		CommonUtils.getInstance().setLoginBack403(mHomeActivity);
	}

	/**
	 * 得到assets下 城市信息、运动类型
	 * 
	 * @param fileName
	 * @return
	 */
	public String getFromAssets(Context context, String fileName) {
		String result = "";
		try {
			InputStream in = context.getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 保存文件数据
	 * 
	 * @param context
	 * @param jsonContent
	 * @param fileName
	 */
	public void updateCacheFile(Context context, String jsonContent,
			String fileName) {
		try {

			JSONObject jsonObject = RequestUtils.String2JSON(jsonContent);
			if (jsonObject != null && jsonObject.length() > 0) {
				FileOutputStream outStream = context.openFileOutput(fileName,
						Context.MODE_PRIVATE);
				outStream.write(jsonContent.getBytes());
				outStream.close();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 获取文件数据
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public String getCacheFile(Context context, String fileName) {

		try {
			FileInputStream inStream = context.openFileInput(fileName);// 只需传文件名
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();// 输出到内存

			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);//
			}

			byte[] content_byte = outStream.toByteArray();
			String content = new String(content_byte);
			System.out.println(content);
			return content;
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * 创建提示框
	 * 
	 * @param context
	 * @param str
	 * @return
	 */
	public ProgressDialog getDialog(Context context, String str) {
		ProgressDialog mpDialog = new ProgressDialog(context);
		mpDialog.setMessage(str);
		mpDialog.setCanceledOnTouchOutside(false);
		return mpDialog;
	}

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public boolean nHomeActivityShowStatus = true;

	@SuppressWarnings("deprecation")
	public ProgressDialog createLoadingDialog(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
		ImageView mImageView = (ImageView) v.findViewById(R.id.dialog_img);
		AnimationDrawable frameAnimation = (AnimationDrawable) context
				.getResources().getDrawable(R.drawable.loading);
		mImageView.setBackgroundDrawable(frameAnimation);
		frameAnimation.start();
		ProgressDialog mpDialog = null;
		try {
			mpDialog = new ProgressDialog(context, R.style.dialog);// 创建自定义样式dialog
			mpDialog.show();// 这个方法应该在mpDialog.setContentView()方法前，否则出现android.util.AndroidRuntimeException
			mpDialog.setContentView(v, new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
			// mpDialog.setCancelable(false);// 不可以用“返回键”取消
			mpDialog.setCanceledOnTouchOutside(false);
			mpDialog.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_BLUR_BEHIND
							| WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		} catch (Exception e) {
		}
		return mpDialog;

	}

	public void setOnkeyBackDialog(final ProgressDialog mDialog,
			final Activity mContext) {
		mDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				System.err
						.println("nHomeActivityShowStatus==============================="
								+ nHomeActivityShowStatus);
				mDialog.dismiss();
				if (!nHomeActivityShowStatus) {
					RequestUtils.cancelAll(mContext);
					mContext.finish();
					CommonUtils.getInstance().setPageBackAnim(mContext);
				}
				return true;
			}
		});
	}

	/**
	 * 隐藏弹出框
	 * 
	 * @param mDialog
	 */
	public void setOnDismissDialog(ProgressDialog mDialog) {
		new DismissTask().execute(mDialog);
	}

	private class DismissTask extends
			AsyncTask<ProgressDialog, Void, ProgressDialog> {
		@SuppressWarnings("null")
		protected void onPostExecute(ProgressDialog result) {
			try {
				if (result != null || result.isShowing()) {
					result.dismiss();
				}
			} catch (Exception e) {
			} finally {

			}
			super.onPostExecute(result);
		}

		@Override
		protected ProgressDialog doInBackground(ProgressDialog... arg0) {
			return arg0[0];
		}

	}

	/**
	 * 根据电话号，拨打电话
	 * 
	 * @param context
	 */
	public void getPhoneToKey(Context context, String tel) {
		Intent intent = new Intent("android.intent.action.DIAL",
				Uri.parse("tel:" + tel));
		context.startActivity(intent);
	}

	/**
	 * 初始化Toast
	 * 
	 * @param text
	 */
	public void initToast(String text) {
		App.getInstance().t(text);
	}

	/**
	 * 自定义圆角头像
	 * 
	 * @param mContext
	 * @param bitmap
	 * @return
	 */
	public Bitmap toRoundCorner(Context mContext, Bitmap bitmap,float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;

		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		final RectF rectF = new RectF(rect);

//		final float roundPx = 100;// 角度

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;

	}

	/**
	 * 圆形头像
	 * @param mContext
	 * @param bitmap
	 * @return
	 */
	public Bitmap toRoundCorner(Context mContext, Bitmap bitmap) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;

		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		final RectF rectF = new RectF(rect);

		final float roundPx = 100;// 角度

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;

	}
	/**
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 设置字体样式
	 * 
	 * @param mTextView
	 */
	public void setTextViewTypeface(Context mContext, TextView mTextView) {
		Typeface face = getAssetTypeFace(mContext);
		mTextView.setTypeface(face);
	}

	public  Typeface getAssetTypeFace(Context mContext) {
		return Typeface.createFromAsset(mContext.getAssets(),
				"DINCondensedC.otf");
	}
}
