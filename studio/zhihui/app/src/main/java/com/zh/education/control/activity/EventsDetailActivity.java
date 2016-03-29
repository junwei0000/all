package com.zh.education.control.activity;

import java.lang.ref.WeakReference;

import com.zh.education.R;
import com.zh.education.model.EventsInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.DatesUtils;
import com.zh.education.utils.MImageGetter;
import com.zh.education.utils.MTagHandler;
import com.zh.education.utils.TextSizeUtils;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventsDetailActivity extends BaseActivity {
	private LinearLayout top_layout_back,events_detail_add_lay;
	private TextView top_tv_name,events_detail_add_txt;

	private TextView noticesdetail_tv_title, noticesdetail_tv_time,
			noticesdetail_tv_content,noticesdetail_tv_bottom_title;
	private EventsInfo eventsInfo;
	private String text_content;
	private static String add;
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
		noticesdetail_tv_bottom_title= (TextView) findViewById(R.id.noticesdetail_tv_bottom_title);
		noticesdetail_tv_title = (TextView) findViewById(R.id.noticesdetail_tv_title);
		noticesdetail_tv_time = (TextView) findViewById(R.id.noticesdetail_tv_time);
		noticesdetail_tv_content = (TextView) findViewById(R.id.noticesdetail_tv_content);
		events_detail_add_lay= (LinearLayout) findViewById(R.id.events_detail_add_lay);
		events_detail_add_txt= (TextView) findViewById(R.id.events_detail_add_txt);
	}

	@Override
	protected void setListener() {
		top_layout_back.setOnClickListener(this);
		top_tv_name.setText("日程详情");
		Intent in = getIntent();
	    eventsInfo=(EventsInfo) in.getSerializableExtra("eventsInfo");
	    
		String title = eventsInfo.getCalendarName();
		
		long BeginTime=eventsInfo.getBeginTime();
		String time=DatesUtils.getInstance().getTimeStampToDate(BeginTime,"yyyy年MM月dd日");
//		String date_ee=DatesUtils.getInstance().getTimeStampToDate(BeginTime,"EE");
//		if(date_ee.equals("周一")){
//			date_ee="星期一";
//		}else if(date_ee.equals("周二")){
//			date_ee="星期二";
//		}else if(date_ee.equals("周三")){
//			date_ee="星期三";
//		}else if(date_ee.equals("周四")){
//			date_ee="星期四";
//		}else if(date_ee.equals("周五")){
//			date_ee="星期五";
//		}else if(date_ee.equals("周六")){
//			date_ee="星期六";
//		}else if(date_ee.equals("周日")){
//			date_ee="星期天";
//		}
		
		add=eventsInfo.getAddress();
		text_content = eventsInfo.getDescription();
		noticesdetail_tv_title.setText(title);
		noticesdetail_tv_time.setText(time);
		if(TextUtils.isEmpty(text_content)){
			text_content="";
		}
		if(!text_content.equals("null")){
			noticesdetail_tv_content.setText(Html.fromHtml(text_content));
		}else{
			noticesdetail_tv_content.setVisibility(View.GONE);
		}
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
	final MyHandler myHandler = new MyHandler(EventsDetailActivity.this);
	
	private static class MyHandler extends Handler {
		WeakReference<EventsDetailActivity> mActivity;

		public MyHandler(EventsDetailActivity eventsDetailActivity) {
			mActivity = new WeakReference<EventsDetailActivity>(eventsDetailActivity);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			EventsDetailActivity theActivity = mActivity.get();
			if (msg.what == 1) {
				String tv_content=(String) msg.obj;
				if(!tv_content.equals("null")&&!TextUtils.isEmpty(tv_content)){
					
					theActivity.noticesdetail_tv_content
					.setText(tv_content);
					theActivity.noticesdetail_tv_bottom_title.setText("主题：");
					theActivity.noticesdetail_tv_bottom_title.setVisibility(View.VISIBLE);
				}
				if(!add.equals("null")){
					theActivity.events_detail_add_lay.setVisibility(View.VISIBLE);
					theActivity.events_detail_add_txt.setText(add);
				}
				
			}
			super.handleMessage(msg);
		}
	}
	@Override
	protected void processLogic() {
		Thread t = new Thread(new Runnable() {
			Message msg = Message.obtain();
			@Override
			public void run() {
				CharSequence tests = Html.fromHtml(text_content,
						new MImageGetter(noticesdetail_tv_content, context),
						new MTagHandler());
				msg.what = 1;
				msg.obj = tests.toString();
				myHandler.sendMessage(msg);
					
			}

		});
		t.start();
	}
}
