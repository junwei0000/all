package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;
import com.bestdo.bestdoStadiums.control.adapter.UserCardAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.R;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class UserCardsTabActivity extends TabActivity implements OnClickListener {
	private LinearLayout pagetop_layout_back;

	TabHost tab;
	Context context;
	RadioGroup rg;

	private TextView pagetop_tv_you;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_cards_tab_pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_tv_you:
			Intent intent = new Intent(UserCardsTabActivity.this, UserCardsActivateActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("intent_from", "UserCenterActivity");
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, UserCardsTabActivity.this);
			break;
		default:
			break;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		setContentView(R.layout.user_cards_tab);
		CommonUtils.getInstance().addActivity(this);
		tab = getTabHost();
		context = this;
		tab.addTab(tab.newTabSpec("A").setIndicator("A").setContent(new Intent(context, UserCardsActivity.class)));
		tab.addTab(tab.newTabSpec("B").setIndicator("B").setContent(new Intent(context, UserQiYeCardsActivity.class)));
		rg = (RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int idx = -1;
				if (checkedId == R.id.rb01) {
					idx = 0;
				} else if (checkedId == R.id.rb02) {
					idx = 1;
				}
				switchActivity(idx);
			}
		});

		findViewById();
		setListener();
	}

	protected void switchActivity(int idx) {
		int n = tab.getCurrentTab();

		// if (idx < n) {
		// tab.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.slide_left_out));
		// } else if (idx > n) {
		// tab.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.slide_right_out));
		// }
		tab.setCurrentTab(idx);
		// if (idx < n) {
		// tab.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.slide_left_in));
		// } else if (idx > n) {
		// tab.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.slide_right_in));
		// }
		RadioButton rb = (RadioButton) rg.getChildAt(idx);
		rb.setChecked(true);
	}

	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.user_cards_tab_pagetop_layout_back);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		pagetop_tv_you.setText("激活");
	}

	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
	}

	/**
	 ** 
	 * 没有数据时加载默认图
	 */

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		System.err.println("onRestart");
	}

	@Override
	protected void onStart() {
		super.onStart();

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
