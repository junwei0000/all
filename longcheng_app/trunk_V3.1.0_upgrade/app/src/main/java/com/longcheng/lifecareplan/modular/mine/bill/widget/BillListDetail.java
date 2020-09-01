package com.longcheng.lifecareplan.modular.mine.bill.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.mine.bill.bean.BillItemBean;

/**
 * Created by Burning on 2018/8/31.
 */

public class BillListDetail extends RelativeLayout {
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvCost, tvcoststatus;

    public BillListDetail(Context context) {
        super(context);
        init();
    }

    public BillListDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BillListDetail(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_bill_list_cell, this);
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvbilldate);
        tvCost = findViewById(R.id.tvcost);
        tvcoststatus = findViewById(R.id.tvcoststatus);
    }

    public void refresh(BillItemBean bean) {
        if (bean != null) {
            tvTitle.setText(bean.getTitle());
            tvDate.setText(bean.getTime());
            tvCost.setText(bean.getMoney());
            tvCost.setTextColor(getResources().getColor(bean.isIncome() ? R.color.color_ff2c2c : R.color.text_biaoTi_color));
            String subtitle = bean.getSubtitle();
            tvcoststatus.setText(subtitle);
            if (!TextUtils.isEmpty(subtitle)) {
                tvcoststatus.setVisibility(View.VISIBLE);
                if (subtitle.contains("成功")) {
                    tvcoststatus.setTextColor(getResources().getColor(R.color.green));
                } else if (subtitle.contains("驳回")) {
                    tvcoststatus.setTextColor(getResources().getColor(R.color.color_ff2c2c));
                } else {
                    tvcoststatus.setTextColor(getResources().getColor(R.color.text_noclick_color));
                }
            } else {
                tvcoststatus.setVisibility(View.GONE);
            }
            this.setVisibility(View.VISIBLE);
        } else {
            this.setVisibility(GONE);
        }
    }
}
