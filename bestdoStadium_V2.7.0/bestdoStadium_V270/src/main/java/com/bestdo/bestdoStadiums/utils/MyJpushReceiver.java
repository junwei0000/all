package com.bestdo.bestdoStadiums.utils;

import org.json.JSONObject;

import com.bestdo.bestdoStadiums.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyJpushReceiver extends BroadcastReceiver {
	private static final String TAG = "MyJpushReceiver";
	private SharedPreferences bestDoInfoSharedPrefs;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "接收Registration Id : " + regId);
			// SDK 向 JPush Server 注册所得到的注册 全局唯一的 ID ，可以通过此 ID 向对应的客户端发送消息和通知。
			// send the Registration Id to your server...
		} else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "接收UnRegistration Id : " + regId);
			// send the UnRegistration Id to your server...
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			Log.d(TAG, "收到了自定义消息 Push :" + regId);

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "接收到推送下来的通知-------------------------------");
			bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(context);
			String loging = bestDoInfoSharedPrefs.getString("login", "");
			if (!TextUtils.isEmpty(loging) && loging.equals("loging")) {
				// 登录状态才接收下线通知，得到附加字段
				String uid = bestDoInfoSharedPrefs.getString("uid", "");
				String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
				String userloginstatus = getLogStatus(extras);// 该用户id的403下线通知
				if (!TextUtils.isEmpty(userloginstatus) && userloginstatus.equals(uid + "_403")) {
					Intent intents = new Intent();
					intents.setAction(context.getString(R.string.action_home));
					context.sendBroadcast(intents);
				}
			}
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "用户点击打开了通知---------------------------------");

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			Log.d(TAG, "用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
		}
	}

	private String getLogStatus(String json) {
		String status = "";
		try {
			if (!TextUtils.isEmpty(json)) {
				JSONObject jsonObject = new JSONObject(json);
				status = jsonObject.optString("status");
			}
		} catch (Exception e) {
			status = "";
		}
		return status;
	}

}
