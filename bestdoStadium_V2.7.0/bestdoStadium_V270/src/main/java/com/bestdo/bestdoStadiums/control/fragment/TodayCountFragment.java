package com.bestdo.bestdoStadiums.control.fragment;

import static com.bestdo.bestdoStadiums.R.id.tv_rank;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.baidu.navisdk.util.common.StringUtils;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.net.GsonServer;
import com.bestdo.bestdoStadiums.control.activity.WalkRankActivity;
import com.bestdo.bestdoStadiums.control.view.ColorArcProgressBar;
import com.bestdo.bestdoStadiums.control.view.ColorArcProgressBar.OnWalkPointClick;
import com.bestdo.bestdoStadiums.model.MyWalkInfoMapper.MyWalkInfo;
import com.bestdo.bestdoStadiums.model.MyWalkInfoMapper.MyWalkInfoBack;
import com.bestdo.bestdoStadiums.utils.App;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Config;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.OneWheelUtil;
import com.bestdo.bestdoStadiums.utils.TimeUtils;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by YuHua on 2017/5/22 16:56 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class TodayCountFragment extends BaseFragment {
	private static final int ONE_WEEK = 7;
	private static final double MIN_STEP_PERCENT = 0.15;
	private View root;
	private ColorArcProgressBar bar;
	private TextView tvRank, tvCompany;
	private MyWalkInfo myWalkInfo;
	private TextView tvKio;
	private TextView tvNo;
	private TextView tvCal;
	private TextView tvDay1, tvDay2, tvDay3, tvDay4, tvDay5, tvDay6, tvDay7;
	private ProgressBar bar1, bar2, bar3, bar4, bar5, bar6, bar7;
	private int curPoint;
	private int curStep;
	private Timer timer;
	private SharedPreferences stepAll_INFOSharedPrefs;
	private boolean initData;

	public static TodayCountFragment newInstance(boolean initData) {
		TodayCountFragment fragment = new TodayCountFragment();
		fragment.initData = initData;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.f_walk_today, container, false);
		initView();
		initData();
		upTodaySteps();
		return root;
	}

	private void initView() {
		bar = (ColorArcProgressBar) root.findViewById(R.id.bar);
		bar.setOnPointClick(new OnWalkPointClick() {

			@Override
			public void onClick() {
				TimeUtils mTimeUtils = new TimeUtils(getActivity());
				mTimeUtils.showOneWheel("请设定目标", OneWheelUtil.getIndex(String.valueOf(curPoint)),
						OneWheelUtil.getData(), onPointClick);
			}
		});
		tvRank = (TextView) root.findViewById(tv_rank);
		tvCompany = (TextView) root.findViewById(R.id.tv_company);
		tvKio = (TextView) root.findViewById(R.id.tv_kio);
		tvNo = (TextView) root.findViewById(R.id.tv_No);
		tvCompany = (TextView) root.findViewById(R.id.tv_company);
		tvCal = (TextView) root.findViewById(R.id.tv_cal);
		tvDay1 = (TextView) root.findViewById(R.id.tv_day1);
		tvDay2 = (TextView) root.findViewById(R.id.tv_day2);
		tvDay3 = (TextView) root.findViewById(R.id.tv_day3);
		tvDay4 = (TextView) root.findViewById(R.id.tv_day4);
		tvDay5 = (TextView) root.findViewById(R.id.tv_day5);
		tvDay6 = (TextView) root.findViewById(R.id.tv_day6);
		tvDay7 = (TextView) root.findViewById(R.id.tv_day7);
		bar1 = (ProgressBar) root.findViewById(R.id.progress1);
		bar2 = (ProgressBar) root.findViewById(R.id.progress2);
		bar3 = (ProgressBar) root.findViewById(R.id.progress3);
		bar4 = (ProgressBar) root.findViewById(R.id.progress4);
		bar5 = (ProgressBar) root.findViewById(R.id.progress5);
		bar6 = (ProgressBar) root.findViewById(R.id.progress6);
		bar7 = (ProgressBar) root.findViewById(R.id.progress7);
		tvRank.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toRank();
			}
		});

	}

	private void initData() {
		if (!initData)
			return;
		stepAll_INFOSharedPrefs = getActivity().getSharedPreferences("stepAll_INFO", 0);
		getTodayStepFromLocal();
		curPoint = CommonUtils.getInstance().getBestDoInfoSharedPrefs(getActivity())
				.getInt(Constans.ShKey.MY_WALK_POINT, 10000);
		myWalkInfo = Config.config().getMyWalkInfo();
		List<MyWalkInfoBack> weekSteps = myWalkInfo.myWalkInfoBacks;
		tvCompany.setText(myWalkInfo.company);
		tvNo.setText(myWalkInfo.no);
		bar.setUnit(StringUtils.isEmpty(myWalkInfo.curTime) ? TimeUtils.getNowYMD() : myWalkInfo.curTime);
		setTodayBar();
		setDays(weekSteps);
		setBars(weekSteps);
	}

	private void getTodayStepFromLocal() {
		timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				curStep = stepAll_INFOSharedPrefs.getInt(DatesUtils.getInstance().getNowTime("yyyy-MM-dd"), 0);
				Config.d("刷新当前步数");
				App.runInMainThread(upNowStepTask);
			}
		};
		timer.schedule(task, 0, 5000);
	}

	private void upTodaySteps() {
		GsonServer.uploadTodayStep(curStep);

	}

	private Runnable upNowStepTask = new Runnable() {

		@Override
		public void run() {
			Config.config().updateMyKart(curStep);
			if (TodayCountFragment.this.isVisible()) {
				bar.setCurrentValues(curStep);
				tvKio.setText(String.format("%.2f", myWalkInfo.kilometers));
				tvCal.setText(String.valueOf((int) myWalkInfo.kart));
			} else
				Config.d("当前页面不可见，不刷新");
		}
	};

	@Override
	public void onDestroyView() {
		safeClose();
		super.onDestroyView();
	}

	private void safeClose() {
		try {
			if (timer != null)
				timer.cancel();
			timer = null;
		} catch (Exception e) {
			// ignore
		}
	}

	private void setTodayBar() {
		bar.setMaxValues(curPoint);
		bar.setTitle2("目标" + curPoint);
		bar.setCurrentValues(curStep);
	}

	private void setDays(List<MyWalkInfoBack> weekSteps) {
		try {
			if (weekSteps != null && weekSteps.size() == ONE_WEEK) {
				tvDay1.setText(weekSteps.get(0).date);
				tvDay2.setText(weekSteps.get(1).date);
				tvDay3.setText(weekSteps.get(2).date);
				tvDay4.setText(weekSteps.get(3).date);
				tvDay5.setText(weekSteps.get(4).date);
				tvDay6.setText(weekSteps.get(5).date);
				tvDay7.setText(weekSteps.get(6).date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setBars(List<MyWalkInfoBack> weekSteps) {
		int minStep = (int) (curPoint * MIN_STEP_PERCENT);
		bar1.setMax(curPoint);
		bar2.setMax(curPoint);
		bar3.setMax(curPoint);
		bar4.setMax(curPoint);
		bar5.setMax(curPoint);
		bar6.setMax(curPoint);
		bar7.setMax(curPoint);
		int time = 1500;
		int s1 = 0, s2 = 0, s3 = 0, s4 = 0, s5 = 0, s6 = 0, s7 = 0;
		try {
			s1 = Integer.valueOf(weekSteps.get(0).step_num);
			s2 = Integer.valueOf(weekSteps.get(1).step_num);
			s3 = Integer.valueOf(weekSteps.get(2).step_num);
			s4 = Integer.valueOf(weekSteps.get(3).step_num);
			s5 = Integer.valueOf(weekSteps.get(4).step_num);
			s6 = Integer.valueOf(weekSteps.get(5).step_num);
			s7 = Integer.valueOf(weekSteps.get(6).step_num);
			s1 = s1 == 0 ? 0 : Math.max(s1, minStep);
			s2 = s2 == 0 ? 0 : Math.max(s2, minStep);
			s3 = s3 == 0 ? 0 : Math.max(s3, minStep);
			s4 = s4 == 0 ? 0 : Math.max(s4, minStep);
			s5 = s5 == 0 ? 0 : Math.max(s5, minStep);
			s6 = s6 == 0 ? 0 : Math.max(s6, minStep);
			s7 = s7 == 0 ? 0 : Math.max(s7, minStep);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectAnimator.ofInt(bar1, "progress", 0, s1).setDuration(time).start();
		ObjectAnimator.ofInt(bar2, "progress", 0, s2).setDuration(time).start();
		ObjectAnimator.ofInt(bar3, "progress", 0, s3).setDuration(time).start();
		ObjectAnimator.ofInt(bar4, "progress", 0, s4).setDuration(time).start();
		ObjectAnimator.ofInt(bar5, "progress", 0, s5).setDuration(time).start();
		ObjectAnimator.ofInt(bar6, "progress", 0, s6).setDuration(time).start();
		ObjectAnimator.ofInt(bar7, "progress", 0, s7).setDuration(time).start();
	}

	private void toRank() {
		upTodaySteps();
		startActivity(new Intent(getActivity(), WalkRankActivity.class));
	}

	private OnClickListener onPointClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int point = 10000;
			try {
				point = Integer.valueOf((String) v.getTag());
			} catch (Exception e) {
			}
			if (curPoint != point) {
				curPoint = point;
				setTodayBar();
				setBars(myWalkInfo.myWalkInfoBacks);
				CommonUtils.getInstance().getBestDoInfoSharedPrefs(getActivity()).edit()
						.putInt(Constans.ShKey.MY_WALK_POINT, curPoint).apply();
			}
		}
	};

}
