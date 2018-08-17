package com.KiwiSports.control.activity;

import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.UserAccountUpdateBusiness;
import com.KiwiSports.business.UserAccountUpdateBusiness.GetAccountUpdateCallback;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.util.Log;

public class UpdateInfoUtils {

	Activity mContext;
	private HashMap<String, String> mhashmap;
	private ProgressDialog mDialog;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private String token;
	private String access_token;
	private String hobby;
	private String nick_name;
	private String sex;
	private String is_anonymous;
	Handler mHandler;
	int mHandlerID;

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(
						mContext);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UpdateInfoUtils(Activity mContext) {
		this.mContext = mContext;
		bestDoInfoSharedPrefs = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(mContext);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");
		hobby = bestDoInfoSharedPrefs.getString("hobby", "");
		nick_name = bestDoInfoSharedPrefs.getString("nick_name", "");
		sex = bestDoInfoSharedPrefs.getString("sexNew", Constants.getInstance().SEX_MALE);
		is_anonymous = bestDoInfoSharedPrefs.getString("is_anonymous", "1");
	}

	public void UpdateInfo(String typekey, String typevalue) {
		if (typekey.equals("hobby")) {
			hobby = typevalue;
		} else if (typekey.equals("nick_name")) {
			nick_name = typevalue;
		} else if (typekey.equals("sex")) {
			sex = typevalue;
		} else if (typekey.equals("is_anonymous")) {
			is_anonymous = typevalue;
		}
		processUpdateInfo(typekey, typevalue);
	}

	private void processUpdateInfo(final String typekey, final String typevalue) {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("hobby", hobby);
		mhashmap.put("nick_name", nick_name + "");
		mhashmap.put("sex", sex + "");
		mhashmap.put("is_anonymous", is_anonymous+"");
		Log.e("decrypt----", mhashmap.toString());
		new UserAccountUpdateBusiness(mContext, "info", mhashmap,
				new GetAccountUpdateCallback() {

					@Override
					public void afterDataGet(HashMap<String, Object> dataMap) {
						CommonUtils.getInstance().setOnDismissDialog(mDialog);
						if (dataMap != null) {
							String status = (String) dataMap.get("status");
							if (status.equals("200")) {
								SharedPreferences bestDoInfoSharedPrefs = CommonUtils
										.getInstance()
										.getBestDoInfoSharedPrefs(mContext);
								Editor bestDoInfoEditor = bestDoInfoSharedPrefs
										.edit();
								bestDoInfoEditor.putString(typekey, typevalue);
								bestDoInfoEditor.commit();
								if (typekey.equals("nick_name")
										|| typekey.equals("sex")
										|| typekey.equals("hobby")) {
									mContext.finish();
									CommonUtils.getInstance().setPageBackAnim(
											mContext);
								}
								if (!typekey.equals("is_anonymous")) {
									String msg = (String) dataMap.get("msg");
									CommonUtils.getInstance().initToast(msg);
								}
							}
						} else {
							CommonUtils.getInstance().initToast(
									mContext.getString(R.string.net_tishi));
						}
						// 清除缓存
						CommonUtils.getInstance().setClearCacheBackDate(
								mhashmap, dataMap);
						if (typekey.equals("hobby")) {
							mContext.finish();
							CommonUtils.getInstance().setPageBackAnim(
									mContext);
						}
					}
				});
	}

}
