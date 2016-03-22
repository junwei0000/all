package com.zh.education.control.activity;

import java.util.HashMap;

import com.zh.education.R;
import com.zh.education.business.BoKeCommentsSendBusiness;
import com.zh.education.business.BoKeCommentsSendBusiness.SendCommentsCallback;
import com.zh.education.model.BoKeInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.Constans;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class BoKeSendCommentsActivity extends BaseActivity {
	private LinearLayout top_layout_back;
	private TextView top_tv_name;
	private Activity activity;
	private BoKeInfo boKeInfo;
	private TextView top_tv_right;
	private String title;
	private EditText sendcommdents_edit;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.top_layout_back:
			onBackPressed();
			break;
		case R.id.top_tv_right:
			title = sendcommdents_edit.getText().toString();
			CommonUtils.getInstance().closeSoftInput(
					BoKeSendCommentsActivity.this);
			if (!TextUtils.isEmpty(title)) {
				sentComments();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.fragment_boke_comments_send);
		CommonUtils.getInstance().addActivity(this);
		Intent in = getIntent();
		boKeInfo = (BoKeInfo) in.getSerializableExtra("boKeInfo");
		activity = BoKeSendCommentsActivity.this;
	}

	@Override
	protected void findViewById() {
		top_layout_back = (LinearLayout) findViewById(R.id.top_layout_back);
		top_tv_name = (TextView) findViewById(R.id.top_tv_name);
		top_tv_name.setText("发评论");
		top_tv_right = (TextView) findViewById(R.id.top_tv_right);
		sendcommdents_edit = (EditText) findViewById(R.id.sendcommdents_edit);
		top_tv_right.setVisibility(View.VISIBLE);
	}

	@Override
	protected void setListener() {
		top_layout_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
		sendcommdents_edit.addTextChangedListener(new TextWatcher() {

			@SuppressLint("NewApi")
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				String ss = sendcommdents_edit.getText().toString();
				if (!TextUtils.isEmpty(ss)) {
					mListHandler.sendEmptyMessage(0);
				} else {
					mListHandler.sendEmptyMessage(1);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
	}

	Handler mListHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				top_tv_right.setBackgroundDrawable(context.getResources()
						.getDrawable(R.drawable.sendcommonts_bg2));
				top_tv_right.setTextColor(getResources().getColor(R.color.white));
				break;
			case 1:
				top_tv_right.setBackgroundDrawable(context.getResources()
						.getDrawable(R.drawable.sendcommonts_bg));
				top_tv_right.setTextColor(getResources().getColor(R.color.contents_color));
				break;
			}
		}
	};

	@Override
	protected void processLogic() {

	}

	private void sentComments() {
		HashMap<String, String> mhashmap = new HashMap<String, String>();
		SharedPreferences zhedu_spf = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(activity);
		mhashmap.put("blogUrl", zhedu_spf.getString("BlogUrl", ""));
		mhashmap.put("tokenStr", zhedu_spf.getString("tokenStr", ""));
		mhashmap.put("postID", boKeInfo.getId() + "");
		mhashmap.put("title", title + "");

		new BoKeCommentsSendBusiness(activity, mhashmap,
				new SendCommentsCallback() {

					@Override
					public void afterDataGet(HashMap<String, Object> dataMap) {
						if (dataMap != null) {
							String status = (String) dataMap.get("status");
							if (status.equals("200")) {
								onBackPressed();
							}
						}
					}
				});

	}

	/**
	 * 监听返回键
	 */
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		sendToCommentsBroadcast();
		finish();
	}

	/**
	 * 向评论列表发送广播
	 */
	private void sendToCommentsBroadcast() {
		Intent i = new Intent();
		i.setAction("commentsRefresh");
		sendBroadcast(i);
	}
}
