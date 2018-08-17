package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.R;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-3-8 下午3:01:11
 * @Description 类描述：卡介绍
 */
public class UserCardAbstractActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_you;
	private ImageView pagetop_iv_you;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_layout_you:
			CommonUtils.getInstance().getPhoneToKey(context);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_card_abstract);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_layout_you = (LinearLayout) findViewById(R.id.pagetop_layout_you);
		pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);
		pagetop_tv_name.setText("卡券说明");
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		pagetop_layout_you.setOnClickListener(this);
		pagetop_iv_you.setVisibility(View.VISIBLE);
		pagetop_iv_you.setBackgroundResource(R.drawable.userorderdetail_ic_phone);
	}

	@Override
	protected void processLogic() {

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

	private void nowFinish() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}
}
