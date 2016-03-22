package com.zh.education.control.activity;

import java.lang.ref.WeakReference;

import com.zh.education.R;
import com.zh.education.model.BoKeInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.DatesUtils;
import com.zh.education.utils.ImageLoader;
import com.zh.education.utils.MImageGetter;
import com.zh.education.utils.MTagHandler;
import com.zh.education.utils.NewTextView;
import com.zh.education.utils.TextSizeUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BoKeDetailActivity extends BaseActivity {
	private String text_content, name, Title, time;
	private LinearLayout top_layout_back;
	private LinearLayout top_head_layout;
	private TextView top_tv_name,  boke_detail_name,
			boke_detail_text_time, boke_detail_text_title;
	private NewTextView boke_detail_text_context ;
	private ProgressBar bar;
	private TextView boke_detail_text_zan;
	private TextView boke_detai_text_pinglun;
	private BoKeInfo boKeInfo;
	private LinearLayout boke_detai_pinglun_layout;
	private ImageView bokelist_item_img_cover;
	private Activity activity;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.top_layout_back:
			onBackPressed();
			break;
		case R.id.boke_detai_pinglun_layout:
			if(!CommonUtils.getInstance().LoginTokenReLog(this)){
			Intent intent_boKeDetail = new Intent(activity,
					BoKeCommentsActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("boKeInfo", boKeInfo);
			intent_boKeDetail.putExtras(bundle);
			intent_boKeDetail.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent_boKeDetail);
			activity.overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_left);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.fragment_boke_detail);
		CommonUtils.getInstance().addActivity(this);
		Intent in = getIntent();
		boKeInfo = (BoKeInfo) in.getSerializableExtra("boKeInfo");
		text_content = boKeInfo.getContent();
		name = boKeInfo.getCreateUser();
		Title = boKeInfo.getTitle();
		time = DatesUtils.getInstance().getTimeStampToDate(
				boKeInfo.getCreateTime(), "yyyy-MM-dd HH:mm");
		activity = this;
	}

	@Override
	protected void findViewById() {
		top_layout_back = (LinearLayout) findViewById(R.id.top_layout_back);
		top_head_layout = (LinearLayout) findViewById(R.id.top_head_layout);
		top_tv_name = (TextView) findViewById(R.id.top_tv_name);
		boke_detail_name = (TextView) findViewById(R.id.boke_detail_name);
		boke_detail_text_context = (NewTextView) findViewById(R.id.boke_detail_text_context);
		boke_detail_text_title = (TextView) findViewById(R.id.boke_detail_text_title);
		boke_detail_text_zan = (TextView) findViewById(R.id.boke_detail_text_zan);
		boke_detai_text_pinglun = (TextView) findViewById(R.id.boke_detai_text_pinglun);
		boke_detai_pinglun_layout = (LinearLayout) findViewById(R.id.boke_detai_pinglun_layout);
		boke_detail_text_time = (TextView) findViewById(R.id.boke_detail_text_time);
		bokelist_item_img_cover = (ImageView) findViewById(R.id.bokelist_item_img_cover);
		bar = (ProgressBar) this.findViewById(R.id.id_bar);
		boke_detail_name.setText(name);
		top_tv_name.setText("博客正文");
		boke_detail_text_time.setText(time);
		boke_detail_text_title.setText(Title);
		TextSizeUtils.getInstance().setChangeTextSize(boke_detail_text_context);
		top_head_layout.setBackgroundColor(getResources().getColor(
				R.color.transparent));

		boke_detail_text_zan.setText(boKeInfo.getLikeCount() + "");
		boke_detai_text_pinglun.setText(boKeInfo.getCommentCount() + "");
		String Cover = boKeInfo.getCover();
		if (TextUtils.isEmpty(Cover) || Cover.equals("null")) {
			bokelist_item_img_cover.setVisibility(View.GONE);
		} else {
			ImageLoader asyncImageLoader = new ImageLoader(context, "");
			asyncImageLoader.DisplayImage(Cover, bokelist_item_img_cover);
			bokelist_item_img_cover.setVisibility(View.VISIBLE);
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void setListener() {
		top_layout_back.setOnClickListener(this);
		boke_detai_pinglun_layout.setOnClickListener(this);
		
		/**
		 * 可以复制文本
		 */
		boke_detail_text_context.setTextIsSelectable(true);
		boke_detail_text_context.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
				cmb.setText(boke_detail_text_context.getText().toString().trim()); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
				return false;
			}
		});
	}

	final MyHandler myHandler = new MyHandler(this);

	@Override
	protected void processLogic() {
		Thread t = new Thread(new Runnable() {
			Message msg = Message.obtain();

			@Override
			public void run() {
				bar.setVisibility(View.VISIBLE);
				CharSequence tests = Html.fromHtml(text_content,
						new MImageGetter(boke_detail_text_context, context),
						new MTagHandler());
				msg.what = 0x101;
				msg.obj = tests;
				myHandler.sendMessage(msg);
			}

		});
		t.start();
	}

	/*
	 * Handler
	 * 类应该应该为static类型，否则有可能造成泄露。在程序消息队列中排队的消息保持了对目标Handler类的应用。如果Handler是个内部类，那
	 * 么它也会保持它所在的外部类的引用。为了避免泄露这个外部类，应该将Handler声明为static嵌套类，并且使用对外部类的弱应用。
	 */
	private static class MyHandler extends Handler {
		WeakReference<BoKeDetailActivity> mActivity;

		public MyHandler(BoKeDetailActivity activity) {
			// TODO Auto-generated constructor stub
			mActivity = new WeakReference<BoKeDetailActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			BoKeDetailActivity theActivity = mActivity.get();
			if (msg.what == 0x101) {
				theActivity.bar.setVisibility(View.GONE);
				theActivity.boke_detail_text_context
						.setText((CharSequence) msg.obj);
			}
			super.handleMessage(msg);
		}
	}

	/**
	 * 监听返回键
	 */
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}
}
