package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;

import android.app.Activity;
import android.os.Handler;

import com.bestdo.bestdoStadiums.business.StadiumAddFavoriteBusiness;
import com.bestdo.bestdoStadiums.business.StadiumDelFavoriteBusiness;
import com.bestdo.bestdoStadiums.business.StadiumAddFavoriteBusiness.GetStadiumAddFavoriteCallback;
import com.bestdo.bestdoStadiums.business.StadiumDelFavoriteBusiness.GetStadiumDelFavoriteCallback;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.R;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-3 下午4:23:56
 * @Description 类描述：收藏，ADD cancel
 */
public class StadiumCollectUtils {

	/**
	 * 加载状态-默认true加载完成
	 */
	public boolean clickloadingoverstatus = true;
	public boolean collectStatus = false;// 收藏状态
	public Activity context;
	public String mer_item_id;
	public String uid;
	public Handler mDetailHandler;
	public int UPDATECOLLECTSTATUS;

	public StadiumCollectUtils(Activity context, String mer_item_id, String uid, Handler mDetailHandler,
			int UPDATECOLLECTSTATUS) {
		super();
		this.mDetailHandler = mDetailHandler;
		this.UPDATECOLLECTSTATUS = UPDATECOLLECTSTATUS;
		this.context = context;
		this.uid = uid;
		this.mer_item_id = mer_item_id;
	}

	HashMap<String, String> mhashmap;

	/**
	 * 点击收藏
	 */
	public void getClickFavorite() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(context)) {
			CommonUtils.getInstance().initToast(context, context.getString(R.string.net_tishi));
			return;
		}
		if (collectStatus) {
			// 友盟埋点：在场馆详情页点击收藏按钮取消收藏场馆
			CommonUtils.getInstance().umengCount(context, "1005002", null);
			// 如果已经收藏，点击进行取消收藏
			cancelStadiumFavorite();
		} else {
			// 友盟埋点：在场馆详情页点击收藏按钮收藏场馆
			CommonUtils.getInstance().umengCount(context, "1005001", null);
			// 如果没有收藏，点击进行收藏
			addStadiumFavorite();

		}
	}

	/**
	 * 取消收藏
	 */
	private void cancelStadiumFavorite() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", "" + uid);
		mhashmap.put("mer_item_id", mer_item_id);
		new StadiumDelFavoriteBusiness(context, mhashmap, new GetStadiumDelFavoriteCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				clickloadingoverstatus = true;
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					} else if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					} else if (status.equals("200")) {
						CommonUtils.getInstance().definedToast(context, "取消收藏成功", "cancelcollect");
						collectStatus = false;
						mDetailHandler.sendEmptyMessage(UPDATECOLLECTSTATUS);
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					}
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	/**
	 * 添加收藏
	 * 
	 * @param uid
	 */
	private void addStadiumFavorite() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", "" + uid);
		mhashmap.put("mer_item_id", mer_item_id);
		new StadiumAddFavoriteBusiness(context, mhashmap, new GetStadiumAddFavoriteCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				clickloadingoverstatus = true;
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					String msg = (String) dataMap.get("msg");
					if (status.equals("400")) {
						CommonUtils.getInstance().initToast(context, msg);
					} else if (status.equals("403")) {

						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					} else if (status.equals("200")) {
						CommonUtils.getInstance().definedToast(context, "收藏成功", "");
						collectStatus = true;
						mDetailHandler.sendEmptyMessage(UPDATECOLLECTSTATUS);
					} else {
						CommonUtils.getInstance().initToast(context, msg);
					}
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

}
