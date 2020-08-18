package com.longcheng.lifecareplan.widget.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.DensityUtil;

/**
 * Created by Burning on 2018/9/21.
 */

public class FooterNoMoreData extends LinearLayout {

    TextView tvDividerTop;
    TextView tvContent;

    public FooterNoMoreData(Context context) {
        super(context);
        initView();
    }

    public FooterNoMoreData(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FooterNoMoreData(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.footer_nomoredata, this);
        tvDividerTop = findViewById(R.id.tvfooterdivider);
        tvContent = findViewById(R.id.tvfootercontent);
    }

    public void showDividerTop(boolean show) {
        tvDividerTop.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showContJiJu(boolean show) {
        if (show) {
            LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mLayoutParams.leftMargin = DensityUtil.dip2px(getContext(), 10);
            mLayoutParams.rightMargin = DensityUtil.dip2px(getContext(), 10);
            tvContent.setLayoutParams(mLayoutParams);
            tvContent.setBackgroundColor(getContext().getResources().getColor(R.color.transparent));
        }
    }


}
