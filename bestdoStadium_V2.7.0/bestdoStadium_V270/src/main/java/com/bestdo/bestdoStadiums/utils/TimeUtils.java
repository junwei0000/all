package com.bestdo.bestdoStadiums.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.teetime.ArrayWheelAdapter;
import com.bestdo.bestdoStadiums.control.teetime.OnWheelChangedListener;
import com.bestdo.bestdoStadiums.control.teetime.WheelView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-10-10 下午3:47:16
 * @Description 类描述：活动选择时间
 */
public class TimeUtils {

	public static final String FORMAT_YYYYMMDD = "yyyy.MM.dd";

	Context mContext;
	public String[][][] ymdays;
	public String[][] ymonths;

	public String[][] days;
	public String[] months;
	public String[] years;
	private Handler mHandler;
	private int mHandlerId;
	//
	public String[] hours;
	public String[] mins;
	int hoursIndex;
	int minsIndex;
	public int hoursIndex_select;
	public int minsIndex_select;
	public boolean hourShowStatus = false;

	public TimeUtils() {
		super();
	}

	public TimeUtils(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public void setInit(Handler mHandler, int mHandlerId) {
		this.mHandler = mHandler;
		this.mHandlerId = mHandlerId;
	}

	int yearIndex;
	int monthsIndex;
	int dayIndex;
	MyDialog selectDialog;
	public int yearIndex_select;
	public int monthsIndex_select;
	public int dayIndex_select;

	public void getDateOrTeetimeDialog() {
		// 防止索引小于0
		selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_time);// 创建Dialog并设置样式主题
		Window dialogWindow = selectDialog.getWindow();
		// WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		dialogWindow.setWindowAnimations(R.style.showAnDialog);
		selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		final LinearLayout teetime_lay = (LinearLayout) selectDialog.findViewById(R.id.teetime_lay);
		teetime_lay.setBackgroundColor(mContext.getResources().getColor(R.color.white));
		teetime_lay.setLayoutParams(new FrameLayout.LayoutParams(
				ConfigUtils.getInstance().getPhoneWidHeigth(mContext).widthPixels, LayoutParams.WRAP_CONTENT));
		TextView teetime_none = (TextView) selectDialog.findViewById(R.id.teetime_none);// 无teetime
		TextView teetime_sure = (TextView) selectDialog.findViewById(R.id.teetime_sure);// 确定
		WheelView teetime_year = (WheelView) selectDialog.findViewById(R.id.teetime_year);
		WheelView teetime_mothch = (WheelView) selectDialog.findViewById(R.id.teetime_mothch);
		final WheelView teetime_day = (WheelView) selectDialog.findViewById(R.id.teetime_day);
		WheelView teetime_hour = (WheelView) selectDialog.findViewById(R.id.teetime_hour);
		WheelView teetime_min = (WheelView) selectDialog.findViewById(R.id.teetime_min);
		// ---------------------
		if (hourShowStatus) {
			teetime_hour.setVisibility(View.VISIBLE);
			teetime_min.setVisibility(View.VISIBLE);
			teetime_hour.setVisibleItems(5);
			teetime_min.setVisibleItems(5);
			teetime_min.setCyclic(true);
			teetime_hour.setCyclic(true);
			hoursIndex = hoursIndex_select;
			minsIndex = minsIndex_select;
			teetime_hour.setAdapter(new ArrayWheelAdapter<String>(hours, hours.length));
			teetime_hour.setCurrentItem(hoursIndex_select);

			teetime_min.setAdapter(new ArrayWheelAdapter<String>(mins, mins.length));
			teetime_min.setCurrentItem(minsIndex_select);
			teetime_hour.addChangingListener(new OnWheelChangedListener() {
				public void onChanged(WheelView wheel, int oldValue, int newValue) {
					hoursIndex = newValue;
				}
			});
			teetime_min.addChangingListener(new OnWheelChangedListener() {
				public void onChanged(WheelView wheel, int oldValue, int newValue) {
					minsIndex = newValue;
				}
			});
		}
		// -----------------

		teetime_year.setVisibleItems(5);
		teetime_mothch.setVisibleItems(5);
		teetime_day.setVisibleItems(5);
		teetime_mothch.setCyclic(true);
		teetime_day.setCyclic(true);
		teetime_year.setAdapter(new ArrayWheelAdapter<String>(years, years.length));
		teetime_year.setCurrentItem(yearIndex_select);

		teetime_mothch.setAdapter(new ArrayWheelAdapter<String>(months, months.length));
		teetime_mothch.setCurrentItem(monthsIndex_select);

		teetime_day
				.setAdapter(new ArrayWheelAdapter<String>(days[monthsIndex_select], days[monthsIndex_select].length));
		teetime_day.setCurrentItem(dayIndex_select);
		yearIndex = yearIndex_select;
		monthsIndex = monthsIndex_select;
		dayIndex = dayIndex_select;
		teetime_year.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				yearIndex = newValue;
			}
		});
		teetime_mothch.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				monthsIndex = newValue;

				teetime_day.setAdapter(new ArrayWheelAdapter<String>(days[newValue]));
				teetime_day.setCurrentItem(0);
			}
		});
		teetime_day.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				dayIndex = newValue;
			}
		});

		teetime_none.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				selectDialog = null;
			}
		});
		teetime_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				selectDialog = null;
				yearIndex_select = yearIndex;
				monthsIndex_select = monthsIndex;
				dayIndex_select = dayIndex;
				String selectday = years[yearIndex_select] + months[monthsIndex_select]
						+ days[monthsIndex_select][dayIndex_select];

				// ---------------
				if (hourShowStatus) {
					hoursIndex_select = hoursIndex;
					minsIndex_select = minsIndex;
					selectday = years[yearIndex_select] + months[monthsIndex_select]
							+ days[monthsIndex_select][dayIndex_select] + "  " + hours[hoursIndex_select]
							+ mins[minsIndex_select];
				}
				// ---------------
				Message msgMessage = new Message();
				msgMessage.what = mHandlerId;
				msgMessage.getData().putString("selectday", selectday);
				mHandler.sendMessage(msgMessage);
				msgMessage = null;
			}
		});
	}

	public static String formatTime(double time, String format) {
		try {
			long l = (long) (time) * 1000;
			Config.d(System.currentTimeMillis() + "   " + l);
			String re = new SimpleDateFormat(format).format(new Date(l));
			return re;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getNowYMD() {
		try {
			return new SimpleDateFormat(FORMAT_YYYYMMDD).format(new Date(System.currentTimeMillis()));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 到当前时间
	 * 
	 * @param startYear
	 */
	public void initFromToNowDates(int startYear) {
		String nowyear = DatesUtils.getInstance().getNowTime("yyyy");
		int num = Integer.parseInt(PriceUtils.getInstance().gteSubtractSumPrice("" + startYear, nowyear)) + 1;
		String[] years = new String[num];
		String[][] months = new String[num][];
		String[][][] days = new String[num][][];
		for (int k = 0; k < num; k++) {
			years[k] = startYear + k + "年";
			int monthnum = 12;
			if (Integer.parseInt(nowyear) - (startYear + k) == 0) {
				monthnum = Integer.parseInt(DatesUtils.getInstance().getNowTime("MM"));
			}
			months[k] = new String[monthnum];
			days[k] = new String[monthnum][];
			for (int i = 0; i < monthnum; i++) {
				String qianzuiString = "0";
				if (i < 9) {
					qianzuiString = "0";
				} else {
					qianzuiString = "";
				}
				months[k][i] = qianzuiString + (1 + i) + "月";
				int lastday = DatesUtils.getInstance().getLastDayOfMonth(startYear + k, 1 + i);
				if ((Integer.parseInt(nowyear) == (startYear + k))
						&& ((1 + i) == Integer.parseInt(DatesUtils.getInstance().getNowTime("MM")))) {
					lastday = Integer.parseInt(DatesUtils.getInstance().getNowTime("dd"));
				}
				days[k][i] = new String[lastday];
				for (int j = 0; j < lastday; j++) {
					String dayqianzuiString = "0";
					if (j < 9) {
						dayqianzuiString = "0";
					} else {
						dayqianzuiString = "";
					}
					days[k][i][j] = dayqianzuiString + (1 + j) + "日";
				}
			}
		}
		this.years = years;
		this.ymonths = months;
		this.ymdays = days;
		System.err.println(years.toString());
		System.err.println(months.toString());
		System.err.println(days.toString());
	}

	/**
	 * 设置年月日
	 */
	public void getYMDDialog(final int yearIndex_select, final int monthsIndex_select, final int dayIndex_select) {
		// 防止索引小于0
		selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_time);// 创建Dialog并设置样式主题
		Window dialogWindow = selectDialog.getWindow();
		// WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		dialogWindow.setWindowAnimations(R.style.showAnDialog);
		selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		final LinearLayout teetime_lay = (LinearLayout) selectDialog.findViewById(R.id.teetime_lay);
		teetime_lay.setBackgroundColor(mContext.getResources().getColor(R.color.white));
		teetime_lay.setLayoutParams(new FrameLayout.LayoutParams(
				ConfigUtils.getInstance().getPhoneWidHeigth(mContext).widthPixels, LayoutParams.WRAP_CONTENT));
		TextView teetime_title = (TextView) selectDialog.findViewById(R.id.teetime_title);
		TextView teetime_none = (TextView) selectDialog.findViewById(R.id.teetime_none);// 无teetime
		TextView teetime_sure = (TextView) selectDialog.findViewById(R.id.teetime_sure);// 确定
		WheelView teetime_year = (WheelView) selectDialog.findViewById(R.id.teetime_year);
		teetime_year.setVisibility(View.VISIBLE);
		final WheelView teetime_mothch = (WheelView) selectDialog.findViewById(R.id.teetime_mothch);
		final WheelView teetime_day = (WheelView) selectDialog.findViewById(R.id.teetime_day);
		// ---------------------
		teetime_title.setText("选择时间");
		teetime_year.setVisibleItems(5);
		teetime_mothch.setVisibleItems(5);
		teetime_day.setVisibleItems(5);
		teetime_day.setCyclic(true);
		teetime_year.setAdapter(new ArrayWheelAdapter<String>(years, years.length));
		teetime_year.setCurrentItem(yearIndex_select);

		teetime_mothch
				.setAdapter(new ArrayWheelAdapter<String>(ymonths[yearIndex_select], ymonths[yearIndex_select].length));
		teetime_mothch.setCurrentItem(monthsIndex_select);
		String[] d = ymdays[yearIndex_select][monthsIndex_select];
		teetime_day.setAdapter(new ArrayWheelAdapter<String>(d, d.length));
		teetime_day.setCurrentItem(dayIndex_select);
		yearIndex = yearIndex_select;
		monthsIndex = monthsIndex_select;
		dayIndex = dayIndex_select;
		teetime_year.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				yearIndex = newValue;
				teetime_mothch.setAdapter(new ArrayWheelAdapter<String>(ymonths[yearIndex]));
				monthsIndex = 0;
				teetime_mothch.setCurrentItem(monthsIndex);
				teetime_day.setAdapter(new ArrayWheelAdapter<String>(ymdays[yearIndex][monthsIndex]));
				teetime_day.setCurrentItem(0);
			}
		});
		teetime_mothch.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				monthsIndex = newValue;

				teetime_day.setAdapter(new ArrayWheelAdapter<String>(ymdays[yearIndex][monthsIndex]));
				teetime_day.setCurrentItem(0);
			}
		});
		teetime_day.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				dayIndex = newValue;
			}
		});

		teetime_none.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				selectDialog = null;
			}
		});
		teetime_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				selectDialog = null;
				String selectday = years[yearIndex] + ymonths[yearIndex][monthsIndex]
						+ ymdays[yearIndex][monthsIndex][dayIndex];
				// ---------------
				Message msgMessage = new Message();
				msgMessage.what = mHandlerId;
				msgMessage.getData().putString("selectday", selectday);
				mHandler.sendMessage(msgMessage);
				msgMessage = null;
			}
		});
	}

	/**
	 * 设置年
	 */
	public void getYDialog(final int yearIndex_select, final int monthsIndex_select, final int dayIndex_select) {
		// 防止索引小于0
		selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_time);// 创建Dialog并设置样式主题
		Window dialogWindow = selectDialog.getWindow();
		// WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		dialogWindow.setWindowAnimations(R.style.showAnDialog);
		selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		final LinearLayout teetime_lay = (LinearLayout) selectDialog.findViewById(R.id.teetime_lay);
		teetime_lay.setBackgroundColor(mContext.getResources().getColor(R.color.white));
		teetime_lay.setLayoutParams(new FrameLayout.LayoutParams(
				ConfigUtils.getInstance().getPhoneWidHeigth(mContext).widthPixels, LayoutParams.WRAP_CONTENT));
		TextView teetime_title = (TextView) selectDialog.findViewById(R.id.teetime_title);
		TextView teetime_none = (TextView) selectDialog.findViewById(R.id.teetime_none);// 无teetime
		TextView teetime_sure = (TextView) selectDialog.findViewById(R.id.teetime_sure);// 确定
		WheelView teetime_year = (WheelView) selectDialog.findViewById(R.id.teetime_year);
		teetime_year.setVisibility(View.VISIBLE);
		final WheelView teetime_mothch = (WheelView) selectDialog.findViewById(R.id.teetime_mothch);
		final WheelView teetime_day = (WheelView) selectDialog.findViewById(R.id.teetime_day);
		teetime_day.setVisibility(View.GONE);
		teetime_mothch.setVisibility(View.GONE);
		// ---------------------
		teetime_title.setText("选择时间");
		teetime_year.setVisibleItems(5);
		teetime_year.setAdapter(new ArrayWheelAdapter<String>(years, years.length));
		teetime_year.setCurrentItem(yearIndex_select);

		yearIndex = yearIndex_select;
		teetime_year.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				yearIndex = newValue;
			}
		});
		teetime_none.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				selectDialog = null;
			}
		});
		teetime_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				selectDialog = null;
				String selectday = years[yearIndex];
				// ---------------
				Message msgMessage = new Message();
				msgMessage.what = mHandlerId;
				msgMessage.getData().putString("selectday", selectday);
				mHandler.sendMessage(msgMessage);
				msgMessage = null;
			}
		});
	}

	private int lastSelect;

	/**
	 * 弹出一个轮子的选择框
	 */
	public void showOneWheel(String title, int curSelect, final String[] items, final OnClickListener onSelect) {
		lastSelect = curSelect;
		// 防止索引小于0
		selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_time);// 创建Dialog并设置样式主题
		Window dialogWindow = selectDialog.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		dialogWindow.setWindowAnimations(R.style.showAnDialog);
		selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		final LinearLayout teetime_lay = (LinearLayout) selectDialog.findViewById(R.id.teetime_lay);
		teetime_lay.setBackgroundColor(mContext.getResources().getColor(R.color.white));
		teetime_lay.setLayoutParams(new FrameLayout.LayoutParams(
				ConfigUtils.getInstance().getPhoneWidHeigth(mContext).widthPixels, LayoutParams.WRAP_CONTENT));
		TextView teetime_title = (TextView) selectDialog.findViewById(R.id.teetime_title);
		TextView teetime_none = (TextView) selectDialog.findViewById(R.id.teetime_none);// 无teetime
		TextView teetime_sure = (TextView) selectDialog.findViewById(R.id.teetime_sure);// 确定
		WheelView teetime_year = (WheelView) selectDialog.findViewById(R.id.teetime_year);
		teetime_year.setVisibility(View.VISIBLE);
		final WheelView teetime_mothch = (WheelView) selectDialog.findViewById(R.id.teetime_mothch);
		final WheelView teetime_day = (WheelView) selectDialog.findViewById(R.id.teetime_day);
		WheelView teetime_hour = (WheelView) selectDialog.findViewById(R.id.teetime_hour);
		teetime_mothch.setVisibility(View.GONE);
		teetime_hour.setVisibility(View.VISIBLE);
		// ---------------------
		teetime_title.setText(title);
		teetime_day.setVisibleItems(5);
		teetime_day.setAdapter(new ArrayWheelAdapter<String>(items, items.length));
		teetime_day.setCurrentItem(curSelect);

		teetime_day.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				Config.d("lastSelect=" + lastSelect + "oldValue=" + oldValue + "newValue=" + newValue);
				lastSelect = newValue;
			}
		});
		teetime_none.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				selectDialog = null;
			}
		});
		teetime_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Config.d("确定lastSelect=" + lastSelect);
				selectDialog.dismiss();
				selectDialog = null;
				if (onSelect != null) {
					v.setTag(items[lastSelect]);
					onSelect.onClick(v);
				}
			}
		});
	}
}
