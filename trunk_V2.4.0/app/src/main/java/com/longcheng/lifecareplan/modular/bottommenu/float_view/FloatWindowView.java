package com.longcheng.lifecareplan.modular.bottommenu.float_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;


/**
 * FloatWindowView:悬浮窗控件V1-利用windowManger控制窗口
 *
 * @author Nonolive-杜乾 Created on 2017/12/12 - 17:16.
 * E-mail:dusan.du@nonolive.com
 */

public class FloatWindowView extends FrameLayout implements IFloatView {
    private TextView tv_cont;
    private ImageView iv_thumb;

    private FloatViewParams params = null;
    private FloatViewListener listener;

    public FloatWindowView(Context context) {
        super(context);
        init();
    }

    public FloatWindowView(Context mContext, FloatViewParams floatViewParams, WindowManager.LayoutParams wmParams) {
        super(mContext);
        this.params = floatViewParams;
        init();
    }

    private void init() {
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View floatView = inflater.inflate(R.layout.float_view_inner_layout, null);
        tv_cont = (TextView) floatView.findViewById(R.id.tv_cont);
        iv_thumb = (ImageView) floatView.findViewById(R.id.iv_thumb);
        tv_cont.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick();
                }
            }
        });
        addView(floatView);
    }

    @Override
    public FloatViewParams getParams() {
        return params;
    }

    @Override
    public void setFloatViewListener(FloatViewListener listener) {
        this.listener = listener;
    }
}
