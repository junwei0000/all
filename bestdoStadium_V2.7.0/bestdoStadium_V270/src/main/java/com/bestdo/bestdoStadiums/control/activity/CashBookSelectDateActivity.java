/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.io.File;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.R.id;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.TimeUtils;

import android.R.integer;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-12-28 下午3:48:09
 * @Description 类描述：记事本--选时间
 */
public class CashBookSelectDateActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private TextView selectdate_tv_starttime;
	private TextView selectdate_tv_endtime;
	private TextView selectdate_tv_onemonth;
	private TextView selectdate_tv_threemonth;
	private TextView selectdate_tv_sixmonth;
	private TextView selectdate_tv_reset;
	private TextView selectdate_tv_sure;
	private String starttime;
	private String endtime;
	private TimeUtils mTimeUtils;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;
		case R.id.selectdate_tv_onemonth:
			setDate(-1, true);
			break;
		case R.id.selectdate_tv_threemonth:
			setDate(-3, true);
			break;
		case R.id.selectdate_tv_sixmonth:
			setDate(-6, true);
			break;
		case R.id.selectdate_tv_reset:
			setDate(-3, true);
			break;
		case R.id.selectdate_tv_sure:
			Intent intent = new Intent();
			intent.putExtra("starttime", starttime);
			intent.putExtra("endtime", endtime);
			setResult(Constans.getInstance().ForResult3, intent);
			doback();
			// String sy=DatesUtils.getInstance().getDateGeShi(starttime,
			// "yyyy-MM-dd", "yyyy");
			// String ey=DatesUtils.getInstance().getDateGeShi(endtime,
			// "yyyy-MM-dd", "yyyy");
			// if(DatesUtils.getInstance().doDateEqual(sy, ey, "yyyy")){
			// }else{
			// CommonUtils.getInstance().initToast(context, "开始时间和结束时间不能跨年");
			// }
			break;
		case R.id.selectdate_tv_starttime:
			mTimeUtils.setInit(mHandler, STARTTIME);
			setIndex(starttime);
			break;
		case R.id.selectdate_tv_endtime:
			mTimeUtils.setInit(mHandler, ENDTIME);
			setIndex(endtime);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.cashbook_selectdate);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);

		selectdate_tv_starttime = (TextView) findViewById(R.id.selectdate_tv_starttime);
		selectdate_tv_endtime = (TextView) findViewById(R.id.selectdate_tv_endtime);
		selectdate_tv_onemonth = (TextView) findViewById(R.id.selectdate_tv_onemonth);
		selectdate_tv_threemonth = (TextView) findViewById(R.id.selectdate_tv_threemonth);
		selectdate_tv_sixmonth = (TextView) findViewById(R.id.selectdate_tv_sixmonth);

		selectdate_tv_reset = (TextView) findViewById(R.id.selectdate_tv_reset);
		selectdate_tv_sure = (TextView) findViewById(R.id.selectdate_tv_sure);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_name.setOnClickListener(this);

		selectdate_tv_starttime.setOnClickListener(this);
		selectdate_tv_endtime.setOnClickListener(this);
		selectdate_tv_onemonth.setOnClickListener(this);
		selectdate_tv_threemonth.setOnClickListener(this);
		selectdate_tv_sixmonth.setOnClickListener(this);
		selectdate_tv_reset.setOnClickListener(this);
		selectdate_tv_sure.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		Intent intent = getIntent();
		starttime = intent.getStringExtra("starttime");
		endtime = intent.getStringExtra("endtime");
		mTimeUtils = new TimeUtils(this);
		mTimeUtils.initFromToNowDates(2016);
		pagetop_tv_name.setText("统计数据时间段筛选");
		if (!TextUtils.isEmpty(starttime) && !TextUtils.isEmpty(endtime)) {
			setDate(0, true);
		} else {
			setDate(-3, true);
		}
	}

	private void setDate(int beforedate, boolean getReDateStatus) {
		if (getReDateStatus && (beforedate == -1 || beforedate == -3 || beforedate == -6)) {
			endtime = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
			starttime = DatesUtils.getInstance().getBeforeDate(beforedate, "yyyy-MM-dd");
		}
		selectdate_tv_starttime.setText(starttime);
		selectdate_tv_endtime.setText(endtime);

		selectdate_tv_onemonth.setBackgroundResource(R.drawable.corners_bg_gray);
		selectdate_tv_threemonth.setBackgroundResource(R.drawable.corners_bg_gray);
		selectdate_tv_sixmonth.setBackgroundResource(R.drawable.corners_bg_gray);
		selectdate_tv_onemonth.setTextColor(getResources().getColor(R.color.text_click_color));
		selectdate_tv_threemonth.setTextColor(getResources().getColor(R.color.text_click_color));
		selectdate_tv_sixmonth.setTextColor(getResources().getColor(R.color.text_click_color));
		if (beforedate == -1) {
			selectdate_tv_onemonth.setBackgroundResource(R.drawable.corners_bg_blue);
			selectdate_tv_onemonth.setTextColor(getResources().getColor(R.color.white));
		} else if (beforedate == -3) {
			selectdate_tv_threemonth.setBackgroundResource(R.drawable.corners_bg_blue);
			selectdate_tv_threemonth.setTextColor(getResources().getColor(R.color.white));
		} else if (beforedate == -6) {
			selectdate_tv_sixmonth.setBackgroundResource(R.drawable.corners_bg_blue);
			selectdate_tv_sixmonth.setTextColor(getResources().getColor(R.color.white));
		}
	}

	private void setIndex(String time) {
		String sy = DatesUtils.getInstance().getDateGeShi(time, "yyyy-MM-dd", "yyyy");
		String sm = DatesUtils.getInstance().getDateGeShi(time, "yyyy-MM-dd", "MM");
		String sd = DatesUtils.getInstance().getDateGeShi(time, "yyyy-MM-dd", "dd");
		int yearIndex_select = Integer.parseInt(sy) - 2016;
		int monthsIndex_select = Integer.parseInt(sm) - 1;
		int dayIndex_select = Integer.parseInt(sd) - 1;
		mTimeUtils.getYMDDialog(yearIndex_select, monthsIndex_select, dayIndex_select);
	}

	protected static final int STARTTIME = 0;
	protected static final int ENDTIME = 1;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String selectday;
			switch (msg.what) {
			case STARTTIME:
				selectday = msg.getData().getString("selectday");
				selectday = DatesUtils.getInstance().getDateGeShi(selectday, "yyyy年MM月dd日", "yyyy-MM-dd");
				System.err.println("starttime=====" + starttime);
				starttime = selectday;
				selectdate_tv_starttime.setText(starttime);
				doNatiDate();
				break;
			case ENDTIME:
				selectday = msg.getData().getString("selectday");
				selectday = DatesUtils.getInstance().getDateGeShi(selectday, "yyyy年MM月dd日", "yyyy-MM-dd");
				System.err.println("endtime=====" + endtime);
				if (DatesUtils.getInstance().doCheck2Date(starttime, selectday, "yyyy-MM-dd")) {
					endtime = selectday;
					selectdate_tv_endtime.setText(endtime);
					doNatiDate();
				} else {
					CommonUtils.getInstance().initToast(context, "结束时间不能小于开始时间");
				}
				break;
			}
		}
	};

	private void doNatiDate() {
		int nati = 0;
		String nowdate = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
		if (DatesUtils.getInstance().doDateEqual(endtime, nowdate, "yyyy-MM-dd")) {
			String starttime1 = DatesUtils.getInstance().getBeforeDate(-1, "yyyy-MM-dd");
			String starttime3 = DatesUtils.getInstance().getBeforeDate(-3, "yyyy-MM-dd");
			String starttime6 = DatesUtils.getInstance().getBeforeDate(-6, "yyyy-MM-dd");
			if (DatesUtils.getInstance().doCheckDate(starttime, starttime1, nowdate)) {
				nati = -1;
			} else if (DatesUtils.getInstance().doCheckDate(starttime, starttime3, nowdate)) {
				nati = -3;
			} else if (DatesUtils.getInstance().doCheckDate(starttime, starttime6, nowdate)) {
				nati = -6;
			}
		}
		setDate(nati, false);
	}

	private void doback() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doback();
		}
		return false;
	}
}
