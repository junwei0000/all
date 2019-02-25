package com.longcheng.lifecareplan.modular.mine.userinfo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.teetime.ArrayWheelAdapter;
import com.longcheng.lifecareplan.utils.teetime.OnWheelChangedListener;
import com.longcheng.lifecareplan.utils.teetime.WheelView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者：jun on
 * 时间：2018/8/22 17:55
 * 意图：
 */

public class CalenderSelectUtils {
    MyDialog selectShengxDialog;
    Activity mContext;
    Handler mHandler;
    int mHandlerID;

    private int yearindex;// 记录当前选中
    private int monthindex;
    private int dayindex;
    String[] yearvalues;
    String[] monthvalues;
    String[] dayvalues;

    public CalenderSelectUtils(Activity mContext, Handler mHandler, int mHandlerID) {
        this.mContext = mContext;
        this.mHandler = mHandler;
        this.mHandlerID = mHandlerID;
        setDate();
    }

    private void setDate() {
        int yearNum = 149;
        yearvalues = new String[yearNum];
        int monthNum = 12;
        monthvalues = new String[monthNum];

        for (int i = 0; i < yearNum; i++) {
            yearvalues[i] = String.valueOf(i + 1901) + "年";
        }
        for (int j = 0; j < monthNum; j++) {
            monthvalues[j] = String.valueOf(j + 1) + "月";
        }
        getDay();
    }

    private void getDay() {
        String currentdate = yearvalues[yearindex] + monthvalues[monthindex];
        String year = DatesUtils.getInstance().getDateGeShi(currentdate, "yyyy年MM月", "yyyy");
        String month = DatesUtils.getInstance().getDateGeShi(currentdate, "yyyy年MM月", "M");
        Date lastDate = getSupportEndDayofMonth(Integer.valueOf(year), Integer.valueOf(month));
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        int lastday = Integer.parseInt(sdf.format(lastDate));
        dayvalues = new String[lastday];
        for (int j = 0; j < lastday; j++) {
            dayvalues[j] = String.valueOf(j + 1) + "日";
        }
    }

    public void initCurrentItem(String selectdate_) {
        int year_c = Integer.parseInt(selectdate_.split("-")[0]);
        int month_c = Integer.parseInt(selectdate_.split("-")[1]);
        int day_c = Integer.parseInt(selectdate_.split("-")[2]);

        if (year_c - 1901 < yearvalues.length) {
            yearindex = year_c - 1901;
        }

        if (month_c - 1 < monthvalues.length) {
            monthindex = month_c - 1;
        }
        getDay();
        if (day_c - 1 < dayvalues.length) {
            dayindex = day_c - 1;
        }
    }

    public void showDialog() {
        if (selectShengxDialog == null) {
            selectShengxDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_calender_time);// 创建Dialog并设置样式主题
            selectShengxDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectShengxDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectShengxDialog.show();
            WindowManager m = mContext.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectShengxDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectShengxDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_title = (TextView) selectShengxDialog.findViewById(R.id.tv_title);
            TextView tv_cancel = (TextView) selectShengxDialog.findViewById(R.id.tv_cancel);
            TextView tv_sure = (TextView) selectShengxDialog.findViewById(R.id.tv_sure);
            WheelView mWheelView = (WheelView) selectShengxDialog.findViewById(R.id.mWheelView);
            WheelView mWheelViewMonth = (WheelView) selectShengxDialog.findViewById(R.id.mWheelViewMonth);
            WheelView mWheelViewDay = (WheelView) selectShengxDialog.findViewById(R.id.mWheelViewDay);
            tv_title.setText("");
            mWheelView.setAdapter(new ArrayWheelAdapter<String>(yearvalues, yearvalues.length));
            mWheelView.setCurrentItem(yearindex);
            mWheelView.setVisibleItems(5);
            mWheelView.setCyclic(true);
            mWheelViewMonth.setAdapter(new ArrayWheelAdapter<String>(monthvalues, yearvalues.length));
            mWheelViewMonth.setCurrentItem(monthindex);
            mWheelViewMonth.setVisibleItems(5);
            mWheelViewMonth.setCyclic(true);
            mWheelViewDay.setAdapter(new ArrayWheelAdapter<String>(dayvalues, dayvalues.length));
            mWheelViewDay.setCurrentItem(dayindex);
            mWheelViewDay.setVisibleItems(5);
            mWheelViewDay.setCyclic(true);
            mWheelView.addChangingListener(new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    yearindex = newValue;
                    getDay();
                    mWheelViewDay.setAdapter(new ArrayWheelAdapter<String>(dayvalues, dayvalues.length));
                    mWheelViewDay.setCurrentItem(0);
                }
            });
            mWheelViewMonth.addChangingListener(new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    monthindex = newValue;
                    getDay();
                    mWheelViewDay.setAdapter(new ArrayWheelAdapter<String>(dayvalues, dayvalues.length));
                    mWheelViewDay.setCurrentItem(0);
                }
            });
            mWheelViewDay.addChangingListener(new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    dayindex = newValue;
                }
            });


            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectShengxDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectShengxDialog.dismiss();
                    Message message = new Message();
                    message.what = mHandlerID;
                    Bundle bundle = new Bundle();
                    bundle.putString("year", yearvalues[yearindex]);
                    bundle.putString("month", monthvalues[monthindex]);
                    bundle.putString("day", dayvalues[dayindex]);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                    message = null;
                }
            });
        } else {
            selectShengxDialog.show();
        }
    }

    /**
     * 根据提供的年月日获取该月份的第一天
     *
     * @param year
     * @param monthOfYear
     * @return
     * @Description: (这里用一句话描述这个方法的作用)
     * @Author: gyz
     * @Since: 2017-1-9下午2:26:57
     */
    public Date getSupportBeginDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return firstDate;
    }

    /**
     * 根据提供的年月获取该月份的最后一天
     *
     * @param year
     * @param monthOfYear
     * @return
     * @Description: (这里用一句话描述这个方法的作用)
     * @Author: gyz
     * @Since: 2017-1-9下午2:29:38
     */
    public Date getSupportEndDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return lastDate;
    }
}
