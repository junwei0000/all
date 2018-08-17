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
import com.bestdo.bestdoStadiums.control.fragment.ClubEventsFragment;
import com.bestdo.bestdoStadiums.control.fragment.ClubMainlFragment;
import com.bestdo.bestdoStadiums.control.fragment.ClubWonderfulFragment;
import com.bestdo.bestdoStadiums.control.fragment.UserAllFragment;
import com.bestdo.bestdoStadiums.control.fragment.UserConfirmingFragment;
import com.bestdo.bestdoStadiums.control.fragment.UserFinishFragment;
import com.bestdo.bestdoStadiums.control.fragment.UserWaitPayFragment;
import com.bestdo.bestdoStadiums.control.fragment.UsersStayoffFragment;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.MyJoinClubmenuInfo;
import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.model.UserOrdersInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
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
public class ClubActivity extends FragmentActivity implements OnClickListener {
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private TextView club_activity_events_text;
	private ImageView club_iv_cursor, club_activity_lay_top_img;
	private ViewPager club__vPager;

	public static MyJoinClubmenuInfo myJoinClubmenuInfo;
	private ImageLoader asyncImageLoader;
	public int selectArg0 = 0;
	private TextView club_activity_main_text;
	private String thumb;

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.club_activity_main_text:
			selectArg0 = Integer.valueOf(getResources().getString(R.string.order_index_all));
			updateCheckView();
			break;
		case R.id.club_activity_events_text:
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
		setContentView(R.layout.club_activity_layout);
		CommonUtils.getInstance().addActivity(this);
		Constans.getInstance().mClubActivity = ClubActivity.this;
	}

	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		club_activity_main_text = (TextView) findViewById(R.id.club_activity_main_text);
		club_activity_events_text = (TextView) findViewById(R.id.club_activity_events_text);
		club_iv_cursor = (ImageView) findViewById(R.id.club_iv_cursor);
		club_activity_lay_top_img = (ImageView) findViewById(R.id.club_activity_lay_top_img);
		club__vPager = (ViewPager) findViewById(R.id.club__vPager);
		asyncImageLoader = new ImageLoader(this, "listdetail");
	}

	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		club_activity_main_text.setOnClickListener(this);
		club_activity_events_text.setOnClickListener(this);
	}

	protected void processLogic() {
		try {
			myJoinClubmenuInfo = (MyJoinClubmenuInfo) getIntent().getSerializableExtra("MyJoinClubmenuInfo");

			clubname = myJoinClubmenuInfo.getClub_name();
			pagetop_tv_name.setText(clubname);
			thumb = myJoinClubmenuInfo.getClub_banner();
			if (!TextUtils.isEmpty(thumb)) {
				asyncImageLoader.DisplayImage(thumb, club_activity_lay_top_img);
			} else {
				club_activity_lay_top_img.setBackgroundResource(R.drawable.club_img_banner);
			}
		} catch (Exception e) {
		}
		// mSportList = SearchGetSportParser.getInstance().parseSportJSON(this);
		initPage();
	}

	/**
	 * 初始化ViewPager
	 */
	private FragmentAdapter mFragmentAdapter;
	private ArrayList<Fragment> fgs;
	private String merid = "";
	private String cid = "";
	private String clubname;

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

	/**
	 * 初始化fragment数据
	 */
	private void InitViewPager() {
		fgs = new ArrayList<Fragment>();
		fgs.add(new ClubMainlFragment(ClubActivity.this, myJoinClubmenuInfo.getClub_description(),
				myJoinClubmenuInfo.getMember_count(), myJoinClubmenuInfo.getClub_id()));
		fgs.add(new ClubEventsFragment(ClubActivity.this, myJoinClubmenuInfo.getClub_id(), clubname));
		mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fgs);
		mFragmentAdapter.setOnReloadListener(new OnReloadListener() {
			@Override
			public void onReload() {
				fgs = null;
				List<Fragment> list = new ArrayList<Fragment>();
				list.add(new ClubMainlFragment(ClubActivity.this, myJoinClubmenuInfo.getClub_description(),
						myJoinClubmenuInfo.getMember_count(), myJoinClubmenuInfo.getClub_id()));
				list.add(new ClubEventsFragment(ClubActivity.this, myJoinClubmenuInfo.getClub_id(), clubname));
				mFragmentAdapter.setPagerItems(list);
			}
		});
		club__vPager.setAdapter(mFragmentAdapter);
		updateCheckView();
		/**
		 * 其中参数可以设为0或者1，参数小于1时，会默认用1来作为参数，未设置之前，ViewPager会默认加载两个Fragment
		 */
		club__vPager.setOffscreenPageLimit(1);
		club__vPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void initPage() {
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
		club__vPager.setCurrentItem(selectArg0);
		club_activity_main_text.setTextColor(getResources().getColor(R.color.text_noclick_color));
		club_activity_events_text.setTextColor(getResources().getColor(R.color.text_noclick_color));
		if (selectArg0 == Integer.valueOf(getResources().getString(R.string.order_index_all))) {
			club_activity_main_text.setTextColor(getResources().getColor(R.color.blue));
		} else if (selectArg0 == Integer.valueOf(getResources().getString(R.string.order_index_waitpay))) {
			club_activity_events_text.setTextColor(getResources().getColor(R.color.blue));
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
		offset = (screenW / 2 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		club_iv_cursor.setImageMatrix(matrix);// 设置动画初始位置
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
		club_iv_cursor.startAnimation(animation);
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
