package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.UserOrderDetailUnsubscribBusiness;
import com.bestdo.bestdoStadiums.business.UserOrdersBusiness;
import com.bestdo.bestdoStadiums.business.UserSendIdearBusiness;
import com.bestdo.bestdoStadiums.business.UserOrderDetailUnsubscribBusiness.GetCallback;
import com.bestdo.bestdoStadiums.business.UserOrdersBusiness.GetUserOrdersCallback;
import com.bestdo.bestdoStadiums.business.UserSendIdearBusiness.GetUserSendIdearCallback;
import com.bestdo.bestdoStadiums.control.adapter.FragmentAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserOrderAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserOrderSportAdapter;
import com.bestdo.bestdoStadiums.control.fragment.UserConfirmingFragment;
import com.bestdo.bestdoStadiums.control.fragment.UserFinishFragment;
import com.bestdo.bestdoStadiums.control.fragment.UserWaitPayFragment;
import com.bestdo.bestdoStadiums.control.fragment.UsersStayoffFragment;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.model.UserOrdersInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.SearchGetSportParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午4:28:11
 * @Description 类描述：用户订单--申请退单
 */
public class UserOrderUnsubscribActivity extends BaseActivity {

	private EditText userorderdetailunsubscrib_tv_content;
	private RelativeLayout userorderdetailunsubscrib_relay_reason;
	private TextView userorderdetailunsubscrib_tv_reason;
	private Button click_btn;
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_back;
	private ProgressDialog mDialog;
	private String idea_content;

	private String uid;
	private String oid;
	View parent;
	PopupWindow mPopupWindow;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.click_btn:
			idea_content = userorderdetailunsubscrib_tv_reason.getText()
					+ userorderdetailunsubscrib_tv_content.getText().toString();
			CommonUtils.getInstance().closeSoftInput(this);
			if (!TextUtils.isEmpty(idea_content)) {
				sendId();
			}
			break;
		case R.id.userorderdetailunsubscrib_relay_reason:
			initPopuptWindow();
			break;
		case R.id.reason_tv_weather:
			mPopupWindow.dismiss();
			userorderdetailunsubscrib_tv_reason.setText("天气原因");
			break;
		case R.id.reason_tv_personal:
			mPopupWindow.dismiss();
			userorderdetailunsubscrib_tv_reason.setText("个人原因");
			break;
		case R.id.reason_tv_other:
			mPopupWindow.dismiss();
			userorderdetailunsubscrib_tv_reason.setText("其他");
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_order_detail_unsubscrib);
		parent = LayoutInflater.from(context).inflate(R.layout.user_order_detail_unsubscrib, null);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		userorderdetailunsubscrib_relay_reason = (RelativeLayout) findViewById(
				R.id.userorderdetailunsubscrib_relay_reason);
		userorderdetailunsubscrib_tv_content = (EditText) findViewById(R.id.userorderdetailunsubscrib_tv_content);
		userorderdetailunsubscrib_tv_reason = (TextView) findViewById(R.id.userorderdetailunsubscrib_tv_reason);

		click_btn = (Button) findViewById(R.id.click_btn);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
	}

	@Override
	protected void setListener() {
		click_btn.setText("提交申请");
		userorderdetailunsubscrib_relay_reason.setOnClickListener(this);
		click_btn.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		userorderdetailunsubscrib_tv_reason.addTextChangedListener(mTextWatcher);
		userorderdetailunsubscrib_tv_content.addTextChangedListener(mTextWatcher);
		Intent intent = getIntent();
		uid = intent.getStringExtra("uid");
		oid = intent.getStringExtra("oid");
	}

	TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			idea_content = userorderdetailunsubscrib_tv_reason.getText()
					+ userorderdetailunsubscrib_tv_content.getText().toString();
			isClick(idea_content);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	@Override
	protected void processLogic() {
		pagetop_tv_name.setText("申请退订");
		click_btn.setText("提交申请");
	}

	/**
	 * 根据输入框内容判断按钮颜色
	 * 
	 * @param phone
	 * @param password
	 */
	private void isClick(String idea_content) {
		if (TextUtils.isEmpty(idea_content)) {
			click_btn.setTextColor(getResources().getColor(R.color.btn_noclick_color));
		} else {
			click_btn.setTextColor(getResources().getColor(R.color.white));
		}
	}

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

	/**
	 * 方法说明：创建PopWindow
	 * 
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void initPopuptWindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.user_order_detail_unsubscrib_pop, null);
		TextView reason_tv_weather = (TextView) popupWindow.findViewById(R.id.reason_tv_weather);
		TextView reason_tv_personal = (TextView) popupWindow.findViewById(R.id.reason_tv_personal);
		TextView reason_tv_other = (TextView) popupWindow.findViewById(R.id.reason_tv_other);

		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setAnimationStyle(R.style.dialog);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		reason_tv_weather.setOnClickListener(this);
		reason_tv_personal.setOnClickListener(this);
		reason_tv_other.setOnClickListener(this);
	}

	HashMap<String, String> mhashmap;

	private void sendId() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("refund_reason", idea_content);
		mhashmap.put("oid", oid);
		mhashmap.put("uid", uid);
		new UserOrderDetailUnsubscribBusiness(this, mhashmap, new GetCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						Intent intent = new Intent();
						intent.putExtra("type", "UPDATESTATUS");
						intent.setAction(context.getString(R.string.action_Success_Faliure)); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
						context.sendBroadcast(intent); // 发送广播

						Intent intentskip = new Intent(UserOrderUnsubscribActivity.this, Success_OrderUnsubscrib.class);
						intentskip.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						intentskip.putExtra("oid", oid);
						startActivity(intentskip);
						doBack();
					} else if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					} else {
						String data = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, data);
					}
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	/**
	 * 退出页面前执行操作
	 */
	private void doBack() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doBack();
		}
		return false;
	};

	@Override
	protected void onDestroy() {

		RequestUtils.cancelAll(this);
		super.onDestroy();
	}
}
