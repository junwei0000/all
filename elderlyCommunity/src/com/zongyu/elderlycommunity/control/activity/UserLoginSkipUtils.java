/**
 * 
 */
package com.zongyu.elderlycommunity.control.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.zongyu.elderlycommunity.R;
import com.zongyu.elderlycommunity.model.UserLoginInfo;
import com.zongyu.elderlycommunity.utils.CommonUtils;
import com.zongyu.elderlycommunity.utils.Constans;

/**
 * @author      作者：zoc
 * @date        创建时间：2016-9-28 下午6:39:46
 * @Description 类描述：
 */
public class UserLoginSkipUtils {

	Activity context;
	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor bestDoInfoEditor;
	private String loginskiptostatus;
	
	
	
	
	public UserLoginSkipUtils() {
		super();
	}

	public UserLoginSkipUtils(Activity context) {
		super();
		this.context = context;
		bestDoInfoSharedPrefs = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(context);
		bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
		loginskiptostatus=bestDoInfoSharedPrefs.getString("loginskiptostatus", "");
	}

	public void cacheLoginInfo(UserLoginInfo loginInfo) {
		if (loginInfo != null) {
			saveLoginInfo(loginInfo);
			skipToPage();
		} else {
			showLoginFailureInfo();
		}
	}

	public void saveLoginInfo(UserLoginInfo loginInfo){
		String uid = loginInfo.getUid();
		String username = loginInfo.getAblum_url();
		String mobile = loginInfo.getEmail();
		String nick_name = loginInfo.getNick_name();
		String real_name = loginInfo.getReal_name();
		String sex = loginInfo.getSex();
		String telephone = loginInfo.getTelephone();

		bestDoInfoEditor.putString("uid", uid + "");
		bestDoInfoEditor.putString("username", "" + username);
		bestDoInfoEditor.putString("mobile", "" + mobile);
		bestDoInfoEditor.putString("nick_name", nick_name + "");
		bestDoInfoEditor.putString("real_name", real_name + "");
		bestDoInfoEditor.putString("sex", sex + "");
		bestDoInfoEditor.putString("telephone", telephone + "");
		bestDoInfoEditor.putString("loginStatus",
				Constans.getInstance().loginStatus);
		bestDoInfoEditor.commit();
	}
	private void showLoginFailureInfo() {
		CommonUtils.getInstance().initToast(context, "登录失败，请重新登录");
	}
	/**
	 * 登录后多方向跳转判断
	 */
	Intent intents;
	public void skipToPage() {
		if (loginskiptostatus
				.equals(Constans.getInstance().loginskiptoTiXing)) {
			intents = new Intent();
			intents.setAction(context.getResources().getString(
					R.string.action_home));
			intents.putExtra(
					"type",context.getResources().getString(
							R.string.action_home_type_loginregok));
			context.sendBroadcast(intents);
			context.finish();
		}
		bestDoInfoEditor.putString("loginskiptostatus", "");
		bestDoInfoEditor.commit();
	}

	/**
	 * 返回前返回是否是收藏列表跳转
	 */
	public void doBackCheck() {
		if (loginskiptostatus
				.equals(Constans.getInstance().loginskiptoTiXing)) {
			intents = new Intent();
			intents.setAction(context.getResources().getString(
					R.string.action_home));
			intents.putExtra(
					"type",
					context.getResources().getString(
							R.string.action_home_type_loginregok));
			context.sendBroadcast(intents);
		}
	}
}
