package com.zh.education.control.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Text;

import com.zh.education.R;
import com.zh.education.business.ClassScheduleBusiness;
import com.zh.education.business.ClassScheduleBusiness.GetClassScheduleCallback;
import com.zh.education.business.NoticesBusiness;
import com.zh.education.business.NoticesBusiness.GetNoticesCallback;
import com.zh.education.model.ClassScheduleInfo;
import com.zh.education.model.NoticesInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.ConfigUtils;
import com.zh.education.utils.DatesUtils;
import com.zh.education.utils.MyHorizontalScrollView;
import com.zh.education.utils.MyScrollView;
import com.zh.education.utils.ProgressDialogUtils;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ClassScheduleFragment extends Fragment implements OnClickListener {

	MyHorizontalScrollView weekScrollView;
	LinearLayout schedule_layout_week;
	MyScrollView timeScrollView;
	LinearLayout schedule_layout_time;
	MyScrollView middleRowScrollView;
	MyHorizontalScrollView middleColumnScrollView;
	LinearLayout schedule_layout_middle;
	TextView TextView_jianju;

	TextView tv_left;
	TextView tv_right;
	TextView tv_week;

	private final static String TAG = "KebiaoFragment";
	Activity activity;
	private Boolean isAttached = false;
	private SharedPreferences zhedu_spf;
	private HashMap<String, String> mhashmap;

	public ClassScheduleFragment() {
		super();
	}

	public ClassScheduleFragment(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		Log.e("channel_id", args.getInt("id", 0)+"课表");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		isAttached = true;
		setUserVisibleHint(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_left:
			String time = mWeeksList[0];
			String day = DatesUtils.getInstance().addTime(time, -7,
					"yyyy-MM-dd");
			long selecttimeWeek = DatesUtils.getInstance().getDateToTimeStamp(
					day, "yyyy-MM-dd");
			getStartEndTimers(selecttimeWeek);
			getData();
			break;
		case R.id.tv_right:
			String timeright = mWeeksList[0];
			String dayright = DatesUtils.getInstance().addTime(timeright, 7,
					"yyyy-MM-dd");
			long selecttimeWeekright = DatesUtils.getInstance()
					.getDateToTimeStamp(dayright, "yyyy-MM-dd");
			getStartEndTimers(selecttimeWeekright);
			getData();
			break;
		default:
			break;
		}
	}

	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser && activity != null) {
			// fragment可见时加载数据
			getStartEndTimers(0);
			getData();
		} else {
			// fragment不可见时不执行操作
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_schedule, null);
		weekScrollView = (MyHorizontalScrollView) view
				.findViewById(R.id.weekScrollView);
		schedule_layout_week = (LinearLayout) view
				.findViewById(R.id.schedule_layout_week);
		timeScrollView = (MyScrollView) view.findViewById(R.id.timeScrollView);
		schedule_layout_time = (LinearLayout) view
				.findViewById(R.id.schedule_layout_time);
		middleRowScrollView = (MyScrollView) view
				.findViewById(R.id.middleRowScrollView);
		middleColumnScrollView = (MyHorizontalScrollView) view
				.findViewById(R.id.middleColumnScrollView);
		schedule_layout_middle = (LinearLayout) view
				.findViewById(R.id.schedule_layout_middle);

		tv_left = (TextView) view.findViewById(R.id.tv_left);
		tv_right = (TextView) view.findViewById(R.id.tv_right);
		tv_week = (TextView) view.findViewById(R.id.tv_week);
		tv_left.setOnClickListener(this);
		tv_right.setOnClickListener(this);
		TextView_jianju = (TextView) view.findViewById(R.id.TextView_jianju);

		int sreen = ConfigUtils.getInstance().getPhoneWidHeigth(activity).widthPixels;
		int width = sreen / 6;
		TextView_jianju.setWidth(width);
		// 监听滑动时更新weekScrollView的选中的位置
		timeScrollView
				.setScrollViewListener(new MyScrollView.ScrollViewListener() {
					public void onScrollChanged(
							MyScrollView paramAnonymousMyScrollView,
							int paramAnonymousInt1, int paramAnonymousInt2,
							int paramAnonymousInt3, int paramAnonymousInt4) {
						middleRowScrollView.scrollTo(0, paramAnonymousInt2);
					}
				});
		weekScrollView
				.setScrollViewListener(new MyHorizontalScrollView.ScrollViewListener() {
					public void onScrollChanged(
							MyHorizontalScrollView paramAnonymousMyHorizontalScrollView,
							int paramAnonymousInt1, int paramAnonymousInt2,
							int paramAnonymousInt3, int paramAnonymousInt4) {
						middleColumnScrollView.scrollTo(paramAnonymousInt1, 0);
					}
				});

		middleColumnScrollView
				.setScrollViewListener(new MyHorizontalScrollView.ScrollViewListener() {
					public void onScrollChanged(
							MyHorizontalScrollView paramAnonymousMyHorizontalScrollView,
							int paramAnonymousInt1, int paramAnonymousInt2,
							int paramAnonymousInt3, int paramAnonymousInt4) {
						weekScrollView.scrollTo(paramAnonymousInt1, 0);
					}
				});
		middleRowScrollView
				.setScrollViewListener(new MyScrollView.ScrollViewListener() {
					public void onScrollChanged(
							MyScrollView paramAnonymousMyScrollView,
							int paramAnonymousInt1, int paramAnonymousInt2,
							int paramAnonymousInt3, int paramAnonymousInt4) {
						timeScrollView.scrollTo(0, paramAnonymousInt2);
					}
				});
		return view;
	}

	/**
	 * 1441003800000
	 */
	private void getStartEndTimers(long selecttimeWeek) {
		mWeeksList = DatesUtils.getInstance().getWeekDayString(selecttimeWeek);
		long[] daysStamp = DatesUtils.getInstance().getWeekDay(selecttimeWeek);
		mWeekIndex = DatesUtils.getInstance().getWeekIndex(mWeeksList[0]);
		dateBegin = daysStamp[0];
		dateEnd = daysStamp[daysStamp.length - 1];
		// dateBegin=1453651200000l;
		// dateEnd=1454255999000l;
	}

	int mWeekIndex;
	long dateBegin;
	long dateEnd;
	/**
	 * 显示week view
	 */
	String[] mWeeksList;

	int showDay = 5;
	int showtimes = 24;

	private void getData() {
		if (isAttached) {
			boolean sd = CommonUtils.getInstance().isNetWorkAvaiable(activity);
			if (!sd) {
				Toast.makeText(activity,
						activity.getResources().getString(R.string.net_tishi),
						0).show();
				return;
			}
			zhedu_spf = CommonUtils.getInstance().getBestDoInfoSharedPrefs(
					activity);
			ProgressDialogUtils.showProgressDialog(activity, "数据加载中...","");
			mhashmap = new HashMap<String, String>();
			mhashmap.put("loginStr", zhedu_spf.getString("loginStr", ""));
			mhashmap.put("dateBegin", "" + dateBegin);
			mhashmap.put("dateEnd", "" + dateEnd);
			new ClassScheduleBusiness(activity, mhashmap,
					new GetClassScheduleCallback() {
						public void afterDataGet(HashMap<String, Object> dataMap) {
							ProgressDialogUtils.dismissProgressDialog("");
							if (dataMap != null) {
								String status = (String) dataMap.get("status");
								if (status.equals("200")) {
									ArrayList<ArrayList<ClassScheduleInfo>> mAllDayList = (ArrayList<ArrayList<ClassScheduleInfo>>) dataMap
											.get("mList");
									HashMap<String, Object> mschedulecoursecolorMap = (HashMap<String, Object>) dataMap
											.get("mschedulecoursecolorMap");
									showWeekView();
									showTimesDuanView();
									showMiddleCoursesView(mAllDayList,
											mschedulecoursecolorMap);
								} else {
									String msg = (String) dataMap.get("msg");
									Toast.makeText(activity, msg, 0).show();
								}
							} else {
								Toast.makeText(
										activity,
										activity.getResources().getString(
												R.string.net_tishi), 0).show();
							}
							CommonUtils.getInstance().setClearCacheBackDate(
									mhashmap, dataMap);

						}
					});

		}
	}

	int beforetimeIndex = -2;
	int nowtimeIndex = 0;
	String beforeCalendarName = "have";
	String nowCalendarName = "";

	private void showMiddleCoursesView(
			ArrayList<ArrayList<ClassScheduleInfo>> mAllDayList,
			HashMap<String, Object> mschedulecoursecolorMap) {
		schedule_layout_middle.removeAllViews();
		for (int i = 0; i < showDay; i++) {
			View fragment_schedule__day = LayoutInflater.from(activity)
					.inflate(R.layout.fragment_schedule_dayitem, null);
			LinearLayout fragment_schedule_layout_day = (LinearLayout) fragment_schedule__day
					.findViewById(R.id.fragment_schedule_layout_day);
			int sreen = ConfigUtils.getInstance().getPhoneWidHeigth(activity).widthPixels;
			int width = sreen / 6;
			fragment_schedule__day.setLayoutParams(new LayoutParams(width,
					LayoutParams.MATCH_PARENT));
			beforetimeIndex = -2;
			nowtimeIndex = 0;
			beforeCalendarName = "have";
			nowCalendarName = "";
			for (int j = 0; j < showtimes; j++) {
				View mdayView = LayoutInflater.from(activity).inflate(
						R.layout.fragment_schedule_dayocurseitem, null);
				LinearLayout fragment_schedule_layout_daycourse = (LinearLayout) mdayView
						.findViewById(R.id.fragment_schedule_layout_daycourse);
				setItemWidth(mdayView);

				if (mAllDayList != null && mAllDayList.size() != 0
						&& mschedulecoursecolorMap != null) {
					int haveDayIndex = checkHaveDay(i, mAllDayList);
					if (haveDayIndex != -1) {
						ArrayList<ClassScheduleInfo> hourList = mAllDayList
								.get(haveDayIndex);
						ArrayList<ClassScheduleInfo> coursesOfTime = hourList
								.get(j).getCoursesOfTime();
						if (coursesOfTime != null && coursesOfTime.size() != 0) {
							if (beforetimeIndex ==-2
									|| nowtimeIndex - beforetimeIndex > 1) {
								// 第一个
								beforeCalendarName = "have";
								nowCalendarName = "";
							} else {
								nowtimeIndex = j;
							}
							nowtimeIndex = j;
							for (int k = 0; k < coursesOfTime.size(); k++) {
								String name = coursesOfTime.get(k)
										.getCalendarName();
								nowCalendarName = name;
								if (mschedulecoursecolorMap.containsKey(i+">"+name)) {
									String valuess = (String) mschedulecoursecolorMap
											.get(i+">"+name);
									int colorid = Color.parseColor(valuess);
									System.err.println("beforetimeIndex="+beforetimeIndex+";nowtimeIndex="+nowtimeIndex);
									System.err.println("beforeCalendarName="+beforeCalendarName+";nowCalendarName="+nowCalendarName);
									showItem(coursesOfTime.size(), name,
											colorid,
											fragment_schedule_layout_daycourse);
									beforetimeIndex = nowtimeIndex;
								}
								beforeCalendarName = nowCalendarName;
							}
						} else {
							showItem(1, "", Color.parseColor("#FFFFFF"),
									fragment_schedule_layout_daycourse);
						}
					} else {
						showItem(1, "", Color.parseColor("#FFFFFF"),
								fragment_schedule_layout_daycourse);
					}
				} else {
					showItem(1, "", Color.parseColor("#FFFFFF"),
							fragment_schedule_layout_daycourse);
				}
				fragment_schedule_layout_day.addView(mdayView);
			}
			schedule_layout_middle.addView(fragment_schedule__day);
		}
	}

	private void showItem(int courseNum, String content, int colorid, 
			LinearLayout fragment_schedule_layout_daycourse) {
		View dayItemcourseView = LayoutInflater.from(activity).inflate(
				R.layout.fragment_schedule_timeitem, null);
		TextView schedule_timeitem_tv_time = (TextView) dayItemcourseView
				.findViewById(R.id.schedule_timeitem_tv_time);
		TextView schedule__timeitem_tv_line = (TextView) dayItemcourseView
				.findViewById(R.id.schedule__timeitem_tv_line);
		if (nowtimeIndex > beforetimeIndex+1) {
			schedule_timeitem_tv_time.setText(content);
		}else{
			if(!beforeCalendarName.equals(nowCalendarName)){
				schedule_timeitem_tv_time.setText(content);
			}
		}
		if (!TextUtils.isEmpty(content)) {
			schedule__timeitem_tv_line.setBackgroundColor(colorid);
		}
//		if(courseNum>1){
//			schedule__timeitem_tv_line.setVisibility(View.GONE);
//		}
		schedule_timeitem_tv_time.setBackgroundColor(colorid);
		int sreen = ConfigUtils.getInstance().getPhoneWidHeigth(activity).widthPixels;
		int width = sreen / 6;
		int height = ConfigUtils.getInstance().dip2px(activity, 45 );
		dayItemcourseView.setLayoutParams(new LayoutParams(width, height/ courseNum));
		fragment_schedule_layout_daycourse.addView(dayItemcourseView);
	}

	private int checkHaveDay(int i,
			ArrayList<ArrayList<ClassScheduleInfo>> mAllDayList) {
		for (int everydayIndex = 0; everydayIndex < mAllDayList.size(); everydayIndex++) {
			String showdayString = mWeeksList[i];
			String backdayString = mAllDayList.get(everydayIndex).get(0)
					.getDay();
			if (DatesUtils.getInstance().doDateEqual(showdayString,
					backdayString, "yyyy-MM-dd")) {
				return everydayIndex;
			}
		}
		return -1;
	}

	private void showWeekView() {
		tv_week.setText("第" + mWeekIndex + "周");
		if (mWeeksList != null && mWeeksList.length != 0) {
			schedule_layout_week.removeAllViews();
			for (int i = -1; i < showDay; i++) {
				View weekviews = LayoutInflater.from(activity).inflate(
						R.layout.fragment_schedule_weekitem, null);
				TextView schedule_week_tv_ee = (TextView) weekviews
						.findViewById(R.id.schedule_week_tv_ee);
				TextView schedule_week_tv_date = (TextView) weekviews
						.findViewById(R.id.schedule_week_tv_date);
				if (i > -1) {
					String dateString = mWeeksList[i];
					String week = DatesUtils.getInstance().getDateGeShi(
							dateString, "yyyy-MM-dd", "E");
					String day = DatesUtils.getInstance().getDateGeShi(
							dateString, "yyyy-MM-dd", "MM.dd");
					if(week.contains("星期")){
						week=week.replace("星期", "周");
					}
					schedule_week_tv_ee.setText(week);
					schedule_week_tv_date.setText(day);
				} else {
					schedule_week_tv_ee.setText("第" + mWeekIndex + "周");
					schedule_week_tv_date.setVisibility(View.GONE);
				}
				int sreen = ConfigUtils.getInstance().getPhoneWidHeigth(
						activity).widthPixels;
				int width = sreen / 6;
				int height = ConfigUtils.getInstance().dip2px(activity, 55);
				weekviews.setLayoutParams(new LayoutParams(width, height));
				schedule_layout_week.addView(weekviews);
				weekviews = null;
			}
		}
	}

	/**
	 * 设置每一块的宽高
	 * 
	 * @param weekviews
	 */
	private void setItemWidth(View weekviews) {
		int sreen = ConfigUtils.getInstance().getPhoneWidHeigth(activity).widthPixels;
		int width = sreen / 6;
		int height = ConfigUtils.getInstance().dip2px(activity, 45);
		weekviews.setLayoutParams(new LayoutParams(width, height));
	}

	/**
	 * 1加载左边时间段
	 */
	private void showTimesDuanView() {
		schedule_layout_time.removeAllViews();
		for (int i = 0; i < showtimes; i++) {
			View mTimesDuanView = LayoutInflater.from(activity).inflate(
					R.layout.fragment_schedule_timeitem, null);
			TextView schedule_timeitem_tv_time = (TextView) mTimesDuanView
					.findViewById(R.id.schedule_timeitem_tv_time);
			setItemWidth(mTimesDuanView);
			if (i < 10) {
				schedule_timeitem_tv_time.setText("0" + i + ":00");
			} else {
				schedule_timeitem_tv_time.setText(i + ":00");
			}
			schedule_timeitem_tv_time.setTextColor(Color.parseColor("#87888c"));
			TextView schedule_timeitem_tv_rowline = (TextView) mTimesDuanView
					.findViewById(R.id.schedule_timeitem_tv_rowline);
			schedule_timeitem_tv_rowline.setVisibility(View.GONE);
			schedule_layout_time.addView(mTimesDuanView);
			mTimesDuanView = null;
		}
	}

	/* 摧毁视图 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	/* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
