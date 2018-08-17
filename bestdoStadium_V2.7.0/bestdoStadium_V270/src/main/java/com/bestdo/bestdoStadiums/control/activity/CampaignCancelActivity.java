/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CampaignCancelBusiness;
import com.bestdo.bestdoStadiums.business.CampaignCancelBusiness.CampaignCancelCallback;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 上午11:30:28
 * @Description 类描述：活动取消
 */
public class CampaignCancelActivity extends BaseActivity {
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private TextView pagetop_tv_you;
	private EditText campaignpublish_et_title;

	private String uid;
	private String activity_id;

	@Override
	public void onClick(View v) {
		CommonUtils.getInstance().closeSoftInput(context);
		reason = campaignpublish_et_title.getText().toString();
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			if (!TextUtils.isEmpty(reason)) {
				cancelEditDialog();
			} else {
				doback();
			}
			break;
		case R.id.pagetop_tv_you:
			if (!TextUtils.isEmpty(reason) && reason.length() >= 4) {
				saveEditDialog();
			} else {
				CommonUtils.getInstance().initToast(context, "请至少输入4~30位字符");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.campaign_cancel);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);

		campaignpublish_et_title = (EditText) findViewById(R.id.campaignpublish_et_title);
	}

	@Override
	protected void setListener() {
		pagetop_tv_name.setText("活动取消");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setText("发送");
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
		SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		Intent intent = getIntent();
		activity_id = intent.getStringExtra("activity_id");
	}

	private ProgressDialog mDialog;
	private HashMap<String, String> mhashmap;
	private String reason;

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(this);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void processLogic() {

	}

	/**
	 * 发布
	 */
	private void clickBtn() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("activity_id", activity_id);
		mhashmap.put("reason", "" + reason);
		System.err.println(mhashmap);
		new CampaignCancelBusiness(context, mhashmap, new CampaignCancelCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					String msg = (String) dataMap.get("msg");
					CommonUtils.getInstance().initToast(context, msg + "");
					if (code.equals("200")) {
						CommonUtils.getInstance().exitPayPage();
						doback();
					}

				}
				CommonUtils.getInstance().setClearCacheDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	public void cancelEditDialog() {
		final MyDialog selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_logout);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView myexit_text = (TextView) selectDialog.findViewById(R.id.myexit_text);
		TextView myexit_text_cirt = (TextView) selectDialog.findViewById(R.id.myexit_text_cirt);
		TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定

		myexit_text.setText("活动取消");
		myexit_text_cirt.setText("是否放弃编辑？");
		text_off.setText("继续");
		text_sure.setText("放弃");
		text_sure.setTextColor(getResources().getColor(R.color.btn_bg));
		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				doback();
			}
		});
	}

	/**
	 * 编辑时判断是否修改地点时间
	 */
	public void saveEditDialog() {
		final MyDialog selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_logout);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView myexit_text = (TextView) selectDialog.findViewById(R.id.myexit_text);
		TextView myexit_text_cirt = (TextView) selectDialog.findViewById(R.id.myexit_text_cirt);
		TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定

		myexit_text.setText("活动取消");
		myexit_text_cirt.setText("是否取消本次活动？");
		text_off.setText("放弃");
		text_sure.setText("取消活动");
		text_sure.setTextColor(getResources().getColor(R.color.btn_bg));
		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				clickBtn();
			}
		});
	}

	private void doback() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doback();
		}
		return false;
	}
}
