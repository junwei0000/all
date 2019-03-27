package com.longcheng.lifecareplan.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.MPermissionUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 作者：MarkShuai on
 * 时间：2017/11/20 16:00
 * 邮箱：mark_mingshuai@163.com
 * 意图：BaseActivityMVP
 */
public abstract class BaseActivityMVP<V, T extends BasePresent<V>> extends RxAppCompatActivity implements View.OnClickListener {
    /**
     * P层引用
     */
    protected T mPresent;

    /**
     * 退出程序的时间间隔
     */
    private long exitTime = 0;


    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 上下文
     **/
    public Context mContext = this;
    public Activity mActivity = this;
    private ActivityManager activityManager;
    private Unbinder bind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BaseActivityMVP-->onCreate()");
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //设置主布局
        View mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        //加载主布局
        setContentView(mContextView);
        // 注册订阅者
//        EventBus.getDefault().register(this);
        //Activity的管理，将Activity压入栈
        activityManager = ActivityManager.getScreenManager();
        activityManager.pushActivity(this);
        bind = ButterKnife.bind(this);
        //创建Presenter层
        mPresent = createPresent();
        //做绑定
        mPresent.attachView((V) this);
        //初始化数据之前获取一些数据的方法
        initDataBefore();
        //调用初始化控件的方法
        initView(mContextView);
        setListener();
        initDataAfter();
        mPresent.fetch();
        //设置监听
    }

    /**
     * [绑定视图]
     *
     * @return
     */
    public abstract View bindView();


    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();


    /**
     * [初始化控件之前初始化数据]
     */
    public void initDataBefore() {
    }

    /**
     * [初始化控件]
     *
     * @param view
     */
    public abstract void initView(View view);

    /**
     * [初始化控件之后初始化数据]
     */
    public abstract void initDataAfter();

    /**
     * [设置监听]
     */
    public abstract void setListener();

    /**
     * View点击
     **/
    public abstract void onClick(View view);

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    /**
     * **********************头部时间*****************************
     */
    /**
     * 开始计时
     */
    TimerTask dateTask;

    protected void initTimer() {
        dateTask = new TimerTask() {
            @Override
            public void run() {
                mTimeHandler.sendEmptyMessage(1);
            }
        };
        new Timer().schedule(dateTask, 1000, 30 * 1000);
    }

    @SuppressLint("HandlerLeak")
    Handler mTimeHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    setDateInfo();
                    break;
            }
        }
    };

    public abstract void setDateInfo();

    /**
     * *********************end**************************
     */
    @Override
    protected void onDestroy() {
        mPresent.detach();
        activityManager.popActivity(this);
        bind.unbind();

        if (dateTask != null) {
            dateTask.cancel();
            mTimeHandler.removeCallbacks(dateTask);
        }
        // 注销订阅者
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

    }

//    /**
//     * @param event
//     * @Subscribe注解的方法，必须是公共方法，否则会报错！
//     */
//    @Subscribe(threadMode = ThreadMode.BACKGROUND)
//    public void onMessageEventBackground(MessageEvent event) {
//        Log.i(TAG, "onMessageEventBackground(), current thread is " + Thread.currentThread().getName());
//    }

    /*
     * @params
     * @name 子类实现具体的构建过程
     * @data 2017/11/20 15:39
     * @author :MarkShuai
     */
    protected abstract T createPresent();


    /**
     * 描述：退出程序
     */
    protected void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            activityManager.popAllActivity();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
