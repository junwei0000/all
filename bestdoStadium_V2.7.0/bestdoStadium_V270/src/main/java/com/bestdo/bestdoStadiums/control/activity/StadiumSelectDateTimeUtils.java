package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.control.teetime.ArrayWheelAdapter;
import com.bestdo.bestdoStadiums.control.teetime.OnWheelChangedListener;
import com.bestdo.bestdoStadiums.control.teetime.WheelView;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author jun
 */
public class StadiumSelectDateTimeUtils {

	private Context context;
	private int firstValue;// 记录当前选中
	private int secondValue;
	private String fromSearch;
	private Handler mHandler;
	private String[] date_;
	private String[][] week_;

	public StadiumSelectDateTimeUtils(Context context, String fromSearch, Handler mHandler) {
		super();
		this.context = context;
		this.mHandler = mHandler;
		this.fromSearch = fromSearch;
	}

	/**
	 * 
	 * 方法说明： 选择日期时，调用的方法
	 * 
	 * @param skipstaus
	 */
	public void getDateDialog(String skipstaus, int firstValue_ji, String data, String cid, int HANDLERID) {
		String str = DatesUtils.getInstance().getNowTime("HH");
		int nottime = 0;
		if (!TextUtils.isEmpty(str)) {
			nottime = Integer.parseInt(str);
		}
		int dateLength;
		int cidBookOverTime;
		if (cid.equals(Constans.getInstance().sportCidGolf)) {
			dateLength = 14;// 预定时间为1-14天
			cidBookOverTime = 17;// 高尔夫预定第二天，17:00后预定第三天
		} else {
			dateLength = 7;// 预定时间为1-7天
			cidBookOverTime = 20;// 游泳健身练习场预定当天，20:00后预定第二天
		}

		if (date_ == null || week_ == null) {
			date_ = new String[dateLength];
			week_ = new String[1][dateLength];
			for (int i = 1; i <= date_.length; i++) {
				int y;
				if (cid.equals(Constans.getInstance().sportCidGolf)) {
					if (nottime == 0 || nottime >= cidBookOverTime) {
						y = i + 1;
					} else {
						y = i;
					}
				} else {
					if (nottime == 0 || nottime >= cidBookOverTime) {
						y = i;
					} else {
						y = i - 1;
					}
				}

				String date = DatesUtils.getInstance().getEDate_y(y,
						context.getResources().getString(R.string.unit_mde));
				String week = DatesUtils.getInstance().getEDate_y(y,
						context.getResources().getString(R.string.unit_ymdq));
				date = DatesUtils.getInstance().getZhuanHuan(date, context.getResources().getString(R.string.unit_week),
						context.getResources().getString(R.string.unit_xingqi));
				date_[i - 1] = date;
				week_[0][i - 1] = week;
				Log.e("date_[" + (i - 1) + "]=====", date + "");
				Log.e("week[0][" + (i - 1) + "]=====", week + "");
			}
		}
		if (fromSearch.equals(Constans.getInstance().selectDTByDetailPage) && !TextUtils.isEmpty(data)
				&& firstValue_ji == 0) {
			// 计算由球场进入场馆详情时详情的初次选中项坐标
			firstValue_ji = DatesUtils.getInstance().timeDiff(week_[0][0], data,
					context.getResources().getString(R.string.unit_ymdq)) / (24 * 60 * 60);
			// 防止索引小于0
			if (firstValue_ji < 0) {
				firstValue_ji = 0;
			}
		}
		getDateOrTeetimeDialog(date_, week_, skipstaus, firstValue_ji, 0, cid, "", HANDLERID);
	}

	/**
	 * teetime选择框 final String hours[] = new String[] { "08", "10", "11" };
	 * final String mins[][] = new String[][] { new String[] { "00", "30"}, new
	 * String[] { "00", "30"}, new String[] { "00", "30"}};
	 * 
	 * @param hours_
	 * @param mins_
	 * @param skipstaus
	 * @param firstValue_ji
	 *            记录选中状态
	 * @param secondValue_ji
	 */
	MyDialog selectDialog;

