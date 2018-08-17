package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.CampaignAddSignupBusiness;
import com.bestdo.bestdoStadiums.business.CampaignAddSignupBusiness.GetCampaignAddSignupCallback;
import com.bestdo.bestdoStadiums.business.CampaignDelSignupBusiness;
import com.bestdo.bestdoStadiums.business.CampaignDelSignupBusiness.GetCampaignDelSignupCallback;
import com.bestdo.bestdoStadiums.utils.CommonUtils;

import android.app.Activity;
import android.os.Handler;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-3 下午4:23:56
 * @Description 类描述：报名，ADD cancel
 */
public class CampaignSignupUtils {

	/**
	 * 加载状态-默认true加载完成
	 */
	public boolean clickloadingoverstatus = true;
	public Activity context;
	public String activity_id;
	public String uid;
	public Handler mDetailHandler;
	public int RELOAD;

	public CampaignSignupUtils(Activity context, String activity_id, String uid, Handler mDetailHandler, int RELOAD) {
		super();
		this.mDetailHandler = mDetailHandler;
		this.RELOAD = RELOAD;
		this.context = context;
		this.uid = uid;
		this.activity_id = activity_id;
	}

	HashMap<String, String> mhashmap;

	/**
	 * 取消
	 */
	public void cancelCampaignSignup() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", "" + uid);
		mhashmap.put("activity_id", activity_id);
		new CampaignDelSignupBusiness(context, mhashmap, new GetCampaignDelSignupCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				clickloadingoverstatus = true;
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					String msg = (String) dataMap.get("msg");
					if (code.equals("400")) {
						CommonUtils.getInstance().initToast(context, msg);
					} else if (code.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					} else if (code.equals("200")) {
						CommonUtils.getInstance().initToast(context, msg);
						mDetailHandler.sendEmptyMessage(RELOAD);
					} else {
						CommonUtils.getInstance().initToast(context, msg);
					}
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	/**
	 * 添加
	 * 
	 * @param uid
	 */
	public void addCampaignSignup() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", "" + uid);
		mhashmap.put("activity_id", activity_id);
		new CampaignAddSignupBusiness(context, mhashmap, new GetCampaignAddSignupCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				clickloadingoverstatus = true;
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					String msg = (String) dataMap.get("msg");
					if (status.equals("400")) {
						CommonUtils.getInstance().initToast(context, msg);
					} else if (status.equals("403")) {

						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					} else if (status.equals("200")) {
						CommonUtils.getInstance().initToast(context, msg);
					} else {
						CommonUtils.getInstance().initToast(context, msg);
					}
					mDetailHandler.sendEmptyMessage(RELOAD);
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

}
