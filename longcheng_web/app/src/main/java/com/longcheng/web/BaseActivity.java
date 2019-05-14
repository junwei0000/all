package com.longcheng.web;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 作者：MarkShuai on
 * 时间：2017/11/20 16:00
 * 邮箱：mark_mingshuai@163.com
 * 意图：BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 退出程序的时间间隔
     */
    private long exitTime = 0;

    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = true;

    /**
     * 是否允许全屏,此处全屏是指将状态栏显示
     **/
    private boolean onlyShowStatusBar = false;

    /**
     * 是否允许全屏,此处全屏是指将状态栏都隐藏掉
     **/
    private boolean mAllowFullScreen = false;

    /**
     * 是否禁止旋转屏幕
     **/
    private boolean isAllowScreenRoate = false;

    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BaseActivityMVP-->onCreate()");
        //是否允许全屏
        if (mAllowFullScreen) {
            //隐藏状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        //判断是否允许全屏
        if (onlyShowStatusBar) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        //是否禁止屏幕旋转
        if (!isAllowScreenRoate) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        //设置主布局
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        //加载主布局
        setContentView(mContextView);
        //Activity的管理，将Activity压入栈
        activityManager = ActivityManager.getScreenManager();
        activityManager.pushActivity(this);
        // 注册订阅者
        EventBus.getDefault().register(this);
        //判断是否设置了沉浸式效果
        if (isSetStatusBar) {
            steepStatusBar();
        }
        //绑定activity
        bind = ButterKnife.bind(this);
        //调用初始化控件的方法
        initView(mContextView);
        initDataAfter();
        setListener();
    }

    /**
     * View点击
     **/
    public abstract void onClick(View view);

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
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        Immersive.steepStatusBar(this);
    }


    /**
     * 调用此方法设置沉浸式效果，向下兼容4.4
     *
     * @param toolbar             顶部导航栏
     * @param bottomNavigationBar 底部导航
     */
    public void setOrChangeTranslucentColor(Toolbar toolbar, View bottomNavigationBar) {
        Immersive.setOrChangeTranslucentColor(toolbar, bottomNavigationBar, this.getResources().getColor(R.color.white), this);
    }

    /**
     * 调用此方法设置沉浸式效果，向下兼容4.4
     *
     * @param toolbar             顶部导航
     * @param bottomNavigationBar 底部导航
     */
    public void setOrChangeTranslucentOtherColor(Toolbar toolbar, View bottomNavigationBar, int color) {
        Immersive.setOrChangeTranslucentColorWrite(toolbar, bottomNavigationBar, color, this);
    }

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
     * [设置监听]
     */
    public abstract void setListener();

    /**
     * [初始化控件之后初始化数据]
     */
    public abstract void initDataAfter();


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
     * @param event
     * @Subscribe注解的方法，必须是公共方法，否则会报错！
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventBackground(MessageEvent event) {
        Log.i(TAG, "onMessageEventBackground(), current thread is " + Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        activityManager.popActivity(this);
        //butterknife 解绑
        bind.unbind();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

    }

    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

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

    /**
     * 描述：退出程序
     */
    protected void exitDialog() {
        defineBackPressed();
    }

    /**
     * 自定义退出应用提示
     */
    public MyDialog defineBackPressed() {
        final MyDialog selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_myexit);// 创建Dialog并设置样式主题
        selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        selectDialog.show();
        TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
        final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定


        text_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
            }
        });
        text_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                activityManager.popAllActivity();
            }
        });
        return selectDialog;
    }


    public void setOnlyShowStatusBar(boolean onlyShowStatusBar) {
        this.onlyShowStatusBar = onlyShowStatusBar;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void doFinish() {
        activityManager.popActivity(this);
        finish();
    }

    public void backs() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}
