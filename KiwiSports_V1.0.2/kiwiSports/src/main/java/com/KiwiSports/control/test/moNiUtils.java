package com.KiwiSports.control.test;

import android.content.Context;

import com.KiwiSports.utils.CommonUtils;

/**
 * 数据库测试
 * 
 * @author Dell
 * 
 */
public class moNiUtils {

	private static class SingletonHolder {
		private static final moNiUtils INSTANCE = new moNiUtils();
	}

	private moNiUtils() {
	}

	public static final moNiUtils getInstance() {
		return SingletonHolder.INSTANCE;
	}

//	public void TestAd(TrackDBOpenHelper mDB, String uid, String token,
//			String access_token, String savetimeTamp, Context context) {
//		add(mDB, uid, token, access_token, savetimeTamp, context);
//	}
//
//	public String add(TrackDBOpenHelper mDB, String uid, String token,
//			String access_token, String savetimeTamp, Context context) {
//		String recordDatas = parseJSON(context);
//		mDB.addTableTrackInfo(uid, token, access_token, recordDatas,
//				savetimeTamp, mDB.currentTrackNOW);
//		return recordDatas;
//	}
//
//	public String add2(TrackDBOpenHelper mDB, String uid, String token,
//			String access_token, String savetimeTamp, Context context) {
//		String recordDatas = parseJSON2(context);
//		return recordDatas;
//	}
//
//	public String parseJSON2(Context context) {
//		String jsondate = CommonUtils.getInstance().getFromAssets(context,
//				"tracktest2.txt");
//		return jsondate;
//	}

	public String parseJSON(Context context) {
		String jsondate = CommonUtils.getInstance().getFromAssets(context,
				"tracktest.txt");
		return jsondate;
	}
}
