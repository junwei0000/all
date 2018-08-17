package com.bestdo.bestdoStadiums.wxapi;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.activity.BaseActivity;
import com.bestdo.bestdoStadiums.control.map.BestDoApplication;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.PayUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
	LinearLayout pagetop_layout_back, page_top_layout;
	TextView pagetop_tv_name;
	TextView payzfb_webviewtishi;
	private IWXAPI api;
	private SharedPreferences bestDoInfoSharedPrefs;

	String remind;
	String oid;
	private PayUtils payUtils;
	private String uid;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.pay_zfb);
		CommonUtils.getInstance().addActivity(this);
		api = BestDoApplication.getInstance().msgApi;
		api.handleIntent(getIntent(), this);
		
	}

	@Override
	protected void findViewById() {
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		payzfb_webviewtishi = (TextView) findViewById(R.id.payzfb_webviewtishi);
		page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		CommonUtils.getInstance().setViewTopHeigth(context, page_top_layout);
		pagetop_tv_name.setText(getString(R.string.payWX_pagetop_name));
		payzfb_webviewtishi.setText(getString(R.string.payWX_loadtishi));
	}

	@Override
	protected void processLogic() {
		
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	/**
	 * 0 成功 展示成功页面 。 -1 错误
	 * 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。 -2 用户取消
	 * 无需处理。发生场景：用户不支付了，点击取消，返回APP。
	 */
	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
			remind = bestDoInfoSharedPrefs.getString("remind", "");
			oid = bestDoInfoSharedPrefs.getString("oid", "");
			uid = bestDoInfoSharedPrefs.getString("uid", "");
			String skiptIntent=CommonUtils.getInstance().getPayWxStatus();
			payUtils = new PayUtils(this, oid, uid, remind, "", "", skiptIntent);
			payUtils.ordermoney=CommonUtils.getInstance().paywxMoney;
			if (resp.errCode == 0) {
				// 当完成时，跳回成功页面
				payUtils.seccessSkip();
			} else {
				payUtils.failureSkip();
			}
			finish();
		}
	}

	private void nowFinish() {
		finish();
	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			nowFinish();
		}
		return false;
	}
}