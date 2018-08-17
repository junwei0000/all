package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bestdo.bestdoStadiums.business.GetSportsListBusiness;
import com.bestdo.bestdoStadiums.business.GetSportsListBusiness.GetSportsListBusinessCallback;
import com.bestdo.bestdoStadiums.business.UserOrdersBusiness;
import com.bestdo.bestdoStadiums.business.UserOrdersBusiness.GetUserOrdersCallback;
import com.bestdo.bestdoStadiums.control.adapter.FragmentAdapter;
import com.bestdo.bestdoStadiums.control.adapter.FragmentAdapter.OnReloadListener;
import com.bestdo.bestdoStadiums.control.adapter.UserOrderAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserOrderSportAdapter;
import com.bestdo.bestdoStadiums.control.fragment.UserAllFragment;
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
import com.bestdo.bestdoStadiums.utils.parser.UserOrderGetSportParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午4:28:11
 * @Description 类描述：用户订单--待下场，待付款
 */
public class UserOrderActivity extends FragmentActivity implements OnClickListener {
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ImageView pagetop_iv_toparrow;
	private TextView userorder_tv_waitpay, userorder_tv_confirming, userorder_tv_stayoff, userorder_tv_overed;
	private ImageView userorder_iv_cursor;
	private ViewPager userorder_vPager;

	private String ordrStatuas, uid;

