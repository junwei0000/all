package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.R;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 预订成功页面
 * 
 * @author jun
 * 
 */
public class Success_YuDing extends BaseActivity {
	private TextView pagetop_tv_name;
	private TextView success_pay_text, success_pay_tishi_text;
	private LinearLayout pagetop_layout_back, success_pay_text_left, success_pay_text_right;
	private String oid, remind;
	private Activity mActivity;
	private String stadium_name;
	private String play_time;
	private String cid;
	private String selectNum;
	private String book_day;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.success_pay_text_left:
			skipIntent();
			finish();
			CommonUtils.getInstance().setPageIntentAnim(null, this);
			break;
		case R.id.success_pay_text_right:
			CommonUtils.getInstance().skipMainActivity(this);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.success_pay);
		CommonUtils.getInstance().addActivity(this);
		mActivity = Success_YuDing.this;

	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setText("预订成功");
		success_pay_text = (TextView) findViewById(R.id.success_pay_text);
		success_pay_text.setText("  预订成功");
		success_pay_tishi_text = (TextView) findViewById(R.id.success_pay_tishi_text);
		success_pay_text_left = (LinearLayout) findViewById(R.id.success_pay_text_left);
		success_pay_text_right = (LinearLayout) findViewById(R.id.success_pay_text_right);

	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		success_pay_text_left.setOnClickListener(this);
		success_pay_text_right.setOnClickListener(this);
		initDate();
	}

	private void skipIntent() {
		CommonUtils.getInstance().exitOrderDetailPage();
		Intent intent = new Intent(this, UserOrderDetailsActivity.class);
		intent.putExtra("oid", oid + "");// 订单号
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	private void initDate() {
		pagetop_tv_name.setText(getResources().getString(R.string.success_yuding_title));
		Intent in = getIntent();
		oid = in.getStringExtra("oid");
		stadium_name = in.getStringExtra("mername");
		play_time = in.getStringExtra("play_time");
		cid = in.getStringExtra("cid");
		selectNum = in.getStringExtra("selectNum");
		remind = in.getStringExtra("remind");
		book_day = in.getStringExtra("book_day");
		book_day = DatesUtils.getInstance().getDateGeShi(book_day, "yyyy-MM-dd", "MM月dd日");
		if (cid.equals(Constans.getInstance().sportCidGolf)) {
			play_time = "【" + play_time + "】";
			selectNum = "，数量" + selectNum;
			success_pay_tishi_text.setText(
					"您已成功预订【" + stadium_name + "】【 " + book_day + "】" + play_time + selectNum + "，当日到场馆签到下场打球！");
		} else {
			success_pay_tishi_text.setText("验证码已发送至手机 " + remind + "请注意查收！");
		}
	}

	@Override
	protected void processLogic() {

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
