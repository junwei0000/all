package com.bestdo.bestdoStadiums.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.control.activity.Base64;
import com.bestdo.bestdoStadiums.control.activity.HomeActivity;
import com.bestdo.bestdoStadiums.control.activity.MainNavImgActivity;
import com.bestdo.bestdoStadiums.control.activity.UserLoginActivity;
import com.bestdo.bestdoStadiums.control.adapter.UserOrderAdapter.ViewHolder;
import com.bestdo.bestdoStadiums.model.CreatOrdersGetVenuePYLWInfo;
import com.bestdo.bestdoStadiums.utils.parser.SearchCityParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;
import com.example.androidbridge.CallBackFunction;
import com.umeng.analytics.MobclickAgent;

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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 项目公共类
 * 
 * @author zoc
 * 
 */
public class CommonUtils {

	/**
	 * 当前home
	 */
	public Activity nHomeActivity;
	/**
	 * 当前显示状态
	 */
	public boolean nHomeActivityShowStatus = true;
	/**
	 * 渠道支付判断 （商城、订场、卡、会员）
	 */
	public String paywxStatus = "";
	public String paywxMoney;
	/**
	 * 点击百动卡购买-跳转方向
	 */
	public String intntStatus;
	/**
	 * 在卡券购买成功页使用----判断激活跳转方向
	 */
	public String intntSelectCardsStatus;
	private static CommonUtils mUtils;

	public synchronized static CommonUtils getInstance() {
		if (mUtils == null) {
			mUtils = new CommonUtils();
		}
		return mUtils;
	}

	public String getPayWxStatus() {
		return paywxStatus;
	}

	public void setPayWXStatus(String paywxStatus) {
		this.paywxStatus = paywxStatus;
	}

