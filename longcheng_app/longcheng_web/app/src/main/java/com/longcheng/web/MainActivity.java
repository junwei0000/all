package com.longcheng.web;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.longcheng.web.adapter.TabPageAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseTabActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    TabHost mTabHost;
    @BindView(R.id.rb_bottom_home)
    RadioButton mButtonHome;

    @BindView(R.id.rb_bottom_helpWith)
    RadioButton mButtonHelpWith;

    @BindView(R.id.rg_bottom_main)
    RadioGroup mRadioGroup;

    @BindView(R.id.iv_function)
    Button iv_function;
    @BindView(R.id.layout_function)
    LinearLayout layout_function;

    @BindView(R.id.iv_refresh)
    Button iv_refresh;

    @BindView(R.id.iv_show)
    Button iv_show;

    @BindView(R.id.group)
    LinearLayout group;

    @BindView(R.id.mFrameLayout)
    FrameLayout mFrameLayout;
    /**
     * 是否显示全屏
     */
    boolean showAllStatus = false;
    public static final String TAB_MAIN = "MAIN_ACTIVITY";
    public static final String TAB_SPORTQURAT = "SPORTQURAT_ACTIVITY";

    int index = 0;
    private long startTime = 0;
    private long endTime = 0;

    private boolean isclick;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.iv_show:
                if (showAllStatus) {
                    showAllStatus = false;
                } else {
                    showAllStatus = true;
                }
                setShow();
                mHandler.sendEmptyMessage(FUNCTOIN);
                break;
            case R.id.iv_refresh:
                setRefresh();
                mHandler.sendEmptyMessage(FUNCTOIN);
                break;
            case R.id.iv_function:
                layout_function.setVisibility(View.VISIBLE);
                group.setVisibility(View.INVISIBLE);
                mHandler.sendEmptyMessageDelayed(FUNCTOIN, 3 * 1000);
                break;
            default:
                break;
        }
    }

    private void setShow() {
        if (showAllStatus) {
            mRadioGroup.setVisibility(View.GONE);
            iv_show.setBackgroundResource(R.mipmap.map_shrink);
        } else {
            iv_show.setBackgroundResource(R.mipmap.map_all);
            mRadioGroup.setVisibility(View.VISIBLE);
        }
    }

    private void setRefresh() {
        if (index == 0) {
            // 发布事件
            new Thread("posting") {
                @Override
                public void run() {
                    EventBus.getDefault().post(new MessageEvent("refreshPingTai"));
                }
            }.start();
        } else {
            // 发布事件
            new Thread("posting") {
                @Override
                public void run() {
                    EventBus.getDefault().post(new MessageEvent("refreshShangHu"));
                }
            }.start();
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(mToolbar, null);
    }

    @Override
    public void initDataAfter() {
        initView();
    }

    @Override
    public void setListener() {
        iv_show.setOnClickListener(this);
        iv_refresh.setOnClickListener(this);
        iv_function.setOnClickListener(this);
        iv_function.setAlpha(0.5f);
    }

    private static final int FUNCTOIN = 1;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FUNCTOIN:
                    layout_function.setVisibility(View.GONE);
                    group.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    private void initView() {
        mTabHost = getTabHost();
        Intent i_main = new Intent(this, MyDeH5Activity.class);
        Intent i_sportqurat = new Intent(this, MyDe2H5Activity.class);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN).setContent(i_main));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_SPORTQURAT).setIndicator(TAB_SPORTQURAT).setContent(i_sportqurat));

        mTabHost.setCurrentTabByTag(TAB_MAIN);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_bottom_home:
                        index = 0;
                        mTabHost.setCurrentTabByTag(TAB_MAIN);
                        break;
                    case R.id.rb_bottom_helpWith:
                        index = 1;
                        mTabHost.setCurrentTabByTag(TAB_SPORTQURAT);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }
}
