package com.bestdo.bestdoStadiums.control.photo.activity;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.activity.BaseActivity;
import com.bestdo.bestdoStadiums.control.photo.adapter.AlbumGridViewAdapter;
import com.bestdo.bestdoStadiums.control.photo.util.Bimp;
import com.bestdo.bestdoStadiums.control.photo.util.ImageItem;
import com.bestdo.bestdoStadiums.control.photo.util.PublicWay;
import com.bestdo.bestdoStadiums.control.photo.vedio.AblumUtils;
import com.bestdo.bestdoStadiums.utils.CommonUtils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * 这个是显示一个文件夹里面的所有图片时的界面
 * 
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日 下午11:49:10
 */
public class ShowAllPhoto extends BaseActivity {
	private GridView gridView;
	private AlbumGridViewAdapter gridImageAdapter;
	// 完成按钮
	// 预览按钮
	private TextView preview;
	// 返回按钮
	private Intent intent;
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private TextView pagetop_tv_you;
	public static ArrayList<ImageItem> dataList = new ArrayList<ImageItem>();
	public static ArrayList<ImageItem> selectedDataList = new ArrayList<ImageItem>();

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.pagetop_tv_you:
			if (selectedDataList.size() > 0) {
				AblumUtils.addMoreEditBimp(this, selectedDataList);
				CommonUtils.getInstance().clearPhoto();
				doBack();
			} else {
				CommonUtils.getInstance().initToast(context, "请选择照片");
			}
			break;
		case R.id.showallphoto_preview:
			if (selectedDataList.size() > 0) {
				intent.setClass(ShowAllPhoto.this, GalleryActivity.class);
				startActivity(intent);
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.plugin_camera_show_all_photo);
		CommonUtils.getInstance().addPhotoActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		preview = (TextView) findViewById(R.id.showallphoto_preview);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setText("完成");
		pagetop_tv_you.setTextColor(getResources().getColor(R.color.text_click_color));
		gridView = (GridView) findViewById(R.id.showallphoto_myGrid);
	}

	@Override
	protected void setListener() {
		this.intent = getIntent();
		String folderName = intent.getStringExtra("folderName");
		if (folderName.length() > 18) {
			folderName = folderName.substring(0, 17) + "...";
		}
		pagetop_tv_name.setText(folderName);
		pagetop_tv_you.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		preview.setOnClickListener(this);
		init();
		initListener();
		isShowOkBt();
	}

	@Override
	protected void processLogic() {

	}

	private void init() {
		IntentFilter filter = new IntentFilter("data.broadcast.action");
		registerReceiver(broadcastReceiver, filter);
		selectedDataList.clear();
		// selectedDataList.addAll(Bimp.tempSelectBitmap);
		gridImageAdapter = new AlbumGridViewAdapter(this, dataList, selectedDataList);
		gridView.setAdapter(gridImageAdapter);
	}

	private void initListener() {

		gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
			public void onItemClick(final ToggleButton toggleButton, int position, boolean isChecked,
					TextView mTextView) {
				if (Bimp.tempSelectBitmap.size() - 1 + selectedDataList.size() >= PublicWay.num && isChecked) {
					mTextView.setBackgroundResource(R.drawable.check_false);
					toggleButton.setChecked(false);
					CommonUtils.getInstance().initToast(context, "最多上传" + PublicWay.num + "张照片");
					return;
				}

				if (isChecked) {
					mTextView.setBackgroundResource(R.drawable.check_true);
					selectedDataList.add(dataList.get(position));
					// Bimp.tempSelectBitmap.add(dataList.get(position));
					preview.setText(getString(R.string.review) + "(" + selectedDataList.size() + ")");
				} else {
					mTextView.setBackgroundResource(R.drawable.check_false);
					selectedDataList.remove(dataList.get(position));
					// Bimp.tempSelectBitmap.remove(dataList.get(position));
					preview.setText(getString(R.string.review) + "(" + selectedDataList.size() + ")");
				}
				isShowOkBt();
			}
		});

	}

	public void isShowOkBt() {
		gridImageAdapter.notifyDataSetChanged();
		if (selectedDataList.size() > 0) {
			preview.setText(getString(R.string.review) + "(" + selectedDataList.size() + ")");
			preview.setPressed(true);
			preview.setClickable(true);
			preview.setTextColor(getResources().getColor(R.color.text_click_color));
		} else {
			preview.setText(getString(R.string.review) + "(" + selectedDataList.size() + ")");
			preview.setPressed(false);
			preview.setClickable(false);
			preview.setTextColor(getResources().getColor(R.color.text_noclick_color));
		}
	}

	@Override
	protected void onRestart() {
		isShowOkBt();
		super.onRestart();
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			gridImageAdapter.notifyDataSetChanged();
		}
	};

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
