package com.longcheng.volunteer.modular.mine.userinfo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivity;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.DatesUtils;
import com.nanchen.calendarview.ClickDataListener;
import com.nanchen.calendarview.MyCalendarView;

import java.util.Locale;

import butterknife.BindView;

public class CalendarActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.calendarView)
    MyCalendarView calendarView;
    private CalenderSelectUtils mCalenderSelectUtils;
    private String showDate;

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pageTop_tv_name:
                if (mCalenderSelectUtils == null) {
                    mCalenderSelectUtils = new CalenderSelectUtils(mActivity, mHandler, SELECTDATE);
                }
                mCalenderSelectUtils.initCurrentItem(showDate);
                mCalenderSelectUtils.showDialog();
//                DatePickUtil.showDatePickerDialog(mActivity, pageTopTvName, Calendar.getInstance(), new OnDatePickedListener() {
//                    @Override
//                    public void pick(String pickedDate) {
//                        calendarView.setSelectDate(pickedDate);
//                    }
//                });
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.dialog_calendar;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        pageTopTvName.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        calendarView.init(mHandler, update);
        Intent intent = getIntent();
        showDate = intent.getStringExtra("showDate");
        Log.e(TAG, "showDate=" + showDate);
        if (!TextUtils.isEmpty(showDate) && !showDate.equals("0000-00-00")) {
            showDate = DatesUtils.getInstance().getDateGeShi(showDate, "yyyy-MM-dd", "yyyy-M-dd");
            calendarView.setSelectDate(showDate);
        } else {
            showDate = DatesUtils.getInstance().getNowTime("yyyy-M-dd");
        }
        calendarView.setClickDataListener(new ClickDataListener() {
            @Override
            public void clickData(int year, int month, int day) {
                showDate = String.format(Locale.CHINA, "%04d-%02d-%02d", year, month, day);
                Log.e(TAG, showDate + "\n");
                Intent intent = new Intent();
                intent.putExtra("birthday", showDate);
                setResult(ConstantManager.USERINFO_FORRESULT_DATE, intent);
                doFinish();
            }
        });
    }

    private final int update = 1;
    private final int SELECTDATE = 2;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case update:
                    String date = (String) msg.obj;
                    pageTopTvName.setText(date);
                    break;
                case SELECTDATE:
                    String year = msg.getData().getString("year");
                    String month = msg.getData().getString("month");
                    String day = msg.getData().getString("day");
                    String selectDate = year + month + day;
                    showDate = DatesUtils.getInstance().getDateGeShi(selectDate, "yyyy年MM月dd日", "yyyy-M-dd");
                    calendarView.setSelectDate(showDate);
                    Log.e(TAG, "  " + showDate);
                    break;
            }
        }
    };

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }
}
