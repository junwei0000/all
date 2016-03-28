package com.zh.education.control.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.zh.education.R;
import com.zh.education.control.onedrives.DisplayItem;
import com.zh.education.control.onedrives.ItemFragment;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.ScreenUtils;
import com.zh.education.control.adapter.NewsFragmentPagerAdapter;
import com.zh.education.control.fragment.BokeFragment;
import com.zh.education.control.fragment.ClassScheduleFragment;
import com.zh.education.control.fragment.EventsFragment;
import com.zh.education.control.fragment.NoticesFragment;
import com.zh.education.control.fragment.YunPanFragment;
import com.zh.education.control.menu.AppApplication;
import com.zh.education.control.menu.ChannelManage;
import com.zh.education.control.menu.ColumnHorizontalScrollView;
import com.zh.education.model.ChannelItemInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2015-12-28 下午4:42:26
 * @Description 类描述：首頁
 */
public class MainActivity extends FragmentActivity implements OnClickListener,ItemFragment.OnFragmentInteractionListener {
	/** 自定义HorizontalScrollView */
	private ColumnHorizontalScrollView main_columnhorizontalscrollview;
	LinearLayout main_layout_columns;
	private ViewPager main_viewpager;
	private LinearLayout main_layout_morecolumns;
	private LinearLayout main_layout_usercenter;
	/** 用户选择的新闻分类列表 */
	private ArrayList<ChannelItemInfo> userChannelList = new ArrayList<ChannelItemInfo>();
	/** 当前选中的栏目 */
	private int columnSelectIndex = 0;
	/** 屏幕宽度 */
	private int mScreenWidth = 0;
	/** Item宽度 */
	private int mItemWidth = 0;
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	private NewsFragmentPagerAdapter mAdapetr;
	/** 请求CODE */
	public final static int CHANNELREQUEST = 1;

