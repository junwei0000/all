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
import com.bestdo.bestdoStadiums.control.fragment.CampaignManagerRegisteredFragment;
import com.bestdo.bestdoStadiums.control.fragment.CampaignManagerzuzhiFragment;
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
 * @Description 类描述：活动管理列表
 */
public class CampaignManagementActivity extends FragmentActivity implements OnClickListener {
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ImageView pagetop_iv_toparrow;
	private TextView campaignmanager_tv_yibaoming;
	private ImageView userorder_iv_cursor;
	private ViewPager userorder_vPager;

	private String uid;

	public int selectArg0;
	private TextView campaignmanager_tv_zuzhi;
	private TextView pagetop_tv_you;
	private LinearLayout line;
	private LinearLayout pagetop_layout_you;

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_layout_you:
			Intent intent = new Intent(this, CampaignRegularListActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("uid", uid);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
			break;
		case R.id.campaignmanager_tv_zuzhi:
			selectArg0 = Integer.valueOf(getResources().getString(R.string.order_index_all));
			updateCheckView();
			break;
		case R.id.campaignmanager_tv_yibaoming:
			selectArg0 = Integer.valueOf(getResources().getString(R.string.order_index_waitpay));
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
		setContentView(R.layout.campaign_management);
		CommonUtils.getInstance().addActivity(this);
		Constans.getInstance().mCampaignManagementActivity = this;
	}

	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_layout_you = (LinearLayout) findViewById(R.id.pagetop_layout_you);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setText("固定活动管理");
		line = (LinearLayout) findViewById(R.id.line);
		pagetop_iv_toparrow = (ImageView) findViewById(R.id.pagetop_iv_toparrow);
		pagetop_iv_toparrow.setVisibility(View.GONE);

		campaignmanager_tv_zuzhi = (TextView) findViewById(R.id.campaignmanager_tv_zuzhi);
		campaignmanager_tv_yibaoming = (TextView) findViewById(R.id.campaignmanager_tv_yibaoming);
		userorder_iv_cursor = (ImageView) findViewById(R.id.userorder_iv_cursor);
		userorder_vPager = (ViewPager) findViewById(R.id.userorder_vPager);

	}

	protected void setListener() {
		pagetop_layout_you.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		campaignmanager_tv_zuzhi.setOnClickListener(this);
		campaignmanager_tv_yibaoming.setOnClickListener(this);
	}

	protected void processLogic() {
		try {
			SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
			uid = bestDoInfoSharedPrefs.getString("uid", "");
		} catch (Exception e) {
		}
		pagetop_tv_name.setText("活动管理");
		InitViewPager();
	}

	/**
	 * 初始化ViewPager
	 */
	private FragmentAdapter mFragmentAdapter;
	private ArrayList<Fragment> fgs;

	/**
	 * 初始化fragment数据
	 */
	private void InitViewPager() {
		fgs = new ArrayList<Fragment>();
		fgs.add(new CampaignManagerzuzhiFragment(this, uid));
		fgs.add(new CampaignManagerRegisteredFragment(this, uid));
		mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fgs);
		mFragmentAdapter.setOnReloadListener(new OnReloadListener() {
			@Override
			public void onReload() {
				fgs = null;
				List<Fragment> list = new ArrayList<Fragment>();
				list.add(new CampaignManagerzuzhiFragment(CampaignManagementActivity.this, uid));
				list.add(new CampaignManagerRegisteredFragment(CampaignManagementActivity.this, uid));
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
		offset = (screenW / 2 - bmpW) / 2;// 计算偏移量
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
		super.onDestroy();
	}

	private void nowFinish() {
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
