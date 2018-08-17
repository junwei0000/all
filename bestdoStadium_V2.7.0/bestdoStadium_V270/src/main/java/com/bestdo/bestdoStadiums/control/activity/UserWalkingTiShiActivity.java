/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.utils.CommonUtils;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 作者：zoc
 * @date 创建时间：2017-4-11 下午3:17:24
 * @Description 类描述：
 */
public class UserWalkingTiShiActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.userwalk_tishi);
		CommonUtils.getInstance().addActivity(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestdo.bestdoStadiums.control.activity.BaseActivity#findViewById()
	 */
	@Override
	protected void findViewById() {
		LinearLayout page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		page_top_layout.setBackgroundColor(getResources().getColor(R.color.blue));
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		ImageView pagetop_iv_back = (ImageView) findViewById(R.id.pagetop_iv_back);
		pagetop_iv_back.setBackgroundResource(R.drawable.back_moren);
		TextView pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setTextColor(getResources().getColor(R.color.white));
		pagetop_tv_name.setText("设置说明");
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub

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
