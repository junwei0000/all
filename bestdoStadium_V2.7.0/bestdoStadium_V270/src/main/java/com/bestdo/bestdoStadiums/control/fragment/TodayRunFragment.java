package com.bestdo.bestdoStadiums.control.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.baidu.navisdk.util.common.StringUtils;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.activity.UserWalkingActivity;
import com.bestdo.bestdoStadiums.control.activity.UserWalkingHistoryActivity;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Config;
import com.bestdo.bestdoStadiums.utils.DpUtil;
import com.bestdo.bestdoStadiums.utils.MyDialog;

/**
 * Created by YuHua on 2017/5/22 16:56 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class TodayRunFragment extends BaseFragment {
	private View root;
	private TextView tvTitle, tvStep, tvMsg;
	private ImageView img_run;
	private Activity mHomeActivity;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.f_walk_run, container, false);
		initView();
		initData();
		return root;
	}

	private void initView() {
		mHomeActivity = CommonUtils.getInstance().nHomeActivity;
		tvTitle = (TextView) root.findViewById(R.id.tv_run_title);
		tvStep = (TextView) root.findViewById(R.id.tv_run_count);
		CommonUtils.getInstance().setTextViewTypeface(getActivity(), tvStep);
		tvMsg = (TextView) root.findViewById(R.id.tv_run_bottom);
		img_run = (ImageView) root.findViewById(R.id.img_run);
		img_run.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
				if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					Intent intent = new Intent(mHomeActivity, UserWalkingActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("skipTypeActivity", "Run");
					startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
				} else {
					defineBackPressed();
				}
			}
		});
	}

	public void initData() {
		String title = Config.config().getMyWalkInfo().myName;
		String steps = Config.config().getMyKm().all_km_num;
		String msg = Config.config().getMyKm().step_msg;
		// tvTitle.setText(getString(R.string.run_title, title));
		tvTitle.setText(getString(R.string.run_title));
		tvStep.setText(StringUtils.isEmpty(steps) ? "0.00" : steps);
		tvMsg.setText(msg);
	}

	public void defineBackPressed() {
		final MyDialog selectDialog = new MyDialog(mHomeActivity, R.style.dialog, R.layout.dialog_userwalking);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定
		TextView myexit_text_title = (TextView) selectDialog.findViewById(R.id.myexit_text_title);

		text_sure.setText("设置");
		myexit_text_title.setText("您的定位服务未开启，请到“设置”中开启定位服务");
		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 返回开启GPS导航设置界面
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivityForResult(intent, 0);
				selectDialog.dismiss();
			}
		});
	}

}