	public int selectArg0;
	private TextView userorder_tv_all;
	private ImageView pagetop_iv_you;
	private LinearLayout line;
	private LinearLayout pagetop_layout_you;

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_layout_you:
			changeOrderStatus();
			break;
		case R.id.userorder_tv_all:
			selectArg0 = Integer.valueOf(getResources().getString(R.string.order_index_all));
			updateCheckView();
			break;
		case R.id.userorder_tv_waitpay:
			selectArg0 = Integer.valueOf(getResources().getString(R.string.order_index_waitpay));
			updateCheckView();
			break;
		case R.id.userorder_tv_confirming:
			selectArg0 = Integer.valueOf(getResources().getString(R.string.order_index_confirming));
			updateCheckView();
			break;
		case R.id.userorder_tv_stayoff:
			selectArg0 = Integer.valueOf(getResources().getString(R.string.order_index_stayoff));
			updateCheckView();
			break;
		case R.id.userorder_tv_overed:
			selectArg0 = Integer.valueOf(getResources().getString(R.string.order_index_canceled));
			updateCheckView();
			break;
		default:
			break;
		}
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		// 另外防止屏幕锁屏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initView();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		loadViewLayout();
		findViewById();
		setListener();
		processLogic();
	}

	protected void loadViewLayout() {
		setContentView(R.layout.user_order);
		CommonUtils.getInstance().addActivity(this);
		Constans.getInstance().mCampaingActivity = this;
	}

	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_layout_you = (LinearLayout) findViewById(R.id.pagetop_layout_you);
		pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);
		pagetop_iv_you.setVisibility(View.VISIBLE);
		line = (LinearLayout) findViewById(R.id.line);
		pagetop_iv_you.setBackgroundResource(R.drawable.userorder_iv_sport);
		pagetop_iv_toparrow = (ImageView) findViewById(R.id.pagetop_iv_toparrow);
		pagetop_iv_toparrow.setVisibility(View.GONE);
		userorder_tv_all = (TextView) findViewById(R.id.userorder_tv_all);
		userorder_tv_waitpay = (TextView) findViewById(R.id.userorder_tv_waitpay);
		userorder_tv_confirming = (TextView) findViewById(R.id.userorder_tv_confirming);
		userorder_tv_stayoff = (TextView) findViewById(R.id.userorder_tv_stayoff);
		userorder_tv_overed = (TextView) findViewById(R.id.userorder_tv_overed);
		userorder_iv_cursor = (ImageView) findViewById(R.id.userorder_iv_cursor);
		userorder_vPager = (ViewPager) findViewById(R.id.userorder_vPager);

	}

	protected void setListener() {
		pagetop_layout_you.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		userorder_tv_all.setOnClickListener(this);
		userorder_tv_waitpay.setOnClickListener(this);
		userorder_tv_confirming.setOnClickListener(this);
		userorder_tv_stayoff.setOnClickListener(this);
		userorder_tv_overed.setOnClickListener(this);
	}

	protected void processLogic() {
		try {
			uid = getIntent().getStringExtra("uid");
			ordrStatuas = getIntent().getStringExtra("status");
		} catch (Exception e) {
		}
		pagetop_tv_name.setText(getString(R.string.usercenter_order));
		// mSportList = SearchGetSportParser.getInstance().parseSportJSON(this);
		getSportsList();
		initPage();
	}

	HashMap<String, String> mhashmap;

	private void getSportsList() {
		mhashmap = new HashMap<String, String>();
		new GetSportsListBusiness(UserOrderActivity.this, mhashmap, new GetSportsListBusinessCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mSportList = (ArrayList<SportTypeInfo>) dataMap.get("mList");
					}
				}
			}

		});

	}

	/**
	 * 初始化ViewPager
	 */
	private FragmentAdapter mFragmentAdapter;
	private ArrayList<Fragment> fgs;
	private String merid = "";
	private String cid = "";

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public final int UPDATEORDERNUM = 0;
	public Handler mUpdateOrderNumHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATEORDERNUM:
				UserOrdersInfo mOrdersNumInfo = (UserOrdersInfo) msg.obj;
				int unpay_num = mOrdersNumInfo.getUnpay_num();
				int confirm_num = mOrdersNumInfo.getConfirm_num();
				int off_num = mOrdersNumInfo.getOff_num();
				if (unpay_num > 0) {
					userorder_tv_waitpay.setText(getResources().getText(R.string.order_waitpay_name) + "" + unpay_num);
				} else {
					userorder_tv_waitpay.setText(getResources().getText(R.string.order_waitpay_name));
				}
				if (confirm_num > 0) {
					userorder_tv_confirming
							.setText(getResources().getText(R.string.order_confirming_name) + "" + confirm_num);
				} else {
					userorder_tv_confirming.setText(getResources().getText(R.string.order_confirming_name));
				}
				if (off_num > 0) {
					userorder_tv_stayoff.setText(getResources().getText(R.string.order_stayoff_name) + "" + off_num);
				} else {
					userorder_tv_stayoff.setText(getResources().getText(R.string.order_stayoff_name));
				}
				break;

			}
		}
	};

	/**
	 * 初始化fragment数据
	 */
	private void InitViewPager() {
		fgs = new ArrayList<Fragment>();
		fgs.add(new UserAllFragment(this, uid));
		fgs.add(new UserWaitPayFragment(this, uid));
		fgs.add(new UserConfirmingFragment(this, uid));
		fgs.add(new UsersStayoffFragment(this, uid));
		fgs.add(new UserFinishFragment(this, uid));
		mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fgs);
		mFragmentAdapter.setOnReloadListener(new OnReloadListener() {
			@Override
			public void onReload() {
				fgs = null;
				List<Fragment> list = new ArrayList<Fragment>();
				list.add(new UserAllFragment(UserOrderActivity.this, uid));
				list.add(new UserWaitPayFragment(UserOrderActivity.this, uid));
				list.add(new UserConfirmingFragment(UserOrderActivity.this, uid));
				list.add(new UsersStayoffFragment(UserOrderActivity.this, uid));
				list.add(new UserFinishFragment(UserOrderActivity.this, uid));
				mFragmentAdapter.setPagerItems(list);
			}
		});
		userorder_vPager.setAdapter(mFragmentAdapter);
		updateCheckView();
		/**
		 * 其中参数可以设为0或者1，参数小于1时，会默认用1来作为参数，未设置之前，ViewPager会默认加载两个Fragment
		 */
		userorder_vPager.setOffscreenPageLimit(1);
		userorder_vPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void initPage() {
		if (ordrStatuas.equals(getResources().getString(R.string.order_all))) {
			selectArg0 = Integer.valueOf(getResources().getString(R.string.order_index_all));
		} else if (ordrStatuas.equals(getResources().getString(R.string.order_putParameters_waitpay))) {
			selectArg0 = Integer.valueOf(getResources().getString(R.string.order_index_waitpay));
		} else if (ordrStatuas.equals(getResources().getString(R.string.order_putParameters_confirming))) {
			selectArg0 = Integer.valueOf(getResources().getString(R.string.order_index_confirming));
		} else if (ordrStatuas.equals(getResources().getString(R.string.order_putParameters_stayoff))) {
			selectArg0 = Integer.valueOf(getResources().getString(R.string.order_index_canceled));
		}
		InitViewPager();
	}

	/**
	 * 获取适配器
	 * 
	 * @return
	 */
	public FragmentAdapter getAdapter() {
		return mFragmentAdapter;
	}

	/**
	 * 切换选中的view颜色
	 * 
	 * @param arg0
	 */
	private void updateCheckView() {
		InitImageView();
		setLineFollowSlide();
		userorder_vPager.setCurrentItem(selectArg0);
		userorder_tv_all.setTextColor(getResources().getColor(R.color.text_noclick_color));
		userorder_tv_waitpay.setTextColor(getResources().getColor(R.color.text_noclick_color));
		userorder_tv_confirming.setTextColor(getResources().getColor(R.color.text_noclick_color));
		userorder_tv_stayoff.setTextColor(getResources().getColor(R.color.text_noclick_color));
		userorder_tv_overed.setTextColor(getResources().getColor(R.color.text_noclick_color));
		if (selectArg0 == Integer.valueOf(getResources().getString(R.string.order_index_all))) {
			userorder_tv_all.setTextColor(getResources().getColor(R.color.blue));
		} else if (selectArg0 == Integer.valueOf(getResources().getString(R.string.order_index_waitpay))) {
			userorder_tv_waitpay.setTextColor(getResources().getColor(R.color.blue));
		} else if (selectArg0 == Integer.valueOf(getResources().getString(R.string.order_index_confirming))) {
			userorder_tv_confirming.setTextColor(getResources().getColor(R.color.blue));
		} else if (selectArg0 == Integer.valueOf(getResources().getString(R.string.order_index_stayoff))) {
			userorder_tv_stayoff.setTextColor(getResources().getColor(R.color.blue));
		} else if (selectArg0 == Integer.valueOf(getResources().getString(R.string.order_index_canceled))) {
			userorder_tv_overed.setTextColor(getResources().getColor(R.color.blue));
		}
	}

	/**
	 * 初始化动画
	 */
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度

	private void InitImageView() {
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
		int screenW = ConfigUtils.getInstance().getPhoneWidHeigth(this).widthPixels;
		offset = (screenW / 5 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		userorder_iv_cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

	/**
	 * 设置跟随滑动
	 */
	private void setLineFollowSlide() {
		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		Animation animation = new TranslateAnimation(offset + one * currIndex, offset + one * selectArg0, 0, 0);// 显然这个比较简洁，只有一行代码。
		currIndex = selectArg0;
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(300);
		userorder_iv_cursor.startAnimation(animation);
	}

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		// int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		// int two = one * 2;// 页卡1 -> 页卡3 偏移量
		// int three = two * 2;

		@Override
		public void onPageSelected(int arg0) {
			selectArg0 = arg0;
			updateCheckView();
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	/**
	 * 方法说明：得到选择订单状态的PopWindow
	 * 
	 */
	private PopupWindow mPopupWindow;
	private ArrayList<SportTypeInfo> mSportList;

	private void changeOrderStatus() {
		if (null != mPopupWindow && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
			return;
		} else {

			if (mSportList != null && mSportList.size() > 0)
				initSelectSportPopuptWindow();
		}
	}

	/**
	 * 方法说明：创建PopWindow
	 * 
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void initSelectSportPopuptWindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.user_orderall_sport_popupwindow, null);
		ListView listview = (ListView) popupWindow.findViewById(R.id.user_orderall_sport_listview);

		final UserOrderSportAdapter adapter = new UserOrderSportAdapter(this, mSportList, cid, merid);
		listview.setAdapter(adapter);
		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		// 防止虚拟软键盘被弹出菜单遮住,例如华为底部菜单
		mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.showAsDropDown(line);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (mPopupWindow != null && mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				merid = mSportList.get(position).getMerid();
				cid = mSportList.get(position).getCid();
				// userorder_vPager.setCurrentItem(selectArg0);
				InitViewPager();
			}
		});

	}

	/**
	 * 在 FragmentActivity 中统计时长：
	 */
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		MobclickAgent.onResume(this); // 统计时长
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		RequestUtils.cancelAll(this);
		unregisterReceiver(dynamicReceiver);
		dynamicReceiver = null;
		filter = null;
		super.onDestroy();
	}

	// -----------------add cancel 收藏-------------------------------
	@Override
	protected void onStart() {
		super.onStart();
		// 动态注册广播
		filter = new IntentFilter();
		filter.addAction(getString(R.string.action_Success_Faliure));
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(dynamicReceiver, filter);
	}

	IntentFilter filter;
	private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(final Context context, Intent intent) {
			Log.e("UserOrderDetailsActivity", "接收--倒计时/支付成功/点击去付款后在支付页添加时间---广播消息");
			String type = intent.getStringExtra("type");
			if (type.equals("UPDATESTATUS") || type.equals("updatepaytime") || type.equals("payover")) {
				InitViewPager();
			}
		}
	};

	private void nowFinish() {
		/**
		 * 防止倒计时都会后台计时-----退出时关闭所有-------------------
		 */
		CommonUtils.getInstance().exitTimer();
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			nowFinish();
		}
		return false;
	}
}
