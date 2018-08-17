package com.bestdo.bestdoStadiums.control.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.fragment.RankDepFragment;
import com.bestdo.bestdoStadiums.control.fragment.RankPersonalFragment;
import com.bestdo.bestdoStadiums.utils.CommonUtils;

/**
 * Created by YuHua on 2017/5/23 15:52 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class WalkRankActivity extends BaseActivity {
	private TextView tvPer, tvCompany;
	private View linePer, lineCompany;
	private FragmentManager fragmentManager;
	private boolean curPagePer;

	@Override
	protected void loadViewLayout() {
		CommonUtils.getInstance().addActivity(this);
		setContentView(R.layout.act_walk_rank);
	}

	@Override
	protected void findViewById() {
		tvPer = (TextView) findViewById(R.id.tv_personal);
		tvCompany = (TextView) findViewById(R.id.tv_t_company);
		linePer = findViewById(R.id.t_r_line1);
		lineCompany = findViewById(R.id.t_r_line2);
	}

	@Override
	protected void setListener() {
		tvPer.setOnClickListener(this);
		tvCompany.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		initData();
	}

	private void initData() {
		curPagePer = true;
		fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().add(R.id.container, new RankPersonalFragment()).commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_personal:
			toPersonal();
			break;
		case R.id.tv_t_company:
			toCompany();
			break;
		}
	}

	private void toCompany() {
		if (curPagePer) {
			setTile();
			switchFragment();
		}
	}

	private void setTile() {
		tvPer.setEnabled(curPagePer);
		tvCompany.setEnabled(!curPagePer);
		linePer.setVisibility(!curPagePer ? View.VISIBLE : View.GONE);
		lineCompany.setVisibility(curPagePer ? View.VISIBLE : View.GONE);
	}

	private void toPersonal() {
		if (!curPagePer) {
			setTile();
			switchFragment();
		}
	}

	private void switchFragment() {
		Fragment fragment = curPagePer ? new RankDepFragment() : new RankPersonalFragment();
		fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
		curPagePer = !curPagePer;
	}

	public void back(View view) {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			back(null);
		}
		return false;
	}
}
