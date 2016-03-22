package com.zh.education.control.activity;

import java.lang.ref.WeakReference;

import com.zh.education.R;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.MImageGetter;
import com.zh.education.utils.MTagHandler;
import com.zh.education.utils.TextSizeUtils;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-25 下午12:13:48
 * @Description 类描述：消息详情
 */
public class NoticesDetailActivity extends BaseActivity {
	private LinearLayout top_layout_back;
	private TextView top_tv_name;

	private TextView noticesdetail_tv_title, noticesdetail_tv_time,
			noticesdetail_tv_content;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.top_layout_back:
			onBackPressed();
			break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.fragment_notices_detail);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		top_layout_back = (LinearLayout) findViewById(R.id.top_layout_back);
		top_tv_name = (TextView) findViewById(R.id.top_tv_name);

		noticesdetail_tv_title = (TextView) findViewById(R.id.noticesdetail_tv_title);
		noticesdetail_tv_time = (TextView) findViewById(R.id.noticesdetail_tv_time);
		noticesdetail_tv_content = (TextView) findViewById(R.id.noticesdetail_tv_content);
	}

	@Override
	protected void setListener() {
		top_layout_back.setOnClickListener(this);
		top_tv_name.setText("消息详情");
		Intent in = getIntent();
		String title = in.getStringExtra("title");
		String time = in.getStringExtra("time");
		String content = in.getStringExtra("content");
		noticesdetail_tv_title.setText(title);
		noticesdetail_tv_time.setText(time);
		noticesdetail_tv_content.setText(Html.fromHtml("主题：" + content));
		TextSizeUtils.getInstance().setChangeTextSize(noticesdetail_tv_content);

	}

	/**
	 * 监听返回键
	 */
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}

	@Override
	protected void processLogic() {

	}
}
