package com.longcheng.volunteer.modular.mine.bill.widget;

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

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.utils.DatesUtils;
import com.longcheng.volunteer.utils.myview.MyDialog;
import com.longcheng.volunteer.utils.teetime.ArrayWheelAdapter;
import com.longcheng.volunteer.utils.teetime.OnWheelChangedListener;
import com.longcheng.volunteer.utils.teetime.WheelView;

import java.util.Calendar;
import java.util.Date;

/**
 * 作者：jun on
 * 时间：2018/8/22 17:55
 * 意图：
 */

public class TimeSelectUtils {
    MyDialog selectShengxDialog;
    Activity mContext;
    Handler mHandler;
    int mHandlerID;

    private int yearindex;// 记录当前选中
    private int monthindex;
    String[] yearvalues;
    String[] monthvalues;

    public TimeSelectUtils(Activity mContext, Handler mHandler, int mHandlerID) {
        this.mContext = mContext;
        this.mHandler = mHandler;
        this.mHandlerID = mHandlerID;
        setDate();
    }

    private void setDate() {
        int startyear = 2015;
        int nowyear = Integer.parseInt(DatesUtils.getInstance().getNowTime("yyyy"));
        int yearNum = nowyear - startyear + 1;
        yearvalues = new String[yearNum];
        yearindex = yearNum - 1;
        for (int i = 0; i < yearNum; i++) {
            yearvalues[i] = String.valueOf(i + startyear);
        }
        getMonth();
    }

    private void getMonth() {
        int monthNum = 12;
        String nowyear = DatesUtils.getInstance().getNowTime("yyyy");
        if (!nowyear.equals(yearvalues[yearindex])) {
            monthvalues = new String[monthNum];
            for (int j = 0; j < monthNum; j++) {
                if (j < 9) {
                    monthvalues[j] = String.valueOf("0" + (j + 1));
                } else {
                    monthvalues[j] = String.valueOf(j + 1);
                }

            }
        } else {
            monthNum = Integer.parseInt(DatesUtils.getInstance().getNowTime("M"));
            monthvalues = new String[monthNum];
            for (int j = 0; j < monthNum; j++) {
                if (j < 9) {
                    monthvalues[j] = String.valueOf("0" + (j + 1));
                } else {
                    monthvalues[j] = String.valueOf(j + 1);
                }

            }
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
            mWheelViewDay.setVisibility(View.GONE);
            tv_title.setText("");
            mWheelView.setAdapter(new ArrayWheelAdapter<String>(yearvalues, yearvalues.length));
            mWheelView.setCurrentItem(yearindex);
            mWheelView.setVisibleItems(5);
            mWheelView.setCyclic(false);
            mWheelViewMonth.setAdapter(new ArrayWheelAdapter<String>(monthvalues, monthvalues.length));
            mWheelViewMonth.setCurrentItem(monthindex);
            mWheelViewMonth.setVisibleItems(5);
            mWheelViewMonth.setCyclic(false);
            mWheelView.addChangingListener(new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    yearindex = newValue;
                    getMonth();
                    mWheelViewMonth.setAdapter(new ArrayWheelAdapter<String>(monthvalues, monthvalues.length));
                    mWheelViewMonth.setCurrentItem(0);
                }
            });
            mWheelViewMonth.addChangingListener(new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    monthindex = newValue;
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
