package com.zh.education.control.activity;

import java.util.ArrayList;

import com.zh.education.R;
import com.zh.education.control.adapter.ChannelItemDragAdapter;
import com.zh.education.control.adapter.ChannelItemOtherAdapter;
import com.zh.education.control.menu.AppApplication;
import com.zh.education.control.menu.ChannelManage;
import com.zh.education.control.menu.ChannelItemDragGridView;
import com.zh.education.control.menu.ChannelItemOtherGridView;
import com.zh.education.model.ChannelItemInfo;
import com.zh.education.utils.CommonUtils;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * 
 * @author 作者：qyy
 * @date 创建时间：2015-12-31 下午5:16:49
 * @Description 类描述：频道管理
 */
public class ChannelActivity extends FragmentBaseActivity implements
		OnItemClickListener, OnClickListener {
	public static String TAG = "ChannelActivity";
	private LinearLayout top_layout_back;
	/** 用户栏目的GRIDVIEW */
	private ChannelItemDragGridView userGridView;
	/** 其它栏目的GRIDVIEW */
	private ChannelItemOtherGridView otherGridView;
	/** 用户栏目对应的适配器，可以拖动 */
	ChannelItemDragAdapter userAdapter;
	/** 其它栏目对应的适配器 */
	ChannelItemOtherAdapter otherAdapter;
	/** 其它栏目列表 */
	ArrayList<ChannelItemInfo> otherChannelList = new ArrayList<ChannelItemInfo>();
	/** 用户栏目列表 */
	ArrayList<ChannelItemInfo> userChannelList = new ArrayList<ChannelItemInfo>();
	/** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
	boolean isMove = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CommonUtils.getInstance().setTranslucentStatus(this);
		setContentView(R.layout.channel);
		CommonUtils.getInstance().addActivity(this);
		initView();
		initData();
	}

	/** 初始化布局 */
	private void initView() {
		top_layout_back = (LinearLayout) findViewById(R.id.top_layout_back);
		userGridView = (ChannelItemDragGridView) findViewById(R.id.channel_dg_user);
		otherGridView = (ChannelItemOtherGridView) findViewById(R.id.channel_dg_other);
		top_layout_back.setOnClickListener(this);
	}

	/** 初始化数据 */
	private void initData() {
		userChannelList = ((ArrayList<ChannelItemInfo>) ChannelManage
				.getManage(AppApplication.getApp().getSQLHelper())
				.getUserChannel());
		otherChannelList = ((ArrayList<ChannelItemInfo>) ChannelManage
				.getManage(AppApplication.getApp().getSQLHelper())
				.getOtherChannel());
		userAdapter = new ChannelItemDragAdapter(this, userChannelList);
		userGridView.setAdapter(userAdapter);
		otherAdapter = new ChannelItemOtherAdapter(this, otherChannelList);
		otherGridView.setAdapter(otherAdapter);
		// 设置GRIDVIEW的ITEM的点击监听
		// otherGridView.setOnItemClickListener(this);
		userGridView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_layout_back:
			setBackChange();
			break;

		default:
			break;
		}
	}

	/** GRIDVIEW对应的ITEM点击监听接口 */
	@Override
	public void onItemClick(AdapterView<?> parent, final View view,
			final int position, long id) {
		// 如果点击的时候，之前动画还没结束，那么就让点击事件无效
		if (isMove) {
			return;
		}
		switch (parent.getId()) {
		case R.id.channel_dg_user:
			/**
			 * 注释------------------ position为 0-4 的不可以进行任何操作
			 */
			if (position != 0 && position != 1) {
				final ImageView moveImageView2 = getView(view);
				if (moveImageView2 != null) {
					TextView newTextView = (TextView) view
							.findViewById(R.id.channel_item_tv);
					final int[] startLocation = new int[2];
					newTextView.getLocationInWindow(startLocation);
					final ChannelItemInfo channel = ((ChannelItemDragAdapter) parent
							.getAdapter()).getItem(position);// 获取点击的频道内容
					otherAdapter.setVisible(false);
					// 添加到最后一个
					otherAdapter.addItem(channel);
					new Handler().postDelayed(new Runnable() {
						public void run() {
							try {
								int[] endLocation = new int[2];
								// 获取终点的坐标
								otherGridView.getChildAt(
										otherGridView.getLastVisiblePosition())
										.getLocationInWindow(endLocation);
								MoveAnim(moveImageView2, startLocation,
										endLocation, channel, userGridView);
								userAdapter.setRemove(position);
							} catch (Exception localException) {
							}
						}
					}, 50L);
				}
			}
			break;
		case R.id.channel_dg_other:
			final ImageView moveImageView = getView(view);
			if (moveImageView != null) {
				TextView newTextView = (TextView) view
						.findViewById(R.id.channel_item_tv);
				final int[] startLocation = new int[2];
				newTextView.getLocationInWindow(startLocation);
				final ChannelItemInfo channel = ((ChannelItemOtherAdapter) parent
						.getAdapter()).getItem(position);
				userAdapter.setVisible(false);
				// 添加到最后一个
				userAdapter.addItem(channel);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						try {
							int[] endLocation = new int[2];
							// 获取终点的坐标
							userGridView.getChildAt(
									userGridView.getLastVisiblePosition())
									.getLocationInWindow(endLocation);
							MoveAnim(moveImageView, startLocation, endLocation,
									channel, otherGridView);
							otherAdapter.setRemove(position);
						} catch (Exception localException) {
						}
					}
				}, 50L);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 点击ITEM移动动画
	 * 
	 * @param moveView
	 * @param startLocation
	 * @param endLocation
	 * @param moveChannel
	 * @param clickGridView
	 */
	private void MoveAnim(View moveView, int[] startLocation,
			int[] endLocation, final ChannelItemInfo moveChannel,
			final GridView clickGridView) {
		int[] initLocation = new int[2];
		// 获取传递过来的VIEW的坐标
		moveView.getLocationInWindow(initLocation);
		// 得到要移动的VIEW,并放入对应的容器中
		final ViewGroup moveViewGroup = getMoveViewGroup();
		final View mMoveView = getMoveView(moveViewGroup, moveView,
				initLocation);
		// 创建移动动画
		TranslateAnimation moveAnimation = new TranslateAnimation(
				startLocation[0], endLocation[0], startLocation[1],
				endLocation[1]);
		moveAnimation.setDuration(300L);// 动画时间
		// 动画配置
		AnimationSet moveAnimationSet = new AnimationSet(true);
		moveAnimationSet.setFillAfter(false);// 动画效果执行完毕后，View对象不保留在终止的位置
		moveAnimationSet.addAnimation(moveAnimation);
		mMoveView.startAnimation(moveAnimationSet);
		moveAnimationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				isMove = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				moveViewGroup.removeView(mMoveView);
				// instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
				if (clickGridView instanceof ChannelItemDragGridView) {
					otherAdapter.setVisible(true);
					otherAdapter.notifyDataSetChanged();
					userAdapter.remove();
				} else {
					userAdapter.setVisible(true);
					userAdapter.notifyDataSetChanged();
					otherAdapter.remove();
				}
				isMove = false;
			}
		});
	}

	/**
	 * 获取移动的VIEW，放入对应ViewGroup布局容器
	 * 
	 * @param viewGroup
	 * @param view
	 * @param initLocation
	 * @return
	 */
	private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
		int x = initLocation[0];
		int y = initLocation[1];
		viewGroup.addView(view);
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mLayoutParams.leftMargin = x;
		mLayoutParams.topMargin = y;
		view.setLayoutParams(mLayoutParams);
		return view;
	}

	/**
	 * 创建移动的ITEM对应的ViewGroup布局容器
	 */
	private ViewGroup getMoveViewGroup() {
		ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
		LinearLayout moveLinearLayout = new LinearLayout(this);
		moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		moveViewGroup.addView(moveLinearLayout);
		return moveLinearLayout;
	}

	/**
	 * 获取点击的Item的对应View，
	 * 
	 * @param view
	 * @return
	 */
	private ImageView getView(View view) {
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(true);
		Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(cache);
		return iv;
	}

	/** 退出时候保存选择后数据库的设置 */
	private void saveChannel() {
		ChannelManage.getManage(AppApplication.getApp().getSQLHelper())
				.deleteAllChannel();
		ChannelManage.getManage(AppApplication.getApp().getSQLHelper())
				.saveUserChannel(userAdapter.getChannnelLst());
		ChannelManage.getManage(AppApplication.getApp().getSQLHelper())
				.saveOtherChannel(otherAdapter.getChannnelLst());
	}

	private void setBackChange() {
		saveChannel();
		if (userAdapter.isListChanged()) {
			Intent intent = new Intent(getApplicationContext(),
					MainActivity.class);
			setResult(MainActivity.CHANNELREQUEST, intent);
			Log.d(TAG, "数据发生改变");
		}
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}

	@Override
	public void onBackPressed() {
		setBackChange();
	}
}
