package com.longcheng.volunteer.modular.mine.bill.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.modular.mine.bill.bean.BillItemBean;

/**
 * Created by Burning on 2018/8/31.
 */

public class BillHeaderView extends LinearLayout {

    TextView tvDate;
    TextView tvsubtitle;
    private String date;
    public LinearLayout layout_month;
    public TextView tv_select;
    private ImageView iv_arrow;

    public BillHeaderView(Context context) {
        super(context);
        init();
    }

    public BillHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BillHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_bill_header, this);
        tvDate = findViewById(R.id.tvDate);
        tvsubtitle = findViewById(R.id.tvsubtitle);
        layout_month = findViewById(R.id.layout_month);
        tv_select = findViewById(R.id.tv_select);
        iv_arrow = findViewById(R.id.iv_arrow);
    }

    /**
     * 主页面设置点击事件和显示
     */
    public void setMainViewShow() {
        tv_select.setVisibility(VISIBLE);
        iv_arrow.setVisibility(VISIBLE);
    }

    /**
     * 没数据时更新头部布局
     *
     * @param selectmonth
     */
    public void updateSelectNotData(String selectmonth) {
        tvDate.setText(selectmonth);
        tvsubtitle.setText("本月没有账单记录");
    }

    public void refreshMainView(BillItemBean bean) {
        if (bean != null) {
            String time = bean.getTitle();
            Log.e("aab", "date@@@" + date);
            Log.e("aab", "bean.getTitle()@@@" + time);
//            if (!TextUtils.isEmpty(time) && !time.equals(date)) {
            date = time;
            tvDate.setText(time);
            tvsubtitle.setText(Html.fromHtml(bean.getSubtitle()));
//            }
            if (getVisibility() != View.VISIBLE) {
                this.setVisibility(VISIBLE);
            }
        } else {
            Log.e("aab", "@@@@@@@@@@@@@@@@@@@@@");
//            this.setVisibility(GONE);
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
