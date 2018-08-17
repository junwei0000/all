package com.bestdo.bestdoStadiums.utils;

import com.bestdo.bestdoStadiums.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * 订单详情自定义倒计时文本控件
 * 
 * @author Administrator
 * 
 */
public class TimeTextView extends TextView implements Runnable {

	private Paint mPaint; // 画笔,包含了画几何图形、文本等的样式和颜色信息

	private long[] times;
	private long mhour, mmin, msecond;// 天，小时，分钟，秒
	private String mminmiao = "";
	private String mminfen = "";
	private boolean run = false; // 是否启动了
	private String strTimezuo;
	private String strTimeyou;
	private Handler mHandler;
	private int handlerid;
	private String comeFromFaliueString;

	public TimeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeTextView);
		array.recycle(); // 一定要调用，否则这次的设定会对下次的使用造成影响
	}

	public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mPaint = new Paint();
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeTextView);

		array.recycle(); // 一定要调用，否则这次的设定会对下次的使用造成影响
	}

	public TimeTextView(Context context) {
		super(context);
	}

	public String getComeFromFaliueString() {
		return comeFromFaliueString;
	}

	public void setComeFromFaliueString(String comeFromFaliueString) {
		this.comeFromFaliueString = comeFromFaliueString;
	}

	public long[] getTimes() {
		return times;
	}

	public void setTimes(long[] times, String strTimezuo, String strTimeyou, int handlerid, Handler mHandler) {
		this.times = times;
		this.strTimezuo = strTimezuo;
		this.strTimeyou = strTimeyou;
		this.handlerid = handlerid;
		this.mHandler = mHandler;
		mhour = times[0];
		mmin = times[1];
		msecond = times[2];

	}

	/**
	 * 倒计时计算
	 */
	private void ComputeTime() {

		if (msecond <= 0) {
			msecond = 59;
			if (mmin <= 0) {
				mmin = 59;
				if (mhour > 0) {
					mhour--;
				}
			} else {
				mmin--;
			}
		} else {
			msecond--;
		}

	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	/**
	 * 支付失败倒计时结束，更新订单状态值
	 */
	private void setOffView() {
		this.setText("");
		String strTime;
		if (TextUtils.isEmpty(comeFromFaliueString)) {
			strTime = strTimezuo + "<font color='#FC821B'>00:00:00</font>" + strTimeyou;
		} else {
			strTime = strTimezuo + "<font color='#A8A8A8'>00:00:00</font>" + strTimeyou;
		}
		this.setText(Html.fromHtml(strTime));
		mHandler.sendEmptyMessage(handlerid);
	}

	public void removeCallbacks() {
		removeCallbacks(this);
	}

	@Override
	public void run() {
		// 标示已经启动
		run = true;
		if (mhour <= 0 && mmin <= 0 && msecond <= 1) {
			setOffView();
			removeCallbacks();
		} else {
			ComputeTime();
			if (mhour >= 0 && mmin >= 0 && msecond > 0) {
				if (mmin < 10) {
					mminfen = "0";
				} else {
					mminfen = "";
				}
				if (msecond < 10) {
					mminmiao = "0";
				} else {
					mminmiao = "";
				}
				Log.e("订单详情", "mmin==" + mmin + ";;msecond==" + msecond);
				String strTime;
				if (TextUtils.isEmpty(comeFromFaliueString)) {
					strTime = strTimezuo + "<font color='#FC821B'>0" + mhour + ":" + mminfen + mmin + ":" + mminmiao
							+ msecond + "</font>" + strTimeyou;
				} else {
					strTime = strTimezuo + "<font color='#A8A8A8'>0" + mhour + ":" + mminfen + mmin + ":" + mminmiao
							+ msecond + "</font>" + strTimeyou;
				}
				this.setText(Html.fromHtml(strTime));
				postDelayed(this, 1000);
			} else {
				setOffView();
				removeCallbacks();
			}
		}

	}

}
