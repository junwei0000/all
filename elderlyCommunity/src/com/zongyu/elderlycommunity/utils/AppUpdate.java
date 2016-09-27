package com.zongyu.elderlycommunity.utils;

import java.util.HashMap;

import com.zongyu.elderlycommunity.R;
import com.zongyu.elderlycommunity.business.GetVersonBusiness;
import com.zongyu.elderlycommunity.business.GetVersonBusiness.GetVersonCallback;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午3:15:35
 * @Description 类描述：app更新
 */
public class AppUpdate {
	private Context context;
	private MyDialog selectDialog;
	String dialogstatus = "";

	public AppUpdate(Context context) {
		this.context = context;
	}

	/**
	 * 方法说明：启动异步任务
	 * 
	 * @param updateDirection
	 */
	public void startUpdateAsy(String updateDirection) {
		String verName = ConfigUtils.getInstance().getVerName(context);
		if (!TextUtils.isEmpty(verName) && !dialogstatus.equals("show")) {
			getServerVerCode(verName, updateDirection);
			dialogstatus = "show";
		}
	}

	HashMap<String, String> mhashmap;

	/**
	 * 方法说明：与服务器交互获取当前程序版本号
	 * 
	 * @param androidVersion
	 * @param EntranceTag
	 * @return
	 */
	private String getServerVerCode(final String appVersion,
			final String updateDirection) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(context)) {
			return null;
		}
		mhashmap = new HashMap<String, String>();
		mhashmap.put("version", appVersion);
		new GetVersonBusiness(context, mhashmap, new GetVersonCallback() {

			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						String url = (String) dataMap.get("url");
						String description = (String) dataMap
								.get("description");
						String version = (String) dataMap.get("version");
						String level = (String) dataMap.get("level");
						if (level.equals("0")) {
							// if
							// (updateDirection.equals(Constans.getInstance().updateVersionFromSetPage))
							// {
							// CommonUtils.getInstance().initToast(context,
							// "app已为最新版本");
							// }
						} else {// 必须更新
							defineUpdated(version, url, level, description);
						}
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap,
						dataMap);
			}
		});

		return null;
	}

	/**
	 * 方法说明：更新对话框提示 后台更新
	 * 
	 * @param androidVersion
	 * @param url
	 * @param level
	 * @param description
	 */
	public void defineUpdated(final String androidVersion, final String url,
			final String level, String description) {
		selectDialog = new MyDialog(context, R.style.dialog,
				R.layout.dialog_myupdate);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		selectDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface arg0) {
				dialogstatus = "";
			}
		});
		selectDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				// if (level.equals("2") && selectDialog.isShowing()) {
				// // 必须更新
				// return true;
				// } else {
				return false;
				// }
			}
		});
		TextView myupdate_tv_updatenow = (TextView) selectDialog
				.findViewById(R.id.myupdate_tv_updatenow);
		TextView myupdate_tv_updateafter = (TextView) selectDialog
				.findViewById(R.id.myupdate_tv_updateafter);
		// if (level.equals("2")) {
		// 必须更新
		// myupdate_tv_updateafter.setVisibility(View.GONE);
		// }
		myupdate_tv_updatenow.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				selectDialog.dismiss();
				Intent updateIntent = new Intent(context, UpdateService.class);
				updateIntent.putExtra("url", url);
				context.startService(updateIntent);
			}
		});
		myupdate_tv_updateafter.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				selectDialog.dismiss();
			}
		});

	}

}
