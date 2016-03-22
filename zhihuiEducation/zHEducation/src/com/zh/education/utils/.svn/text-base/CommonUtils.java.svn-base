package com.zh.education.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.zh.education.R;
import com.zh.education.control.activity.MainActivity;
import com.zh.education.control.activity.UserLoginActivity;
import com.zh.education.control.activity.WelcomeStartActiyity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2015-12-31 下午5:05:20
 * @Description 类描述：项目公共类
 */
public class CommonUtils {

	private static CommonUtils mUtils;

	public synchronized static CommonUtils getInstance() {
		if (mUtils == null) {
			mUtils = new CommonUtils();
		}
		return mUtils;
	}

	/**
	 * 自定义退出应用提示
	 * 
	 * @param content
	 * @param activitypath
	 *            exit退出,cancel注销
	 */
	public String exit = "exit";
	public String cancel = "cancel";

	public void defineBackPressed(final Activity content,
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
		if (btnClickEvent.equals(exit)) {
			myexit_text_title.setText("确定要退出吗？");
			text_sure.setText("确认退出");
		} else if (btnClickEvent.equals(cancel)) {
			myexit_text_title.setText("确定要注销吗？");
			text_sure.setText("确认注销");
		}

		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		text_sure.setOnTouchListener(new OnTouchListener() {
			@SuppressWarnings("deprecation")
			public boolean onTouch(View arg0, MotionEvent arg1) {
				text_sure.setBackgroundDrawable(content.getResources()
						.getDrawable(R.drawable.myexit_sureclick_bg));
				text_sure.setTextColor(content.getResources().getColor(
						R.color.white));
				return false;
			}
		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				if (btnClickEvent.equals(exit)) {
					getInstance().exit();
				}else 
				if (btnClickEvent.equals(cancel)) {
					clearAllBestDoInfoSharedPrefs(content);
					Intent its;
					its = new Intent(content, UserLoginActivity.class);
					its.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					content.startActivity(its);

				}
			}
		});
	}

	/**
	 * 
	 * @param activity
	 * @return logintimeoutstatus false 没有失效
	 */
	public boolean LoginTokenReLog(Activity activity){
		boolean logintimeoutstatus=false;
		SharedPreferences zhedu_spf = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(activity);
		String loginTime=zhedu_spf.getString("loginTime", "");
		String loginTimeNow=DatesUtils.getInstance().getTimeStampToDate(DatesUtils.getInstance().syncCurrentTime(), "yyyy-MM-dd");
		if(12<DatesUtils.getInstance().timeDiffhours(loginTime, loginTimeNow, "yyyy-MM-dd")){
		clearAllBestDoInfoSharedPrefs(activity);
		Intent its;
		its = new Intent(activity, UserLoginActivity.class);
		its.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		activity.startActivity(its);
		activity.overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
		logintimeoutstatus=true;
		Toast.makeText(activity, "登录已失效，请重新登录！",
				Toast.LENGTH_SHORT).show();
	}
		return logintimeoutstatus;
	
	
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

	/**
	 * 关闭键盘
	 * 
	 * @param context
	 */
	public void closeSoftInput(Activity context) {
		((InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(context.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 字体大小
	 * 
	 * @param context
	 * @return
	 */
	public SharedPreferences getTextSizeSharedPrefs(Context context) {
		String key = Constans.getInstance().textSizeSharedPrefsKey;
		SharedPreferences bestDoInfoSharedPrefs = context.getSharedPreferences(
				key, 0);
		return bestDoInfoSharedPrefs;
	}

	/**
	 * 初始化 软引用
	 * 
	 * @param context
	 * @return
	 */
	public SharedPreferences getBestDoInfoSharedPrefs(Context context) {
		String key = Constans.getInstance().bestDoInfoSharedPrefsKey;
		SharedPreferences bestDoInfoSharedPrefs = context.getSharedPreferences(
				key, 0);
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
	 * 正常退出
	 */
	private List<Activity> activityList = new LinkedList<Activity>();

	public void addActivity(Activity activity) {
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
	 * 关闭其他页面
	 */
	public void clear() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		activityList.clear();
	}

	/**
	 * 根据电话号，拨打电话
	 * 
	 * @param context
	 * @param tel
	 */
	public void getPhoneToKey(Context context, String tel) {
		Intent intent = new Intent("android.intent.action.DIAL",
				Uri.parse("tel:" + tel));
		context.startActivity(intent);
	}

	/**
	 * 设置状态栏背景状态一体化
	 * 
	 * @param context
	 */
	public void setTranslucentStatus(Activity context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window win = context.getWindow();
			WindowManager.LayoutParams winParams = win.getAttributes();
			final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
			winParams.flags |= bits;
			win.setAttributes(winParams);
		}
		SystemStatusManager tintManager = new SystemStatusManager(context);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(0);// 状态栏无背景
	}

	/**
	 * dip转为 px
	 */
	public int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px 转为 dip
	 */
	public int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 判断是否联网
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean isNetWorkAvaiable(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null) {
				return info.isAvailable();
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