	/**
	 * 自定义退出应用提示
	 * 
	 * @param content
	 * @param mHandler
	 * @param activitypath
	 *            exit退出,cancel注销
	 */
	public MyDialog defineBackPressed(final Activity content, final Handler mHandler, final String btnClickEvent) {
		final MyDialog selectDialog = new MyDialog(content, R.style.dialog, R.layout.dialog_myexit);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView myexit_text_title = (TextView) selectDialog.findViewById(R.id.myexit_text_title);
		TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定
		final String exit = Constans.getInstance().exit;
		final String cancel = Constans.getInstance().cancel;
		final String exit_pay = Constans.getInstance().exit_pay;

		if (btnClickEvent.equals(exit_pay)) {
			myexit_text_title.setText("付款尚未完成，是否要退出支付？");
			text_sure.setText("继续支付");
			text_off.setText("退出支付");
		}

		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				if (btnClickEvent.equals(exit_pay)) {
					if (mHandler != null) {
						mHandler.sendEmptyMessage(5);
						selectDialog.dismiss();
					}
				}
			}
		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// text_sure.setBackgroundColor(R.drawable.myexit_sureclick_bg);
				selectDialog.dismiss();
				if (btnClickEvent.equals(exit)) {
					getInstance().exit();
				} else if (btnClickEvent.equals(cancel)) {
					clearAllBestDoInfoSharedPrefs(content);
					removeCookie();
					skipMainActivity(content);
					Constans.getInstance().walkPageComInstatus = true;
				} else if (btnClickEvent.equals(exit_pay)) {

				}
			}
		});
		return selectDialog;
	}

	private void removeCookie() {
		CookieManager cookieManager2 = CookieManager.getInstance();
		cookieManager2.setAcceptCookie(true);
		cookieManager2.removeSessionCookie();// 移除
		cookieManager2.removeAllCookie();
	}

	/**
	 * 
	 * @param context
	 * @param url
	 * @param name
	 * @param upload_url 图片上传地址
	 * @param intent_from 跳转activity
	 * @param showTitleStatus 是否显示头部
	 * @param showYouStatus 是否显示右边按钮
	 */
	public void toH5(Context context, String url, String name, String upload_url, String intent_from,
			boolean showTitleStatus,boolean showYouStatus) {
		Intent intent = new Intent(context, MainNavImgActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("html_url", url);
		intent.putExtra("name", name);
		intent.putExtra("upload_url", upload_url);
		intent.putExtra("intent_from", intent_from);
		intent.putExtra("showTitleStatus", showTitleStatus);
		intent.putExtra("showYouStatus", showYouStatus);
		context.startActivity(intent);
	}
	/**
	 * 
	 * @param context
	 * @param url
	 * @param name
	 * @param upload_url 图片上传地址
	 * @param showTitleStatus 是否显示头部
	 */
	public void toH5(Context context, String url, String name, String upload_url,  
			boolean showTitleStatus) {
		if(!TextUtils.isEmpty(name)){
			if(name.contains("商城")||name.equals("鸡年大吉")||name.equals("企业员工")){
			showTitleStatus=false;
		}
		}
		toH5(context, url, name, upload_url,"", showTitleStatus,true);
	}
	/**
	 * 跳转首页
	 * 
	 * @param context
	 */
	public boolean refleshScroolStatus = false;

	public void skipMainActivity(Activity context) {
		refleshScroolStatus = true;
		Intent in = new Intent(context, HomeActivity.class);
		in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		context.startActivity(in);
		CommonUtils.getInstance().setPageIntentAnim(in, context);
		Intent intents = new Intent();
		intents.setAction(context.getResources().getString(R.string.action_home));
		intents.putExtra("type", context.getResources().getString(R.string.action_home_type_gotohome));
		context.sendBroadcast(intents);
		context.finish();
		clear();
		clearCache();
	}

	/**
	 * 清除用户缓存
	 */
	private void clearCache() {
		com.bestdo.bestdoStadiums.utils.Config.config().clear();

	}

	public void skipCenterActivity(Activity context) {
		refleshScroolStatus = true;
		Intent intents = new Intent();
		intents.setAction(context.getResources().getString(R.string.action_home));
		intents.putExtra("type", context.getResources().getString(R.string.action_home_type_loginregok));
		context.sendBroadcast(intents);
		Intent in = new Intent(context, HomeActivity.class);
		in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		context.startActivity(in);
		CommonUtils.getInstance().setPageIntentAnim(in, context);
		context.finish();
		clear();
	}

	public void skipCenterActivityFromMain(Activity context) {
		refleshScroolStatus = true;
		Intent intents = new Intent();
		intents.setAction(context.getResources().getString(R.string.action_home));
		intents.putExtra("type", context.getResources().getString(R.string.action_home_type_centerfrommain));
		context.sendBroadcast(intents);
		Intent in = new Intent(context, HomeActivity.class);
		in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		context.startActivity(in);
		CommonUtils.getInstance().setPageIntentAnim(in, context);
		context.finish();
		clear();
	}

	/**
	 * 请求返回403时，提示并跳转登录
	 * 
	 * @param context
	 */
	public void setLoginBack403(Activity context) {
		SharedPreferences bestDoInfoSharedPrefs = getBestDoInfoSharedPrefs(context);
		Editor bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
		bestDoInfoEditor.putString("logintostatus", "login403");
		bestDoInfoEditor.commit();
		Intent in = new Intent(context, UserLoginActivity.class);
		in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		context.startActivity(in);
		CommonUtils.getInstance().setPageIntentAnim(in, context);
		context.finish();
	}

	/**
	 * 发送验证码时添加token
	 * 
	 * @param mhashmap
	 */
	public void addToken(HashMap<String, String> mhashmap, String mobile) {
		mhashmap.put("token", ConfigUtils.getInstance().MD5(mobile + "hc^w6BPbRG%t0LVi"));
	}

	/**
	 * 清除返回的数据缓存
	 * 
	 * @param mhashmap
	 * @param dataMap
	 */
	public void setClearCacheBackDate(HashMap<String, String> mhashmap, HashMap<String, Object> dataMap) {
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
	 * @param listview
	 */
	public void doLoadDateNot(Adapter mAdapter, LinearLayout layout, TextView cont, String loadstatus) {
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
	public void setViewTopHeigth(Context context, View page_top_layout) {
		double hiegh = ConfigUtils.getInstance().getPhoneWidHeigth(context).heightPixels / (11.3);
		page_top_layout
				.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, (int) hiegh));
	}

	/**
	 * 关闭键盘
	 * 
	 * @param context
	 */
	public void closeSoftInput(Activity context) {
		((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
				context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public TranslateAnimation showAnimBottom2Top() {
		// 菜单进入动画
		TranslateAnimation showAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		showAnim.setDuration(300);
		return showAnim;
	}

	public TranslateAnimation showAnimTop2Bottom() {
		// 菜单退出动画
		TranslateAnimation hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
		hideAnim.setDuration(200);
		return hideAnim;
	}

	/**
	 * 
	 * @param play_names
	 *            "jun,we,qrrt,erg"
	 * @param eachrow
	 *            每行个数
	 * @param xialastatus
	 *            false显示所有，true显示省略的
	 * @return
	 */
	public String jiequPlayName(String play_names, int eachrow, boolean xialastatus) {
		String na = "";
		try {
			play_names = DatesUtils.getInstance().getZhuanHuan(play_names, "，", ",");
			String[] plays = play_names.split(",");
			StringBuffer names = new StringBuffer();
			if (plays.length > 0 && plays.length <= eachrow) {
				for (int i = 0; i < plays.length; i++) {
					if (i == 0) {
						names.append(plays[i]);
					} else {
						names.append(" " + plays[i]);
					}
				}
				na = names.toString();
			} else if (plays.length > eachrow) {
				if (xialastatus) {
					// 当前状态为已下拉，点击收起
					for (int i = 1; i <= eachrow; i++) {
						if (i == eachrow) {
							names.append(plays[i - 1] + "...");
						} else {
							names.append(plays[i - 1] + " ");
						}
					}
				} else {
					// 点击下拉
					for (int i = 1; i <= plays.length; i++) {
						if (i != 0 && i % (eachrow) == 0) {
							names.append(plays[i - 1] + "\r\n");
						} else {
							names.append(plays[i - 1] + " ");
						}
					}
				}
				na = names.toString();
			} else {
				na = "";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return na;
	}

	/**
	 * 判断登录状态
	 * 
	 * @param loginStatus
	 * @param mActivity
	 * @return
	 */
	public boolean chackLoginStatus(String loginStatus, Activity mActivity) {
		if (!loginStatus.equals(Constans.getInstance().loginStatus)) {
			Intent intent = new Intent(mActivity, UserLoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			mActivity.startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, mActivity);
			return false;
		}
		return true;
	}

	/**
	 * 初始化 软引用
	 * 
	 * @param context
	 * @return
	 */
	public SharedPreferences getBestDoInfoSharedPrefs(Context context) {
		String key = Constans.getInstance().bestDoInfoSharedPrefsKey;
		SharedPreferences bestDoInfoSharedPrefs = context.getSharedPreferences(key, 0);
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
	 * 保存对象
	 * 
	 * @param m7DaysPriceList
	 * @return
	 */
	public String SceneList2String(ArrayList m7DaysPriceList) {
		String SceneListString = null;
		try {
			// 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			// 然后将得到的字符数据装载到ObjectOutputStream
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			// writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
			objectOutputStream.writeObject(m7DaysPriceList);
			// 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
			SceneListString = new String(Base64.encode(byteArrayOutputStream.toByteArray()));
			// 关闭objectOutputStream
			objectOutputStream.close();
		} catch (Exception e) {
		}
		return SceneListString;

	}

	@SuppressWarnings("unchecked")
	public List String2SceneList(String SceneListString) {
		List SceneList = null;
		try {
			byte[] mobileBytes = Base64.decode(SceneListString);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			SceneList = (List) objectInputStream.readObject();
			objectInputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SceneList;
	}

	/**
	 * 设置页面跳转效果 实现两个 Activity 切换时的动画。 在Activity中使用 有两个参数：进入动画和出去的动画。 注意 1、必须在
	 * StartActivity() 或 finish() 之后立即调用。 2、而且在 2.1 以上版本有效 3、手机设置-显示-动画，要开启状态
	 * 第二个参数为当前页面的效果
	 */
	public void setPageIntentAnim(Intent startIntent, Activity mActivity) {
		// 设置切换动画，从右边进入，左边退出
		mActivity.overridePendingTransition(R.anim.page_intent_from_right, R.anim.page_now);
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
		mActivity.overridePendingTransition(R.anim.page_now, R.anim.page_back_to_left);
	}

	/**
	 * 登录从下向上开启动画
	 * 
	 * @param startIntent
	 * @param mActivity
	 */
	public void setPageLoginIntentAnim(Intent startIntent, Activity mActivity) {
		// 设置切换动画，从右边进入，左边退出
		mActivity.overridePendingTransition(R.anim.page_loginintent_open, R.anim.page_now);
		startIntent = null;
	}

	/**
	 * 登录back动画
	 * 
	 * @param mActivity
	 */
	public void setPageLoginBackAnim(Activity mActivity) {
		CommonUtils.getInstance().removeActivity(mActivity);
		// 设置切换动画，从右边进入，左边退出
		mActivity.overridePendingTransition(R.anim.page_loginintent_close, R.anim.page_now);
	}

	/**
	 * 晃动后还原位置
	 * 
	 * @param view
	 * @param fromXDelta
	 * @param toXDelta
	 * @param fromYDelta
	 * @param toYDelta
	 */
	public void shakeAnimation(View view, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
		Animation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
		translateAnimation.setDuration(1000);
		view.startAnimation(translateAnimation);
	}

	/**
	 * 缩放特效 方法说明：
	 * 
	 * @param view
	 */
	public void setImgSml(View view, Activity mActivity) {
		ScaleAnimation mScaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(mActivity,
				R.anim.scale_small_anim);
		view.startAnimation(mScaleAnimation);
	}

	public void setImgBig(View view, Activity mActivity) {
		ScaleAnimation mScaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(mActivity,
				R.anim.scale_big_anim);
		view.startAnimation(mScaleAnimation);
	}

	public ArrayList<CreatOrdersGetVenuePYLWInfo> mStadiumDetailWeeksList;
	/**
	 * 当前activity
	 */
	public Activity mCurrentActivity;
	/**
	 * 正常退出
	 */
	private List<Activity> activityList = new LinkedList<Activity>();
	public List<Activity> payPage = new LinkedList<Activity>();
	public List<Activity> searchPage = new LinkedList<Activity>();
	public List<Activity> orderDetailPage = new LinkedList<Activity>();
	public List<Activity> homePage = new LinkedList<Activity>();
	public List<Activity> buyCardsList = new LinkedList<Activity>();
	private AnimationDrawable frameAnimation;
	public List<Activity> photo = new LinkedList<Activity>();

	public void addPhotoActivity(Activity activity) {
		photo.add(activity);
	}

	/**
	 * 关闭除了首页的其他页面
	 */
	public void clearPhoto() {
		for (Activity activity : photo) {
			activity.finish();
		}
		photo.clear();
	}

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
		if (searchPage.contains(activity)) {
			searchPage.remove(activity);
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
		if (nHomeActivity != null) {
			nHomeActivity.finish();
		}
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

	public void addBuyCardsList(Activity activity) {
		buyCardsList.add(activity);
	}

	public void exitBuyCardsList() {
		for (Activity activity : buyCardsList) {
			activity.finish();
		}
		buyCardsList.clear();
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
	 * 关闭支付方式页， 登录注册页面
	 */
	public void exitPayPage() {
		for (Activity activity : payPage) {
			activity.finish();
		}
		payPage.clear();
	}

	/**
	 * 1.订单详情页添加；2.用于绑定手机号页添加，设置密码成功页移除
	 * 
	 * @param activity
	 */
	public void addOrderDetailPage(Activity activity) {
		orderDetailPage.add(activity);
	}

	/**
	 * 1、在订单状态超时取消后在支付宝支付状态页返回时移除订单详情页；2、设置密码成功页移除绑定手机号页
	 */
	public void exitOrderDetailPage() {
		for (Activity activity : orderDetailPage) {
			activity.finish();
		}
		orderDetailPage.clear();
	}

	/**
	 * 标记是否开启找球场页
	 * 
	 * @param activity
	 */
	public void addSearchPage(Activity activity) {
		searchPage.add(activity);
	}

	/**
	 * 移除找球场页
	 */

	public void exitSearchPage() {
		for (Activity activity : searchPage) {
			activity.finish();
		}
		searchPage.clear();
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
			result = EncodingUtils.getString(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取城市列表数据
	 * 
	 * @param context
	 * @return
	 */
	public HashMap<String, Object> getCityMap(Context context) {
		SearchCityParser mSearchCityParser = new SearchCityParser();
		HashMap<String, Object> mCityMap = mSearchCityParser.parseJSON(context, Constans.getInstance().cityFileName);
		mSearchCityParser = null;
		return mCityMap;
	}

	/**
	 * 保存文件数据
	 * 
	 * @param context
	 * @param jsonContent
	 * @param fileName
	 */
	public void updateCacheFile(Context context, String jsonContent, String fileName) {
		try {

			JSONObject jsonObject = RequestUtils.String2JSON(jsonContent);
			if (jsonObject != null && jsonObject.length() > 0) {
				FileOutputStream outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
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
	 * @param file
	 *            文件上传时将file转化为byte[]
	 * @return
	 */
	public static byte[] File2Bytes(File file) {
		int byte_size = 1024;
		byte[] b = new byte[byte_size];
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(byte_size);
			for (int length; (length = fileInputStream.read(b)) != -1;) {
				outputStream.write(b, 0, length);
			}
			fileInputStream.close();
			outputStream.close();
			return outputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Java文件操作 获取文件扩展名
	 * 
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
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


	public ProgressDialog createLoadingDialog(Activity context) {
		if (context == null) {
			return null;
		}
		LayoutInflater inflater = LayoutInflater.from(context);
		ProgressDialog mpDialog = null;
		try {
			View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
			ImageView mImageView = (ImageView) v.findViewById(R.id.dialog_img);
			if (frameAnimation == null) {
				frameAnimation = (AnimationDrawable) context.getResources().getDrawable(R.anim.a);
			}
			mImageView.setBackgroundDrawable(frameAnimation);
			frameAnimation.start();
			mpDialog = new ProgressDialog(context, R.style.dialog);// 创建自定义样式dialog
			if (mpDialog != null) {
				mpDialog.setOwnerActivity(context);
				Activity activity = mpDialog.getOwnerActivity();
				if (activity != null && !activity.isFinishing()) {
					mpDialog.show();// 这个方法应该在mpDialog.setContentView()方法前，否则出现android.util.AndroidRuntimeException
					mpDialog.setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
							LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
					// mpDialog.setCancelable(false);// 不可以用“返回键”取消
					mpDialog.setCanceledOnTouchOutside(false);
					mpDialog.getWindow().clearFlags(
							WindowManager.LayoutParams.FLAG_BLUR_BEHIND | WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				}
				setOnkeyBackDialog(mpDialog, activity);
			}

		} catch (Exception e) {
		}
		return mpDialog;

	}

	public ProgressDialog createLoadingDialog2(Activity context) {
		if (context == null) {
			return null;
		}
		ProgressDialog mpDialog = null;
		try {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
			ImageView mImageView = (ImageView) v.findViewById(R.id.dialog_img);
			if (frameAnimation == null) {
				frameAnimation = (AnimationDrawable) context.getResources().getDrawable(R.anim.order_anim);
			}
			mImageView.setBackgroundDrawable(frameAnimation);
			frameAnimation.start();
			mpDialog = new ProgressDialog(context, R.style.dialog);// 创建自定义样式dialog
			mpDialog.setOwnerActivity(context);
			if (mpDialog != null) {
				Activity activity = mpDialog.getOwnerActivity();
				if (activity != null && !activity.isFinishing()) {
					mpDialog.show();// 这个方法应该在mpDialog.setContentView()方法前，否则出现android.util.AndroidRuntimeException
					mpDialog.setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
							LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
					// mpDialog.setCancelable(false);// 不可以用“返回键”取消
					mpDialog.setCanceledOnTouchOutside(false);
					mpDialog.getWindow().clearFlags(
							WindowManager.LayoutParams.FLAG_BLUR_BEHIND | WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				}
				setOnkeyBackDialog(mpDialog, activity);
			}
		} catch (Exception e) {
		}
		return mpDialog;

	}

	public void setOnkeyBackDialog(final ProgressDialog mDialog, final Activity mContext) {
		mDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				System.err.println("nHomeActivityShowStatus===============================" + nHomeActivityShowStatus);
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
		if (mDialog == null) {
			return;
		}
		new DismissTask().execute(mDialog);
	}

	private class DismissTask extends AsyncTask<ProgressDialog, Void, ProgressDialog> {
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
	 * 拨打电话
	 * 
	 * @param context
	 */
	public void getPhoneToKey(Context context) {
		Intent intent = new Intent("android.intent.action.DIAL",
				Uri.parse("tel:" + context.getResources().getString(R.string.tel)));
		context.startActivity(intent);
	}

	/**
	 * 根据电话号，拨打电话
	 * 
	 * @param context
	 */
	public void getPhoneToKey(Context context, String tel) {
		Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + tel));
		context.startActivity(intent);
	}

	/**
	 * 初始化Toast
	 * 
	 * @param context
	 * @param text
	 */
	private Toast mToast;

	public void initToast(Context context, String text) {
		// if (context == null) {
		// return;
		// }
		// if (mToast == null) {
		// mToast = new Toast(context.getApplicationContext());
		// }
		// View view =
		// LayoutInflater.from(context).inflate(R.layout.define_toast, null);
		// ImageView define_toast_img = (ImageView)
		// view.findViewById(R.id.define_toast_img);
		// TextView define_toast_text = (TextView)
		// view.findViewById(R.id.define_toast_text);
		// int tblr = ConfigUtils.getInstance().dip2px(context, 8);
		// view.setPadding(tblr, tblr, tblr, tblr);
		// define_toast_text.setText(text);
		// define_toast_img.setVisibility(View.GONE);
		// mToast.setDuration(Toast.LENGTH_SHORT);
		// mToast.setView(view);
		// // mToast.setGravity(Gravity.CENTER, 0, 0);
		// mToast.show();
		App.t(text);
	}

	/**
	 * 添加取消收藏，自定义toast
	 * 
	 * @param context
	 * @param text
	 * @param status
	 */
	private Toast mDefineToast;

	public void definedToast(Context context, String text, String status) {
		if (mDefineToast == null) {
			mDefineToast = new Toast(context.getApplicationContext());
		}
		View view = LayoutInflater.from(context).inflate(R.layout.define_toast, null);
		ImageView define_toast_img = (ImageView) view.findViewById(R.id.define_toast_img);
		TextView define_toast_text = (TextView) view.findViewById(R.id.define_toast_text);
		int wid = ConfigUtils.getInstance().dip2px(context, 100);
		define_toast_text.setWidth(wid);
		define_toast_text.setText(text);
		if (status.equals("cancelcollect")) {
			define_toast_img.setImageResource(R.drawable.stadiumdetail_collect_img_moren);
		} else {
			define_toast_img.setImageResource(R.drawable.stadiumdetail_collect_img_select);
		}
		mDefineToast.setGravity(Gravity.CENTER, 0, 0);
		mDefineToast.setDuration(Toast.LENGTH_SHORT);
		mDefineToast.setView(view);
		mDefineToast.show();
	}

	/**
	 * 半角字符与全角字符混乱所致：这种情况一般就是汉字与数字、英文字母混用
	 * 
	 * @param input
	 * @return
	 */
	public String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 方法说明：友盟埋点
	 * 
	 * @param context
	 *            指当前的Activity
	 * @param id
	 *            自定义事件id
	 * @param map
	 *            为当前事件的属性和取值（Key-Value键值对）
	 */
	public void umengCount(Context context, String id, Map map) {

		if (map == null) {
			MobclickAgent.onEvent(context, id);
		} else
			MobclickAgent.onEvent(context, id, map);
	}

	/**
	 * 关闭timer倒计时集合
	 */
	private List<ViewHolder> orderTimerList = new LinkedList<ViewHolder>();

	public void addTimer(ViewHolder mView) {
		orderTimerList.add(mView);
	}

	public void exitTimer() {
		for (ViewHolder mView : orderTimerList) {
			mView.stateChange = false;
			mView.timer = null;
		}
		orderTimerList.clear();
	}

	/**
	 * 圆角头像
	 * 
	 * @param mContext
	 * @param bitmap
	 * @return
	 */
	public Bitmap toRoundCorner(Context mContext, Bitmap bitmap) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);

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

	public Bitmap toConformBitmap(Bitmap background, Bitmap foreground) {
		if (background == null) {
			return null;
		}

		int bgWidth = background.getWidth();
		int bgHeight = background.getHeight();
		int fgWidth = foreground.getWidth();
		int fgHeight = foreground.getHeight();
		// create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
		Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Config.ARGB_8888);
		Canvas cv = new Canvas(newbmp);
		// draw bg into
		cv.drawBitmap(background, 0, 0, null);// 在 0，0坐标开始画入bg
		// draw fg into
		// foreground = foreground.copy(Bitmap.Config.ARGB_8888, true);
		// 建立Paint 物件
		Paint vPaint = new Paint();
		vPaint.setStyle(Paint.Style.STROKE); // 空心
		vPaint.setAlpha(50); // Bitmap透明度(0 ~ 100)

		cv.drawBitmap(foreground, 0, bgHeight - fgHeight, vPaint);// 在
																	// 0，0坐标开始画入fg
																	// ，可以从任意位置画入
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newbmp;
	}

	/*
	 * 使用Canvas合并Bitmap
	 */
	public Bitmap mergeBitmap(Bitmap bmp1, Bitmap bmp2) {
		// 获取ImageView上得Bitmap图片
		// Bitmap bmp1 = ((BitmapDrawable) ivBmp1.getDrawable()).getBitmap();
		// Bitmap bmp2 = ((BitmapDrawable) ivBmp2.getDrawable()).getBitmap();
		if (bmp1 == null) {
			return null;
		}
		// 创建空得背景bitmap
		// 生成画布图像
		Bitmap resultBitmap = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(resultBitmap);// 使用空白图片生成canvas

		// 将bmp1绘制在画布上
		Rect srcRect = new Rect(0, 0, bmp1.getWidth(), bmp1.getHeight() - bmp2.getHeight());// 截取bmp1中的矩形区域
		Rect dstRect = new Rect(0, 0, bmp1.getWidth(), bmp1.getHeight() - bmp2.getHeight());// bmp1在目标画布中的位置
		canvas.drawBitmap(bmp1, srcRect, dstRect, null);

		// 将bmp2绘制在画布上
		srcRect = new Rect(0, bmp1.getHeight() - bmp2.getHeight(), bmp2.getWidth(), bmp1.getHeight());// 截取bmp1中的矩形区域
		dstRect = new Rect(bmp1.getWidth(), 0, bmp1.getWidth(), bmp2.getHeight());// bmp2在目标画布中的位置
		canvas.drawBitmap(bmp2, srcRect, dstRect, null);
		// 将bmp1,bmp2合并显示
		return resultBitmap;
	}

	/**
	 * 设置字体样式
	 * 
	 * @param mTextView
	 */
	public void setTextViewTypeface(Context mContext, TextView mTextView) {
		Typeface face = getAssetTypeFace(mContext);
		mTextView.setTypeface(face);
		// mTextView.getPaint().setFakeBoldText(true);
	}

	public static Typeface getAssetTypeFace(Context mContext) {
		return Typeface.createFromAsset(mContext.getAssets(), "DINCondensedC.otf");
	}
}
