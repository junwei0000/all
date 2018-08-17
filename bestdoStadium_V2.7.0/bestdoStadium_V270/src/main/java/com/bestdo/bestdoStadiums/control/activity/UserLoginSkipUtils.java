/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.UserLoginInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-1-22 上午11:31:39
 * @Description 类描述：
 */
public class UserLoginSkipUtils {

	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor bestDoInfoEditor;
	private String loginskiptostatus;
	Activity mActivity;
	Intent intents;

	public UserLoginSkipUtils(Activity mActivity) {
		this.mActivity = mActivity;
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(mActivity);
		bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
		loginskiptostatus = bestDoInfoSharedPrefs.getString("loginskiptostatus", "");
	}

	/**
	 * 保存第三方登录状态
	 */
	public void saveThirdPartyInfo(String ThirdPartyLoginMode) {
		bestDoInfoEditor.putString("ThirdPartyLoginMode", "" + ThirdPartyLoginMode);
		bestDoInfoEditor.commit();
	}

	/**
	 * 保存登录信息
	 * 
	 * @param loginInfo
	 */
	public void getLoginInfo(UserLoginInfo loginInfo) {
		if (loginInfo != null) {
			String isBind = loginInfo.getIsBind();
			if (!TextUtils.isEmpty(isBind) && isBind.equals("1")) {
				// 为1时需要绑定手机号
				Intent intent2 = new Intent(mActivity, UserSetUpdatePwActivity.class);
				intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent2.putExtra("intentStatus", "bindMobile");
				intent2.putExtra("mobile", "");
				mActivity.startActivity(intent2);
				CommonUtils.getInstance().setPageIntentAnim(intent2, mActivity);
			} else {
				String uid = loginInfo.getUid();
				String sid = loginInfo.getSid();
				String mobile = loginInfo.getMobile();
				String ablum = loginInfo.getAblum();
				String loginName = loginInfo.getLoginName();
				String nickName = loginInfo.getNickName();
				String realName = loginInfo.getRealName();
				String sex = loginInfo.getSex();
				String qq = loginInfo.getQq();
				String email = loginInfo.getEmail();
				String nameChangeTimes = loginInfo.getNameChangeTimes();
				String bid = loginInfo.getBid();
				String bname = loginInfo.getBname();
				String deptId = loginInfo.getDeptId();
				String deptName = loginInfo.getDeptName();
				String ranked_show = loginInfo.getRanked_show();
				String active_url = loginInfo.getActive_url();
				String company_title = loginInfo.getCompany_title();
				String department_title = loginInfo.getDepartment_title();
				bestDoInfoEditor.putString("loginStatus", Constans.getInstance().loginStatus);
				bestDoInfoEditor.putString("uid", uid + "");
				bestDoInfoEditor.putString("sid", sid);
				bestDoInfoEditor.putString("mobile", "" + mobile);
				bestDoInfoEditor.putString("ablum", "" + ablum);
				bestDoInfoEditor.putString("loginName", "" + loginName);
				bestDoInfoEditor.putString("nickName", "" + nickName);
				bestDoInfoEditor.putString("realName", "" + realName);
				bestDoInfoEditor.putString("bid", "" + bid);
				bestDoInfoEditor.putString("bname", "" + bname);
				bestDoInfoEditor.putString("deptId", "" + deptId);
				bestDoInfoEditor.putString("deptName", "" + deptName);
				bestDoInfoEditor.putString("ranked_show", "" + ranked_show);
				bestDoInfoEditor.putString("active_url", "" + active_url);

				bestDoInfoEditor.putString("company_title", "" + company_title);
				bestDoInfoEditor.putString("department_title", "" + department_title);
				bestDoInfoEditor.putString("sex", "" + sex);
				bestDoInfoEditor.putString("qq", "" + qq);
				bestDoInfoEditor.putString("email", "" + email);
				bestDoInfoEditor.putString("nameChangeTimes", "" + nameChangeTimes);

				bestDoInfoEditor.putString("name", "" + loginInfo.getName());
				bestDoInfoEditor.putString("department", "" + loginInfo.getDepartment());
				bestDoInfoEditor.putString("corp_id", "" + loginInfo.getCorp_id());
				bestDoInfoEditor.putString("manage_office_name", "" + loginInfo.getManage_office_name());
				bestDoInfoEditor.putString("privilege_id", "" + loginInfo.getPrivilege_id());
				bestDoInfoEditor.putString("corp_name", "" + loginInfo.getCorp_name());
				bestDoInfoEditor.putString("corp_head_title", "" + loginInfo.getCorp_head_title());

				bestDoInfoEditor.commit();
				if (TextUtils.isEmpty(mobile)) {
					// 为1时需要绑定手机号
					Intent intent2 = new Intent(mActivity, UserSetUpdatePwActivity.class);
					intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent2.putExtra("intentStatus", "bindMobileByOldUser");
					intent2.putExtra("mobile", mobile);
					mActivity.startActivity(intent2);
					CommonUtils.getInstance().setPageIntentAnim(intent2, mActivity);
				} else {
					skipToPage();
				}
			}
		} else {
			CommonUtils.getInstance().initToast(mActivity, "登录失败，请重新登录");
		}
	}