	public void getDateOrTeetimeDialog(String[] hours_, String[][] mins_, final String selectstaus, int firstValue_ji,
			int secondValue_ji, String cid, final String priceRuleType, final int HANDLERID) {
		// 防止索引小于0
		if (firstValue_ji < 0) {
			firstValue_ji = 0;
		}
		if (secondValue_ji < 0) {
			secondValue_ji = 0;
		}
		selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_teetime);// 创建Dialog并设置样式主题
		Window dialogWindow = selectDialog.getWindow();
		// WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		dialogWindow.setWindowAnimations(R.style.showAnDialog);
		selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		final LinearLayout teetime_lay = (LinearLayout) selectDialog.findViewById(R.id.teetime_lay);
		teetime_lay.setLayoutParams(new FrameLayout.LayoutParams(
				ConfigUtils.getInstance().getPhoneWidHeigth(context).widthPixels, LayoutParams.WRAP_CONTENT));
		TextView teetime_title = (TextView) selectDialog.findViewById(R.id.teetime_title);
		TextView teetime_none = (TextView) selectDialog.findViewById(R.id.teetime_none);// 无teetime
		TextView teetime_sure = (TextView) selectDialog.findViewById(R.id.teetime_sure);// 确定
		WheelView teetime_hour = (WheelView) selectDialog.findViewById(R.id.teetime_hour);
		final WheelView teetime_mins = (WheelView) selectDialog.findViewById(R.id.teetime_mins);
		WheelView teetime_one = (WheelView) selectDialog.findViewById(R.id.teetime_one);
		WheelView teetime_four = (WheelView) selectDialog.findViewById(R.id.teetime_four);

		final String hours[] = hours_;
		teetime_hour.setVisibleItems(5);
		final String mins[][] = mins_;
		firstValue = firstValue_ji;
		teetime_hour.setAdapter(new ArrayWheelAdapter<String>(hours, hours.length));
		teetime_hour.setCurrentItem(firstValue_ji);

		if (selectstaus.equals(Constans.getInstance().selectDTByDate)) {
			if (cid.equals(Constans.getInstance().sportCidGolf)
					|| cid.equals(Constans.getInstance().sportCidGolfrange)) {
				teetime_title.setText(context.getResources().getString(R.string.stadium_detail_playdate));
			} else if (cid.equals(Constans.getInstance().sportCidFitness)
					|| cid.equals(Constans.getInstance().sportCidSwim)) {
				teetime_title.setText(context.getResources().getString(R.string.stadium_detail_spdate));
			}
			teetime_one.setVisibility(View.GONE);
			teetime_four.setVisibility(View.GONE);
			teetime_mins.setVisibility(View.GONE);
		} else if (selectstaus.equals(Constans.getInstance().selectDTByTeetime)) {
			secondValue = secondValue_ji;
			if (cid.equals(Constans.getInstance().sportCidGolf)) {
				teetime_title.setText(context.getResources().getString(R.string.stadium_detail_teetime));
			} else if (cid.equals(Constans.getInstance().sportCidGolfrange)) {
				teetime_title.setText(context.getResources().getString(R.string.stadium_detail_playteetime));
			}
			teetime_mins.setVisibleItems(5);
			teetime_mins.setAdapter(new ArrayWheelAdapter<String>(mins[firstValue_ji]));
			teetime_mins.setCurrentItem(secondValue_ji);
			teetime_mins.addChangingListener(new OnWheelChangedListener() {
				public void onChanged(WheelView wheel, int oldValue, int newValue) {
					secondValue = newValue;
				}
			});
		}
		teetime_hour.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (selectstaus.equals(Constans.getInstance().selectDTByTeetime)) {
					teetime_mins.setAdapter(new ArrayWheelAdapter<String>(mins[newValue]));
					if (priceRuleType.equals("noBanTimeRule") && newValue == hours.length - 1) {
						teetime_mins.setCurrentItem(0);
					}
				}
				firstValue = newValue;
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
				Message msg = new Message();
				if (selectstaus.equals(Constans.getInstance().selectDTByDate)) {
					String data = mins[0][firstValue];
					if (fromSearch.equals(Constans.getInstance().selectDTBySearchPage)) {
						data = hours[firstValue];
						msg.getData().putString("data", mins[0][firstValue]);
					}
					msg.obj = data;
				} else if (selectstaus.equals(Constans.getInstance().selectDTByTeetime)) {
					String time_ = hours[firstValue] + ":" + mins[firstValue][secondValue];
					msg.obj = time_;
				}
				msg.arg1 = firstValue;
				msg.arg2 = secondValue;
				msg.what = HANDLERID;
				mHandler.handleMessage(msg);
				msg = null;
			}
		});
	}

}
