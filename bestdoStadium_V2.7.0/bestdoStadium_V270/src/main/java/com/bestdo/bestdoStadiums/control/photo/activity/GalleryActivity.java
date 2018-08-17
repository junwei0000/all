package com.bestdo.bestdoStadiums.control.photo.activity;

import java.util.ArrayList;
import java.util.List;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.activity.BaseActivity;
import com.bestdo.bestdoStadiums.control.photo.util.Bimp;
import com.bestdo.bestdoStadiums.control.photo.zoom.PhotoView;
import com.bestdo.bestdoStadiums.control.photo.zoom.ViewPagerFixed;
import com.bestdo.bestdoStadiums.utils.CommonUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 这个是用于进行图片浏览时的界面
 * 
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日 下午11:47:53
 */
@SuppressLint("NewApi")
public class GalleryActivity extends BaseActivity {
	private Intent intent;
	// 当前的位置
	private int location = 0;

	private ArrayList<View> listViews = null;
	private ViewPagerFixed pager;
	private MyPageAdapter adapter;

	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<String> drr = new ArrayList<String>();
	public List<String> del = new ArrayList<String>();

	RelativeLayout photo_relativeLayout;
	private LinearLayout pagetop_layout_back;
	private ImageView pagetop_iv_you;
	private TextView pagetop_tv_name;
	/**
	 * 是否直接删除
	 */
	private String DelStatus;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.pagetop_iv_you:
			del();
			break;
		default:
			break;
		}
	}

	private void del() {
		if (listViews.size() == 1) {
			ShowAllPhoto.selectedDataList.clear();
			Bimp.max = 0;
			Intent intent = new Intent("data.broadcast.action");
			sendBroadcast(intent);
			doBack();
		} else {
			ShowAllPhoto.selectedDataList.remove(location);
			Bimp.max--;
			pager.removeAllViews();
			listViews.remove(location);
			adapter.setListViews(listViews);
			adapter.notifyDataSetChanged();
			setTopText();
		}
	}

	private void setTopText() {
		pagetop_tv_name.setText((location + 1) + "/" + ShowAllPhoto.selectedDataList.size());
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.plugin_camera_gallery);// 切屏到主界面
		CommonUtils.getInstance().addPhotoActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);
		pagetop_iv_you.setVisibility(View.VISIBLE);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		LayoutParams lp;
		lp = pagetop_iv_you.getLayoutParams();
		lp.width = 45;
		lp.height = 50;
		pagetop_iv_you.setLayoutParams(lp);
		pagetop_iv_you.setBackgroundResource(R.drawable.pengyou_dele);

		pagetop_layout_back.setOnClickListener(this);
		pagetop_iv_you.setOnClickListener(this);
	}

	@Override
	protected void setListener() {
		intent = getIntent();
		DelStatus = intent.getExtras().getString("DelStatus", "");
		if (!TextUtils.isEmpty(DelStatus)) {
			pagetop_iv_you.setVisibility(View.GONE);
			ShowAllPhoto.selectedDataList.clear();
			for (int i = 0; i < Bimp.tempSelectBitmap.size() - 1; i++) {
				ShowAllPhoto.selectedDataList.add(Bimp.tempSelectBitmap.get(i));
			}
		}
		// 为发送按钮设置文字
		pager = (ViewPagerFixed) findViewById(R.id.gallery01);
		pager.setOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < ShowAllPhoto.selectedDataList.size(); i++) {
			initListViews(ShowAllPhoto.selectedDataList.get(i).getBitmap());
		}
		adapter = new MyPageAdapter(listViews);
		pager.setAdapter(adapter);
		pager.setPageMargin(10);
		int id = intent.getIntExtra("ID", 0);
		location = id;
		pager.setCurrentItem(id);
		setTopText();
	}

	@Override
	protected void processLogic() {
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			location = arg0;
			setTopText();
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageScrollStateChanged(int arg0) {

		}
	};

	private void initListViews(Bitmap bm) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		PhotoView img = new PhotoView(this);
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bm);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		listViews.add(img);
	}

	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;

		private int size;

		public MyPageAdapter(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
		}

		public void finishUpdate(View arg0) {
		}

		public Object instantiateItem(View arg0, int arg1) {
			try {
				((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

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
	}
}