	/**
	 * 返回前返回是否是收藏列表跳转
	 */
	public void doBackCheck() {
		if (loginskiptostatus.equals(Constans.getInstance().loginskiptoinmain)) {
			intents = new Intent();
			intents.setAction(mActivity.getResources().getString(R.string.action_home));
			intents.putExtra("type", mActivity.getResources().getString(R.string.action_home_type_back));
			mActivity.sendBroadcast(intents);
		}
	}

	/**
	 * 登录后多方向跳转判断
	 */
	public void skipToPage() {
		if (loginskiptostatus.equals(Constans.getInstance().loginskiptoinmain)) {
			intents = new Intent();
			intents.setAction(mActivity.getResources().getString(R.string.action_home));
			intents.putExtra("type", mActivity.getResources().getString(R.string.action_home_type_loginregok));
			mActivity.sendBroadcast(intents);
			mActivity.finish();

		} else if (loginskiptostatus.equals(Constans.getInstance().loginrefreshDetail)) {
			/**
			 * 刷新场馆详情会员报价折扣信息
			 */
			intents = new Intent();
			intents.setAction(mActivity.getString(R.string.action_StadiumCollectUtils));
			intents.putExtra("type", Constans.getInstance().loginrefreshDetail);
			mActivity.sendBroadcast(intents);
			mActivity.finish();
		} else if (loginskiptostatus.equals(Constans.getInstance().loginskiptoaddfavorite)) {
			/**
			 * 当从详情或更多详情进来时，更新详情或更多详情的收藏状态
			 */
			intents = new Intent();
			intents.setAction(mActivity.getString(R.string.action_StadiumCollectUtils));
			mActivity.sendBroadcast(intents);
			mActivity.finish();
		} 
//		else if (loginskiptostatus.equals(Constans.getInstance().loginskiptocreateorder)) {
//			intents = new Intent();
//			intents.setAction(mActivity.getString(R.string.action_StadiumCollectUtils));
//			intents.putExtra("type", Constans.getInstance().loginskiptoADDMEMBER);
//			mActivity.sendBroadcast(intents);
//			Intent intent = new Intent(mActivity, CreatOrdersActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//			mActivity.startActivity(intent);
//			CommonUtils.getInstance().setPageIntentAnim(intent, mActivity);
//			mActivity.finish();
//		} else if (loginskiptostatus.equals(Constans.getInstance().loginskiptoPYLWyuding)) {
//			intents = new Intent();
//			intents.setAction(mActivity.getString(R.string.action_StadiumCollectUtils));
//			intents.putExtra("type", Constans.getInstance().loginskiptoADDMEMBER);
//			mActivity.sendBroadcast(intents);
//			Intent intent = new Intent(mActivity, CreatOrdersGetVenuePYLWActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//			mActivity.startActivity(intent);
//			CommonUtils.getInstance().setPageIntentAnim(intent, mActivity);
//			mActivity.finish();
//		}
		else if (loginskiptostatus.equals(Constans.getInstance().loginskiptoMeber)) {
			Intent intent = new Intent(mActivity, UserMeberActiyity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			mActivity.startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, mActivity);
			mActivity.finish();
		} else if (loginskiptostatus.equals(Constans.getInstance().loginskiptologin403)) {
			CommonUtils.getInstance().skipMainActivity(mActivity);

		} else if (loginskiptostatus.equals(Constans.getInstance().loginskiptoPersonImgActivity)) {
			mActivity.finish();
		} else {
			CommonUtils.getInstance().skipCenterActivity(mActivity);
		}
		bestDoInfoEditor.putString("loginskiptostatus", "");
		bestDoInfoEditor.commit();
	}
}
