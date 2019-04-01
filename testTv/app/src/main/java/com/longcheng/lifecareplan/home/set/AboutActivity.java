package com.longcheng.lifecareplan.home.set;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DatesUtils;

import butterknife.BindView;

/**
 * 关于
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.pageTop_tv_time)
    TextView pageTopTvTime;
    @BindView(R.id.pageTop_tv_date)
    TextView pageTopTvDate;
    @BindView(R.id.pageTop_tv_week)
    TextView pageTopTvWeek;


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.set_about;
    }

    @Override
    public void initView(View view) {
        initTimer();
    }

    @Override
    public void setListener() {
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setDateInfo() {
        String date = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
        pageTopTvDate.setText(date);
        String week = DatesUtils.getInstance().getNowTime("EE");
        pageTopTvWeek.setText(week);
        String time = DatesUtils.getInstance().getNowTime("HH:mm");
        pageTopTvTime.setText(time);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_relay_update:

                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