	/** 调整返回的RESULTCODE */

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_layout_usercenter:
			/*
			 * 跳转用户中心
			 */
			if (!CommonUtils.getInstance().LoginTokenReLog(MainActivity.this)) {
				Intent intent_center = new Intent(getApplicationContext(),
						UserCenterActivity.class);
				intent_center.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent_center);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			}
			break;
		case R.id.main_layout_morecolumns:
			/*
			 * 添加更多渠道功能
			 */
			if (!CommonUtils.getInstance().LoginTokenReLog(MainActivity.this)) {
				Intent intent_channel = new Intent(getApplicationContext(),
						ChannelActivity.class);
				intent_channel.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivityForResult(intent_channel, CHANNELREQUEST);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CommonUtils.getInstance().setTranslucentStatus(this);
		setContentView(R.layout.main);
		CommonUtils.getInstance().addActivity(this);
		initView();
	}

	@Override
	public void onFragmentInteraction(final DisplayItem item) {
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment, ItemFragment.newInstance(item.getId()))
				.addToBackStack(null)
				.commit();
	}
	/** 初始化layout控件 */
	private void initView() {
		findViewById();
		setListener();
		setChangelView();
	}

	private void findViewById() {
		main_columnhorizontalscrollview = (ColumnHorizontalScrollView) findViewById(R.id.main_columnhorizontalscrollview);
		main_layout_columns = (LinearLayout) findViewById(R.id.main_layout_columns);
		main_layout_morecolumns = (LinearLayout) findViewById(R.id.main_layout_morecolumns);
		main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
		main_layout_usercenter = (LinearLayout) findViewById(R.id.main_layout_usercenter);
	}

	private void setListener() {
		main_layout_usercenter.setOnClickListener(this);
		main_layout_morecolumns.setOnClickListener(this);
	}

	/**
	 * 当栏目项发生变化时候调用
	 */
	private void setChangelView() {
		initColumnData();
		initTabColumn();
		initFragment();
	}

	/**
	 * 获取Column栏目 数据
	 */
	private void initColumnData() {
		userChannelList = ((ArrayList<ChannelItemInfo>) ChannelManage
				.getManage(AppApplication.getApp().getSQLHelper())
				.getUserChannel());
	}

	/**
	 * 初始化Column栏目项
	 */
	private void initTabColumn() {
		main_layout_columns.removeAllViews();
		int count = userChannelList.size();
		mScreenWidth = ScreenUtils.getScreenWidth(MainActivity.this);
		/*
		 * 一个Item宽度为屏幕的1/7
		 */
		mItemWidth = (6 * (mScreenWidth / 8)) / 5;

		for (int i = 0; i < count; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					mItemWidth, LayoutParams.WRAP_CONTENT);
			TextView columnTextView = new TextView(this);
			columnTextView.setBackgroundResource(R.drawable.main_channel_bg);
			columnTextView.setGravity(Gravity.CENTER);
			columnTextView.setPadding(5, 5, 5, 5);
			columnTextView.setTextSize(16);
			columnTextView.setId(i);
			columnTextView.setText(userChannelList.get(i).getName());
			columnTextView.setTextColor(getResources().getColor(R.color.white));
			if (columnSelectIndex == i) {
				columnTextView.setSelected(true);
			}
			columnTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < main_layout_columns.getChildCount(); i++) {
						View localView = main_layout_columns.getChildAt(i);
						if (localView != v)
							localView.setSelected(false);
						else {
							localView.setSelected(true);
							main_viewpager.setCurrentItem(i);
						}
					}
				}
			});
			main_layout_columns.addView(columnTextView, i, params);
		}
	}

	/**
	 * 选择的Column里面的Tab
	 * */
	private void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion;
		for (int i = 0; i < main_layout_columns.getChildCount(); i++) {
			View checkView = main_layout_columns.getChildAt(tab_postion);
			int k = checkView.getMeasuredWidth();
			int l = checkView.getLeft();
			int i2 = l + k / 2 - mScreenWidth / 2;
			main_columnhorizontalscrollview.smoothScrollTo(i2, 0);
		}
		// 判断是否选中
		for (int j = 0; j < main_layout_columns.getChildCount(); j++) {
			View checkView = main_layout_columns.getChildAt(j);
			boolean ischeck;
			if (j == tab_postion) {
				ischeck = true;
			} else {
				ischeck = false;
			}
			checkView.setSelected(ischeck);
		}
	}

	/**
	 * 初始化Fragment
	 * */
	private void initFragment() {
		fragments.clear();// 清空
		
		int count = userChannelList.size();
		String name;
		int id;

		for (int i = 0; i < count; i++) {
			Bundle data = new Bundle();
			name = userChannelList.get(i).getName();
			id = userChannelList.get(i).getOrderId();
			 Log.e("id", id+""+name);
			
			data.putString("text", name);
			data.putInt("id", userChannelList.get(i).getOrderId());

			if (name.equals("课表")) {
				ClassScheduleFragment kebiaoFragment = new ClassScheduleFragment(
						this);
				kebiaoFragment.setArguments(data);
				fragments.add(kebiaoFragment);
			} else if (name.equals("博客")) {
				BokeFragment bokeFragment = new BokeFragment(this);
				bokeFragment.setArguments(data);
				fragments.add(bokeFragment);
			} else if (name.equals("消息")) {
				NoticesFragment xiaoxiFragment = new NoticesFragment(this);
				xiaoxiFragment.setArguments(data);
				fragments.add(xiaoxiFragment);
			} else if (name.equals("日程")) {
				EventsFragment riChengFragment = new EventsFragment(this);
				riChengFragment.setArguments(data);
				fragments.add(riChengFragment);
			} else if (name.equals("云盘")) {
				YunPanFragment yunPanFragment = new YunPanFragment(this);
				yunPanFragment.setArguments(data);
				fragments.add(yunPanFragment);
			}
		}
		
		 Log.e("fragments1", fragments+"");
		 Collections.sort(fragments, new Comparator<Fragment>() {
			             @Override
			             public int compare(Fragment user1, Fragment user2) {
			            	 
			                 Integer id1 = user1.getArguments().getInt("id");
			                 Integer id2 = user2.getArguments().getInt("id");
			                 
			                 if(id1>id2){
			                     return 1;
			                 }else if(id1==id2){
			                     return 0;
			                 }else {
			                     return -1;
			                 }
			             }
			         });
		 Log.e("fragments2", fragments+"");
		mAdapetr = new NewsFragmentPagerAdapter(
				getSupportFragmentManager(), fragments);
//		main_viewpager.setOffscreenPageLimit(0);
		main_viewpager.setAdapter(mAdapetr);
		main_viewpager.setOnPageChangeListener(pageListener);
	}

	/**
	 * ViewPager切换监听方法
	 * */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			if (!CommonUtils.getInstance().LoginTokenReLog(MainActivity.this)) {
				main_viewpager.setCurrentItem(position);
				selectTab(position);
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case CHANNELREQUEST:
			mViewHandler.sendEmptyMessage(UPDATEVIEW);
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private final int UPDATEVIEW = 3;
	Handler mViewHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATEVIEW:
				main_viewpager.setCurrentItem(0);
				selectTab(0);
//				mAdapetr.setFragments(fragments);
				setChangelView();
				break;
			}
		}
	};
	@Override
	protected void onRestart() {
		super.onRestart();
	}

	/**
	 * 监听返回键
	 */
	public void onBackPressed() {
		CommonUtils.getInstance().defineBackPressed(this,
				CommonUtils.getInstance().exit);

	}
}
