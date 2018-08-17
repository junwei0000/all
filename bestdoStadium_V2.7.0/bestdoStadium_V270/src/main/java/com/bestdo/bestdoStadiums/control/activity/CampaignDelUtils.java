/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CampaignGMDelBusiness;
import com.bestdo.bestdoStadiums.business.CampaignGMDelBusiness.CampaignGMDelCallback;
import com.bestdo.bestdoStadiums.business.CampaignGoodMonmentBusiness;
import com.bestdo.bestdoStadiums.business.CampaignGoodMonmentBusiness.CampaignGoodMonmentCallback;
import com.bestdo.bestdoStadiums.model.CircleBean;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.MyDialog;

/**
 * @author 作者：zoc
 * @date 创建时间：2017-3-13 下午1:54:57
 * @Description 类描述：
 */
public class CampaignDelUtils {
	Handler mHandler;
	int mHandlerID;
	private HashMap<String, String> mhashmap;
	Context context;

	public CampaignDelUtils(Context context, Handler mHandler, int mHandlerID) {
		super();
		this.mHandler = mHandler;
		this.mHandlerID = mHandlerID;
		this.context = context;
	}

	public void showDialogProcessType(final String won_id, final String uid, final int position) {
		final MyDialog selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_pylwmangetishi);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView myexit_text_title = (TextView) selectDialog.findViewById(R.id.myexit_text_title);
		TextView myexit_text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);
		TextView myexit_text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);
		myexit_text_title.setText("确认删除该活动吗？");
		myexit_text_off.setText("取消");
		myexit_text_sure.setText("确认");
		myexit_text_off.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
		myexit_text_sure.setTextColor(context.getResources().getColor(R.color.text_contents_color));
		myexit_text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		myexit_text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				processLogic(won_id, uid, position);
			}
		});
	}

	protected void processLogic(String won_id, String uid, final int position) {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("won_id", won_id);
		mhashmap.put("uid", uid);

		System.err.println(mhashmap);
		new CampaignGMDelBusiness(context, mhashmap, new CampaignGMDelCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					if (status.equals("400")) {
						String data = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, data);
					} else if (status.equals("200")) {
						String data = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, data);
						Message msg = mHandler.obtainMessage();
						msg.arg1 = position;
						msg.what = mHandlerID;
						mHandler.sendMessage(msg);
					}
				} else {
					CommonUtils.getInstance().initToast(context, context.getResources().getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}
}
