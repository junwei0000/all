package com.KiwiSports.control.activity;

import com.KiwiSports.R;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.MyDialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 唯一设备登录提示
 * 
 * @author jun
 * 
 */
public class UserLoginBack403Utils {
	private static UserLoginBack403Utils mUtils;

	public synchronized static UserLoginBack403Utils getInstance() {
		if (mUtils == null) {
			mUtils = new UserLoginBack403Utils();
		}
		return mUtils;
	}

	/**
	 * 单点登录弹层
	 * 
	 * @param mActivity
	 */
	public void sendBroadcastLoginBack403(Activity mActivity) {
		Intent intents = new Intent();
		intents.setAction(mActivity.getString(R.string.action_home));
		intents.putExtra("type", mActivity.getString(R.string.action_home_type_login403));
		mActivity.sendBroadcast(intents);
	}

	/**
	 * 提示设备重新登录
	 */
	public void showDialogPromptReLogin(final Activity context) {
		final MyDialog selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_logout);
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		selectDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				return true;
			}
		});
		TextView myexit_text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);
		TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);
		myexit_text_off.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				CommonUtils.getInstance().clearAllBestDoInfoSharedPrefs(context);
				CommonUtils.getInstance().setLoginBack403(context);
			}
		});
		text_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				CommonUtils.getInstance().clearAllBestDoInfoSharedPrefs(context);
				CommonUtils.getInstance().setLoginBack403(context);
			}
		});
	}

}
